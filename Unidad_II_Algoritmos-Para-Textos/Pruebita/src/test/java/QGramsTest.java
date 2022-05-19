public class QGramsTest {
    public static void main(String[] args) {
        QGrams g= new QGrams(2);  // 1, 2, 3 .etc
        g.printTokens("alal");
        g.printTokens("salesal");
        //   #a  1
        //   al   2
        //   la   1
        //   l#   1

    }
}
