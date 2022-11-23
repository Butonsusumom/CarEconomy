package com.upm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class TripOptionTest {

    private static final String TRIP_FILE_NAME = "trip.csv";

    @Before
    public void setUp() throws IOException {
        File targetFile = new File("C:\\Users\\KSU\\UPM\\Verification and Validation\\CarEconomy"+TRIP_FILE_NAME);
        targetFile.createNewFile();
        fillTestFile(TRIP_FILE_NAME);
    }

    @After
    public void cleanUp() throws IOException {
        File notExistent = new File("C:\\Users\\KSU\\UPM\\Verification and Validation\\CarEconomy"+TRIP_FILE_NAME);
        notExistent.delete();
    }

    @Test
    public void shouldPrintInformation_whenPrintFuelAndCost_givenFileWithAData() {
        //given
        TripOption given = new TripOption(100);
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        given.printFuelAndCost(TRIP_FILE_NAME);
        System.setOut(standardOut);

        //then
        assertEquals("Fuel needed for a trip: 9,4\r\nTotal cost for a trip: 26", outputStreamCaptor.toString()
                .trim());
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