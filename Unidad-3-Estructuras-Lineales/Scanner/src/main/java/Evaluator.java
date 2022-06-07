import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Evaluator {
    private Stack<Double> stack = new Stack<>();
    private Scanner inputScanner;
    private Map<String, Operators> operator = new HashMap<>();
    private static Map<String, Integer> mapping = new HashMap<String, Integer>()
    {   { put("+", 0); put("-", 1); put("*", 2); put("/", 3); }
    };

    // La siguiente matriz muestra la precedencia entre el elemento actual
    // y el elemento al tope de la pila. El true me indica que el elemento que
    // se encuentra al topo de la pila tiene mayor precedencia que el actual
    private static boolean[][] precedenceMatriz=
            {   { true,  true,  false, false },
                { true,  true,  false, false },
                { true,  true,  true,  true  },
                { true,  true,  true,  true },
            };

    public Evaluator(){
        inputScanner = new Scanner(System.in).useDelimiter("\\n");
        //System.out.print("Introduzca la expresión en notación postfija: ");
        System.out.print("Introduzca la expresión en notación infija: ");
        operator.put("+", Operators.ADD);
        operator.put("-", Operators.SUBSTRACT);
        operator.put("*", Operators.PRODUCT);
        operator.put("/", Operators.DIVIDE);
    }
    private boolean getPrecedence(String tope, String current) {
        Integer topeIndex;
        Integer currentIndex;

        if ((topeIndex= mapping.get(tope))== null)
            throw new RuntimeException(String.format("tope operator %s not found", tope));

        if ((currentIndex= mapping.get(current)) == null)
            throw new RuntimeException(String.format("current operator %s not found", current));

        return precedenceMatriz[topeIndex][currentIndex];
    }

    public double evaluate(){
        double rta = 0.0;
        inputScanner.hasNextLine();
        String line = inputScanner.nextLine();
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        while(lineScanner.hasNext()) {
            String token = lineScanner.next();
            try{
                double number = Double.parseDouble(token);
                stack.push(number);
            }catch (Exception e){
                rta = operator.get(token).apply(stack.pop(), stack.pop());
                stack.push(rta);
            }
        }
        return rta;
    }

    public static void main(String[] args) {
        //String input= "2 -0.1 + 10 2 * /";
        String input= "2 + -0.1 / 10 * 2";
        InputStream inputstream= new ByteArrayInputStream(input.getBytes());
        System.setIn(inputstream);
        String rta = new Evaluator().infijaToPostfija();
        //Double rta = new Evaluator().evaluate();
        //assertEquals(0.095, rta);
        System.out.println(rta);
        System.setIn(System.in);
    }

    private boolean isOperand(String currentToken){
        return !operator.containsKey(currentToken);
    }
    private String infijaToPostfija(){
        String postfija= "";
        Stack<String> theStack= new Stack<String>();
        // InputScanner contiene el string con la cuenta. Si tiene
        // siguiente entonces entramos en el ciclo.
        while( inputScanner.hasNext() )   {
            // Guardamos el current token.
            String currentToken = inputScanner.next();
            // Si el current token es un operand entonces, debemos
            // empezar a formar el string
            if ( isOperand(currentToken) ) {
                postfija += String.format("%s ", currentToken);
            }
            // Si no es un operando, es un operador y en este caso debo chequear la
            // precedencia.
            else {
                // Hago un ciclo que tiene como objetivo chequear la precedencia.
                //      * Lo primero que chequea es que el stack no este vacio
                //      * Lo segundo que chequea es la precedencia entre el tope de pila y el current
                // Si no esta vacio y el getPrecedence da true entonces empiezo a popear todos
                // los operadores que cumplan con esa condicion.
                while (!theStack.empty()  && getPrecedence(theStack.peek(), currentToken) ) {
                    postfija += String.format("%s ", theStack.pop() );
                }
                // Cuando deja de cumplir lo previo se popean.
                theStack.push(currentToken);
            }
        }

        while ( !theStack.empty() ) {
            postfija+= String.format("%s ", theStack.pop() );
        }

        return postfija;
    }

}