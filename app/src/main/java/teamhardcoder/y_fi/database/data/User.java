package teamhardcoder.y_fi.database.data;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Andrew on 2/13/17.
 */


@DynamoDBTable(tableName = "USER")
public class User {

    private String userId; // useraccount
    private String password;
    private String nickname;
    private String email;

    public User(){

    }

    public User(String userId, String password, String nickname, String email){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "nickname")
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password=" + password +
                ", nickname=" + nickname +
                ", email=" + email +
                "}";
    }
}
