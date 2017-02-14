package teamhardcoder.y_fi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.data.Message;
import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.model.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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


                Group tmp = mapper.load(Group.class, "95b075ea-86ac-442c-abfc-878eeb8dd23e");
                System.out.println("Testing!! ");
                System.out.println("Hi Everyone!");


                Set<String> tgsa = new HashSet<String>();
                tgsa.add("otto");
                tgsa.add("chris");
                tgsa.add("aaron");
                tgsa.add("chelin");
                tgsa.add("hyc");
                tgsa.add("kevin");
                tgsa.add("jenny");
                tgsa.add("amy");

                Set<String> torreyPines = new HashSet<String>();
                torreyPines.add("otto");
                torreyPines.add("chris");
                torreyPines.add("hyc");

                Set<String> oms = new HashSet<String>();
                oms.add("aaron");
                oms.add("andrew");
                oms.add("chelin");
                oms.add("silvia");
                oms.add("alice");
                oms.add("sherry");
                oms.add("jenny");

                Set<String> database = new HashSet<String>();
                database.add("otto");
                database.add("andrew");
                database.add("chelin");
                database.add("jessica");

                GroupManager manager = ManagerFactory.getGroupManager(getApplicationContext());
                manager.createGroup(new Group("OMS Club", oms));
                manager.createGroup(new Group("Torrey Pines Village", torreyPines));
                manager.createGroup(new Group("TGSA", tgsa));
                manager.createGroup(new Group("Database Dev", database));

                List<Group> result = manager.getAllGroupsOfUser("otto");
                for (Group g: result) {
                    System.out.println(g.getGroupName());
                }
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<PersonalExpense> result1 = mapper.scan(PersonalExpense.class, scanExpression);


                for(PersonalExpense ea: result1){
                    System.out.println(ea);
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
