package src.model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.Utils;
import src.model.AuthUser;
import src.model.Post;
import src.custom_exception.DuplicatePost;

/**
 * PostCollection implements the collection of Posts. 
 * Responsible for storing, retriving, deleting and filtering multiple posts.
 * The Posts are stored in a HashMap JCF.
 */

public class PostDAOImpl implements PostDAO{
    
    /**
     * Add a single post to the post collection.
     * @param post
     * @throws DuplicatePost
     */
    public void addPost(Post newItem, AuthUser user) throws SQLException, DuplicatePost{
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Utils.DATE_FORMAT);
            String sqlQuery = String.format("INSERT INTO Post (content, author, likes, shares, creationDate, user_id) VALUES ('%s', '%s', %d, %d, '%s', %d);",
                newItem.getContent(), newItem.getAuthor(), newItem.getLikes(), newItem.getShares(), formatter.format(newItem.getCreationDate()), user.getUserId());
            statement.executeUpdate(sqlQuery);
            dbCon.close();
        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();

            if(e.getMessage().contains("[SQLITE_CONSTRAINT_PRIMARYKEY]")){
                throw new DuplicatePost("Post already exists");
            }
            throw e;
        }
    }

    /**
     * Retrieve a post from the database by its ID.
     * 
     * @param postID The ID of the post to retrieve.
     * @return The retrieved Post object or null if not found.
     */
    public Post retrievePost(int postID){
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("SELECT * FROM Post WHERE id = '%s';", postID);
            ResultSet resultset = statement.executeQuery(sqlQuery);
            Post newPost = createPostInstance(resultset);
            dbCon.close();
            return newPost;
        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return null;
        }
    }

    /**
     * Retrieve a list of posts from the database based on specified conditions.
     * 
     * @param conditions The conditions for filtering the posts.
     * @return An ArrayList of Post objects that meet the specified conditions.
     */
    public ArrayList<Post> retrievePosts(PostCondition conditions){
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("SELECT * FROM Post");
            if(conditions != null && !conditions.toString().isEmpty()){
                sqlQuery += " " + conditions;
            }
            ArrayList<Post> posts = new ArrayList<Post>();
            ResultSet resultset = statement.executeQuery(sqlQuery);
            while(true){
                Post newPost = createPostInstance(resultset);
                if(newPost == null){
                    break;
                }
                posts.add(newPost);
            }
            dbCon.close();
            return posts;
        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return null;
        }
    }

    /**
     * Delete a post from the database.
     * 
     * @param post The Post object to be deleted.
     * @return true if the post is successfully deleted, false otherwise.
     */
    public Boolean deletePost(Post post){
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("DELETE FROM Post WHERE id = '%d';", post.getId());
            statement.executeUpdate(sqlQuery);
            dbCon.close();
            return true;
        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return false;
        }
    }

    /**
     * Create a Post object from a ResultSet query result.
     * 
     * @param queryResult The ResultSet containing the post data.
     * @return The created Post object or null if the ResultSet is empty.
     * @throws SQLException If there's an issue with parsing the data.
     */
    private Post createPostInstance(ResultSet queryResult) throws SQLException{
        Post newPost;
        if(!queryResult.next()){
            return null;
        }
        Integer id = (Integer) queryResult.getObject("id");
        String content = (String) queryResult.getObject("content");
        String author = (String) queryResult.getObject("author");
        Integer likes = (Integer) queryResult.getObject("likes");
        Integer shares = (Integer) queryResult.getObject("shares");
        String dateString = (String) queryResult.getObject("creationDate");
        LocalDateTime dateTime;
        try{
            dateTime = Utils.parseDate(dateString);
            newPost = new Post(id, content, author, likes, shares, dateTime);
            try {
                Integer user_id = (Integer) queryResult.getObject("user_id");
                newPost.setUser(user_id);  
            } catch (Exception e) {}
            return newPost;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
