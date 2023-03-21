package ca.mcmaster.cas.se2aa4.a2.island.configuration;

import org.apache.commons.cli.*;
public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String MODE = "mode";
    public static final String SHAPE = "shape";

    public static final String ALTITUDE = "altitude";


    private CommandLine cli;
    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.mesh");
    }
    public String mode() {
        return this.cli.getOptionValue(MODE);
    }
    public String shape() {
        return this.cli.getOptionValue(SHAPE);
    }

    public String altitude() {
        return this.cli.getOptionValue(ALTITUDE);
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(MODE, true, "Type of island to be created"));
        options.addOption(new Option(SHAPE, true, "Island's shape"));
        options.addOption(new Option(ALTITUDE, true, "Island's altitude"));
        return options;
    }


}
