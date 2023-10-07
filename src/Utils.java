package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.CustomExceptions.InvalidInputDataType;
import src.Model.Post;

/**
 * Util class contains functions acting as general utility functions for the project.
 */

public class Utils {

    // Project static values
    private static final String CSV_COMMA_DELIMITER = ",";
    public static final String DATE_FORMAT = "d/M/y H:m";


    public static ArrayList<Post> postsCSVReader(String pathString) throws FileNotFoundException, IOException, InvalidInputDataType{
        
        ArrayList<Post> parsedPosts = new ArrayList<Post>();
        int index = 0;
        for(List<String> post: csvReader(pathString)){
            try{
                LocalDateTime dateTime = parseDate(post.get(5));
                parsedPosts.add(new Post(Integer.parseInt(post.get(0)), post.get(1), post.get(2),Integer.parseInt(post.get(3)), Integer.parseInt(post.get(4)), dateTime));
                index++;
            }catch (NumberFormatException e ) {
                throw new InvalidInputDataType(String.format("File contains incorrect input at index %d. Contains String, Expected Number.", index));
            }
            catch(InvalidInputDataType e){
                throw new InvalidInputDataType(String.format("%s. File contains incorrect date input at index %d.", e.getMessage(), index));
            }
        }
        return parsedPosts;
    }

    public static List<List<String>> csvReader(String pathString) throws FileNotFoundException, IOException{
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathString))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null) {
                if(i!=0){
                    String[] values = line.split(CSV_COMMA_DELIMITER);
                    records.add(Arrays.asList(values));
                }
                i++;
            }
        }
        return records;
    }

    public static LocalDateTime parseDate(String dateString) throws InvalidInputDataType{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try{
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
            return dateTime;
        } catch(DateTimeParseException e){
            throw new InvalidInputDataType(String.format("Invalid date format. Expected %s", DATE_FORMAT));
        }
    }

    public static String getStringFromDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return formatter.format(date);
    }
}