package Models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScanFiles {
    private final Path rootDirectory;

    public ScanFiles(Path rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public List<Path> getFiles() throws IOException {

        List<Path> textFiles = new ArrayList<>();


        Files.walk(rootDirectory)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .sorted()
                .forEach(textFiles::add);

        return textFiles;
    }


}

