package Models;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Concatenator {
    public  void concatenateFiles(List<Path> sortedFiles, Path outputFile1) throws IOException {

        Path outputFile = outputFile1.resolve("output.txt").toAbsolutePath();
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            for (Path file : sortedFiles) {
                if (Files.isRegularFile(file) && file.toString().endsWith(".txt")) {
                    Files.lines(file).forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            System.err.println("Ошибка записи в файл: " + e.getMessage());
                        }
                    });
                } else {
                    System.err.println("Пропускаем файл (не является текстовым): " + file.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в выходной файл: " + e.getMessage());
            throw e;
        }

        System.out.println("Все файлы успешно соединены в " + outputFile);
    }
}
