package src.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import src.Model.User;
import src.View.BaseScene;

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
        BaseScene newScene = switchScene("UpdateProfile");
        LoggedInController newController = (LoggedInController) newScene.getController();
        newController.setUser(user);
    }

}
