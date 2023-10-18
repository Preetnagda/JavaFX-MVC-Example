package src.Model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.Utils;

/**
 * Post implemets a social media post.
 */

public class Post{
    private int id;
    private String content;
    private String author;
    private int likes;
    private int shares;
    private LocalDateTime dateTime;
    private int userId;

    public Post(int id, String content, String author, int likes, int shares, LocalDateTime dateTime){
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
    }

    public void setUser(int userId){
        this.userId = userId;
    }

    public int getUser(){
        return userId;
    }

    public int getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }

    public int getLikes(){
        return this.likes;
    }

    public int getShares(){
        return this.shares;
    }

    public String getAuthor(){
        return this.author;
    }

    public LocalDateTime getCreationDate(){
        return this.dateTime;
    }

    public String toString(){
        // The format allows to create a tabular like output while representing a class object as string.
        String printFormat = "%-6d%-8s%-70s%-15d%-15d%s%n";

        // Convert Date time to string in order to get output in the expected format.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Utils.DATE_FORMAT);
        
        return String.format(printFormat, this.id, this.author, this.content, this.likes, this.shares, this.dateTime.format(formatter));
    }
}