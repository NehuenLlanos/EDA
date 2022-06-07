public enum Operators {
    ADD("+") {
        public double apply(double a, double b) {
            return a + b;
        }
    },
    SUBSTRACT("-"){
        public double apply(double a, double b){
            return a - b;
        }
    },
    PRODUCT("*"){
        public double apply(double a, double b){
            return a * b;
        }
    },
    DIVIDE("/"){
        public double apply(double a, double b){
            return b / a;
        }
    };

    private final String symbol;
    public abstract double apply(double a, double b);
    Operators(String symbol){
        this.symbol = symbol;
    }
}
