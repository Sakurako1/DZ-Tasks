package Models;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckDependency {
    public Map<Path, List<Path>> parseDependencies(List<Path> textFiles, Path rootDirectory) throws IOException {
        Map<Path, List<Path>> dependencies = new HashMap<>();

        for (Path file : textFiles) {
            List<Path> requiredFiles = new ArrayList<>();
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                if (line.startsWith("require")) {
                    String requiredPath = extractPath(line);
                    Path requiredFile = Paths.get(rootDirectory.toString() + "\\" + requiredPath);
                    if (rootDirectory.toString().endsWith("\\") ^ requiredPath.startsWith("\\")){
                        requiredFile = Paths.get(rootDirectory + requiredPath);
                    }
                    else if (rootDirectory.toString().endsWith("\\") && requiredPath.startsWith("\\")) {
                        requiredFile = Paths.get(rootDirectory.toString().substring(0,rootDirectory.toString().length()-1) + requiredPath);
                    }

                    requiredFiles.add(requiredFile);
                }
            }
            dependencies.put(file, requiredFiles);
        }

        return dependencies;
    }

    private String extractPath(String line) {
        return line.split("‘")[1].split("’")[0];
    }
}
