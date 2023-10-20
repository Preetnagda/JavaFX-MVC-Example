package src.model;

/**
 * The User class represents a user entity with attributes such as first name, last name, username,
 * password, and VIP status.
 */
public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Integer isVip;

    public User(String firstname, String lastname, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.isVip = 0;
    }

    public User(String firstname, String lastname, String username, String password, Integer isVip){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.isVip = isVip;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getIsVip() {
        return isVip;
    }
}
