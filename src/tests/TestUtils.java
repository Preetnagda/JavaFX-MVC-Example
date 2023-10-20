package src.tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import src.model.Post;
import src.Utils;
import src.custom_exception.InvalidInputDataType;

/**
 * Test all methods of the Utils class.
 */
public class TestUtils {

    public static String TEST_FILE_PATH = "bin/resources/";

    @Test
    public void testparseDate() throws InvalidInputDataType{
        LocalDateTime parsedDate = src.Utils.parseDate("24/04/2023 04:21");
        assertEquals("Date time parsed correctly", 4, parsedDate.getMonthValue());
        assertEquals("Date time parsed correctly", 24, parsedDate.getDayOfMonth());
    }

    /**
     * Tests if InvalidInputDataType exception is thrown in case where unreadable date string is provided.
     * @throws InvalidInputDataType
     */
    @Test(expected = InvalidInputDataType.class)
    public void testInvalidParseDate() throws InvalidInputDataType{
        src.Utils.parseDate("2404");
    }

    /**
     * Tests if FileNotFoundException error is thrown if the csv path is invalid
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test(expected = FileNotFoundException.class)
    public void testCsvParserWithNoFile() throws FileNotFoundException, IOException{
        System.out.println(System.getProperty("user.dir"));
        Utils.csvReader("");
    }

    /**
     * Tests if an empty file is parsed without any errors. It should return empty 2D array of string.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void testCsvParserWithInvalidFile() throws FileNotFoundException, IOException{
        List<List<String>> csvData = Utils.csvReader(TEST_FILE_PATH + "EmptyPosts.csv");
        assertEquals("Csv parsed correctly", 0, csvData.size());
    }

    /**
     * Tests if any valid csv can be parsed correctly.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testCsvParserValidFile() throws FileNotFoundException, IOException{
        List<List<String>> parsedCsv = Utils.csvReader(TEST_FILE_PATH +"posts.csv");
        assertEquals("Csv parsed correctly", "20582", parsedCsv.get(0).get(0));
        assertEquals("Csv parsed correctly", "36778", parsedCsv.get(4).get(0));
    }

    /**
     * Tests if csv with valid data of posts can be parsed correctly.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test
    public void testpostsParser() throws FileNotFoundException, IOException, InvalidInputDataType{
        ArrayList<Post> parsedCsv = Utils.postsCSVReader(TEST_FILE_PATH + "posts.csv");
        assertEquals("Csv of valid posts parsed correctly", 20582, parsedCsv.get(0).getId());
        assertEquals("Csv of valid posts parsed correctly", 36778, parsedCsv.get(4).getId());
    }

    /**
     * Tests if csv with invalid data of posts throws the expected error.
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Test(expected = InvalidInputDataType.class)
    public void testInvalidPostsParser() throws FileNotFoundException, IOException, InvalidInputDataType{
        ArrayList<Post> parsedCsv = Utils.postsCSVReader(TEST_FILE_PATH + "InvalidPosts.csv");
        assertEquals("Csv of valid posts parsed correctly", "20582", parsedCsv.get(0).getId());
        assertEquals("Csv of valid posts parsed correctly", "36778", parsedCsv.get(4).getId());
    }



}
