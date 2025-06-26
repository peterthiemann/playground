# Tactical programming

Following the "construction principles" of "Your Program" we propose the following formal language.
For simplicity, the syntax is based on yaml for now.
The example corresponds to the example from page 40, but adapted to the target language Python.

Each tactical command corresponds to some element in the output file (a Python program).
Executing the tactic works on an internal model, a program fragment, which is initially empty.

## Description

```
description: Calculate the monthly bill for cheap energy
```

Output: A comment with this description.

## Signature

```
signature:
  cheap_energy : int -> float
```

Internally: Construct a program fragment with two holes

```
def cheap_energy ([0] : int) -> float:
    [1]
	return [2]
```

* hole `[0]` is a variable hole to be filled with a variable name
* hole `[1]` is a statement hole to be filled with statements
* hole `[2]` is an expression hole

This fragment could be printed or shown in a separate pane when the `signature` tactic is executed.

## Testing

```
testing:
  test: cheap_energy (0)
  should-be: 4.9

  test: cheap_energy (10)
  should-be: 6.8

  test: cheap_energy (20)
  should-be: 8.7

  test: cheap_energy (30)
  should-be: 10.6
```

Output: a Python function for each test in the format accepted by `pytest`.

```
def test_1():
      assert math.isclose(cheap_energy(0), 4.9)
def test_2():
      assert math.isclose(cheap_energy(10), 6.8)
```

... and so on. (This example is already special in that is uses `math.isclose` to check for equality because the return type is `float`.
For most other types, the comparison would be done with `==`.)

## Skeleton

```
intro: kwh
```

Internally: Modify the program fragment by filling in the next available variable placeholder.
Result:

```
def cheap_energy (kwh : int) -> float:
    [1]
	return [2]
```

## Body (1)

In the body we can use different tactics to fill the holes.
The focus is automatically on the first hole and the tactic determines, which type of hole is addressed.

```
assume: base_price = 4.9
```

This tactic works on the (first) statement hole. It inserts the assumption as an assignment (in the internal model).
Result:

```
def cheap_energy (kwh : int) -> float:
    base_price = 4.9
    [1]
	return [2]
```

## Body (2)

```
assume: work_price = 0.19 * kwh
```

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price = 4.9
    work_price = 0.19 * kwh
    [1]
	return [2]
```

## Body (3)

```
return: base_price + work_price
```

Addresses the next available expression hole.

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price = 4.9
    work_price = 0.19 * kwh
    [1]
	return base_price + work_price
```

## Body (4)

```
finish:
```

Closes all remaining statement holes. 
Only works if all expression holes are filled.

Result and output:

```
def cheap_energy (kwh : int) -> float:
    base_price = 4.9
    work_price = 0.19 * kwh
	return base_price + work_price
```

## Final result

The finish tactic flushes the internal model and generates an output file.
In this case, the file should look like this.

```
# Calculate the monthly bill for cheap energy
def cheap_energy (kwh : int) -> float:
    base_price = 4.9
    work_price = 0.19 * kwh
	return base_price + work_price

def test_1():
      assert math.isclose(cheap_energy(0), 4.9)
def test_2():
      assert math.isclose(cheap_energy(10), 6.8)
```

## Collected tactic input

The complete input developed by the programmer:

```
description: Calculate the monthly bill for cheap energy

signature:
  cheap_energy : int -> float

testing:
  test: cheap_energy (0)
  should-be: 4.9

  test: cheap_energy (10)
  should-be: 6.8

  test: cheap_energy (20)
  should-be: 8.7

  test: cheap_energy (30)
  should-be: 10.6

intro: kwh
assume: base_price = 4.9
assume: work_price = 0.19 * kwh
return: base_price + work_price
finish:
```

## Afterthoughts

* Each expression hole has a type
* Hole filling operations only work with existing entities (e.g., variables) and require type compatibility

I think it would be better to introduce local variables with intro.

```
intro: 
	base_price: float
	work_price: float
```

Internal

```
def cheap_energy (kwh : int) -> float:
    base_price : float = [1]
	work_price : float = [2]
    [3]
	return [4]
```

Command

```
constant: 4.9
```

(Error if the constant does not fit the expected type.)


Result:

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = [1]
    [2]
	return [3]
```

Command

```
binop: *
```

(Error if `*` does not exist or if its return type is not `float`.)

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = [1] * [2]
	[3]
	return [4]
```

Command

```
constant: 0.19
```

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = 0.19 * [1]
	[2]
	return [3]
```

Command

```
use: kwh
```

(Error if the variable does not exist or if its type does not fit the type of the hole.)

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = 0.19 * kwh
	[1]
	return [2]
```

Command

```
binop: +
```

(Operators on next available expression hole)

Result:

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = 0.19 * kwh
	[1]
	return [2] + [3]
```

Command

```
use: base_price
use: work_price
```

Result

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = 0.19 * kwh
	[1]
	return base_price + work_price
```

Command

```
finish:
```

Result

```
def cheap_energy (kwh : int) -> float:
    base_price : float = 4.9
	work_price : float = 0.19 * kwh
	return base_price + work_price
```
