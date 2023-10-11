package src.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.Utils;
import src.Model.Post;
import src.Model.DAO.PostDAOImpl;

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
    public void cancelRetrievePost(ActionEvent event){
        switchScene("Dashboard", user);
    }

    @FXML
    public void retrievePostRequest(ActionEvent event){
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
    }

    private void emptyFields(){
        author.setText("");
        content.setText("");
        likes.setText("");
        shares.setText("");
        date.setText("");
    }

}