package com.upm;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RecordOptionTest {

    private static final String ERROR_RECORD_FILE_NAME = "record_error.csv";
    private static final String FIXED_ERROR_RECORD_FILE_NAME = "fixed_record.csv";
    private static final String SIMPLE_RECORD_FILE_NAME = "simple_record.csv";
    private static final String ONE_LINE_RECORD_FILE_NAME = "one_line_record.csv";

    @Test
    public void shouldPrintDataToFile_whenRecordFilling_givenNoAnswer() throws IOException {
        //given
        RecordOption given = new RecordOption(100,1,1);
        String givenAnswer = "gg\r\nno";
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        fillTestFile(ERROR_RECORD_FILE_NAME);

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setIn(new ByteArrayInputStream(givenAnswer.getBytes()));
        given.recordFilling(ERROR_RECORD_FILE_NAME);
        System.setOut(standardOut);
        System.setIn(standardIn);


        //then
        assertEquals("Something is wrong. Did you forget the last fueling? (Yes/No)\r\n" +
                "Something is wrong. Did you forget the last fueling? (Yes/No)", outputStreamCaptor.toString()
                .trim());
        assertEquals(readLastLine(ERROR_RECORD_FILE_NAME),"100.0 1.0 1.0");

    }

    @Test
    public void shouldPrintZeroDataToFile_whenRecordFilling_givenYesAnswer() throws IOException {
        //given
        RecordOption given = new RecordOption(100,1,1);
        String givenAnswer = "gg\r\nyes";
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        InputStream standardIn = System.in;
        fillTestFile(FIXED_ERROR_RECORD_FILE_NAME);

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setIn(new ByteArrayInputStream(givenAnswer.getBytes()));
        given.recordFilling(FIXED_ERROR_RECORD_FILE_NAME);
        System.setOut(standardOut);
        System.setIn(standardIn);


        //then
        assertEquals("Something is wrong. Did you forget the last fueling? (Yes/No)\r\n" +
                "Something is wrong. Did you forget the last fueling? (Yes/No)", outputStreamCaptor.toString()
                .trim());
        assertEquals(readPreLastLine(FIXED_ERROR_RECORD_FILE_NAME),"0 0 0");
        assertEquals(readLastLine(FIXED_ERROR_RECORD_FILE_NAME),"100.0 1.0 1.0");
    }

    @Test
    public void shouldPrintDataToFile_whenRecordFilling_givenCorrectData() throws IOException {
        //given
        RecordOption given = new RecordOption(400,5,20);
        fillTestFile(SIMPLE_RECORD_FILE_NAME);

        //when
        given.recordFilling(SIMPLE_RECORD_FILE_NAME);

        //then
        assertEquals(readLastLine(SIMPLE_RECORD_FILE_NAME),"400.0 5.0 20.0");
    }

    @Test
    public void shouldPrintDataToFile_whenRecordFilling_givenOneLineFile() throws IOException {
        //given
        RecordOption given = new RecordOption(100,5,20);
        fillOneLineFile(ONE_LINE_RECORD_FILE_NAME);

        //when
        given.recordFilling(ONE_LINE_RECORD_FILE_NAME);

        //then
        assertEquals(readLastLine(ONE_LINE_RECORD_FILE_NAME),"100.0 5.0 20.0");
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

    private void fillOneLineFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("0 0 0");
        bw.newLine();
        bw.close();
    }

}