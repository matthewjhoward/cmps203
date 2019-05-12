package hw4

public abstract class Exp {
    public Exp(String kind) {
        this.kind = kind;
    }

    public abstract Exp eval();

    public static Map<String, Integer> vals = new HashMap<String, Integer>(); //needs to be some sort of object template? or just string, int
}

public class Aexp extends Exp {
    public Aexp(String kind, Exp e1) {
        super(kind);
        this.e1 = e1;
    }

    public Aexp(String kind, Exp e1, Exp e2) {
        super(kind);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Exp eval() {
        if(this.kind == "IntExp") {
            return this.e1;
        }
        else if(this.kind == "SumExp") {
            return this.e1.eval() + this.e2.eval();
        }
        else if(this.kind == "MulExp") {
            return this.e1.eval() * this.e2.eval();
        }
        else if(this.kind == "LocExp") {
            return vals.get(this.e1);
        }
    }
}

public class Bexp extends Exp {
    public Bexp(String kind) {
        super(kind);
    }

    public Bexp(String kind, Exp e1) {
        super(kind);
        this.e1 = e1;
    }

    public Bexp(String kind, Exp e1, Exp e2) {
        super(kind);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Exp eval() {
        if(this.kind == "TrueExp") {
            return true;
        }
        else if(this.kind == "FalseExp") {
            return false;
        }
        else if(this.kind == "EqualsExp") {
            return this.e1.eval() == this.e2.eval();
        }
        else if(this.kind == "LessExp") {
            return this.e1.eval() < this.e2.eval();
        }
        else if(this.kind == "NotExp") {
            return !(this.e1.eval());
        }
        else if(this.kind == "AndExp") {
            return this.e1.eval() && this.e2.eval();
        }
        else if(this.kind == "OrExp") {
            return this.e1.eval() || this.e2.eval();
        }
    }
}

public class Comm extends Exp {
    public Comm(String kind) {
        super(kind);
    }

    public Comm(String kind, Exp e1) {
        super(kind);
        this.e1 = e1;
    }

    public Comm(String kind, Exp e1, Exp e2) {
        super(kind);
        this.e1 = e1;
        this.e2 = e2;
    }

    public Comm(String kind, Exp e1, Exp e2, Exp e3) {
        super(kind);
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    @Override
    public Exp eval() {
        if(this.kind == "Skip") {
            continue;
        }
        else if(this.kind == "Set") {
            vals.put(this.e1, this.e2.eval());
        }
        else if(this.kind == "IfThenElse") {
            if(this.e1.eval()) {
                this.e2.eval();
            }
            else {
                this.e3.eval();
            }
        }
        else if(this.kind == "Sequence") {
            this.e1.eval();
            this.e2.eval();
        }
        else if(this.kind == "While") {
            if(this.e1.eval()) {
                self.e2.eval();
                Comm comm = new Comm("While", this.e1, this.e2);
                comm.eval();
            }
        }
    }
}