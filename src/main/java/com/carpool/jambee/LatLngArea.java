package com.carpool.jambee;

public class LatLngArea {
    private double lat1;
    private double lat2;
    private double lat3;
    private double lat4;
    private double lng1;
    private double lng2;
    private double lng3;
    private double lng4;

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }

    public double getLat3() {
        return lat3;
    }

    public void setLat3(double lat3) {
        this.lat3 = lat3;
    }

    public double getLat4() {
        return lat4;
    }

    public void setLat4(double lat4) {
        this.lat4 = lat4;
    }

    public double getLng1() {
        return lng1;
    }

    public void setLng1(double lng1) {
        this.lng1 = lng1;
    }

    public double getLng2() {
        return lng2;
    }

    public void setLng2(double lng2) {
        this.lng2 = lng2;
    }

    public double getLng3() {
        return lng3;
    }

    public void setLng3(double lng3) {
        this.lng3 = lng3;
    }

    public double getLng4() {
        return lng4;
    }

    public void setLng4(double lng4) {
        this.lng4 = lng4;
    }

    public void printArea() {
        System.out.println("{" + lat1 + "," + lng1 + "}");
        System.out.println("{" + lat2 + "," + lng2 + "}");
        System.out.println("{" + lat3 + "," + lng3 + "}");
        System.out.println("{" + lat4 + "," + lng4 + "}");
    }
}
