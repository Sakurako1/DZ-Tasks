package Tests;
import Models.Sort;
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

    @org.junit.jupiter.api.Test
    @BeforeEach
    void setUp() {
        fileSorter = new Sort(); // Предположим, что наша функция находится в классе FileSorter
    }

    @Test
    void testSortFiles_successfulSort() throws Exception {
        // Arrange
        Map<Path, List<Path>> dependencies = new HashMap<>();

        Path fileA = Paths.get("A");
        Path fileB = Paths.get("B");
        Path fileC = Paths.get("C");

        // Зависимости
        dependencies.put(fileA, Arrays.asList(fileB, fileC));
        dependencies.put(fileB, Arrays.asList(fileC));
        dependencies.put(fileC, new ArrayList<>());

        // Act
        List<Path> sortedFiles = fileSorter.sortFiles(dependencies);

        // Assert
        List<Path> expectedOrder = Arrays.asList(fileC, fileB, fileA);
        assertEquals(expectedOrder, sortedFiles);
    }
    @Test
    void testSortFiles_cycleDetected() {
        // Arrange
        Map<Path, List<Path>> dependencies = new HashMap<>();

        Path fileA = Paths.get("A");
        Path fileB = Paths.get("B");
        Path fileC = Paths.get("C");

        // Зависимости с циклом
        dependencies.put(fileA, Collections.singletonList(fileB));
        dependencies.put(fileB, Collections.singletonList(fileC));
        dependencies.put(fileC, Collections.singletonList(fileA)); // Цикл

        // Act & Assert
        Exception exception = assertThrows(IOException.class, () -> fileSorter.sortFiles(dependencies));
        assertEquals("Циклы найдены", exception.getMessage());
    }
    @Test
    void testSortFiles_emptyDependencies() throws Exception {
        // Arrange
        Map<Path, List<Path>> dependencies = new HashMap<>();

        // Act
        List<Path> sortedFiles = fileSorter.sortFiles(dependencies);

        // Assert
        assertEquals(Collections.emptyList(), sortedFiles);
    }

    @Test
    void testSortFiles_noDependenciesForSomeFiles() throws Exception {
        // Arrange
        Map<Path, List<Path>> dependencies = new HashMap<>();
        Path fileA = Paths.get("A");
        Path fileB = Paths.get("B");
        // Файл B не имеет зависимых файлов
        dependencies.put(fileA, Collections.singletonList(fileB));
        dependencies.put(fileB, new ArrayList<>());
        // Act
        List<Path> sortedFiles = fileSorter.sortFiles(dependencies);
        // Assert
        List<Path> expectedOrder = Arrays.asList(fileB, fileA);
        assertEquals(expectedOrder, sortedFiles);
    }
}