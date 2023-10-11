package src.Controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.CustomExceptions.DuplicatePost;
import src.CustomExceptions.InvalidInputDataType;
import src.Model.Post;
import src.Model.PostBuilder;
import src.Model.DAO.PostDAOImpl;

public class AddPostController extends LoggedInController {
    
    @FXML
    private TextField postID;
    @FXML
    private TextField author;
    @FXML
    private TextArea content;
    @FXML
    private TextField likes;
    @FXML
    private TextField shares;
    @FXML
    private TextField date;

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

        postBuilder.addAuthor(author.getText());
        postBuilder.addContent(content.getText());

        try {
            postBuilder.addPostId(postID.getText());
        } catch (NumberFormatException e) {
            generateError("Invalid Post ID, please enter a valid integer");
            return;
        }

        try {
            postBuilder.addPostDate(date.getText());
        } catch (InvalidInputDataType e) {
            generateError("Invalid Date, please enter the date in the given format.");
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
            postDAO.addPost(newPost);
            switchScene("Dashboard", user);
        } catch (SQLException e) {
            generateError("Unable to add Post at this moment, please try again later");
        } catch(DuplicatePost e){
            generateError("A Post with the same ID already exists, please try with a different ID");
        }
    }

}
