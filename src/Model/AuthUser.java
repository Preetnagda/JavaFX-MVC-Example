package src.Model;

public class AuthUser extends User {

    private Integer id;

    public AuthUser(Integer id, String firstname, String lastname, String username, String password, Integer is_vip) {
        super(firstname, lastname, username, password, is_vip);
        this.id = id;
    }
    public AuthUser(Integer id, String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
        this.id = id;
    }

    public Integer getUserId(){
        return id;
    }
}
