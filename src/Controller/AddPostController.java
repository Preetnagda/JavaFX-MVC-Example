package src.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.Utils;
import src.custom_exception.DuplicatePost;
import src.custom_exception.InvalidInputDataType;
import src.model.Post;
import src.model.PostBuilder;
import src.model.dao.PostDAOImpl;

public class AddPostController extends LoggedInController {
    
    @FXML
    private TextArea content;
    @FXML
    private TextField likes;
    @FXML
    private TextField shares;

    @FXML
    public void cancelAddPost(ActionEvent event){
        switchScene("Dashboard", user);
    }

    @FXML
    public void addPostRequest(ActionEvent event){

        if(!isAuth()){
            return;
        }

        PostBuilder postBuilder = new PostBuilder();

        postBuilder.addAuthor(user.getFirstname());
        postBuilder.addContent(content.getText());
        postBuilder.addPostId("-1");
        
        try {
            postBuilder.addPostDate(Utils.getStringFromDate(LocalDateTime.now()));
        } catch (InvalidInputDataType e) {
            // generateError("Invalid Date, please enter the date in the given format.");
            return;
        }
        try {
            postBuilder.addLikes(likes.getText());
        } catch (NumberFormatException e) {
            generateError("Invalid Likes, please enter a valid integer");
            return;
        }
        try {
            postBuilder.addShares(shares.getText());
        } catch (NumberFormatException e) {
            generateError("Invalid Shares, please enter a valid integer");
            return;
        }

        Post newPost = postBuilder.build();

        PostDAOImpl postDAO = new PostDAOImpl();

        try {
            postDAO.addPost(newPost, user);
            switchScene("Dashboard", user);
        } catch (SQLException e) {
            generateError("Unable to add Post at this moment, please try again later");
        } catch(DuplicatePost e){
            generateError("A Post with the same ID already exists, please try with a different ID");
        }
    }

}
