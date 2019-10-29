{-# LANGUAGE FlexibleContexts #-}
{-# LANGUAGE NamedFieldPuns #-}
module Cache where

import Control.Monad.Except
import Control.Monad.Reader
import Control.Monad.State.Strict

-- Syntax: a language with pattern matching on natural numbers and single arg functions

-- exp ::= x | n | e + e | match e e x e | call f e
-- def ::= f x = e
-- prg ::= def*

type Ident = String
type FIdent = String

data Exp = Var Ident | Const Integer | Add Exp Exp | MatchN Exp Exp Ident Exp | Call FIdent Exp

data DefBody = DefBody Ident Exp
type Def = (FIdent, DefBody)

type Prg = [Def]

-- Semantics: match is pattern matching on numbers represented as n ::= 0 | S n
-- match 0 e0 xs es --> e0
-- match (S n) e0 xs es --> es [ xs := n ]

type Value = Integer
data IValue = IValue Desc Value
  deriving Show
data Desc = Arg Int | Ret
  deriving (Eq, Show)

-- The interpreter wraps values in IValue that indicates the source of the value in type Desc:
-- Arg i: suppose the function was called with argument n, then this value is obtained from the argument by i-fold pattern matching in the S-branch, that is, the value is n - i
-- Ret: value not related to the function argument

cacheBound :: Int
cacheBound = 3

-- The cache bound is statically computed from the program.
-- It can be chosen as the maximum nesting depth of matches in the successor branch.
-- Technically, it would be sufficient to use the maximum j where some (Arg j v) reaches a (recursive) function call.
-- Clearly this information can be computed from the program text

data RR = RR { env :: Ident -> Maybe IValue, prg :: Prg }
type Cache = [((FIdent, Desc), Value)]

int :: (MonadReader RR m, MonadState Cache m, MonadError String m, MonadIO m) => Exp -> m IValue
int (Var x) = do
  RR {env = env} <- ask
  -- env :: Ident -> Maybe IValue
  maybe (throwError ("unbound variable " ++ x)) return $ env x

int (Const n) = do
  return $ IValue Ret n

int (Add e1 e2) = do
  IValue d1 v1 <- int e1
  IValue d2 v2 <- int e2
  return $ IValue Ret (v1 + v2)

int (MatchN e0 ez xs es) = do
  IValue d0 v0 <- int e0
  if v0 == 0 then
    int ez
    else
    let vs = IValue ds (v0 - 1)
        ds = case d0 of
               Ret -> Ret
               Arg n -> Arg (n+1)
    in local (update xs vs) (int es)

int (Call f e0) = do
  IValue d0 v0 <- int e0
  cache <- get
  -- liftIO $ putStrLn ("lookup " ++ show (f, d0) ++ " in " ++ show cache)
  case lookup (f, d0) cache of
    Just vr -> do
      -- liftIO $ putStrLn ("cache hit " ++ show vr)
      return $ IValue Ret vr
    Nothing -> do
      RR { prg = prg } <- ask
      DefBody xf ef <- maybe (throwError ("unbound function " ++ f)) return $ lookup f prg
      let cachedown = (adjustdown cache f d0)
      -- liftIO $ putStrLn ("cache miss. cachedown = " ++ show cachedown)
      put cachedown
      ivf@(IValue _ vf) <- local (setenv [(xf, IValue (Arg 0) v0)]) $ int ef
      cache <- get
      let cache' = adjustup cache f cacheBound d0
          cache'' = case d0 of
                      Ret -> cache'
                      Arg _ -> updateCache (f, d0) vf cache'
      -- liftIO $ putStrLn ("updated cache " ++ show cache'')
      put cache''
      return ivf

-- clear unreachable cache entries
adjustdown :: Cache -> FIdent -> Desc -> Cache
-- calling on a brand new value, no relation to previous arguments
adjustdown cache f Ret = []
adjustdown cache f (Arg j) =
  [((g, Arg (gj - j)), vgj) | ((g, Arg gj), vgj) <- cache, gj >= j]

adjustup :: Cache -> FIdent -> Int -> Desc -> Cache
adjustup cache f bnd Ret = []
adjustup cache f bnd (Arg j) =
  [((g, Arg (gj + j)), vgj) | ((g, Arg gj), vgj) <- cache, gj + j <= bnd]

-- update variable environment

update :: Ident -> IValue -> RR -> RR
update xv iv rr@RR{env} = rr{ env = \x -> if x==xv then Just iv else env x }

setenv :: [(Ident, IValue)] -> RR -> RR
setenv al rr@(RR {env}) = rr{ env = \x -> lookup x al }

updateCache :: (FIdent, Desc) -> Value -> Cache -> Cache
updateCache key val cache = (key, val) : cache


-- entry point

run :: Prg -> FIdent -> Integer -> IO (Either String IValue)
run p f n =
  case lookup f p of
    Just (DefBody xf ef) ->
      evalStateT
      (runReaderT
        (runExceptT (int ef))
        (RR { env = \x -> if x==xf then Just (IValue (Arg 0) n) else Nothing,
              prg = p
            }))
      []
    Nothing ->
      return $ Left ("No function " ++ f)

-- example prg

fibprg =
  [("fib",
    DefBody "n" (MatchN
                 (Var "n")
                 (Const 1)
                 "n-1"
                 (MatchN
                  (Var "n-1")
                  (Const 1)
                  "n-2"
                  (Add
                   (Call "fib" (Var "n-2"))
                   (Call "fib" (Var "n-1"))
                  ))))]
