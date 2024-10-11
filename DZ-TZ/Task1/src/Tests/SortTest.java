package Tests;
import Models.Sort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortTest {
    private Sort fileSorter;

    @Test
    public void testSortFilesWithoutCycles() throws Exception {
        // Создаем тестовые данные (без циклов)
        Map<String, List<String>> dependencies = new HashMap<>();
        dependencies.put("FileA", Arrays.asList("FileB", "FileC"));
        dependencies.put("FileB", Arrays.asList("FileD"));
        dependencies.put("FileC", Arrays.asList("FileD"));
        dependencies.put("FileD", Collections.emptyList());

        Sort sort = new Sort();
        List<String> sortedFiles = sort.sortFiles(dependencies);

        // Проверяем, что сортировка произошла корректно
        List<String> expectedOrder = Arrays.asList("FileD", "FileC", "FileB", "FileA");
        Assertions.assertEquals(expectedOrder, sortedFiles);
    }

    @Test
    public void testSortFilesWithCycle() {
        // Создаем тестовые данные с циклом
        Map<String, List<String>> dependencies = new HashMap<>();
        dependencies.put("FileA", Arrays.asList("FileB"));
        dependencies.put("FileB", Arrays.asList("FileC"));
        dependencies.put("FileC", Arrays.asList("FileA")); // Цикл

        Sort sort = new Sort();

        // Ожидаем, что метод выбросит IllegalStateException из-за цикла
        Assertions.assertThrows(IllegalStateException.class, () -> {
            sort.sortFiles(dependencies);
        });
    }

    @Test
    public void testSortFilesEmptyGraph() throws Exception {
        // Тест для пустого графа
        Map<String, List<String>> dependencies = new HashMap<>();

        Sort sort = new Sort();
        List<String> sortedFiles = sort.sortFiles(dependencies);

        // Пустая карта должна вернуть пустой список
        Assertions.assertTrue(sortedFiles.isEmpty());
    }

    @Test
    public void testSortFilesSingleNode() throws Exception {
        // Тест для одного узла без зависимостей
        Map<String, List<String>> dependencies = new HashMap<>();
        dependencies.put("FileA", Collections.emptyList());

        Sort sort = new Sort();
        List<String> sortedFiles = sort.sortFiles(dependencies);

        // Сортировка для одного файла должна вернуть его в одиночном экземпляре
        Assertions.assertEquals(Collections.singletonList("FileA"), sortedFiles);
    }

    @Test
    public void testSortFilesMultipleNodesWithoutDependencies() throws Exception {
        // Тест для нескольких узлов без зависимостей
        Map<String, List<String>> dependencies = new HashMap<>();
        dependencies.put("FileA", Collections.emptyList());
        dependencies.put("FileB", Collections.emptyList());
        dependencies.put("FileC", Collections.emptyList());

        Sort sort = new Sort();
        List<String> sortedFiles = sort.sortFiles(dependencies);

        // Все файлы независимы, поэтому порядок не имеет значения
        Assertions.assertTrue(sortedFiles.containsAll(Arrays.asList("FileA", "FileB", "FileC")));
    }
}