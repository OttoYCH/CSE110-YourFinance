package teamhardcoder.y_fi.database.model;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * Created by otto on 2/13/17.
 */

public class DatabaseHelper {

    private static DynamoDBMapper mapper = null;

    public static DynamoDBMapper getDBMapper(Context context) {
        if (mapper == null) {

            // Initialize the Amazon Cognito credentials provider
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    context,
                    "us-west-2:8e1f3028-b4d6-43a0-9f15-5d8ad1cd3734", // Identity Pool ID
                    Regions.US_WEST_2 // Region
            );

            AmazonDynamoDBClient ddbClient = Region.getRegion(Regions.US_WEST_2) // CRUCIAL
                    .createClient(
                            AmazonDynamoDBClient.class,
                            credentialsProvider,
                            new ClientConfiguration()
                    );

            mapper = new DynamoDBMapper(ddbClient);
        }

        return mapper;
    }


}
