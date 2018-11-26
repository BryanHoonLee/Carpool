package com.carpool.jambee;

public class CityRouteMath {

    /* Distance between two lat/lng coordinates in km using the Haversine formula */
    public static double getDistanceBetweenCities(
        double lat1,
        double lng1,
        double lat2,
        double lng2
    ) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double earthRadius = 6371;

        double latitudeDiff = lat2 - lat1;
        double longitudeDiff = Math.toRadians(lng2 - lng1);

        double a = Math.pow(Math.sin(latitudeDiff / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.pow(Math.sin(longitudeDiff / 2), 2);

        double distance = 2 * earthRadius * Math.asin(Math.sqrt(a));

        return distance;
    }

    public static LatLngArea getLatLngArea(double lat, double lng, double radius) {

        // 1 degree latitude is approximately 110.54 km at the equator (or everywhere, generally)
        double lengthDegree = 1 / 110.54;
        double radiusAsDegrees = radius * lengthDegree;
        double topLat = lat + radiusAsDegrees;
        double botLat = lat - radiusAsDegrees;
        double degOfTopLng = Math.cos(Math.toRadians(topLat)) * radiusAsDegrees;
        double degOfBotLng = Math.cos(Math.toRadians(botLat)) * radiusAsDegrees;
        double topLeftLng = lng - (degOfTopLng / 2);
        double topRightLng = lng + (degOfTopLng / 2);
        double botLeftLng = lng - (degOfBotLng / 2);
        double botRightLng = lng + (degOfBotLng / 2);

        LatLngArea area = new LatLngArea();
        area.setLat1(topLat);
        area.setLat2(topLat);
        area.setLat3(botLat);
        area.setLat4(botLat);
        area.setLng1(topLeftLng);
        area.setLng2(topRightLng);
        area.setLng3(botLeftLng);
        area.setLng4(botRightLng);

        return area;
    }
}
