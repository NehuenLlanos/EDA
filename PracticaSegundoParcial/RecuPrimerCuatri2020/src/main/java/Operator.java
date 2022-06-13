import static java.lang.Math.pow;

public enum Operator {
    AND("&&"){
        public boolean apply(boolean exp1, boolean exp2){
            if(exp1 && exp2){
                return true;
            }
            return false;
        }
    },
    OR("||"){
        public boolean apply(boolean exp1, boolean exp2){
            if(!exp1 && !exp2){
                return false;
            }
            return true;
        }
    },
    IMP("=>"){
        public boolean apply(boolean exp1, boolean exp2){
            if(exp1 && !exp2){
                return false;
            }
            return true;
        }
    };
    String op;
    Operator(String op){
        this.op = op;
    }
    public abstract boolean apply(boolean exp1, boolean exp2);
    public String getOp(){
        return op;
    }
}
