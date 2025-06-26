# Mini-Go

## Syntax

First iteration: just blocks (no functions)
Inspired by https://arxiv.org/abs/1608.08330

* `t` type
* `e` expressions
* `S` statements 

All communication on predefined channels ci with types `chan t`
(send/receive).
Expression variables are also predefined:  x1:t1 , ..., xn: tn

      t ::= bool | int | chan t

      e ::= true | false | 0 | 1 | ...
	      | x
	      | e + e | e < e | e && e | ...
		  | <- c          # receive on channel c

	  S ::= skip          # no op
	      | c <- e        # send on channel c
		  | x = e         # assignment
		  | if e then S else S 
		  | for x := e .. e do S
		  | while e do S
		  | S; S
		  | go S
		  | close c
	  
Later: functions

		  | return e
	      | select [ pi ⇒ Si ]i∈I
          | defer S

## Vorgehen

Sie können zunächst mit abstrakter Syntax starten und dann bei Bedarf einen Parser dafür schreiben.

Dann gibt es zwei Möglichkeiten. Entweder
1. Sie übersetzen diese Syntax in einen Prozess oder
2. Sie versuchen direkt Session Types für die Kanäle zu berechnen.

Meine Tendenz ist 2., weil wir damit vermeiden eine weitere Syntax für Prozesse zu definieren.

Da der Grundtyp jedes Kanals schon vorgegeben ist, muss man ihn im Typ nicht nochmal angeben, sodass folgende Syntax für die Beschreibung des Verhaltens ausreichend sein sollte:

    s ::= skip | ! | ? | end | s;s | s if e else s | for e s | while s

Zur Berechnung sollte es reichen, ein Statement S einmal zu durchlaufen.
Als Ergebnis bekommen Sie zu jeden Kanal c ein s, das die Verwendung von c im Statement s beschreibt.

Beispiele: (alle mit `c chan int`)

```
c <- 42
close c
```
Ergebnis: `c |-> !; end`

```
x = <- c
if x > 0 then c <- 2*x else skip
close c
```
Ergebnis: `c |-> ?; ! if x>0 else skip ; end`

```
i := 0
for i < 10 { c <- i; i++ }
close c
```
Ergebnis: `c |-> for (i<10) !; end`

Macht das für Sie schon Sinn oder soll ich es noch weiter spezifizieren?
Zur Kontrolle wäre es gut, wenn Sie die Berechnung der ST zuerst "auf Papier"
spezifizieren, bevor Sie da implementieren.
