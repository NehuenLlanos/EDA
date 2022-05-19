package Core;

import org.apache.lucene.search.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;

public class TheSearcher {
    private static IndexReader getIndexReader() throws IOException {

        // target index directory
        Directory indexDir = FSDirectory.open( Paths.get(Utils.getPrefixDir() + "/index/"));

        return DirectoryReader.open( indexDir );

    }


    public static void main( String[] args ) {

        try {
            IndexReader index = getIndexReader();
            IndexSearcher searcher= new IndexSearcher(index);
            searcher.setSimilarity(new ClassicSimilarity());


            // field of interest
            String fieldName = "content"; // Nombre del campo
            String queryStr= "ham"; // Nombre de la query

            Term myTerm = new Term(fieldName, queryStr); // Busca el termino tal cual
            // PrefixQuery busca los terminos que empiezan con el string citado.
            //3.1
            //Query query= new TermQuery(myTerm );
            //3.2
            //Query query= new PrefixQuery(new Term("content", "g"));
            //3.3
            /*
                Lo que hace esto es buscar si existe alguna palabra en el documento que se encuentre en el rango dado,
                en este caso, "game" esta entre "gam" y "gum".
             */
            //Query query= new TermRangeQuery(fieldName, new BytesRef("gam"), new BytesRef("gum"), true, true);
            //3.4
            /*
                "store,," "game" no aparece porque lo que hace es buscar en la tabla. Como aparecen separadas pero
                no juntas entonces nunca las encuentra.
             */
            //Query query = new PhraseQuery(fieldName, "store", "game");
            //3.5
            /*
                Dependiendo del myTerm puedo tener diferentes salidas.
                    Si pongo "g*e" entre g y e puede haber varios caracteres o ninguno.
                    Si pongo "g?me" entre g y puede haber un caracter o ninguno
                    Si pongo "g?m" no encuentra nada.
                    Si pongo "G??e" no encuentra nada.
             */
            //Query query = new WildcardQuery(myTerm);
            //3.6
            /*
                El query utilizando Levenshtein con un maximo de 2 cambios
                    * gno no devuelve nada
                    * gem devuelve 3
                    * agem devuelve 3
                    * hm devuelve nada
                    * ham devuelve 3. Todos los game. Se sustituye g por h y ademas se agrega una e
             */
            Query query = new FuzzyQuery(myTerm);

            // run the query
            long startTime = System.currentTimeMillis();
            TopDocs topDocs = searcher.search(query, 20);
            long endTime = System.currentTimeMillis();

            // show the resultset
            System.out.println(String.format("Query=> %s\n", query));
            System.out.println(String.format("%d topDocs documents found in %d ms.\n", topDocs.totalHits,
                    (endTime - startTime) ) );

            ScoreDoc[] orderedDocs = topDocs.scoreDocs;

            int position= 1;
            System.out.println("Resultset=> \n");

            for (ScoreDoc aD : orderedDocs) {

                // print info about finding
                int docID= aD.doc;
                double score = aD.score;
                System.out.println(String.format("position=%-10d  score= %10.7f", position, score ));

                // print docID, score
                System.out.println(aD);

                // obtain ALL the stored fields
                Document aDoc = searcher.doc(docID);
                System.out.println("Store values " + aDoc);

                System.out.println(aDoc.get("path"));
                System.out.println(aDoc.get("content"));
                /*
                    Content tira null porque cuando se almacena es un Stream y los Streams no se almacenan

                 */
                //			Explanation rta = searcher.explain(query, docID);
                //            System.out.println(rta);

                position++;
                System.out.println();
            }

            index.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
