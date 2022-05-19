public class LevenshteinDistanceTest {

    public static void main(String[] args) {
        int dist= Levenshtein.distance("big data", "bigdaa");		// debería devolver el entero 2
        System.out.println(dist);
        double simil= Levenshtein.normalizedSimilarity("big data", "bigdaa"); //0.75
        System.out.println(simil);
    }


}
