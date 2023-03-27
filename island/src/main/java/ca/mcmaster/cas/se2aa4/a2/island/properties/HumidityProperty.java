package ca.mcmaster.cas.se2aa4.a2.island.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.util.List;


public class HumidityProperty implements PropertyAccess<String> {

    @Override
    public String extract(List<Property> props) {
        String value = new Reader(props).get("humidity");
        if (value == null)
            return "";
        return value;

    }
}
