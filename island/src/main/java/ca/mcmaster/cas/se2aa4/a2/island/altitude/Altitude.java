package ca.mcmaster.cas.se2aa4.a2.island.altitude;


public class Altitude {

    private String altitude;
    private  String mode;

    public Altitude(String x, String y) {
        this.altitude =x;
        this.mode=y;
    }

    public int setAltitude() {


        int elevation = 0;
        if (mode.equals("lagoon")) {
            if (altitude.equals("high")) {
                elevation = (int) (Math.random() * (255 - 100)) + 100;

            } else if (altitude.equals("low")) {
                elevation = (int) (Math.random() * (100 - 1)) + 1;
            } else {
                elevation = (int) (Math.random() * (255 - 1)) + 1;
            }
            //return elevation;
        }
        return elevation;
    }
}
