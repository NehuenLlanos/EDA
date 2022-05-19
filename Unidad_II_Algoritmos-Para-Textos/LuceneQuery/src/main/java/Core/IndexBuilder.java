package Core;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class IndexBuilder {
    public static void main(String[] args) throws IOException {
        createIndex();
    }

    private static void createIndex() throws IOException 	{


        IndexWriter index = initIndexWriter();

        // docs directory
        // agrego los documentos desde el source directory. solo los txt
        List<String> fileIterator = Utils.listFilesRelativePath( "docs", Arrays.asList("txt") );
        for (String docFileName : fileIterator) {

            System.out.println(docFileName);

            // Lucene doc
            Document aDoc = new Document();

            // field content se comleta con los tokens de archivo.
            // es imposible pedir a Lucene que lo almacene fuera del indice, pero si que genere el indice
            InputStream theFile = Files.newInputStream(Paths.get(docFileName));
            aDoc.add(new TextField("content",  new BufferedReader(
                    new InputStreamReader(theFile, StandardCharsets.UTF_8))));


            // el Path donde se encuentra fisicamente el documento no me interesa indexar (nadie busca por path)
            // pero lo quiero desplegar.
            FieldType aFieldPath = new FieldType();
            aFieldPath.setStored(true);
            aFieldPath.setIndexOptions(IndexOptions.NONE);
            aDoc.add(new Field("path", docFileName, aFieldPath));


            index.addDocument(aDoc);
        }

        index.close();
    }


    private static IndexWriter initIndexWriter() throws IOException {

        // target index directory
        Directory indexDir = FSDirectory.open( Paths.get(Utils.getPrefixDir() + "/index/"));

        IndexWriterConfig indexConfig = new IndexWriterConfig(new StandardAnalyzer());

        // create or overwrites an existing one
        indexConfig.setOpenMode(OpenMode.CREATE);  // other options are available

        return new IndexWriter(indexDir, indexConfig);

    }
}
