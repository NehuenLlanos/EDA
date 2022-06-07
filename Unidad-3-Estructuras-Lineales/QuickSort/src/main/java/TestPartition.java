public class TestPartition {
    public static void main(String[] args) {
        int[] array = new int[]{15,19,2,8,23,4,10};
        int pos = ArraysUtilities.partition(array, 0, 5, 10);
        System.out.println(pos);
        System.out.println(array);
    }
}
