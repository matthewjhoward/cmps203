# -- Program: hw2.py
# -- Authors: Matthew J. Howard and Alex Williamson
# -- Date: 4/26/2019
# -- Details: Implements interpreter for WHILE language, which covers arithmetic, boolean, and command expressions. 
# --          Adds a new feature for division operations (DivExp). 
# --          Test cases provided for each expression, with print statements to check if each expression is executed properly.
# --
# --          On this homework, we worked in total for 5 hours.
# --          Matt worked independently on the AST interpreter for 2 hours.
# --          Alex worked independently on the AST class implementation and test cases for 2 hours.
# --          We worked together on merging code/ideas for 1 hour.     

vals = {}
class Exp:
    def __init__(self, kind):
        self.kind = kind

class Aexp(Exp):
    def __init__(self, kind, e1, e2=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2
  
class Bexp(Exp):
    def __init__(self, kind, e1=None, e2=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2

class Comm(Exp):
    def __init__(self, kind, e1=None, e2=None, e3=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2
        self.e3 = e3

def evaluate(statement):
    if statement.kind == 'IntExp':
        return statement.e1
    elif statement.kind == 'SumExp':
        return evaluate(statement.e1) + evaluate(statement.e2)
    elif statement.kind == 'SubExp':
        return evaluate(statement.e1) - evaluate(statement.e2)
    elif statement.kind == 'MulExp':
        return evaluate(statement.e1) * evaluate(statement.e2)
    # Added division
    elif statement.kind == 'DivExp':
        return int(evaluate(statement.e1) / evaluate(statement.e2))
    elif statement.kind == 'PowExp':
        return pow(evaluate(statement.e1), evaluate(statement.e2))
    elif statement.kind == 'LocExp':
        # print(vals)
        return vals[statement.e1]
    elif statement.kind == 'TrueExp':
        return True
    elif statement.kind == 'FalseExp':
        return False
    elif statement.kind == 'EqualsExp':
        return evaluate(statement.e1) == evaluate(statement.e2)
    elif statement.kind == 'LessExp':
        return evaluate(statement.e1) < evaluate(statement.e2)
    elif statement.kind == 'NotExp':
        return not(evaluate(statement.e1))
    elif statement.kind == 'AndExp':
        return evaluate(statement.e1) and evaluate(statement.e2)
    elif statement.kind == 'OrExp':
        return evaluate(statement.e1) or evaluate(statement.e2)
    elif statement.kind == 'Skip':
            pass
    elif statement.kind == 'Set':
        vals[statement.e1] = evaluate(statement.e2)
        # print(vals)
    elif statement.kind == 'IfThenElse':
        if evaluate(statement.e1):
            evaluate(statement.e2)
        else:
            evaluate(statement.e3)
    elif statement.kind == 'Sequence':
        evaluate(statement.e1)
        evaluate(statement.e2)
    elif statement.kind == 'While':
        if evaluate(statement.e1):
            evaluate(statement.e2)
            evaluate(Comm('While', statement.e1, statement.e2))            

# Setting up the expressions
c1 = Comm('Sequence', Comm('Set', 'y', Aexp('IntExp', 10)), Comm('Set', 'x', Aexp('IntExp', 1)))    # x = 1; y = 10
a1 = Aexp('SumExp', Aexp('LocExp', 'x'), Aexp('IntExp', 2))                                         # a1 = x + 2 = 3
a2 = Aexp('SubExp', Aexp('IntExp', 1), Aexp('IntExp', 1))                                           # a2 = 1 - 1 = 0
a3 = Aexp('MulExp', Aexp('LocExp', 'y'), Aexp('LocExp', 'x'))                                       # a3 = y * x = 10
a4 = Aexp('DivExp', Aexp('LocExp', 'y'), Aexp('LocExp', 'x'))                                       # a4 = y / x = 10
a5 = Aexp('PowExp', Aexp('LocExp', 'y'), Aexp('LocExp', 'x'))                                       # a5 = y ^ x = 10
b1 = Bexp('EqualsExp', Aexp('LocExp', 'y'), Aexp('IntExp', 20))                                     # b1 = y == 10 = False
b2 = Bexp('LessExp', Aexp('LocExp', 'x'), Aexp('LocExp', 'y'))                                      # b2 = x < y = True
b3 = Bexp('TrueExp')                                                                                # b3 = True
b4 = Bexp('NotExp', Bexp('FalseExp'))                                                               # b4 = !(False) = True
b5 = Bexp('AndExp', b3, b4)                                                                         # b5 = True && True = True
b6 = Bexp('OrExp', b3, b4)                                                                          # b6 = True || True = True
c2 = Comm('Skip')                                                                                   # pass (prints None)
c3 = Comm('IfThenElse', b2, a2, a1)                                                                 # x < y, so a2  (prints None)
c4 = Comm('IfThenElse', b1, a2, a1)                                                                 # x != y, so a1 (Prints None)
c4 = Comm('Set', 'x', a1)                                                                           # x = (x+2) = 3 
c5 = Comm('While', b2, c4)                                                                          # While x<y, x = x+1 => x = 3;5;7;9;11

# Testing the expressions
evaluate(c1), print("Results of c1: ", vals), print() 
print("Results of a1: ", evaluate(a1)), print() 
print("Results of a2: ", evaluate(a2)), print()
print("Results of a3: ", evaluate(a3)), print()
print("Results of a4: ", evaluate(a4)), print()
print("Results of a5: ", evaluate(a5)), print()
print("Results of b1: ", evaluate(b1)), print()
print("Results of b2: ", evaluate(b2)), print()
print("Results of b3: ", evaluate(b3)), print()
print("Results of b4: ", evaluate(b4)), print()
print("Results of b5: ", evaluate(b5)), print()
print("Results of b6: ", evaluate(b6)), print()
print("Results of c2: ", evaluate(c2)), print() 
print("Results of c3: ", evaluate(c3)), print() 
evaluate(c4), print("Results of c4: ", vals), print() 
evaluate(c5), print("Results of c5: ", vals)  

# command = Comm('Sequence', 
#                  Comm('Set', 'x', Aexp('IntExp', 3)), 
#                  Comm('IfThenElse', 
#                      Bexp('LessExp', Aexp('LocExp', 'x'), Aexp('IntExp', 5)), 
#                      Comm('Set', 'x', Aexp('SumExp', Aexp('LocExp', 'x'), Aexp('IntExp', 1))), 
#                      Comm('Set', 'x', Aexp('SubExp', Aexp('LocExp', 'x'), Aexp('IntExp', 1))))
#                         )

# print(evaluate(command)) 
# print(vals) la