package Models;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckDependency {
    public Map<String, List<String>> parseDependencies(List<Path> textFiles, Path rootDirectory) throws IOException {
        Map<String, List<String>> dependencies = new HashMap<>();
        for (Path file : textFiles) {
            List<String> requiredFiles = new ArrayList<>();
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("require")) {
                        String requiredFile = extractPath(line);
                        requiredFiles.add(rootDirectory.resolve(requiredFile).toString());
                    }
                }
            }
            dependencies.put(file.toString(), requiredFiles);
        }

        return dependencies;
    }

    private String extractPath(String line) {
        return line.split("‘")[1].split("’")[0];
    }
}
