package ca.mcmaster.cas.se2aa4.a2.island.altitude;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;

public class Altitude {

    private String altitude;
    private  String mode;

    tileCreater createTile = new tileCreater();

    public Altitude(String x, String y) {
        this.altitude =x;
        this.mode=y;
    }

    public int setAltitude() {


        int elevation = 0;
//        if (mode.equals("lagoon")) {
//            if (altitude.equals("high")) {
//                elevation = (int) (Math.random() * (255 - 100)) + 100;
//
//            } else if (altitude.equals("")) {
//                elevation = (int) (Math.random() * (100 - 1)) + 1;
//            } else {
        if(altitude.equals("random"))
            elevation = (int) (Math.random() * (255 - 1)) + 1;
        else{
            elevation = (int) (Math.random() * (255 - 1)) + 1;
        }
//            }
            //return elevation;
//        } else if(mode.equals("plain")){
//            if (altitude.equals("high")) {
//                elevation = (int) (Math.random() * (255 - 100)) + 100;
//
//            } else if (altitude.equals("random")) {
//                elevation = (int) (Math.random() * (100 - 1)) + 1;
//            } else {
//                elevation = (int) (Math.random() * (255 - 1)) + 1;
//            }
        //}
        return elevation;
    }










}
