package src.Controller;

import src.Model.User;

public class LoggedInController extends Controller {
    protected User user;

    public void setUser(User user){
        this.user = user;
    }

    protected Boolean isAuth(){
        if(this.user == null){
            return false;
        }
        return true;
    }
}
