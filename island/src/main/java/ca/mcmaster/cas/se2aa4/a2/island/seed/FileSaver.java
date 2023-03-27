package ca.mcmaster.cas.se2aa4.a2.island.seed;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class FileSaver {

    private static String seed;
    private static String  output;
    //private static int x;

    public static void setSeed(String s) {
        seed = s;
    }

public static int x =0;
public static void copyFile() throws Exception {

    if(seed.equals("None")) {
        x = new CmdNumber().CmdNumber();
        File sourceFile = new File("img/output.mesh");

        File distinationFile = new File("img/newOutput" + x + ".mesh");

        try {
            Files.copy(sourceFile.toPath(), distinationFile.toPath());
        } catch (Exception e) {
            //System.out.println("Error");
        }
    }
    if(!seed.equals("None"))
        employFile(Integer.parseInt(seed));
}

public static void  employFile(int x) throws Exception {

            String command = "java -jar visualizer/visualizer.jar -i img/newOutput"+x+".mesh -o img/seed.svg "; // Command to execute
            Process process = Runtime.getRuntime().exec(command); // Execute the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print the output of the command
            }
            process.waitFor(); // Wait for the command to complete
        }

    }


