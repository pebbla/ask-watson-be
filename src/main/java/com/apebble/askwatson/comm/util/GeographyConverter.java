package com.apebble.askwatson.comm.util;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.ParseException;

public class GeographyConverter {

    public static Point strToPoint(Double longitude, Double latitude) throws ParseException{
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point point = (Point) new WKTReader().read(pointWKT);

        return point;
    }

}
