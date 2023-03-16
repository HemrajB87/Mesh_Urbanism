package ca.mcmaster.cas.se2aa4.a2.island.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface PropertyAccess<T> {

    String extract(List<Structs.Property> props);

}

