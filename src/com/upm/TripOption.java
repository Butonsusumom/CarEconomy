package com.upm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TripOption {
    private int odometer;

    public TripOption(int odometer) {
        this.odometer = odometer;
    }

    public void printFuelAndCost(String fileRoute){
        String actualLine = "";
        String[] fillingData = new String[3];
        ArrayList<Double> allOdometers = new ArrayList<Double>();
        ArrayList<Double> allVolumes = new ArrayList<Double>();
        ArrayList<Double> allPrices = new ArrayList<Double>();

        double fuel = 0;
        double cost = 0;
        int size = 0;

        try {
            File f = new File(fileRoute);
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((actualLine = br.readLine()) != null) {
                fillingData = actualLine.split(" ");

                allOdometers.add(Double.parseDouble(fillingData[0]));
                allVolumes.add(Double.parseDouble(fillingData[1]));
                allPrices.add(Double.parseDouble(fillingData[2]));
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        for(int i=0; i<allOdometers.size(); i++){
            if(allVolumes.get(i)!=0 && allOdometers.get(i)!=0 && allPrices.get(i)!=0) {
                fuel += allVolumes.get(i) / allOdometers.get(i);
                cost += allPrices.get(i) / allOdometers.get(i);
                size++;
            }
        }

        System.out.println("Fuel needed for a trip: "+odometer*fuel/size);
        System.out.println("Total cost for a trip: "+odometer*cost/size);
    }
}
