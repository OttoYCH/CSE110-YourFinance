package teamhardcoder.y_fi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.model.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yfi);

        Runnable runnable = new Runnable() {
            public void run() {


                DynamoDBMapper mapper = DatabaseHelper.getDBMapper(getApplicationContext());


                mapper.save(new PersonalExpense("1234", 9487, "p1", "none"));
                mapper.save(new PersonalExpense("2345", 9487.92, "p2", "none"));
                mapper.save(new PersonalExpense("3456", 9487.123, "p3", "none"));

                mapper.save(new GroupExpense("9876", 1234, "g1", "none"));
                mapper.save(new GroupExpense("8765", 1234.92, "g2", "none"));
                mapper.save(new GroupExpense("6543", 1234.123, "g3", "none"));

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<PersonalExpense> result1 = mapper.scan(PersonalExpense.class, scanExpression);
                PaginatedScanList<GroupExpense> result2 = mapper.scan(GroupExpense.class, scanExpression);

                for(PersonalExpense ea: result1){
                    System.out.println(ea.getAmount());
                }

                System.out.println("Next");

                for(GroupExpense ea: result2){
                    System.out.println(ea.getAmount());
                }

                /*
                Set<String> res = mapper.load(Group.class,"12341231").getMember();
                for(String each: res){
                    System.out.println(each);
                }
                */

                /*
                Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
                eav.put(":val", new AttributeValue().withS("Andrew"));

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                        .withFilterExpression("contains(memberSet,:val)").withExpressionAttributeValues(eav);

                PaginatedList<Group> result = mapper.scan(Group.class, scanExpression);
                for(Group ea: result){
                    System.out.println(ea.getGroupName());
                }
                */

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
