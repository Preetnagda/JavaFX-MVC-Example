package src.Controller;

import src.Model.AuthUser;
import src.View.BaseScene;

public class LoggedInController extends Controller {
    protected AuthUser user;

    public void setUser(AuthUser user){
        this.user = user;
    }

    public BaseScene switchScene(String sceneType, AuthUser user){
        BaseScene newScene = super.switchScene(sceneType);
        LoggedInController newController = (LoggedInController) newScene.getController();
        newController.setUser(user);
        newController.initWithUser();
        return newScene;
    }

    public void initWithUser(){}

    protected Boolean isAuth(){
        if(this.user == null){
            return false;
        }
        return true;
    }
}
