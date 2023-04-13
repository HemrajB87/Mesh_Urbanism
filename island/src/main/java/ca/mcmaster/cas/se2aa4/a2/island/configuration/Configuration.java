package ca.mcmaster.cas.se2aa4.a2.island.configuration;

import org.apache.commons.cli.*;
public class Configuration {

    public static final String OUTPUT = "o";
    public static final String INPUT = "i";
    public static final String MODE = "mode";
    public static final String SHAPE = "shape";

    public static final String ALTITUDE = "altitude";
    public static final String LAKES = "lakes";
    public static final String RIVERS = "rivers";
    public static final String AQUIFERS = "aquifers";
    public static final String SOIL = "soil";
    public static final String BIOMES = "biomes";
    public static final String SEED = "seed";

    public static final String CITY = "city";



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

    public String lakes() {
        return this.cli.getOptionValue(LAKES);
    }
    public String rivers() {
        return this.cli.getOptionValue(RIVERS);
    }
    public String aquifers() {
        return this.cli.getOptionValue(AQUIFERS);
    }
    public String soil() {
        return this.cli.getOptionValue(SOIL);
    }
    public String biomes() {
        return this.cli.getOptionValue(BIOMES);
    }
    public String seed() {
        return this.cli.getOptionValue(SEED);
    }
    public String city() {
        return this.cli.getOptionValue(CITY);
    }




    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(new Option(MODE, true, "Type of island to be created"));
        options.addOption(new Option(SHAPE, true, "Island's shape"));
        options.addOption(new Option(ALTITUDE, true, "Island's altitude"));
        options.addOption(new Option(LAKES, true, "Island # of lakes"));
        options.addOption(new Option(RIVERS, true, "Island # of rivers"));
        options.addOption(new Option(AQUIFERS, true, "Island # of aquifers"));
        options.addOption(new Option(SOIL, true, "Island soil absorption type"));
        options.addOption(new Option(BIOMES, true, "Island type of biome"));
        options.addOption(new Option(SEED, true, "Island specific seed #"));
        options.addOption(new Option(CITY, true, "Island specific city #"));

        return options;
    }


}
