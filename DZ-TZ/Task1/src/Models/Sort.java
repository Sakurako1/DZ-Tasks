package Models;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Sort {
    public List<Path> sortFiles(Map<Path, List<Path>> dependencies) throws Exception {
        List<Path> sortedFiles = new ArrayList<>();
        Set<Path> visited = new HashSet<>();
        Set<Path> stack = new HashSet<>();

        for (Path file : dependencies.keySet()) { // Инициация поиска и обработка циклической зависимости
            if (!visited.contains(file)) {
                if (DFS(file, dependencies, visited, stack, sortedFiles)) {
                    throw new IOException("Циклы найдены");
                }
            }
        }


        return sortedFiles;
    }

    private boolean DFS(Path file, Map<Path, List<Path>> dependencies, Set<Path> visited, Set<Path> inStack, List<Path> sortedFiles) {
        visited.add(file);
        inStack.add(file);

        for (Path requiredFile : dependencies.getOrDefault(file, Collections.emptyList())) {
            if (inStack.contains(requiredFile)) {
                System.out.println("Цикл найден в: " + requiredFile);  // Логика поиска циклической зависимости и первичная обработка ее
                return true; // Цикл обнаружен
            }
            if (!visited.contains(requiredFile)) {
                if (DFS(requiredFile, dependencies, visited, inStack, sortedFiles)) {
                    return true;
                }
            }
        }

        inStack.remove(file);
        sortedFiles.add(file);
        return false;
    }
}
