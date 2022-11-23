package com.upm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ValidationHelperTest {

    private static final String EXISTENT_FIlE_NAME = "existentFile.csv";
    private static final String NOT_EXISTENT_FIlE_NAME = "notExistentFile.csv";
    private static final String ERROR_MESSAGE_NO_FILE_CONSULT = "No data found. Try recording your first fueling with -r or use -h to get help.";


    private ValidationHelper classUnderTest = new ValidationHelper();

    @BeforeAll
    public void setUp() throws IOException {
        File targetFile = new File(EXISTENT_FIlE_NAME);
        targetFile.createNewFile();
    }

    @AfterEach
    public void cleanUp() throws IOException {
        File notExistent = new File(NOT_EXISTENT_FIlE_NAME);
        notExistent.delete();
    }

    @Test
    public void shouldReturnTrue_whenCheckArguments_givenListWithOneMuchingOption(){
        //given
        String[] givenArguments = {"-r", "-h"};

        //when
        boolean actual = classUnderTest.checkArguments(givenArguments, "-h");

        //then
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalse_whenCheckArguments_givenListWithNoMuchingOption(){
        //given
        String[] givenArguments = {"-r", "-a"};

        //when
        boolean actual = classUnderTest.checkArguments(givenArguments, "-h");

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalse_when_givenNull(){
        //given

        //when
        boolean actual = classUnderTest.isNumeric(null);

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalse_whenIsNumeric_givenString(){
        //given

        //when
        boolean actual = classUnderTest.isNumeric("string");

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalse_whenIsNumeric_givenNegative(){
        //given

        //when
        boolean actual = classUnderTest.isNumeric("-20.5");

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrue_whenIsNumeric_givenPositive(){
        //given

        //when
        boolean actual = classUnderTest.isNumeric("20.5");

        //then
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalse_whenValidateArguments_givenListWithNoMuchingOption(){
        //given
        String[] givenArguments = {"1", "2", "s", "4"};

        //when
        boolean actual = classUnderTest.validateArguments(givenArguments, 0,3);

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrue_whenValidateArguments_givenCorrectArgument(){
        //given
        String[] givenArguments = {"1", "2", "3", "4"};

        //when
        boolean actual = classUnderTest.validateArguments(givenArguments, 0,3);

        //then
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalse_whenValidateFile_givenNoArguments(){
        //given
        String[] givenArguments = {};

        //when
        boolean actual = classUnderTest.validateFile(givenArguments);

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalse_whenValidateFile_givenNotAFileArgument(){
        //given
        String[] givenArguments = {"1"};

        //when
        boolean actual = classUnderTest.validateFile(givenArguments);

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrue_whenValidateFile_givenExistentFile(){
        //given
        String[] givenArguments = {EXISTENT_FIlE_NAME};

        //when
        boolean actual = classUnderTest.validateFile(givenArguments);

        //then
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalse_whenValidateFile_givenNotExistentFileAndWrongOption(){
        //given
        String[] givenArguments = {NOT_EXISTENT_FIlE_NAME, "-g"};

        //when
        boolean actual = classUnderTest.validateFile(givenArguments);

        //then
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalse_whenValidateFile_givenNotExistentFileAndConsultOption(){
        //given
        String[] givenArguments = {NOT_EXISTENT_FIlE_NAME, "-c"};
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;

        //when
        System.setOut(new PrintStream(outputStreamCaptor));
        boolean actual = classUnderTest.validateFile(givenArguments);
        System.setOut(standardOut);

        //then
        assertEquals(ERROR_MESSAGE_NO_FILE_CONSULT, outputStreamCaptor.toString()
                .trim());
        assertFalse(actual);
    }

    @Test
    public void shouldReturnTrue_whenValidateFile_givenNotExistentFileAndRecordOption(){
        //given
        String[] givenArguments = {NOT_EXISTENT_FIlE_NAME, "-r"};

        //when
        boolean actual = classUnderTest.validateFile(givenArguments);

        //then
        assertTrue(actual);
    }
}