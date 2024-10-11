package Models;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Concatenator {
    public  void concatenateFiles(List<String> sortedFiles, Path outputFiles) throws IOException {
        Path outputFile = outputFiles.resolve("output.txt").toAbsolutePath();
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            for (String pathString : sortedFiles) {
                Path pathFile = Paths.get(pathString);
                if (Files.isRegularFile(pathFile) && pathFile.toString().endsWith(".txt")) {
                    Files.lines(pathFile).forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            System.err.println("Ошибка записи в файл: " + e.getMessage());
                        }
                    });
                } else {
                    System.err.println("Пропускаем файл (не является текстовым): " + pathFile.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в выходной файл: " + e.getMessage());
            throw e;
        }

        System.out.println("Все файлы успешно соединены в " + outputFile);
    }
}
