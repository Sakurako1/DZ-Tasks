import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import Models.Sort;
import Models.CheckDependency;
import Models.ScanFiles;
import Models.Concatenator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите директорию с файлами для обработки:");
        Path rootDirectory = Paths.get(input.nextLine());
        System.out.println("Введите директорию для сохранения файла:");
        Path outputFile = Paths.get(input.nextLine());

        try {

            ScanFiles scanner = new ScanFiles(rootDirectory);
            List<Path> textFiles = scanner.getFiles();


            CheckDependency parser = new CheckDependency();
            Map<Path, List<Path>> dependencies = parser.parseDependencies(textFiles, rootDirectory);


            Sort sorter = new Sort();
            List<Path> sortedFiles = sorter.sortFiles(dependencies);


            Concatenator concatenator = new Concatenator();
            concatenator.concatenateFiles(sortedFiles, outputFile);

            System.out.println("Файлы соединены " + outputFile);
            System.out.println("Файлы отсортированы:");

            List<Path> filteredFiles = sortedFiles.stream()
                       .filter(path -> path.toString().endsWith(".txt"))
                       .collect(Collectors.toList());
            for (Path file : filteredFiles){
                System.out.println(file);

            }



        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.out.println("Прерывание работы..");
        }
    }
}
