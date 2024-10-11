package Models;
import java.util.*;

public class Sort {
    public List<String> sortFiles(Map<String, List<String>> dependencies) throws Exception {
        Map<String, Boolean> visited = new HashMap<>();
        List<String> sortedList = new ArrayList<>();
        Set<String> inStack = new HashSet<>();

        for (String file : dependencies.keySet()) {
            if (DFS(file, dependencies, visited, sortedList, inStack)) {
                throw new IllegalStateException("Цикл найден : " + inStack);
            }
        }

        return sortedList;

    }

    private boolean DFS(String file, Map<String, List<String>> dependencies, Map<String, Boolean> visited, List<String> sortedList, Set<String> recursionStack) {
        if (recursionStack.contains(file)) {
            return true;
        }
        if (Boolean.TRUE.equals(visited.get(file))) {
            return false;
        }

        visited.put(file, true);
        recursionStack.add(file);

        for (String dependency : dependencies.getOrDefault(file, Collections.emptyList())) {
            if (DFS(dependency, dependencies, visited, sortedList, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(file);
        sortedList.add(file);
        return false;
    }
}
