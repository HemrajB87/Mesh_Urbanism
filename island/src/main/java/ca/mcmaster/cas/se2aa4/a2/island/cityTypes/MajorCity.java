package ca.mcmaster.cas.se2aa4.a2.island.cityTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class MajorCity {

    public Structs.Vertex majorCities(List<Structs.Vertex> vertex, List<Structs.Polygon> polygon) {
        int x = 0;
        for(Structs.Polygon poly: polygon) {

            x = poly.getCentroidIdx();

        }
        Structs.Vertex.Builder majorVertex = Structs.Vertex.newBuilder(vertex.get(x));

        Structs.Property p = Structs.Property.newBuilder().setKey("rgb_color").setValue("255,0,0,255").build();
        Structs.Property p1 = Structs.Property.newBuilder().setKey("thickness").setValue("20").build();
        Structs.Property p2 = Structs.Property.newBuilder().setKey("city").setKey("Major").build();

        return majorVertex.addProperties(p).addProperties(p1).addProperties(p2).build();
    }

    public Structs.Vertex getVertex(List<Structs.Vertex> vertices, int index) {
        return vertices.get(index);
    }
}


