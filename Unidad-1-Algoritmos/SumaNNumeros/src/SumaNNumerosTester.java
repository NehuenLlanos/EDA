public class SumaNNumerosTester {
    public static void main(String[] args) {
        SumaNNumeros num = new SumaNNumeros();
        // 0 + 1 + 2 + 3 + 4 + 5 = 15
        System.out.println(num.sumaRec(5));
        System.out.println(num.sumaIter(5));
        // 0 + 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 = 36
        System.out.println(num.sumaRec(8));
        System.out.println(num.sumaIter(8));
    }
}
