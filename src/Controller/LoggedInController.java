package src.controller;

import src.view.BaseScene;
import src.model.AuthUser;

/**
 * The LoggedInController class extends the Controller class and provides functionality specific to views
 * that can only be used by logged in customers.
 */
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
