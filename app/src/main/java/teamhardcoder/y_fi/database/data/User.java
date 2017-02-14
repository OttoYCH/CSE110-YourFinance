package teamhardcoder.y_fi.database.data;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Set;

/**
 * Created by otto on 2/13/17.
 */

@DynamoDBTable(tableName = "USER")
public class User {

    private String userId;
    private String name;
    private String email;
    private Set<String> categorySet;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "categorySet")
    public Set<String> getCategorySet() {
        return categorySet;
    }
    public void setCategorySet(Set<String> categorySet) {
        this.categorySet = categorySet;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name=" + name +
                ", email=" + email +
                ", categorySet{" + categorySet +
                "}";
    }
}
