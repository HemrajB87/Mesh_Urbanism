package ca.mcmaster.cas.se2aa4.a2.island.altitude;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.configuration.tileCreater;
import ca.mcmaster.cas.se2aa4.a2.island.properties.ColourProperty;
import ca.mcmaster.cas.se2aa4.a2.island.properties.TypeProperty;
import ca.mcmaster.cas.se2aa4.a2.island.tiles.TileSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Altitude {

    private String altitude;
    private String mode;



    tileCreater createTile = new tileCreater();

    public Altitude(String x,String y) {
        this.altitude = x;
        this.mode =y;

    }

    public List<Structs.Polygon> setAltitude(List<Structs.Polygon> temp) {


        int n = 12; // run 10 times
        // List<Structs.Polygon> tiles = temp;
        List<Structs.Polygon> tiles = null;
        int evl = 15;
        Random rand = new Random();
        if (altitude.equals("volcano") && mode.equals("lagoon")) {
            tiles = addAltTiles(temp);
            for (int i = 0; i < n; i++) {
                evl += 15;
                tiles = extendAltTiles(tiles,evl);
            }
        }
        if(altitude.equals("random")&&mode.equals("lagoon") || mode.equals("plain")){
            tiles = addAltTiles(temp);
            for (int i = 0; i < n; i++) {
                evl = rand.nextInt(255) + 1;
                tiles = extendAltTiles(tiles,evl);
            }
        }
        if (altitude.equals("volcano") && mode.equals("plain")) {
            tiles = addAltTiles(temp);
            for (int i = 0; i < n; i++) {
                evl += 15;
                tiles = extendAltTiles(tiles,evl);
            }
        }

        return tiles;
    }


    public List<Structs.Polygon> addAltTiles(List<Structs.Polygon> temps) {

        List<Structs.Polygon> updatedTileList = new ArrayList<>();
        Structs.Polygon newTile;

        for (Structs.Polygon currentPoly : temps) {

            boolean isBoundry = false;

            List<Integer> neighborList = currentPoly.getNeighborIdxsList();

            for (Integer i : neighborList) {

                //extracts the tile type value of the current polygon we are looking at
                String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

                //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                String neighborTileType = new TypeProperty().extract(temps.get(i).getPropertiesList());

                //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "lagoon" or "ocean"
                if (currentPolyTileType.equals("land") && neighborTileType.equals("ocean")) {
                    isBoundry = true;
                    break;
                }
            }
            //changes land tile to boundary tile if requirements are met
            if (isBoundry) {
                int eval=0;
                if(mode.equals("lagoon")){
                    eval =255;
                } else{
                    eval=20;
                }
                String color = 25 + "," + 140 + "," + 100 + "," + eval;
                String type = "boundary";
                newTile = createTile.createTile(currentPoly, color, type);
                updatedTileList.add(newTile);
            } else {
                updatedTileList.add(currentPoly);
            }
            extendAltTiles(updatedTileList,10);
        }
        return updatedTileList;
    }

    public List<Structs.Polygon> extendAltTiles(List<Structs.Polygon> temps, int evl) {

        List<Structs.Polygon> updatedTileList = new ArrayList<>();
        Structs.Polygon newTile;

        for (Structs.Polygon currentPoly : temps) {

            boolean isBoundry = false;

            List<Integer> neighborList = currentPoly.getNeighborIdxsList();

            for (Integer i : neighborList) {

                if (i >= 0 && i < temps.size()) {

                    //extracts the tile type value of the current polygon we are looking at
                    String currentPolyTileType = new TypeProperty().extract(currentPoly.getPropertiesList());

                    //extracts the tile type value of one of the current neighbor to the polygon we are looking at
                    String neighborTileType = new TypeProperty().extract(temps.get(i).getPropertiesList());

                    //if the current polygon we are looking at has the tile type of "land" and the neighbor tile type is either "land" or "boundary"
                    if (currentPolyTileType.equals("land") && neighborTileType.equals("boundary")) {
                        isBoundry = true;
                        break;
                    }
                }
            }
            //changes land tile to boundary tile if requirements are met
            if (isBoundry) {
                String color = 25 + "," + 140 + "," + 100 + "," + evl;
                String type = "boundary";
                newTile = createTile.createTile(currentPoly, color, type);
                updatedTileList.add(newTile);
            } else {
                updatedTileList.add(currentPoly);
            }
        }
        return updatedTileList;
    }
}

