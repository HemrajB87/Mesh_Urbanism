import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.islandTypes.LagoonIsland;
import ca.mcmaster.cas.se2aa4.a2.island.shape.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.shape.MeshCenter;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());

        MeshCenter islandCenter = new MeshCenter(aMesh);
        islandCenter.FindCenter();

        //if -mode is equal to "lagoon" then we create a lagoon island
        //for now we are just using an if statement to determine if a lagoon island should be made

        if(config.mode() == null){
            new MeshFactory().write(aMesh, config.output());
        }

        //if the mode is not lagoon then we just return the mesh that was passed in as the -i parameter
        else if(config.mode().equals("lagoon")){
            //currently we are hard coding the radius of the bounding circles, in the future we might have to change that
            Circle innerBound = new Circle(islandCenter.center_x, islandCenter.center_y,islandCenter.center_x/4);
            Circle outerBound = new Circle(islandCenter.center_x,islandCenter.center_y,islandCenter.center_x/2);

            LagoonIsland lagoon = new LagoonIsland(innerBound,outerBound,aMesh);

            Structs.Mesh island = lagoon.createIsland();

            new MeshFactory().write(island, config.output());
        }

    }
}