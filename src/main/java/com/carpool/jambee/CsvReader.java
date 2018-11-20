package com.carpool.jambee;

import com.carpool.jambee.mongodb.model.CityData;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvReader {

    public ArrayList<CityData> readFile(String citiesFile) {
        Scanner input = null;
        File file;

        try {
            file = ResourceUtils.getFile(citiesFile);
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find cities CSV file. Aborting.");
            System.exit(1);
        }

        input.nextLine(); // Skip first line of file because it's only labels

        ArrayList<CityData> cities = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();

            String information[];
            information = line.split("\",\"");

            // Remove quote mark from first position of first info
            String first = information[0];
            first = first.substring(1, first.length());
            information[0] = first;

            // Remove quote mark from last position of last info
            String last = information[information.length - 1];
            last = last.substring(0, last.length() - 1);
            information[information.length - 1] = last;

            CityData city = new CityData();
            city.setCity(information[0]);
            city.setCityASCII(information[1]);
            city.setStateID(information[2]);
            city.setStateName(information[3]);
            city.setCountyFips(Integer.parseInt(information[4]));
            city.setCountyName(information[5]);
            city.setLat(Double.parseDouble(information[6]));
            city.setLng(Double.parseDouble(information[7]));
            if (information[8].length() > 0)
                city.setPopulation((int)Double.parseDouble(information[8]));
            if (information[9].length() > 0)
                city.setPopulationProper((int)Double.parseDouble(information[9]));
            if (information[10].length() > 0)
                city.setDensity(Double.parseDouble(information[10]));
            city.setSource(information[11]);
            // See 12 below
            city.setTimezone(information[13]);
            // See 14 below
            city.setId(information[15]);


            if (information[12].equals("True"))
                city.setIncorporated(true);
            else
                city.setIncorporated(false);


            if (information[14].length() > 0) {
                String zips[] = information[14].split(" ");
                for (int i = 0; i < zips.length; i++)
                    city.insertZip(Integer.parseInt(zips[i]));
            }

            cities.add(city);
        }

        return cities;
    }

}
