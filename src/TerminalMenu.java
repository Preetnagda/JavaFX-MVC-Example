package src;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import src.custom_exception.DuplicatePost;
import src.custom_exception.InvalidInputDataType;
import src.model.Post;
import src.model.PostCollection;

/**
 * TerminalMenu class implements the Menu of the project in CLI.
 */
public class TerminalMenu {

    private PostCollection menuCollection;

    /**
     * Constructor reads the input csv files. It program skips reading the csv file if not present.
     * The program quits if invalid csv is present.
     * @param input_csv_path
     */
    public TerminalMenu(String input_csv_path){
        menuCollection = new PostCollection();
        try {
            ArrayList<Post> posts_csv = Utils.postsCSVReader(input_csv_path);
            try{
                menuCollection.addBulkPosts(posts_csv);
            }catch (DuplicatePost e){
                System.out.print(e.getMessage());
                System.out.println(". Skipped");
            }

        } catch (InvalidInputDataType e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Skipping");
        }catch (IOException e) {
            System.out.println("Error parsing file. Quitting");
            System.exit(1);
        }
    }

    /**
     * This function is used to start the program. It displays the available options available to the user to make choice from.
     * It also takes the response from main menu, validates it and calls the responsible function implement the functionality. 
     */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        System.out.println("Welcome to Social Media Analyzer!");
        do {
            displayMainMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addPost(getUserInputForAddPost(scanner));
                    break;
                case 2:
                    System.out.print("Please provide the post ID: ");
                    // The user is promted to enter a integer number which is passed to delete post.
                    deletePost(getUserIntInput(scanner, false));
                    break;
                case 3:
                    System.out.print("Enter the post id: ");
                    // The user is promted to enter a integer number which is passed to retrieve post.
                    retrievePost(getUserIntInput(scanner, false));
                    break;
                case 4:
                    System.out.print("Enter the number of results required: ");
                    // The user is promted to enter a postive integer number which is passed to the below function.
                    retrieveTopNPostsWithMostLikes(getUserIntInput(scanner, true));
                    break;
                case 5:
                    System.out.print("Enter the number of results required: ");
                    // The user is promted to enter a postive integer number which is passed to the below function.
                    retrieveTopNPostsWithMostShares(getUserIntInput(scanner, true));
                    break;
                case 6:
                    System.out.println("Thanks for using Social Media Analyzer!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 6);

        scanner.close();
    }

    private void displayMainMenu(){
        
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("> Select from main menu");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("1) Add a social media post");
        System.out.println("2) Delete an existing social media post");
        System.out.println("3) Retrieve a social media post");
        System.out.println("4) Retrieve the top N posts with most likes");
        System.out.println("5) Retrieve the top N posts with most shares");
        System.out.println("6) Exit");
        System.out.print("Please select: ");
    }

    /**
     * Function responsible to take a integer input from the user. 
     * It recursively asks user to input a correct integer if invalid data is entered.
     * @param scanner
     * @param isPositive If true, accepts and returns only postive integer from the user.
     * @return int
     */
    private int getUserIntInput(Scanner scanner, Boolean isPositive){
        try{
            int userInput = scanner.nextInt();
            if(isPositive && userInput < 0){
                throw new InvalidInputDataType("");
            }
            return userInput;

        } catch (InputMismatchException e) {
            System.out.println("Incorrect integer input. Please try again.");
            scanner.nextLine();
            System.out.print("Enter integer value: ");
            return getUserIntInput(scanner, isPositive);
        } catch (InvalidInputDataType e) {
            System.out.println("Incorrect integer input.");
            System.out.print("Please enter a positive value: ");
            scanner.nextLine();
            return getUserIntInput(scanner, isPositive);
        }
    }

    /**
     * Function responsible to take user input for adding a post.
     * @param scanner
     * @return A list of object with a length of 6, containing all user inputs.
     */
    private List<Object> getUserInputForAddPost(Scanner scanner){
        System.out.print("Please provide the post ID: ");
        int postId = getUserIntInput(scanner, false);
        scanner.nextLine();

        System.out.print("Please provide the post content: ");
        String content = scanner.nextLine();

        System.out.print("Please provide the post author: ");
        String author = scanner.nextLine();

        System.out.print("Please provide the number of likes of the post: ");
        int likes = getUserIntInput(scanner, true);

        System.out.print("Please provide the number of shares of the post: ");
        int shares = getUserIntInput(scanner, true);
        
        scanner.nextLine();
        
        System.out.print("Please provide the date and time of the post in the format of DD/MM/YYYY HH:MM: ");
        String dateTime = scanner.nextLine();

        return Arrays.asList(postId, content, author, likes, shares, dateTime);
    }

    /**
     * Validates the user input and asks the collection class to add a new post based on the user input.
     * @param userInput
     */
    public void addPost(List<Object> userInput) {
        try{
            LocalDateTime dateTimeParsed = Utils.parseDate(userInput.get(5).toString());
            Post post = new Post(
                Integer.parseInt(userInput.get(0).toString()),
                userInput.get(1).toString(), 
                userInput.get(2).toString(), 
                Integer.parseInt(userInput.get(3).toString()), 
                Integer.parseInt(userInput.get(4).toString()), 
                dateTimeParsed);
            
            menuCollection.addPost(post);

            System.out.println("The post has been added to the collection!");
        }
        catch(InvalidInputDataType e){
            System.out.println(e.getMessage());
            System.out.println("Skipping Insertion");
        } catch(DuplicatePost e){
            System.out.println("Duplicate post ID. Skipping Insertion.");
        } catch(Exception e){
            System.out.println("Invalid input. Skipping Insertion.");
        }
    }

    public void deletePost(int postId) {
        boolean found = menuCollection.deletePost(postId);
        if (found) {
            System.out.println("The post has been deleted from the collection!");
        } else {
            System.out.println("Sorry, the post does not exist in the collection!");
        }
    }

    public void retrievePost(int postId) {
        Post retrivedPost = menuCollection.retrievePost(postId);
        if(retrivedPost != null){
            System.out.println(retrivedPost);
        }else{
            System.out.println("Post with the given ID does not exist");
        }
    }

    public void retrieveTopNPostsWithMostLikes(int sizeOfReturn) {
        ArrayList<Post> topPosts = menuCollection.getTopLikedPosts(sizeOfReturn);
        for(Post post: topPosts){
            System.out.println(post);
        }
    }

    public void retrieveTopNPostsWithMostShares(int sizeOfReturn) {
        ArrayList<Post> topPosts = menuCollection.getTopSharedPosts(sizeOfReturn);
        for(Post post: topPosts){
            System.out.println(post);
        }
    }
}
