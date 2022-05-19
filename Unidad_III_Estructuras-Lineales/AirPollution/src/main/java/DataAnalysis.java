import eda.IndexParametricService;
import eda.IndexWithDuplicates;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;

public class DataAnalysis {
    public static void main(String[] args) throws IOException {

        // leemos el archivo
        //    URL resource = DataAnalysis.class.getClassLoader().getResource("co_1980_alabama.csv");
        URL resource = DataAnalysis.class.getResource("/co_1980_alabama.csv");

        Reader in = new FileReader(resource.getFile());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        // imprimimos los registros con todos sus valores
//        for (CSVRecord record : records) {
//            String value = record.get("daily_max_8_hour_co_concentration");
//            System.out.println(String.format("%s, %s", value, record.toString()));
//
//        }
//        in.close();
        // coleccion de valores
        HashMap<Long, CSVRecord> datos= new HashMap<>();

        // indice sobre polucion o los que deseemos
        IndexParametricService<IdxRecord<Double, Long>> indexPolucion =
                new IndexWithDuplicates<>();

        for (CSVRecord record : records) {
            // insertamos en la colección y en indice
            indexPolucion.insert(new IdxRecord<Double, Long>(
                    Double.parseDouble(record.get("daily_max_8_hour_co_concentration")),
                    record.getRecordNumber()));
            datos.put(record.getRecordNumber(), record);
        }
        System.out.println(IdxRecord.getPollution28((IndexWithDuplicates<IdxRecord<Double, Long>>) indexPolucion));
        System.out.println(IdxRecord.getMinPollution((IndexWithDuplicates<IdxRecord<Double, Long>>) indexPolucion));
//        Record record = IdxRecord.getMinPollutionInfo((IndexWithDuplicates<IdxRecord<Double, Long>>) indexPolucion, datos);
//        System.out.println(String.format("%s, %s",
//                IdxRecord.getMinPollution((IndexWithDuplicates<IdxRecord<Double, Long>>) indexPolucion), record.toString()));
    }
}
