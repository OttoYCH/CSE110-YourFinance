package teamhardcoder.y_fi.database.data;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Andrew on 2/13/17.
 */


@DynamoDBTable(tableName = "USER")
public class User {

    private String userId; // ID is email
    private String password;
    private String nickname;

    public User(){

    }

    public User(String userId, String password, String nickname){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;

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


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password=" + password +
                ", nickname=" + nickname +
                "}";
    }
}
