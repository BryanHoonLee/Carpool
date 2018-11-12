package com.carpool.jambee;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class CityData {
    private  String city;
    private  String cityASCII;
    private  String stateID;
    private  String stateName;
    private     int countyFips;
    private  String countyName;
    private  double lat;
    private  double lng;
    private     int population;
    private     int populationProper;
    private  double density;
    private  String source;
    private boolean incorporated;
    private  String timezone;
    private   int[] zips;
    private     int id;

    public CityData() {
        city = null;
        cityASCII = null;
        stateID = null;
        stateName = null;
        countyFips = -1;
        countyName = null;
        lat = Double.MAX_VALUE;
        lng = Double.MAX_VALUE;
        population = -1;
        populationProper = -1;
        density = Double.MAX_VALUE;
        source = null;
        incorporated = false;
        timezone = null;
        zips = new int[0];
        id = -1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityASCII() { return cityASCII; }

    public void setCityASCII(String cityASCII) { this.cityASCII = cityASCII; }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getCountyFips() {
        return countyFips;
    }

    public void setCountyFips(int countyFips) {
        this.countyFips = countyFips;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopulationProper() {
        return populationProper;
    }

    public void setPopulationProper(int populationProper) {
        this.populationProper = populationProper;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isIncorporated() {
        return incorporated;
    }

    public void setIncorporated(boolean incorporated) {
        this.incorporated = incorporated;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int[] getZips() {
        return zips;
    }

    public void insertZip(int zip) {
        int temp[] = new int[zips.length + 1];

        for (int i = 0; i < zips.length; i++)
            temp[i] = zips[i];

        temp[zips.length] = zip;
        zips = temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        String out = "";

        out += city;
        out += ", " + cityASCII;
        out += ", " + stateID;
        out += ", " + stateName;
        out += ", " + countyFips;
        out += ", " + countyName;
        out += ", " + lat;
        out += ", " + lng;
        out += ", " + population;
        out += ", " + populationProper;
        out += ", " + density;
        out += ", " + source;
        out += ", " + incorporated;
        out += ", " + timezone;
        out += ", ";
        for (int i = 0; i < zips.length; i++) {
            out += zips[i];
            if (i < zips.length - 1)
                out += " ";
        }
        out += ", " + id;


        return out;
    }
}
