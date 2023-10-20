package src.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.Utils;
import src.model.Post;
import src.model.dao.PostDAOImpl;

public class RetrievePostController extends LoggedInController {
    
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
    private void cancelRetrievePost(ActionEvent event){
        switchScene("Dashboard", user);
    }

    @FXML
    private void retrievePostRequest(ActionEvent event){
        emptyFields();
        Post retrievedPost;

        if(!isAuth()){
            return;
        }

        PostDAOImpl postDAO = new PostDAOImpl();

        try {
            retrievedPost = postDAO.retrievePost(Integer.parseInt(postID.getText()));
        } catch (NumberFormatException e) {
            generateError("Invalid Post ID");
            return;
        }

        if(retrievedPost == null){
            generateError("Post not found");
            return;
        }

        author.setText(retrievedPost.getAuthor());
        content.setText(retrievedPost.getContent());
        likes.setText(Integer.toString(retrievedPost.getLikes()));
        shares.setText(Integer.toString(retrievedPost.getShares()));
        date.setText(Utils.getStringFromDate(retrievedPost.getCreationDate()));
        clearError();
    }

    private void emptyFields(){
        author.setText("");
        content.setText("");
        likes.setText("");
        shares.setText("");
        date.setText("");
    }

}
