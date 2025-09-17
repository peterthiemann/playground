# Equivalence of session processes

We want to test if one session type is (equivalent to) the dual of another session type.

1. Define function `dual` on session types. It flips the direction of all communications, i.e., `c!` -> `c?` and `c?` -> `c!`
2. Define equivalence of session types as a relation _~_.

Recall the definition of ST.

      s ::= new c. s | skip | c! | c? | c# | s;s | s if (e) else s | for (e) s | go s s

Will write `if (e) {s1}{s2}` instead of `s1 if (e) else s2`

For now, 

* we assume that all channel are synchronous, i.e., have buffer size 0.
* we ignore `go`
* we assume we work with existing channels, so ignore `new`

Equivalence

* atom-end: c# ~ c#
* atom-send: c! ~ c!
* atom-recv: c? ~ c?

* trans: s1 ~ s2 /\ s2 ~ s3 => s1 ~ s3

* id-left:  skip; s ~ s
* id-right: s; skip ~ s
* id-for: for (e) skip ~ skip
* [ id-go: go s skip ~ s ]

* assoc: (s1 ; s2) ; s3 ~ s1 ; (s2 ; s3)
* cond-eta: if (e) {s}{s} ~ s 
* cond-dist: if (e){s1}{ s2} ; s ~ if (e) {s1 ; s} {s2; s}

* comp: s1 ~ s2 /\ s3 ~ s4 => s1; s3 ~ s2; s4
* cond: s1 ~ s2 /\ s3 ~ s4 => if (e) {s1}{ s3} ~ if (e) {s2}{ s4}

## Conditionals

For further simplification, we have to *evaluate* conditions of conditional STs.
To this end, all conditions have to be phrased in terms of the parameters.
That is, each assignment must be modeled by a substitution when *creating* the ST.

Example:
Suppose we have one function with ST `if(x){!c}{?c}; c#` and we want to test it against this function

```
int chan c
int x

int y := x
int r := 0
if (y) {
   c <- y
} else {
   r = <- c
}
close c
```

Naively, we would get `if(y){!c}{?c}; c#`. But `y` is not a parameter variable, so the type would be useless for external comparisons.
Hence, processing of `int y := x` remembers the substitution `y |-> x` and this substitution is applied to the condition `if(y)...`
when creating the session type. That is, the session type is created as `if(x)...`

Another example:
```
int y := 2*x
int z := x + y
```

leads to the substitution `y |- 2*x` and `z |- x + (2 * x)` (because we applied the current substitution to `x + y` before extending the substitution)

## Equivalence with conditionals

(everything above still holds)

We add a predicate `C` as the current assumption to the equivalence relation leading to `C |- s1 ~ s2` = given predicate `C` on the variables in `s1` and `s2` we can prove that
`s1` and `s2` are equivalent. Initially, `C = true`.

The interesting part happens, when we process a conditional.
If `e` is true or false, we select the corresponding branch of the conditional.
Technically, this decision is implemented by calling an SMT solver (with `C /\ ~e` or `C /\ e`, respectively).

	C |- e
	------------------
	C |- if(e){s1}{s2} ~ s1

	not (C |- e)
	------------------
	C |- if(e){s1}{s2} ~ s2

If we cannot decide `C |- e`, we add `e` or `~e` to the assumption:

	C /\ e |- s1 ~ s
	C /\ ~e |- s2 ~ s
	----------------
	C |- if(e){s1}{s2} ~ s

We also have the symmetric rule:

	C /\ e |- s ~ s1
	C /\ ~e |- s ~ s2
	----------------
	C |- s ~ if(e){s1}{s2}

We could also get rid of the first two rules by setting

    C inconsistent
	--------------
	C |- s1 ~ s2

But it might be more efficient to have the "decision" rules.
