import Models.CheckDependency;
import Models.Concatenator;
import Models.ScanFiles;
import Models.Sort;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите директорию с файлами для обработки:");
        Path rootDirectory = Paths.get(input.nextLine());
        System.out.println("Введите директорию для сохранения файла:");
        Path outputFile = Paths.get(input.nextLine());
        try {

            ScanFiles scanner = new ScanFiles(rootDirectory);
            int x = 0;
            List<Path> textFiles = scanner.getFiles();
            System.out.println("Файлы найден и отсортированы по имени: " + textFiles);

            CheckDependency parser = new CheckDependency();
            Map<String, List<String>> dependencies = parser.parseDependencies(textFiles, rootDirectory);

            Map<String, List<String>> forPrint = sortByValueCount(dependencies);
            System.out.println("Файлы отсортированы");
            for (String key : forPrint.keySet()) {
                String formattedPath = formatPath(key);
                System.out.println(formattedPath);
            }

            Sort sorter = new Sort();
            List <String> sortedFiles = sorter.sortFiles(dependencies);


            Concatenator concatenator = new Concatenator();
            concatenator.concatenateFiles(sortedFiles, outputFile);

            System.out.println("Файлы соединены " + outputFile);

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.out.println("Прерывание работы..");
        }
    }
    public static Map<String, List<String>> sortByValueCount(Map<String, List<String>> dependencies) {
        return dependencies.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry1.getValue().size(), entry2.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public static String formatPath(String path) {
        // Определяем символ разделителя для ОС
        String separator = "/";
        if (path.contains("\\")) {
            separator = "\\";
        }
        // Разделяем строку на компоненты папки и файла
        int lastSeparatorIndex = path.lastIndexOf(separator);
        String folder = (lastSeparatorIndex != -1) ? path.substring(0, lastSeparatorIndex) : "";
        String fileWithExtension = (lastSeparatorIndex != -1) ? path.substring(lastSeparatorIndex + 1) : path;

        // Проверяем, что файл заканчивается на ".txt"
        if (!fileWithExtension.endsWith(".txt")) {
            return null; // возвращаем null, если файл не .txt
        }
        // Убираем расширение из имени файла
        int dotIndex = fileWithExtension.lastIndexOf('.');
        String fileName = (dotIndex != -1) ? fileWithExtension.substring(0, dotIndex) : fileWithExtension;
        // Возвращаем формат "имя папки \ имя файла"
        return folder + " \\ " + fileName;
    }

}

