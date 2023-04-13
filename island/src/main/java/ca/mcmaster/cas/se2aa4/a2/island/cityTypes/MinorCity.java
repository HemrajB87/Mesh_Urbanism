package ca.mcmaster.cas.se2aa4.a2.island.cityTypes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;

import java.util.*;

public class MinorCity {


    private final Integer city;

    private final List<Structs.Polygon> polygons;
    private final List<Structs.Vertex> vertexs;

    private List<Structs.Vertex> realcityVertexs = new ArrayList<>();
    //private List<Structs.Vertex> realMajorCityVertexs = new ArrayList<>();
    private final List<Structs.Vertex> allCityVertexs = new ArrayList<>();

    private final List<Structs.Vertex> returnCityVertexs = new ArrayList<>();

    public MinorCity(Integer city, List<Structs.Polygon> p, List<Structs.Vertex> v){
        this.city=city;
        this.polygons=p;
        this.vertexs=v;
    }

    public List<Structs.Vertex> minorCityVertex() {
        for (Structs.Polygon p : polygons) {
            String currentPolyTileType = new TypeProperty().extract(p.getPropertiesList());
            if(currentPolyTileType.equals("land")){
                allCityVertexs.add(vertexs.get(p.getCentroidIdx()));
            }
        }

    Collections.shuffle(allCityVertexs);
    realcityVertexs = allCityVertexs.subList(0,city);


    for(Structs.Vertex v : vertexs){

        if(realcityVertexs.contains(v)){
            Structs.Vertex.Builder newV = Structs.Vertex.newBuilder(v);
            Structs.Property p = Structs.Property.newBuilder().setKey("rgb_color").setValue("255,255,0,255").build();
            Structs.Property p1 = Structs.Property.newBuilder().setKey("thickness").setValue("20").build();
            Structs.Property p2 = Structs.Property.newBuilder().setKey("city").setKey("Minor").build();
            Structs.Vertex city = newV.addProperties(p).addProperties(p1).addProperties(p2).build();
            returnCityVertexs.add(city);
        } else{
            Structs.Vertex.Builder vc = Structs.Vertex.newBuilder(v);
            Structs.Vertex reg = vc.build();
            returnCityVertexs.add(reg);
        }
    }
        return returnCityVertexs;
    }



}
