package com.upm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ConsultOptionTest {

    private static final String CONSULT_FILE_NAME = "consult.csv";

    @Before
    public void setUp() throws IOException {
        File targetFile = new File("C:\\Users\\KSU\\UPM\\Verification and Validation\\CarEconomy"+CONSULT_FILE_NAME);
        targetFile.createNewFile();
        fillTestFile(CONSULT_FILE_NAME);
    }

    @After
    public void cleanUp() throws IOException {
        File notExistent = new File("C:\\Users\\KSU\\UPM\\Verification and Validation\\CarEconomy"+CONSULT_FILE_NAME);
        notExistent.delete();
    }

    @Test
    public void shouldPrintMetrics_when_givenFiledFile(){
        //given
        ConsultOption given = new ConsultOption();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        given.printMetrics(CONSULT_FILE_NAME);
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