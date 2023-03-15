import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.shape.MeshCenter;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileColor;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        MeshCenter creationPoint = new MeshCenter(aMesh);
        creationPoint.FindCenter();

        Circle landBound = new Circle(creationPoint.center_x, creationPoint.center_y);

        TileColor islandTiles = new TileColor(aMesh,landBound);

        Structs.Mesh updatedMesh = islandTiles.assignColor();

        new MeshFactory().write(updatedMesh, config.output());

    }
}