from abc import abstractmethod
vals = {}
class Exp:
    def __init__(self, kind):
        self.kind = kind

    @abstractmethod
    def eval(self):
        pass

class Aexp(Exp):
    def __init__(self, kind, e1, e2=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2

    def eval(self):
        if self.kind == 'IntExp':
            return self.e1
        elif self.kind == 'SumExp':
            return self.e1.eval() + self.e2.eval()
        elif self.kind == 'MulExp':
            return self.e1.eval() * self.e2.eval()
        elif self.kind == 'LocExp':
            # print(vals)
            return vals[self.e1]
    
class Bexp(Exp):
    def __init__(self, kind, e1=None, e2=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2

    def eval(self):
        if self.kind == 'TrueExp':
            return True
        elif self.kind == 'FalseExp':
            return False
        elif self.kind == 'EqualsExp':
            return self.e1.eval() == self.e2.eval()
        elif self.kind == 'LessExp':
            return self.e1.eval() < self.e2.eval()
        elif self.kind == 'NotExp':
            return not(self.e1.eval())
        elif self.kind == 'AndExp':
            return self.e1.eval() and self.e2.eval()
        elif self.kind == 'OrExp':
            return self.e1.eval() or self.e2.eval()

class Comm(Exp):
    def __init__(self, kind, e1=None, e2=None, e3=None):
        super().__init__(kind)
        self.e1 = e1
        self.e2 = e2
        self.e3 = e3
        # self.vals = {}

    def eval(self):
        if self.kind == 'Skip':
            pass
        elif self.kind == 'Set':
            vals[self.e1] = self.e2.eval()
            # print(vals)
        elif self.kind == 'IfThenElse':
            if self.e1.eval():
                self.e2.eval()
            else:
                self.e3.eval()
        elif self.kind == 'Sequence':
            self.e1.eval()
            self.e2.eval()
        elif self.kind == 'While':
            if self.e1.eval():
                self.e2.eval()
                Comm('While', self.e1, self.e2).eval()







# e = Aexp('MulExp', Aexp('IntExp', 5), Aexp('IntExp', 5))
i1 = Comm('Set', 'x', Aexp('IntExp', 1))
i2 = Comm('Set', 'y', Aexp('IntExp', 10))
b = Bexp('LessExp', Aexp('LocExp', 'x'), Aexp('IntExp', 3))
i3 = Comm('Set', 'x', Aexp('SumExp', Aexp('LocExp', 'x'), Aexp('IntExp', 1)))

test = Comm('While', b, i3)

i1.eval()
print(test.eval()) 

