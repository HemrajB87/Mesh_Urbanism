package ca.mcmaster.cas.se2aa4.a2.island.shape;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class MeshCenter {
    private double max_x = Double.MIN_VALUE;
    private double max_y = Double.MIN_VALUE;

    public double center_x;
    public double center_y;
    private Structs.Mesh aMesh;

    public MeshCenter(Structs.Mesh aMesh){
        this.aMesh = aMesh;
    }

    public void FindCenter(){
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }

        center_x = max_x/2;
        center_y = max_y/2;

    }


}
