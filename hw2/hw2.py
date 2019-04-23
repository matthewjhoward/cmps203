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
    elif statement.kind == 'MulExp':
        return evaluate(statement.e1) * evaluate(statement.e2)
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
            



# e = Aexp('MulExp', Aexp('IntExp', 5), Aexp('IntExp', 5))
i1 = Comm('Set', 'x', Aexp('IntExp', 1))
i2 = Comm('Set', 'y', Aexp('IntExp', 10))
b = Bexp('LessExp', Aexp('LocExp', 'x'), Aexp('IntExp', 3))
i3 = Comm('Set', 'x', Aexp('SumExp', Aexp('LocExp', 'x'), Aexp('IntExp', 1)))

test = Comm('While', b, i3)

evaluate(i1)
print(evaluate(test)) 
print(vals)
