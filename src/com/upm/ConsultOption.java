package com.upm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultOption {
    public ConsultOption() {
    }

    public void printMetrics(String fileRoute) {
        String actualLine = "";
        String[] fillingData = new String[3];
        ArrayList<Double> allOdometers = new ArrayList<Double>();
        ArrayList<Double> allVolumes = new ArrayList<Double>();
        ArrayList<Double> allPrices = new ArrayList<Double>();

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

        printConsumptionLastFilling(allOdometers, allVolumes);
        printConsumptionTotal(allOdometers, allVolumes);
        printPriceLastFilling(allOdometers, allVolumes, allPrices);
        printPriceTotal(allOdometers, allVolumes, allPrices);
        printAveragePrice(allPrices);
        printPossibleRange(allOdometers, allVolumes);
    }

    // Print last consumption corresponding to last filling. Consumption is
    // calculated as: (actualOdometer - previousOdometer) / volume
    private void printConsumptionLastFilling(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes) {
        int numberEntries = allOdometers.size();
        double consumption = (allOdometers.get(numberEntries - 1) - allOdometers.get(numberEntries - 2))
                / allVolumes.get(numberEntries - 1);
        String consumptionString =  String.format("%.2g", consumption);
        System.out.println("Average consumption since the last fueling: " + consumptionString + " km/l");
    }

    // Print average consumption (km/l) since first record
    private void printConsumptionTotal(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes) {
        double consumptions = 0.0;

        for (int i = 1; i < allOdometers.size(); i++) {
            consumptions += (allOdometers.get(i) - allOdometers.get(i - 1)) / allVolumes.get(i);
        }
        double average = consumptions / allOdometers.size() - 1;
        String averageString =  String.format("%.2g", average);

        System.out.println("Average consumption since the first record: " + averageString + " km/l");
    }

    // Print price per kilometer since last filling
    private void printPriceLastFilling(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes,
            ArrayList<Double> allPrices) {
        int numberEntries = allOdometers.size();

        double pricePerLiter = allPrices.get(numberEntries - 1) / allVolumes.get(numberEntries - 1);
        double consumption = (allOdometers.get(numberEntries - 1) - allOdometers.get(numberEntries - 2))
                / allVolumes.get(numberEntries - 1);
        double pricePerKilometer = pricePerLiter / consumption;
        String priceString =  String.format("%.2g", pricePerKilometer);

        System.out.println("Price per kilometer traveled since the last filling: " + priceString + " €/km");
    }

    // Print price per kilometer since first record
    private void printPriceTotal(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes,
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
        double average = prices / (allOdometers.size()-1);
        String averageString =  String.format("%.2g", average);

        System.out.println("Price per kilometer traveled since first filling: " + averageString + " €/km");
    }

    // Print average of prices
    private void printAveragePrice(ArrayList<Double> allPrices) {
        double prices = 0.0;

        for (int i = 1; i < allPrices.size(); i++) {
            prices += allPrices.get(i);
        }
        double average = prices / allPrices.size() - 1;
        String averageString =  String.format("%.2g", average);

        System.out.println("Average price paid for fuel historically: " + averageString + " €");
    }

    // Print current possible range (km). Calculated as: consumption average * last
    // volume
    private void printPossibleRange(ArrayList<Double> allOdometers, ArrayList<Double> allVolumes) {
        double consumptions = 0.0;
        for (int i = 1; i < allOdometers.size(); i++) {
            consumptions = (allOdometers.get(i) - allOdometers.get(i - 1))
                    / allVolumes.get(i);
        }
        double average = consumptions / allOdometers.size() - 1;
        double range = average * allVolumes.get(allVolumes.size() - 1);
        String rangeString =  String.format("%.2g", range);

        System.out.println("Current possible range: " + rangeString + " km");
    }
}
