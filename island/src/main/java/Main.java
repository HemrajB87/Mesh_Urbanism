import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.islandTypes.IslandSpecification;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.shape.MeshCenter;

import java.awt.geom.Point2D;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        MeshCenter islandCenter = new MeshCenter(aMesh);
        Point2D.Double centerPoint = islandCenter.FindCenter();

        IslandSpecification islandSpecs = new IslandSpecification(config.mode(), config.shape(),aMesh, centerPoint);

        new MeshFactory().write(islandSpecs.islandGenerated(), config.output());
    }
}