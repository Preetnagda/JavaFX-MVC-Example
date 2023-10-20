package src.Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import src.Post;
import src.PostCollection;
import src.CustomExceptions.DuplicatePost;

public class TestPostCollection {

    private HashMap<Integer, Post> postMap;

    public PostCollection populateMap() throws DuplicatePost{
        PostCollection pcollection = new PostCollection();
        ArrayList<Post> postList = new ArrayList<Post>(postMap.values());
        pcollection.addBulkPosts(postList);
        return pcollection;
    }

    @Before
    public void setUp(){
        postMap = new HashMap<Integer, Post>();
        String[][] postData = {
            {"1", "Post Content", "Post Author", "10", "10"},
            {"2", "Post Content", "Post Author", "11", "11"},
            {"3", "Post Content", "Post Author", "12", "12"},
            {"4", "Post Content", "Post Author", "13", "13"},
            {"5", "Post Content", "Post Author", "14", "15"},
        };

        for (String[] postStrings : postData) {
            Post newpost = new Post(Integer.parseInt(postStrings[0]), postStrings[1], postStrings[2], Integer.parseInt(postStrings[3]), Integer.parseInt(postStrings[4]), LocalDateTime.now());
            postMap.put(Integer.parseInt(postStrings[0]), newpost);
        }
    }

    @Test
    public void testaddBulkPostsWithoutDuplicates(){
        ArrayList<Post> postList = new ArrayList<Post>(postMap.values());
        PostCollection pcollection = new PostCollection();

        try {
            pcollection.addBulkPosts(postList);
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test(expected = DuplicatePost.class)
    public void testaddBulkPostsWithDuplicates() throws DuplicatePost{
        ArrayList<Post> postList = new ArrayList<Post>(postMap.values());
        Post newPost = new Post(1, "Post Content", "Post Author", 10, 10, LocalDateTime.now());
        postList.add(newPost);
        PostCollection pcollection = new PostCollection();
        pcollection.addBulkPosts(postList);
    }

    @Test
    public void testAddNonDuplicatePost() throws DuplicatePost{
        PostCollection pcollection = new PostCollection();
        Post newPost = new Post(50, "Post Content", "Post Author", 10, 10, LocalDateTime.now());
        pcollection.addPost(newPost);
    }

    @Test(expected = DuplicatePost.class)
    public void testAddDuplicatePost() throws DuplicatePost{
        PostCollection pcollection = new PostCollection();
        Post newPost = new Post(1, "Post Content", "Post Author", 10, 10, LocalDateTime.now());
        pcollection.addPost(newPost);
        pcollection.addPost(newPost);
    }

    @Test
    public void testretrievePost() throws DuplicatePost{
        PostCollection pcollection = populateMap();

        Post newPost = pcollection.retrievePost(1);
        assertEquals("Correct post retrived", newPost.getId(), 1);
        assertEquals("Correct post retrived", newPost.getLikes(), 10);
    }

    @Test
    public void testdeletePost() throws DuplicatePost{
        PostCollection pcollection = populateMap();
        Boolean isDeleted = pcollection.deletePost(1);
        if(!isDeleted){
            fail();
        }

        assertEquals("Post deleted", pcollection.retrievePost(1), null);
    }

    @Test
    public void testgetTopLikedPosts() throws DuplicatePost{
        PostCollection pcollection = populateMap();
        ArrayList<Post> postList = pcollection.getTopLikedPosts(3);
        assertEquals("Correct amount of posts retrived", postList.size(), 3);
        assertEquals("Post with most likes on top", postList.get(0).getLikes(), 14);

    }

    @Test
    public void testgetTopSharedPosts() throws DuplicatePost{
        PostCollection pcollection = populateMap();
        ArrayList<Post> postList = pcollection.getTopSharedPosts(3);
        assertEquals("Correct amount of posts retrived", postList.size(), 3);
        assertEquals("Post with most shares on top", postList.get(0).getShares(), 15);
    }

}
