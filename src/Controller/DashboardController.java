package src.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import src.Utils;
import src.CustomExceptions.DuplicatePost;
import src.CustomExceptions.InvalidInputDataType;
import src.Model.AuthUser;
import src.Model.Post;
import src.Model.DAO.PostDAOImpl;
import src.Model.DAO.UserDAOImpl;
import src.Model.DAO.PostCondition;
import src.View.PostView;

public class DashboardController extends LoggedInController{

    @FXML
    private Text usernameGreeting;

    @FXML
    private VBox postContainer;

    @FXML
    private Button postsTypeSwitchButton;

    @FXML
    private Button sortByLikeButton;

    @FXML
    private TextField postSearchById;

    @FXML
    private TextField topNpostsField;

    @FXML
    private Text accountStatus;

    @FXML
    private Hyperlink vipHyperlink;

    @FXML
    private Button analysisScreenButton;

    @FXML
    private Button bulkImportButton;
    
    private Boolean isPostTypeAll;
    private Boolean isSorted;

    private PostCondition condition;

    @Override
    public void setUser(AuthUser user){
        super.setUser(user);
        usernameGreeting.setText(user.getFirstname());
        String status = "Non-VIP";
        vipHyperlink.setText("Become a VIP now");
        if(user.getIsVip() == 1){
            status = "VIP";
            vipHyperlink.setText("Cancel VIP subscription");
            analysisScreenButton.setDisable(false);
            analysisScreenButton.setVisible(true);
            bulkImportButton.setDisable(false);
            bulkImportButton.setVisible(true);
        }
        accountStatus.setText("Account Status: " + status);
    }

    @Override
    public void initWithUser(){
        populateAllPosts();
        isPostTypeAll = true;
        isSorted = false;
        condition = new PostCondition();
    }

    @FXML
    public void onVipClick(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        if(user.getIsVip() == 0){
            alert.setContentText("Would you like to subscribe to the application for a monthly fee of $0?");
        }else{
            alert.setContentText("Are you sure you want to unsubscribe?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                AuthUser updatedUser;
                if(user.getIsVip() == 1){
                    updatedUser = new UserDAOImpl().updateUserVipStatus(user, 0);
                    generateNotification("VIP functionalities will not be available once you logout.", "error");
                }else{
                    updatedUser = new UserDAOImpl().updateUserVipStatus(user, 1);
                    generateNotification("Please log out and log in again to access VIP functionalities.", "success");

                }
            } catch (SQLException e) {
                generateError("Unable to update status. Please try later.");
            }
        } else {}
    }

    @FXML
    public void switchSortByLike(){
        isSorted = !isSorted;
        if(isSorted){
            condition.addCondition("sort", "likes", "desc");
            sortByLikeButton.setText("Unsort");
            generateNotification("Viewing sorted posts", "success");

        }else{
            condition.deleteCondition("sort", "likes");
            sortByLikeButton.setText("Sort By Likes");
            clearError();
        }
        populateAllPosts();
    }

    public void populateAllPosts(){
        PostDAOImpl postDAO = new PostDAOImpl();
        ArrayList<Post> posts = postDAO.retrievePosts(condition);
        populatePosts(posts);
    }

    public void populatePosts(ArrayList<Post> posts){
        postContainer.getChildren().clear();
        if(posts == null){
            return;
        }
        for(Post newPost: posts){
            ArrayList<String> permissions = new ArrayList<>();
            permissions.add("export");
            if(newPost.getUser() == user.getUserId()){
                permissions.add("delete");
            }
            PostView postView = new PostView(newPost, permissions);
            if(permissions.contains("delete")){
                postView.deleteMenuItem.setOnAction((event -> {
                    deletePost(newPost);
                }));
            }
            if(permissions.contains("export")){
                postView.exportMenuItem.setOnAction((event -> {
                    exportPost(newPost);
                }));
            }
            postContainer.getChildren().add(postView);
        }
    }

    public void exportPost(Post post){
        // Prompt the user to select a folder to save the CSV file
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Save CSV");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            String filePath = selectedDirectory.getAbsolutePath() + File.separator + String.format("post_%d.csv", post.getId());
            Utils.generatePostCsv(post, filePath);
            generateNotification("Post exported successfully", "success");
        }
    }

    public void deletePost(Post post){
        PostDAOImpl postDAO = new PostDAOImpl();
        postDAO.deletePost(post);
        generateNotification("Post Deleted Successfully", "success");
        populateAllPosts();
    }

    @FXML
    public void onTopNposts(ActionEvent event){
        if(topNpostsField.getText().isEmpty()){
            condition.deleteCondition("limit", "");
            populateAllPosts();
            clearError();
            return;
        }
        try{
            Integer n = Integer.parseInt(topNpostsField.getText());
            condition.addCondition("limit", "", Integer.toString(n));
            generateNotification("Posts filtered", "success");
            populateAllPosts();
        }catch(NumberFormatException e){
            generateError("Invalid input for N");
        }
    }

    @FXML
    public void postIdEntered(ActionEvent event){
        String value = postSearchById.getText();
        if(value.isEmpty()){
            condition.deleteCondition("where", "id");
            clearError();
        }else{
            condition.addCondition("where", "id", value);
            generateNotification("Posts filtered", "success");

        }
        populateAllPosts();
    }

    @FXML
    public void resetView(ActionEvent event){
        condition.resetCondition();
        postsTypeSwitchButton.setText("View My Posts");
        sortByLikeButton.setText("Sort By Likes");
        postSearchById.setText("");
        topNpostsField.setText("");
        clearError();
        populateAllPosts();
    }

    @FXML
    public void updateProfile(ActionEvent event){
        switchScene("UpdateProfile", user);
    }

    @FXML
    public void addPost(ActionEvent event){
        switchScene("AddPost", user);
    }

    public void retrievePost(ActionEvent event){
        switchScene("RetrievePost", user);
    }

    @FXML
    public void postsTypeSwitch(ActionEvent event){
        if(isPostTypeAll){
            postsTypeSwitchButton.setText("View All Posts");
            condition.addCondition(user);
            generateNotification("Viewing your posts", "success");

        }else{
            postsTypeSwitchButton.setText("View My Posts");
            condition.deleteCondition("where", "user_id");
            generateNotification("Viewing all posts", "success");
        }
        isPostTypeAll = !isPostTypeAll;
        populateAllPosts();
    }

    @FXML
    public void onAnalysisScreen(ActionEvent event){
        switchScene("Analysis", user);
    }

    @FXML
    public void onBulkImport(ActionEvent event){
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        String selectedFile = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();
        try{
            PostDAOImpl postDAO = new PostDAOImpl();
            ArrayList<Post> posts = Utils.postsCSVReader(selectedFile);
            for (Post post : posts) {
                try{
                    postDAO.addPost(post, user);
                }catch(DuplicatePost e){
                    generateNotification("A post with given id already exists. Skipping post with ID "+ post.getId() , "error");
                }
            }
            generateNotification("Importing Finished", "success");
        }catch(InvalidInputDataType i){
            generateNotification(i.getMessage(), "error");
        }catch(FileNotFoundException i){
            generateNotification("Invalid file location.", "error");
        }catch(IOException i){
            generateNotification("Unable to read file. Please try again.", "error");
        } catch (SQLException e) {
            generateNotification("Unable to write to database. Please try again.", "error");
        }
        populateAllPosts();
    }

    @FXML
    public void logout(ActionEvent event){
        this.user = null;
        switchScene("Login");
    }
}
