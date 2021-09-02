{-# LANGUAGE GADTs #-}
module Main where

-- Task A: Given
data Type t where
  Int    :: Type Int
  Bool   :: Type Bool
  (:><:) :: Type a -> Type b -> Type (a, b)
  (:->:) :: Type a -> Type b -> Type (a -> b)

-- write a function
generate :: Type t -> t
-- such that `generate ty` returns a value of that type.

-- Answer:
generate Int = 42
generate Bool = True
generate (s :><: t) = (generate s, generate t)
generate (s :->: t) = \s -> generate t


-- Task B: Consider -- typed predicate logic:
-- P, Q ::= forall x:t. P | neg P | P v Q | E = F
-- encoded in the following datatype
data Pred env where
  Forall :: Bounded t => Type t -> Pred (t, env) -> Pred env
  Not    :: Pred env -> Pred env
  Or     :: Pred env -> Pred env -> Pred env
  Eq     :: Eq t => (env -> t) -> (env -> t) -> Pred env
  Le     :: Ord t => (env -> t) -> (env -> t) -> Pred env

-- where `env` is an environment containing variable values. The constructor `Eq` represents an equation between values of type `t` which must therefore satisfy `Eq t`. The left and right hand side of an equation is given by functions, which map the environment to a value of type `t`. The constructor `Le` is analogous for the less-than-or-equal relation.

-- Here are some example predicates:

-- forall x:Bool. x = True
p0 = Forall Bool (Eq fst (const True))
-- forall x:Bool. forall y:Bool. x=y or not (x=y)
p1 = Forall Bool (Forall Bool (Or (Eq fst (fst.snd)) (Not (Eq fst (fst.snd)))))
-- forall x:Int. not (x = 0)
p2 = Forall Int  (Not (Eq fst (const 0)))
-- forall x:Int. (x = 0)
p3 = Forall Int  (Eq fst (const 0))

-- Implement an evaluation function 
eval :: Pred env -> env -> Bool
-- for typed predicate logic in this encoding.

-- Answer:
eval (Eq f g) env = f env == g env
eval (Le f g) env = f env <= g env
eval (Or p q) env = eval p env || eval q env
eval (Not p)  env = not (eval p env)
eval (Forall ty p) env = and (map (\t -> eval p (t, env)) [minBound, maxBound])

-- Encode the predicate
-- forall i:Int, forall j:Int, i <= j v j <= i

main :: IO ()
main = do
  putStrLn ("p0 = " ++ show (eval p0 ()))
  putStrLn ("p1 = " ++ show (eval p1 ()))
  putStrLn ("p2 = " ++ show (eval p2 ()))
  putStrLn ("p3 = " ++ show (eval p3 ()))

