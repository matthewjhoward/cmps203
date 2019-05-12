import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class HW4 {
    public static Map<String, Integer> vals = new HashMap<String, Integer>();


    static abstract class Exp {
        public String kind;
    
        public Exp(String kind) {
            this.kind = kind;
        }

        public String toString(){
            return "empty";
        }
    }
    
    static class Aexp extends Exp {
        public int value;
        public String var;
        public Aexp e1;
        public Aexp e2;

        public Aexp(String kind) {
            super(kind);
        }
        
        public Aexp(String kind, String var){
            super(kind);
            this.var = var;
        }

        public Aexp(String kind, int val){
            super(kind);
            this.value = val;
        }

        public Aexp(String kind, Aexp e1, Aexp e2){
            super(kind);
            this.e1 = e1;
            this.e2 = e2;
        }

        public String toString(){
            if(this.kind == "IntExp"){
                return Integer.toString(this.value);
            }
            else if (this.kind == "LocExp"){
                return (this.var);
            }else if (this.kind == "SumExp"){
                return e1.toString() + "+" + e2.toString();
            }else if (this.kind == "SubExp"){
                return e1.toString() + "-" + e2.toString();
            }else if (this.kind == "MulExp"){
                return e1.toString() + "*" + e2.toString();
            }

            return "";
        }
    }

    static class Bexp extends Exp {
        //Inputs can be either Aexp or Bexp, depending on 
        //what type of Bexp it is. 
        public Exp e1;
        public Exp e2;

        public Bexp(String kind){
            super(kind);
        }
        public Bexp(String kind, Exp e1){
            super(kind);
            this.e1 = e1;
        }

        public Bexp(String kind, Exp e1, Exp e2){
            super(kind);
            this.e1 = e1;
            this.e2 = e2;
        }

        public String toString(){
            if(this.kind == "LessExp"){
                return e1.toString() + "<" + e2.toString();
            }else if(this.kind == "TrueExp"){
                return "True";
            }else if (this.kind == "FalseExp"){
                return "False";
            }else if(this.kind == "NotExp"){
                return "not(" + e1.toString()+")";
            }else if(this.kind == "AndExp"){
                return e1.toString() + "^"+ e2.toString();
            }else if(this.kind == "OrExp"){
                return e1.toString() + "v" +e2.toString();
            }else if(this.kind == "EqualsExp"){
                return e1.toString() + "==" + e2.toString();
            }

            return "";
        }
    }

    static class Comm extends Exp {
        String var;
        public Exp e1;
        public Exp e2;
        public Exp e3;

        public Comm(String kind){
            super(kind);
        }

        public Comm(String kind, String var, Exp e1){
            super(kind);
            this.var = var;
            this.e1 = e1;
        }

        public Comm(String kind, Exp e1, Exp e2){
            super(kind);
            this.e1 = e1;
            this.e2 = e2;
        }

        public Comm(String kind, Exp e1, Exp e2, Exp e3){
            super(kind);
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
        }

        public String toString(){
            if(this.kind == "Skip"){
                return "skip";
            }else if(this.kind == "Set"){
                return this.var + ":=" + ((Aexp)e1).toString();
            }else if(this.kind == "Sequence"){
                return ((Comm)e1).toString() + "; " + ((Comm)e2).toString();
            }else if(this.kind == "IfThenElse"){
                return "if (" + e1.toString() + ") " + e2.toString() + " else " + e3.toString();
            }else if(this.kind == "While"){
                return "while (" + e1.toString() + ") " + e2.toString();
            }
            return "";
        }
    }
    
    public static void main(String[] args){

        
        
        Aexp x = new Aexp("LocExp", "x");
        Comm hw_example = new Comm("Sequence", 
            new Comm("Set", "x", new Aexp("IntExp", 3)), 
            new Comm("IfThenElse", new Bexp("LessExp", x, new Aexp("IntExp", 5)),
            new Comm("Set", "x", new Aexp("SumExp", x, new Aexp("IntExp", 1))),
            new Comm("Set", "x", new Aexp("SubExp", x, new Aexp("IntExp", 1)))));

        Comm x1 = new Comm("Set", "x", new Aexp("IntExp", 1));
        Comm x0 = new Comm("Set", "x", new Aexp("IntExp", 0));
        Comm x100 = new Comm("Set", "x", new Aexp("IntExp", 100));
        Bexp xless3 = new Bexp("LessExp", new Aexp("LocExp", "x"), new Aexp("IntExp", 3));
        Bexp xless2 = new Bexp("LessExp", new Aexp("LocExp", "x"), new Aexp("IntExp", 2)); 
        Comm xplus1 = new Comm("Set", "x", new Aexp("SumExp", new Aexp("LocExp", "x"), new Aexp("IntExp", 1)));
        Comm test = new Comm("While", xless3, xplus1);
        Comm test_while = new Comm("Sequence", x1, test);

        Comm while2 = new Comm("Sequence",
            x1, 
            new Comm("While", 
                new Bexp("NotExp", new Bexp("EqualsExp", x, new Aexp("IntExp", 32))),
                new Comm("Set", "x", new Aexp("MulExp", x, new Aexp("IntExp", 2)))));
            

        Comm if2 = new Comm("Sequence",
            x0,
            new Comm("IfThenElse", xless2,
                new Comm("While", xless2, xplus1),
                x100));

        System.out.println("--- Test Cases ---");
        System.out.println();
        System.out.println("-- Test 1: IfThenElse (from HW instructions) --");
        evaluate(hw_example);
        
        System.out.println();
        System.out.println("-- Test 2: x=1, While x<3, x+1 --");
        vals.clear();
        evaluate(test_while);

        System.out.println();
        System.out.println("-- Test 3: x=1, While not(x==32), x=2*x --");
        vals.clear();
        evaluate(while2);

        System.out.println();
        System.out.println("-- Test 4: x=0; If (x<2) then while (x<2) x=x+1 else x=100 --");
        vals.clear();
        evaluate(if2);

    }

    public static void evaluate(Exp statement){
        if(statement instanceof Aexp){
            Aexp exp = (Aexp) statement;
            evaluateA(exp);
        }
        else if(statement instanceof Bexp){
            Bexp exp = (Bexp) statement;
            evaluateB(exp);
        }
        else if (statement instanceof Comm){
            Comm exp = (Comm) statement;
            evaluateC(exp, true);
        }
    }
    public static int evaluateA(Aexp exp){
        switch(exp.kind)
        {
            case "IntExp":
                return exp.value;
            case "SumExp":
                return (evaluateA(exp.e1) + evaluateA(exp.e2));
            case "SubExp":
                return (evaluateA(exp.e1) - evaluateA(exp.e2));
            case "MulExp":
                return (evaluateA(exp.e1) * evaluateA(exp.e2));
            case "LocExp":
                return vals.get(exp.var);
        }
        return -1;
    }
    public static boolean evaluateB(Bexp exp){
        switch(exp.kind)
        {
            case "TrueExp":
                return true;
            case "FalseExp":
                return false;
            case "EqualsExp":
                return (evaluateA((Aexp)exp.e1) == evaluateA((Aexp)exp.e2));
            case "LessExp":
                return (evaluateA((Aexp)exp.e1) < evaluateA((Aexp)exp.e2));
            case "NotExp":
                return !(evaluateB((Bexp)exp.e1));
            case "AndExp":
                return (evaluateB((Bexp)exp.e1) & evaluateB((Bexp)exp.e2));
            case "OrExp":
                return (evaluateB((Bexp)exp.e1) | evaluateB((Bexp)exp.e2));

        }

        return false;
    }

    public static Comm evaluateC(Comm exp, boolean print){
        if(print){
            System.out.println("<" + exp + ", " + vals + ">" );
        }
        switch(exp.kind)
        {
            case "Skip":
                return null;
            case "Set":
                vals.put(exp.var, evaluateA((Aexp)exp.e1));
                evaluateC(new Comm("Skip"), print);
                return new Comm("Skip");
            case "IfThenElse":
                if (evaluateB((Bexp)exp.e1)){
                    evaluateC((Comm)exp.e2, true);
                }else{
                    evaluateC((Comm)exp.e3, true);
                }
                return null;
            case "Sequence":
                Comm c1 = evaluateC((Comm)exp.e1, false);
                if(c1 != null){
                    Comm c2 = new Comm("Sequence", c1, (Comm)exp.e2);
                    evaluateC(c2, true);
                }else{
                    evaluateC((Comm)exp.e2,true);
                }
                return null;
            case "While":
                if(evaluateB((Bexp)exp.e1)){
                    Comm c2 = new Comm("Sequence", exp.e2, exp);
                    evaluateC(c2, true);

                }else{
                    evaluateC(new Comm("Skip"), true);
                }
                return null;


        }
        return null;

        
    }
    
    

}






