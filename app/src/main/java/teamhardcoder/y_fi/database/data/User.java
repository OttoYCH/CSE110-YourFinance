package teamhardcoder.y_fi.database.data;

/**
 * Created by Andrew on 2/13/17.
 */


public class User {

    private String userId; // username
    private String email;
    private String password;

    public User(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password=" + password +
                ", email=" + email +
                "}";
    }
}
