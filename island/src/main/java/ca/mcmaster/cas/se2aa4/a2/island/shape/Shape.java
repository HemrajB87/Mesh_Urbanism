package ca.mcmaster.cas.se2aa4.a2.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface Shape {

    //having this interface allows us to determine if a centroid is in a different shape in the future
    boolean inShape(Structs.Vertex centroid);
}
