module Splittablex

import Control.Linear.LIO
import Linear -- local version of System.Concurrency.Linear

import Sessionx

||| here we fake context-free session types using regular session types that close with a `Ret`
||| these are true borrow types where borrows of type `Ret` can simply be dropped.
data BSession : Type where
  Send : (ty : Type) -> (s : BSession) -> BSession
  Recv : (ty : Type) -> (s : BSession) -> BSession
  Ret  : BSession

||| composition of borrowed session and real session
comp : BSession -> Session -> Session
comp (Send ty s1) s2 = Send ty (comp s1 s2)
comp (Recv ty s1) s2 = Recv ty (comp s1 s2)
comp Ret s2          = s2

||| composition of two borrowed sessions
bcomp : BSession -> BSession -> BSession
bcomp (Send ty s1) s2 = Send ty (bcomp s1 s2)
bcomp (Recv ty s1) s2 = Recv ty (bcomp s1 s2)
bcomp Ret s2          = s2

||| associativity of `comp` over `bcomp`
compBcompAssoc : (s1 : BSession) -> (s2 : BSession) -> (s3 : Session) ->
   comp s1 (comp s2 s3) === comp (bcomp s1 s2) s3
compBcompAssoc (Send ty s1) s2 s3 = cong (Send ty) (compBcompAssoc s1 s2 s3)
compBcompAssoc (Recv ty s1) s2 s3 = cong (Recv ty) (compBcompAssoc s1 s2 s3)
compBcompAssoc Ret s2 s3 = Refl

||| A cell is a one-shot channel to synchronize uses of an underlying primitive channel.
||| Cells are higher-order sessions that carry (primitive) channels.
Cell : Type -> Session
Cell ty = Recv ty Wait

read : LinearIO io =>
  Channel (Cell a) -@
  L1 io a
read ch = do
  (val # ch) <- recv ch
  wait ch
  pure1 val

write :
  a -@
  Channel (Dual (Cell a)) -@
  L1 IO ()
write x ch = do
  _ <- fork1 $ do
    ch <- send ch x
    close ch
  pure1 ()

||| type of the (single) owning reference to a session
||| it only accepts operations split, close, and wait
public export
data Owner : Session -> Type where
  MkOwner : Channel (Cell (Channel s)) -@ 
            Owner s

||| type of a borrowed reference to a session
||| it accepts operations split, send, recv, and so on
public export
data Borrow : BSession -> Type where
  MkBorrow : {0 s1 : BSession} ->
           (0 s2 : Session) -> 
           Channel (Cell (Channel (comp s1 s2))) -@ 
           Channel (Dual (Cell (Channel s2))) -@ 
           Borrow s1

mkSplittable :
  Channel s ->
  L1 IO (Owner s)
mkSplittable {s} ch = do
  (cin # cout) <- makeChannel (Cell (Channel s))
  write ch cout
  pure1 (MkOwner cin)

split : LinearIO io =>
  Owner (comp s1 s2) ->
  L1 io (LPair (Borrow s1) (Owner s2))
split {s1}{s2} (MkOwner cin) = do
  (linkin # linkout) <- makeChannel (Cell (Channel s2))
  let 1 left : Borrow s1 := MkBorrow s2 cin linkout
  let 1 rght : Owner s2 := MkOwner linkin
  pure1 (left # rght)

splitB : LinearIO io =>
  Borrow (bcomp s1 s2) ->
  L1 io (LPair (Borrow s1) (Borrow s2))
splitB {s1}{s2} (MkBorrow s3 cin cout) = do
  (linkin # linkout) <- makeChannel (Cell (Channel (comp s2 s3)))
  let 1 left : Borrow s1 := MkBorrow {s1} (comp s2 s3) (rewrite compBcompAssoc s1 s2 s3 in cin) linkout
  let 1 rght : Borrow s2 := MkBorrow s3 linkin cout
  pure1 (left # rght)

sendB :
  ty ->
  Borrow (Send ty Ret) ->
  L1 IO ()
sendB x ch@(MkBorrow s cin cout) = do
  p <- read cin
  p' <- send p x
  write p' cout

recvB :
  Borrow (Recv ty Ret) ->
  L1 IO ty
recvB ch@(MkBorrow s cin cout) = do
  p <- read cin
  (x # p') <- recv p
  write p' cout
  pure1 x  

closeO : LinearIO io =>
  Owner Close ->
  L io ()
closeO ch@(MkOwner cin) = do
  p <- read cin
  close p

waitO : LinearIO io =>
  Owner Wait ->
  L io ()
waitO ch@(MkOwner cin) = do
  p <- read cin
  wait p


-- Local Variables:
-- idris-load-packages: ("linear")
-- End:
