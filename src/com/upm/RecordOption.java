package com.upm;

import java.io.*;
import java.util.Scanner;

public class RecordOption {
    private double odometer;
    private double volume;
    private double total_price;

    private static final String MESSAGE_ASK_ABOUT_FUELING = "Something is wrong. Did you forget the last fueling? (Yes/No)";

    public RecordOption(double odometer, double volume, double total_price) {
        this.odometer = odometer;
        this.volume = volume;
        this.total_price = total_price;
    }

    // Add the filling to the file
    public void recordFilling(String fileRoute) {
        boolean incorrectAnswer = true;
        String answer = "";

        try {
            if (!canRecord(fileRoute)) {
                Scanner scanner = new Scanner(System.in);

                while (incorrectAnswer) {
                    System.out.println(MESSAGE_ASK_ABOUT_FUELING);
                    answer = scanner.next();
                    answer = answer.trim().toLowerCase();
                    if (answer.equals("yes") || answer.equals("no")) {
                        incorrectAnswer = false;
                    }
                }
                scanner.close();
            }

            FileWriter fw = new FileWriter(fileRoute, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (answer.equals("yes")) {
                bw.write("0 0 0");
                bw.newLine();
            }
            bw.write(odometer + " " + volume + " " + total_price);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    // Check if there might be a missing filling
    private boolean canRecord(String fileRoute) throws IOException {
        double averageConsumption = 0.0;
        double historicalConsumption = 0.0;
        double historicalConsumptionAverage = 0.0;
        int numberLines = 0;
        double previousOdometer = 0;
        String actualLine = "";
        String[] fillingData = new String[3];
        File f = new File(fileRoute);
        BufferedReader br = new BufferedReader(new FileReader(f));

        while ((actualLine = br.readLine()) != null) {
            numberLines++;
            fillingData = actualLine.split(" ");

            if (numberLines >= 2) {
                historicalConsumption += (Double.parseDouble(fillingData[0]) - previousOdometer)
                        / Double.parseDouble(fillingData[1]);
            }
            previousOdometer = Double.parseDouble(fillingData[0]);
        }
        br.close();

        if(numberLines == 1){
            return true;
        }

        historicalConsumptionAverage = historicalConsumption / numberLines;
        averageConsumption = (odometer - Double.parseDouble(fillingData[0])) / volume;

        double ratio = (Math.abs(historicalConsumptionAverage - averageConsumption)
                / ((historicalConsumptionAverage + averageConsumption) / 2)) * 100;

        if (ratio > 50) {
            return true;
        } else {
            return false;
        }
    }
}
