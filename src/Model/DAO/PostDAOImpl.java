package src.Model.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import src.Utils;
import src.CustomExceptions.DuplicatePost;
import src.Model.AuthUser;
import src.Model.Post;
import src.Model.PostLikesComparator;
import src.Model.PostSharesComparator;

/**
 * PostCollection implements the collection of Posts. 
 * Responsible for storing, retriving, deleting and filtering multiple posts.
 * The Posts are stored in a HashMap JCF.
 */

public class PostDAOImpl implements PostDAO{
    private HashMap<Integer, Post> itemList;

    /**
     * The function is responsible for adding multiple posts at one time. 
     * It parses all non duplicate posts and throws exception if any duplicates are present.
     * @param bulkPosts
     * @throws DuplicatePost
     */
    public void addBulkPosts(ArrayList<Post> bulkPosts) throws DuplicatePost{
        // String exception = new String();
        // for(Post newPost: bulkPosts){
            // try{
                // addPost(newPost);
            // } catch (DuplicatePost e){
            //     exception =  exception + e.getMessage();
            // }
        // }
        
        // if(!exception.isEmpty()){
        //     throw new DuplicatePost(exception);
        // }
    }

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
     * Accepts a size of return and comparator
     * Sorts the post collection based on the comparator and returns the top "size of return" posts.
     * @param sizeOfReturn
     * @param comparator
     * @return
     */
    public ArrayList<Post> getTopPosts(int sizeOfReturn, Comparator<Post> comparator){
        if(sizeOfReturn > itemList.size()){
            sizeOfReturn = itemList.size();
            System.out.println();
            System.out.println("Only " + Integer.toString(sizeOfReturn) + " posts available. Showing " + Integer.toString(sizeOfReturn) + " results.");
            System.out.println();
        }

        ArrayList<Post> postsList = new ArrayList<>(itemList.values());
        Collections.sort(postsList, comparator);
        ArrayList<Post> filteredList = new ArrayList<>(postsList.subList(0, sizeOfReturn));
        return filteredList;
    }

    /**
     * Passes the comparator responsible for sorting on likes, to the getTopPosts function
     * @param sizeOfReturn
     * @return
     */
    public ArrayList<Post> getTopLikedPosts(int sizeOfReturn){
        PostLikesComparator postLikeComparator = new PostLikesComparator();
        return getTopPosts(sizeOfReturn, postLikeComparator);
    }

    /**
     * Passes the comparator responsible for sorting on shares, to the getTopPosts function
     * @param sizeOfReturn
     * @return
     */
    public ArrayList<Post> getTopSharedPosts(int sizeOfReturn){
        PostSharesComparator postSharesComparator = new PostSharesComparator();
        return getTopPosts(sizeOfReturn, postSharesComparator);
    }

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
