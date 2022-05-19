public class ExactSearchFromScratch {

    public static int indexOf(char[] query, char[] target){
        int idxTarget= 0;
        int idxQuery= 0;

        while(idxTarget < target.length &&  idxQuery < query.length)  {
            if (query[idxQuery] == target[idxTarget])  {
                idxQuery++;
                idxTarget++;
            }
            else  {
                idxTarget= idxTarget - idxQuery + 1;
                idxQuery = 0;
            }
        }

        if (idxQuery == query.length) // found!
            return idxTarget-idxQuery;
        return -1;

    }
}
