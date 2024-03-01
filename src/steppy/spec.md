# Steppy, a stepper for Python

## Statements and expressions

Features

* variables, assignment, expressions
* print

Eingabe(datei):

```python
celsius = 36
fahrenheit = (celsius * 9/5) + 32
print(fahrenheit)
```

Die Ausgabe ist immer ein `Block`, bestehend aus drei Teilen jeweils getrennt durch einen Strich `######`.

1. Die Variablen mit ihren aktuellen Werten.
2. Ausgaben
3. Die Statements, die noch ausgeführt werden müssen.

Dieses Format hat die Eigenschaft, dass jeder Zwischenschritt wieder ein gültiges Python-Skript ist, ausgeführt werden kann und dasselbe Ergebnis/Ausgaben liefert wie das ursprüngliche Skript.

Startzustand (1. Ausgabe)

```python
###################
###################
celsius = 36
fahrenheit = (celsius * 9/5) + 32
print(fahrenheit)
```

Erster Schritt: `36` ist ausgewertet, also Ausführung der Zuweisung:

```python
celsius = 36 
###################
###################
fahrenheit = (celsius * 9/5) + 32
print(fahrenheit)
```

Jetzt: schrittweise Auswertung von `(celsius * 9/5) + 32`.
Die Teilausdrücke werden von links nach rechts bearbeitet.
Nachschlagen des Werts von `celsius`:

```python
celsius = 36 
###################
###################
fahrenheit = (36 * 9/5) + 32
print(fahrenheit)
```

Nächster Teilausdruck `9/5` --> `1.8`

```python
celsius = 36 
###################
###################
fahrenheit = (36 * 1.8) + 32
print(fahrenheit)
```

Nächster Teilausdruck `36 * 1.8` --> `64.8`

```python
celsius = 36 
###################
###################
fahrenheit = 64.8 + 32
print(fahrenheit)
```

Nächster Teilausdruck `64.8 + 32` --> `96.8`

```python
celsius = 36 
###################
###################
fahrenheit = 96.8
print(fahrenheit)
```

Zuweisung

```python
celsius = 36 
fahrenheit = 96.8
###################
###################
print(fahrenheit)
```

Variable `fahrenheit` nachschlagen

```python
celsius = 36 
fahrenheit = 96.8
###################
###################
print(96.8)
```

Ausgabe `96.8`

```python
celsius = 36 
fahrenheit = 96.8
###################
print(96.8)
###################
```

Fertig!

## Konditional

```python
x = 3
if x>0:
    y = 1
else:
    y = 42
```

Start; nach der ersten Zuweisung

```python
x = 3
####################
####################
if x>0:
    y = 1
else:
    y = 42
```

Variable `x`

```python
x = 3
####################
####################
if 3>0:
    y = 1
else:
    y = 42
```

Ausdruck `x>0`

```python
x = 3
####################
####################
if True:
    y = 1
else:
    y = 42
```

If-True: if-Block wird ausgeführt, else-Block fällt weg

```python
x = 3
####################
####################
y = 1
```

Zuweisung

```python
x = 3
y = 1
####################
####################
```

Fertig.

## Iteration (next)

```python
xs = [1, 2]
s = 0
for x in xs:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 0
####################
####################
for x in xs:
    s = s + x
print (s)
```


```python
xs = [1, 2]
s = 0
####################
####################
for x in [1, 2]:
    s = s + x
print (s)
```

for-unroll

```python
xs = [1, 2]
s = 0
####################
####################
x = 1
s = s + x
for x in [2]:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 0
x = 1
####################
####################
s = 0 + 1
for x in [2]:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 0
x = 1
####################
####################
s = 1
for x in [2]:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 1
x = 1
####################
####################
for x in [2]:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 1
x = 1
####################
####################
x = 2
s = s + x
for x in []:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 1
x = 2
####################
####################
s = 1 + 2
for x in []:
    s = s + x
print (s)
```

```python
xs = [1, 2]
s = 3
x = 2
####################
####################
for x in []:
    s = s + x
print (s)
```

for-empty

```python
xs = [1, 2]
s = 3
x = 2
####################
####################
print (s)
```

```python
xs = [1, 2]
s = 3
x = 2
####################
####################
print (3)
```

```python
xs = [1, 2]
s = 3
x = 2
####################
print (3) 
####################
```

### continue

```python
for x in expr:
    BLOCK
```

--->

```python
__i = iter(expr)
x = next (__i)
BLOCK
for x in __i:
    BLOCK
```

but it's wrong if `BLOCK` now contains `continue` or `break`.

Alternative
--->

```python
__i = iter(expr)
__n = next(__i)
for x in [__n]:
    BLOCK
else:
    for x in __i:
	    BLOCK
```

```python
continue
:
:
for ...
```

reduces to 

```python
for ...
```


Die Intention der Regel ist, dass alle Zwischenschritte wieder ausführbare Python Skripte sind.
Vermutlich würde man's erstmal listenspezifisch machen (falls das mit Python's Semantik des Listeniterators zusammenpasst - was passiert, wenn sich die Liste im `BLOCK` ändert?):

```python
for x in [v1, v2, ..., vn]:
    BLOCK
```

--->

```python
x = v1
for _ in [None]:
    BLOCK
else:
    for x in [v2, ..., vn]:
	    BLOCK
```

* `for _ in [None]` bleibt einfach so stehen und wir führen die Statements in `BLOCK` aus
* wenn in `BLOCK` ein `continue` ausgeführt wird, geht's in den `else`-Zweig zum Rest der for-Schleife
* wenn in `BLOCK` ein `break` ausgeführt wird, dann wird `else` übersprungen und es passiert auch das Richtige.
