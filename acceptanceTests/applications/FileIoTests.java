package applications;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

import exceptions.MyInputException;

/**
 * A simple set of unit tests that just run the MachineShopSimulator on a set
 * of input files and compare the results to the expected output files.
 * 
 * @author Nic McPhee
 */
public class FileIoTests {

    private static final String ACCEPTANCE_TEST_DIRECTORY = "acceptanceTests/";
    private static final String TEST_FILE_DIRECTORY 
        = ACCEPTANCE_TEST_DIRECTORY + "Machine_shop_test_files/";
    private static final String EXCEPTION_TEST_FILE_DIRECTORY
        = ACCEPTANCE_TEST_DIRECTORY + "Machine_shop_exception_test_files/";

    /**
     * For each file in the Test_files directory with a name of the form
     * *.input, run the simulator with that as input, save the output
     * to a temp file, and compare that to the expected output file.
     * @throws IOException 
     */
    @Test
    public final void runFileBasedTests() throws IOException {
        String[] inputFiles = collectInputFileNames();
        for (String inputFile : inputFiles) {
            File outputFile = runInputFile(inputFile, TEST_FILE_DIRECTORY);
            String expectedOutputFile = generateExpectedOutputFile(inputFile);
            checkFilesMatch(expectedOutputFile, outputFile);
        }
    }

    private String[] collectInputFileNames() {
        File testFileDirectory = new File(TEST_FILE_DIRECTORY);
        FilenameFilter inputFileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File directory, String name) {
                return name.endsWith(".input");
            }
        };
        String[] inputFiles = testFileDirectory.list(inputFileFilter);
        return inputFiles;
    }

    private File runInputFile(String inputFile, String directory) throws IOException {
        System.err.println("Processing input file: <" + inputFile + ">");
        FileInputStream fileInputStream = new FileInputStream(directory + inputFile);
        System.setIn(fileInputStream);
        File outputFile = File.createTempFile("simulator", ".ouptut");
        PrintStream filePrintStream = new PrintStream(outputFile);
        System.setOut(filePrintStream);
        MachineShopSimulator.main(new String[] { });
        return outputFile;
    }

    private String generateExpectedOutputFile(String inputFile) {
        String outputFile = inputFile.replace(".input", ".output");
        return outputFile;
    }

    private void checkFilesMatch(String expectedOutputFile, File outputFile) 
            throws IOException {
        FileReader expectedFileReader = new FileReader(TEST_FILE_DIRECTORY + expectedOutputFile);
        FileReader actualFileReader = new FileReader(outputFile);
        BufferedReader expectedReader = new BufferedReader(expectedFileReader);
        BufferedReader actualReader = new BufferedReader(actualFileReader);
        String expectedLine = expectedReader.readLine();
        String actualLine = actualReader.readLine();
        
        while (expectedLine != null && actualLine != null) {
            assertEquals("In file " + expectedOutputFile, expectedLine, actualLine);
            expectedLine = expectedReader.readLine();
            actualLine = actualReader.readLine();            
        }
        assertTrue("In file " + expectedOutputFile + " the files didn't end at the same time", 
                expectedLine == null && actualLine == null);
    }
    
    @Test
    public void noMachinesShouldThrowException() throws IOException {
        String inputFile = "NoMachines.input";
        String expectedMessage = MachineShopSimulator.NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void noJobsShouldThrowException() throws IOException {
        String inputFile = "NoJobs.input";
        String expectedMessage = MachineShopSimulator.NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void negativeChangeOverShouldThrowException() throws IOException {
        String inputFile = "NegativeChangeOver.input";
        String expectedMessage = MachineShopSimulator.CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void negativeNumberOfTasksShouldThrowException() throws IOException {
        String inputFile = "NegativeNumberOfTasks.input";
        String expectedMessage = MachineShopSimulator.EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void zeroTasksShouldThrowException() throws IOException {
        String inputFile = "ZeroTasks.input";
        String expectedMessage = MachineShopSimulator.EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void zeroMachineNumberShouldThrowException() throws IOException {
        String inputFile = "ZeroMachineNumber.input";
        String expectedMessage = MachineShopSimulator.BAD_MACHINE_NUMBER_OR_TASK_TIME;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void tooLargeMachineNumberShouldThrowException() throws IOException {
        String inputFile = "MachineNumberTooLarge.input";
        String expectedMessage = MachineShopSimulator.BAD_MACHINE_NUMBER_OR_TASK_TIME;
        runExceptionTest(inputFile, expectedMessage);
    }

    @Test
    public void zeroTaskTimeShouldThrowException() throws IOException {
        String inputFile = "ZeroTaskTime.input";
        String expectedMessage = MachineShopSimulator.BAD_MACHINE_NUMBER_OR_TASK_TIME;
        runExceptionTest(inputFile, expectedMessage);
    }

    private void runExceptionTest(String inputFile, String expectedMessage)
            throws IOException {
        try {
            runInputFile(inputFile, EXCEPTION_TEST_FILE_DIRECTORY);
            fail("Running the \"" + inputFile + "\" test should have thrown an exception.");
        } catch (MyInputException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
