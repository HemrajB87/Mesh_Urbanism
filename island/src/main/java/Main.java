import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.islandTypes.IslandSpecification;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.seed.FileSaver;
import ca.mcmaster.cas.se2aa4.a2.island.shape.MeshCenter;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Main {



    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        MeshCenter islandCenter = new MeshCenter(aMesh);
        Point2D.Double centerPoint = islandCenter.FindCenter();

        IslandSpecification islandSpecs = new IslandSpecification(config.mode(), config.shape(), config.altitude(), config.lakes(), config.rivers(), config.aquifers(), config.soil(), config.biomes(), config.seed(),config.city(), aMesh, centerPoint);

        new MeshFactory().write(islandSpecs.islandGenerated(), config.output());

        FileSaver.copyFile();

        }
    }
