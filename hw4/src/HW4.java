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
    }

    static class Bexp extends Exp {
        //Inputs can be either Aexp or Bexp, depending on 
        //what type of Bexp it is. 
        public Exp e1;
        public Exp e2;

        public Bexp(String kind){
            super(kind);
        }

        public Bexp(String kind, Exp e1, Exp e2){
            super(kind);
            this.e1 = e1;
            this.e2 = e2;
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
    }

    

    
    public static void main(String[] args){
        // System.out.println("hello, world");
        // Aexp i10 = new Aexp("IntExp", 10);
        // Aexp i5 = new Aexp("IntExp", 5);
        // Aexp mul510 = new Aexp("MulExp", i5, i10);
        Comm i1 = new Comm("Set", "x", new Aexp("IntExp", 1));

        
        Bexp b = new Bexp("LessExp", new Aexp("LocExp", "x"), new Aexp("IntExp", 3));
        Comm i3 = new Comm("Set", "x", new Aexp("SumExp", new Aexp("LocExp", "x"), new Aexp("IntExp", 1)));
        Comm test = new Comm("While", b, i3);
        
        // evaluate(i1);
        // System.out.println(Arrays.asList(vals));
        // evaluate(test);
        // System.out.println(Arrays.asList(vals));

        Comm iftest = new Comm("IfThenElse", new Bexp("FalseExp"), new Comm("Set", "x", new Aexp("IntExp", 20)), new Comm("Set", "x", new Aexp("IntExp", -10)));
        evaluate(iftest);
        System.out.println(Arrays.asList(vals));
    }

    public static void evaluate(Exp statement){
        if(statement instanceof Aexp){
            // Aexp new_statement = (Aexp) statement;
            // System.out.println(new_statement.value);
            Aexp exp = (Aexp) statement;
            int out = evaluateA(exp);
            System.out.println(out);
        }
        else if(statement instanceof Bexp){
            Bexp exp = (Bexp) statement;
            evaluateB(exp);
        }
        else if (statement instanceof Comm){
            Comm exp = (Comm) statement;
            evaluateC(exp);
        }
    }
    public static int evaluateA(Aexp exp){
        switch(exp.kind)
        {
            case "IntExp":
                return exp.value;
            case "SumExp":
                return (evaluateA(exp.e1) + evaluateA(exp.e2));
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

    public static void evaluateC(Comm exp){
        switch(exp.kind)
        {
            case "Skip":
                break;
            case "Set":
                vals.put(exp.var, evaluateA((Aexp)exp.e1));
                break;
            case "IfThenElse":
                if (evaluateB((Bexp)exp.e1)){
                    evaluate(exp.e2);
                }else{
                    evaluate(exp.e3);
                }
                break;
            case "Sequence":
                evaluate(exp.e1);
                evaluate(exp.e2);
                break;
            case "While":
                if(evaluateB((Bexp)exp.e1)){
                    evaluate(exp.e2);
                    evaluate(exp);
                }
                break;

        }
        
    }
    
    

}






