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

        String totalFuel =  String.format("%.2g", odometer/calculateConsumptionTotal(allOdometers, allVolumes));
        String totalCost =  String.format("%.2g",odometer*calculatePriceTotal(allOdometers, allVolumes, allPrices));
        System.out.println("Fuel needed for a trip: "+totalFuel);
        System.out.println("Total cost for a trip: "+totalCost);
    }

    // price per kilometer since first record
    private double calculatePriceTotal(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes,
                                 ArrayList<Double> allPrices) {
        double prices = 0.0;
        double pricePerLiter = 0.0;
        double consumption = 0.0;

        for (int i = 1; i < allOdometers.size(); i++) {
            pricePerLiter = allPrices.get(i) / allVolumes.get(i);
            consumption = (allOdometers.get(i) - allOdometers.get(i - 1))
                    / allVolumes.get(i);
            prices += pricePerLiter / consumption;
        }
        return prices / (allOdometers.size()-1);
    }

    // Average consumption (km/l) since first record
    private double calculateConsumptionTotal(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes) {
        double consumptions = 0.0;

        for (int i = 1; i < allOdometers.size(); i++) {
            consumptions += (allOdometers.get(i) - allOdometers.get(i - 1)) / allVolumes.get(i);
        }
        return consumptions / allOdometers.size() - 1;
    }
}
