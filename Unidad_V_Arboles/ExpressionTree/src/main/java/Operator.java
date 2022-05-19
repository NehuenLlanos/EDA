import static java.lang.Math.pow;

public enum Operator {
    ADD("+"){
        public double apply(double num1, double num2){
            return num1 + num2;
        }
    },
    MULT("*"){
        public double apply(double num1, double num2){
            return num1 * num2;
        }
    },
    SUB("-"){
        public double apply(double num1, double num2){
            return num1 - num2;
        }
    },
    DIV("/"){
        public double apply(double num1, double num2){
            return num1 / num2;
        }
    },
    POW("^"){
        public double apply(double num1, double num2){
            return pow(num1, num2);
        }
    };
    String op;
    Operator(String op){
        this.op = op;
    }
    public abstract double apply(double num1, double num2);
    public String getOp(){
        return op;
    }
}
