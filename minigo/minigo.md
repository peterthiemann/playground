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
		  | for e S
		  | S; S
		  | go S
		  | close c
	  
Later: functions

		  | return e
	      | select [ pi ⇒ Si ]i∈I
          | defer S

