package ca.mcmaster.cas.se2aa4.a2.island.seed;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

public class CmdNumber {

    public int CmdNumber() throws IOException {
        Path folderPath = Paths.get("img");
        if (!Files.exists(folderPath)) {
            System.out.println("Folder not found: img");

        }
        File folder = folderPath.toFile();
        int count = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().startsWith("new")) {
                count++;
            }
        }

        return count;
    }
}
