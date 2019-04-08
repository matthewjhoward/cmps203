-- Author: Matthew J. Howard
-- Date: 4/7/2019
-- Details: Implements basic interpreter for ARITH language. Handles basic sums (SumExpr), multiplication (MulExpr) and adds a new feature for power operations (PowExpr). 3 test cases provided for each expression provided via Test.QuickCheck module.

import Test.QuickCheck

--ARITH AST IMPLEMENTATION
data Expr = IntExpr Int 
        | SumExpr Expr Expr 
        | MulExpr Expr Expr
        | PowExpr Expr Expr

--AST INTERPRETER
eval :: Expr -> Int
eval (IntExpr n) = n
eval (SumExpr e1 e2) = (eval e1) + (eval e2)
eval (MulExpr e1 e2) = (eval e1) * (eval e2)
eval (PowExpr e1 e2) = (eval e1) ^ (eval e2)

--TEST CASES
intTest1 = IntExpr 5 -- 5
intTest2 = IntExpr (-1) -- (-1)
intTest3 = IntExpr 0 -- 5

sumTest1 = SumExpr (IntExpr 1) (IntExpr 5) -- 1+5 = 6
sumTest2 = SumExpr (IntExpr 1) (IntExpr (-1)) -- 1+(-1) = 0
sumTest3 = SumExpr (IntExpr 1000) (SumExpr (IntExpr 1000) (IntExpr 1)) --1000 + (1000+1)=2001

mulTest1 = MulExpr (IntExpr 5) (IntExpr 6) -- 5*6=30
mulTest2 = MulExpr (MulExpr (IntExpr 3) (IntExpr 100)) (SumExpr (IntExpr 4) (IntExpr 9)) -- (3*100)*(4+9)=3900
mulTest3 = MulExpr (MulExpr (IntExpr 0) (IntExpr 5)) (IntExpr 10) -- (0*5)*10=0

powTest1 = PowExpr (IntExpr 2) (IntExpr 2) -- 2^2 = 4
powTest2 = PowExpr (SumExpr (IntExpr 1) (IntExpr 1)) (SumExpr (IntExpr 1) (IntExpr 1)) -- (1+1)^(1+1)=4
powTest3 = PowExpr (IntExpr 0) (IntExpr 0) -- 0^0 = 1

main :: IO ()  
main = do

    quickCheck( eval(intTest1) == 5)
    quickCheck( eval(intTest2) == (-1))
    quickCheck( eval(intTest3) == 0)

    quickCheck( eval(sumTest1) == 6)
    quickCheck( eval(sumTest2) == 0)
    quickCheck( eval(sumTest3) == 2001)

    quickCheck( eval(mulTest1) == 30)
    quickCheck( eval(mulTest2) == 3900)
    quickCheck( eval(mulTest3) == 0)

    quickCheck( eval(powTest1) == 4)
    quickCheck( eval(powTest2) == 4)
    quickCheck( eval(powTest3) == 1)

