package src.tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import src.model.Post;

public class TestPost {
    
    @Test
    public void testCreatePost(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y H:m");
        LocalDateTime dateTime = LocalDateTime.parse("23/08/2023 19:26", formatter);
        Post newPost = new Post(1, "New content", "author string", 10, 10, dateTime);

        assertEquals("Post with correct data created", newPost.getId(), 1);
        assertEquals("Post with correct data created", newPost.getContent(), "New content");
        assertEquals("Post with correct data created", newPost.getAuthor(), "author string");
        assertEquals("Post with correct data created", newPost.getLikes(), 10);
        assertEquals("Post with correct data created", newPost.getShares(), 10);
        assertEquals("Post with correct data created", newPost.getCreationDate(), dateTime);
    }
}
