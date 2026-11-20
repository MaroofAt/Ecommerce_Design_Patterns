package logs;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class LogsIO {
    private static final Path logs_files_dir =
            Path.of("")
                .resolve("src")
                .resolve("logs")
                .resolve("files");

    public static void write_to(String filename, String data){
        Path filePath = logs_files_dir.resolve(filename).toAbsolutePath();
        try {
            Files.writeString(filePath, data, StandardOpenOption.APPEND);
        }catch (IOException ioe){
//            ioe.printStackTrace();
            return;
        }
    }
    public static String read_from(String filename){
        Path filePath = logs_files_dir.resolve(filename);
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
    }


}
