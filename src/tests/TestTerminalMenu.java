package src.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import src.TerminalMenu;

public class TestTerminalMenu{

    /**
     * Test inititating terminal menu without a proper csv path
     */
    @Test
    public void testInitatingTerminalMenu(){
        TerminalMenu menu = new TerminalMenu("");
        assertEquals("Terminal menu inititated", TerminalMenu.class, menu.getClass());
    }

    @Test
    public void testaddPost(){
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 5, 5, "25/08/2023 04:21");
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out));    

        menu.addPost(inputObject);

        // Test system output
        assertTrue(out.toString().contains("The post has been added to the collection!"));
    }

    @Test
    public void testaddInvalidPost(){
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 5, 5);
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out));    

        menu.addPost(inputObject);
        String outputString = out.toString();
        assertTrue(outputString.contains("Skipping Insertion"));
    }

    @Test
    public void testdeletePost(){
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 5, 5, "25/08/2023 04:21");
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out));    

        menu.addPost(inputObject);

        menu.deletePost(5);
        assertTrue(out.toString().contains("The post has been deleted from the collection!"));
    }

    @Test
    public void testdeleteNotExistingPost(){
        TerminalMenu menu = new TerminalMenu("");
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out));    
        menu.deletePost(5);
        assertTrue(out.toString().contains("Sorry, the post does not exist in the collection!"));
    }

    @Test
    public void testretrievePost(){
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 5, 5, "25/08/2023 04:21"); 
        menu.addPost(inputObject);
        System.setOut(new java.io.PrintStream(out));    
        menu.retrievePost(5);
        // Check if the printed string has the exact date as inserted for the post
        assertTrue(out.toString().contains("25/8/2023 4:21"));
    }

    @Test
    public void testretrieveNonPresentPost(){
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out));    
        TerminalMenu menu = new TerminalMenu("");  
        menu.retrievePost(5); 
        // Check if correct user acknowledment is printed
        assertTrue(out.toString().contains("Post with the given ID does not exist"));
    }

    @Test
    public void testTopNPostsWithMostLikes(){
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 6, 5, "25/08/2023 04:21"); 
        menu.addPost(inputObject);
        List<Object> inputObject2 = Arrays.asList(6, "","", 9, 5, "25/08/2023 04:21"); 
        menu.addPost(inputObject2);
        List<Object> inputObject3 = Arrays.asList(8, "","", 7, 5, "25/08/2023 04:21"); 
        menu.addPost(inputObject3);
        List<Object> inputObject4 = Arrays.asList(7, "","", 8, 5, "25/08/2023 04:21"); 
        menu.addPost(inputObject4);

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();   
        System.setOut(new java.io.PrintStream(out));    

        menu.retrieveTopNPostsWithMostLikes(3);

        String[] outputLines = out.toString().split("\n");

        // To get actual output instead of the ASCII value, use - '0'
        assertEquals("First result is the id of the post with most likes", 6, outputLines[0].charAt(0) - '0');

        assertEquals("Last result is the id of the post with least likes", 8, outputLines[4].charAt(0) - '0');
        
        //Every result has one line skipped after display except for the last one. Hence expect 5 lines.
        assertEquals("Three results are expected", 5, out.toString().split("\n").length);

    }

    @Test
    public void testTopNPostsWithMostShares(){
        TerminalMenu menu = new TerminalMenu("");
        List<Object> inputObject = Arrays.asList(5, "","", 10, 4, "25/08/2023 04:21"); 
        menu.addPost(inputObject);
        List<Object> inputObject2 = Arrays.asList(6, "","", 9, 15, "25/08/2023 04:21"); 
        menu.addPost(inputObject2);
        List<Object> inputObject3 = Arrays.asList(7, "","", 8, 10, "25/08/2023 04:21"); 
        menu.addPost(inputObject3);
        List<Object> inputObject4 = Arrays.asList(8, "","", 7, 12, "25/08/2023 04:21"); 
        menu.addPost(inputObject4);

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();   
        System.setOut(new java.io.PrintStream(out));    

        menu.retrieveTopNPostsWithMostShares(4);

        String[] outputLines = out.toString().split("\n");

        // To get actual output instead of the ASCII value, use - '0'
        assertEquals("First result is the id of the post with most shares", 6, outputLines[0].charAt(0) - '0');
        
        assertEquals("Last result is the id of the post with least shares", 5, outputLines[6].charAt(0) - '0');

        //Every result has one line skipped after display except for the last one. Hence expect 5 lines.
        assertEquals("Three results are expected", 7, out.toString().split("\n").length);

    }

}