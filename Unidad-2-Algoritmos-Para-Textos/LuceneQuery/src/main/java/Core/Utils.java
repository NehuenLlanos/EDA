package Core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private static Properties properties = new Properties();

    static
    {
        try  {
            // opcion 1
            properties.load(Utils.class.getResourceAsStream("/config.txt") );

            // opcion 2
            //properties.load(Utils.class.getClassLoader().getResourceAsStream("config.txt"));


            // opcion 3
            //properties.load(Class.forName("core.Utils").getResourceAsStream("/config.txt") );

        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String getPrefixDir() {
        return properties.getProperty("prefixDir");
    }



    // Ex: listFilesAbsolutePath( "/user/docs", Arrays.asList("txt", "pdf")
    // if subdir does not exist throws IOException
    public static List<String> listFilesAbsolutePath(String dir, List<String> extension) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file) && file.toString().contains("."))
                    .filter(file -> extension.contains( file.toString().substring(file.toString().lastIndexOf(".") + 1) ) )
                    .map(Path::toFile)
                    .map(f -> f.getAbsolutePath() )
                    .sorted()
                    .collect(Collectors.toList());
        }
    }


    // Ex: listFilesRelativePath("docs", Arrays.asList("txt", "pdf")
    // if subdir does not exist throws IOException
    public static List<String> listFilesRelativePath(String subdir, List<String> extension) throws IOException {
        return listFilesAbsolutePath(getPrefixDir() + "/" + subdir, extension);
    }


    public static void main(String[] args) throws IOException {
        System.out.println(Utils.getPrefixDir());

        System.out.println("Absolute Path ----");
        for (String fileName : Utils.listFilesAbsolutePath("c:/tmp", Arrays.asList("txt", "pdf"))) {
            System.out.println(fileName);
        }

        System.out.println("Relative Path ----");
        for (String fileName : Utils.listFilesRelativePath("docs", Arrays.asList("txt", "pdf"))) {
            System.out.println(fileName);
        }
    }

}
