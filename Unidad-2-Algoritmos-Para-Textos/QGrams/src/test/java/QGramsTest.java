public class QGramsTest {
    public static void main(String[] args) {
        QGrams g= new QGrams(2);  // 1, 2, 3 .etc
        g.printTokens("alale");
        g.printTokens("salesal");
        //   #a al la al le e#
        // #s sa al le es sa al l#
        //Comparten = al al le = 3
        //No comparten = #a la e# #s sa es sa l# = 8
        System.out.println(g.similarity("alale", "salesal"));

    }
}
