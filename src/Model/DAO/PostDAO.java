package src.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import src.custom_exception.DuplicatePost;
import src.model.AuthUser;
import src.model.Post;

public interface PostDAO {
    
    void addPost(Post newItem, AuthUser user) throws SQLException, DuplicatePost;
    Post retrievePost(int postID);
    ArrayList<Post> retrievePosts(PostCondition conditions);
    Boolean deletePost(Post post);
}
