package src.Controller;


import javafx.fxml.FXML;
import javafx.scene.text.Text;
import src.Model.User;

public class DashboardController extends Controller{

    private User user;

    @FXML
    private Text usernameGreeting;

    public DashboardController(User user){
        this.user = user;
    }

}
