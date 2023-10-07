package src.Controller;

import src.Model.User;
import src.View.BaseScene;

public class LoggedInController extends Controller {
    protected User user;

    public void setUser(User user){
        this.user = user;
    }

    public BaseScene switchScene(String sceneType, User user){
        BaseScene newScene = super.switchScene(sceneType);
        LoggedInController newController = (LoggedInController) newScene.getController();
        newController.setUser(user);
        return newScene;
    }

    protected Boolean isAuth(){
        if(this.user == null){
            return false;
        }
        return true;
    }
}
