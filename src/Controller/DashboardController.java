package src.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import src.Model.User;

public class DashboardController extends LoggedInController{

    @FXML
    private Text usernameGreeting;

    @Override
    public void setUser(User user){
        super.setUser(user);
        usernameGreeting.setText(user.getFirstname());
    }

    @FXML
    public void updateProfile(ActionEvent event){
        switchScene("UpdateProfile", user);
    }

    @FXML
    public void addPost(ActionEvent event){
        switchScene("AddPost", user);
    }

}
