package com.upm;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private static final String MAIN_TRIP_FILE_NAME = "main_trip.csv";
    private static final String MAIN_CONSULT_FILE_NAME = "main_consult.csv";
    private static final String MAIN_RECORD_FILE_NAME= "main_record.csv";

    @Test
    public void shouldRunHelpFunction_whenMain_givenHelpOption(){
        //given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        String[] givenArguments = {"-r", "-h"};

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Usage: careconomy <filename> [-h] [-r odometer volume total_price | -c | -t distance]", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintErrorMessage_whenMain_givenWrongFileName(){
        //given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        String[] givenArguments = {"file", "-r"};

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Please specify a valid CSV filename.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintErrorMessage_whenMain_givenWrongNumberOfArguments(){
        //given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        String[] givenArguments = {"file.csv"};

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Invalid option. Please select at least one option from [-r]record, [-c]onsult, or [-t]ravel.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintErrorMessage_whenMain_givenWrongNumberOfArgumentsForRecordOption(){
        //given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        String[] givenArguments = {"file.csv", "-r", "1", "2"};

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Please input the correct number of arguments: 3 (three)", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintErrorMessage_whenMain_givenWrongNumberOfArgumentsForTripOption(){
        //given
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        String[] givenArguments = {"file.csv", "-t"};

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Please input the correct number of arguments: 1 (one)", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintInformation_whenMain_givenFileWithAData() throws IOException {
        //given
        String[] givenArguments = {MAIN_TRIP_FILE_NAME, "-t", "100"};
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        fillTestFile(MAIN_TRIP_FILE_NAME);

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Fuel needed for a trip: 9,4\r\nTotal cost for a trip: 26", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintMetrics_when_givenFiledFile() throws IOException {
        //given
        String[] givenArguments = {MAIN_CONSULT_FILE_NAME, "-c"};
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        fillTestFile(MAIN_CONSULT_FILE_NAME);

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        Main.main(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals("Average consumption since the last fueling: 20 km/l\r\n" +
                "Average consumption since the first record: 11 km/l\r\n" +
                "Price per kilometer traveled since the last filling: 0,22 €/km\r\n" +
                "Price per kilometer traveled since first filling: 0,26 €/km\r\n" +
                "Average price paid for fuel historically: 18 €\r\n" +
                "Current possible range: 20 km", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    public void shouldPrintDataToFile_whenMain_givenRecordOption() throws IOException {
        //given
        String[] givenArguments = {MAIN_RECORD_FILE_NAME, "-r", "400", "5", "20"};

        //when
        Main.main(givenArguments);

        //then
        assertEquals(readPreLastLine(MAIN_RECORD_FILE_NAME),"0 0 0");
        assertEquals(readLastLine(MAIN_RECORD_FILE_NAME),"400.0 5.0 20.0");
    }

    private String readPreLastLine(String fileName) throws IOException {
        String actualLine = "";
        String[] fillingData = new String[3];
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String lastLine="";
        String preLastLine="";

        while ((actualLine = br.readLine()) != null) {
            preLastLine=lastLine;
            lastLine = actualLine;
        }
        br.close();

        return preLastLine;
    }

    private String readLastLine(String fileName) throws IOException {
        String actualLine = "";
        String[] fillingData = new String[3];
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String lastLine="";

        while ((actualLine = br.readLine()) != null) {
            lastLine=actualLine;
        }
        br.close();

        return lastLine;
    }

    private void fillTestFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("0 0 0");
        bw.newLine();
        bw.write("100 5 20");
        bw.newLine();
        bw.write("200 15 35");
        bw.newLine();
        bw.write("300 5 22");
        bw.newLine();
        bw.close();
    }

}
