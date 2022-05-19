public class Levenshtein {
    private static int[][] matrix;

    public static int distance(String str1, String str2){
        matrix = new int[str1.length()][str2.length()];
        matrix[0][0] = 0;
        // Arriba en horizontal va el string 2
        for(int i = 1; i < str2.length(); i++){
            matrix[0][i] = i;
        }
        // Arriba en vertical va el string 1
        for(int i = 1; i < str1.length() ; i++){
            matrix[i][0] = i;
        }
        // Recorro las filas
        for(int i = 1 ; i < str1.length(); i++){
            // Recorro las columnas
            for(int j = 1; j < str2.length(); j++){
                //Diagonal
                matrix[i][j] = Math.min(matrix[i-1][j-1] + (str1.charAt(i) == str2.charAt(j) ? 0 : 1), Math.min(matrix[i-1][j] + 1, matrix[i][j-1] + 1));
            }
        }
        return matrix[str1.length()-1][str2.length()-1];
    }
    public static double normalizedSimilarity(String str1, String str2){
        return 1.0 - distance(str1, str2) / (double) Math.max(str1.length(), str2.length());
    }
}
