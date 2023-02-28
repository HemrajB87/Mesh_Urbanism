import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;
import java.io.PrintWriter;
import java.io.IOException;

public class Main {
    private static final Option ARG_Help = new Option("h", "help", false, "User Configuration Mode");
    //Variables for custom user configuration. SET DEFAULT VALUEs HERE TO BE USED WHEN USER DOESNT ENTER ANYTHING
    public static String meshType = "grid";
    public static int numPolygons = 500;
    public static int relaxationLevel = 0;

    //print command line argument message
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out);
        pw.println();
        formatter.printUsage(pw, 100, "java -jar generator.jar test1.mesh [grid/irregular] [#_polygons] [relaxation_level]");
        formatter.printOptions(pw, 100, options, 2, 5);
        pw.close();
    }

    //Function for parsing command line inputs
    public static void main(String[] args) throws IOException {
        CommandLineParser clp = new DefaultParser();

        Options options = new Options();
        options.addOption(ARG_Help);

        test:
        try {
            CommandLine c1 = clp.parse(options, args);

            //check if -h is entered for help mode display
            if (c1.hasOption(ARG_Help.getLongOpt())) {
                printHelp(options);
            }
            //check if we use Default mode with no input args
            if (c1.getArgList().size() < 2) {
                System.out.println("Running Default Mesh Generation");
                break test;
            }

            if (c1.getArgList().size() > 4) {
                System.out.println("Error: Too many arguments. Please follow input specifications");
                printHelp(options);
                return;
            }

            //check if all required parameters exist for Custom Configuration Mode
            String gridType = "";
            int numPoly = 0;
            int relax = 0;

            try {
                gridType = c1.getArgList().get(1);
                numPoly = Integer.parseInt(c1.getArgList().get(2));
                relax = Integer.parseInt(c1.getArgList().get(3));

            } catch (Exception e) {
                System.out.println("Please follow input specifications");
            }

            //set vars from user
            meshType = gridType;
            numPolygons = numPoly;
            relaxationLevel = relax;

        } catch (Exception e) {
            printHelp(options);
            System.out.println("Please follow input specifications");
        }

        DotGen generator = new DotGen();
        generator.setVar(meshType, numPolygons, relaxationLevel);
        Mesh myMesh = generator.generate();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }
}
