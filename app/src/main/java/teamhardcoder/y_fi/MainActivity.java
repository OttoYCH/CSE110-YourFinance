package teamhardcoder.y_fi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.ClientConfiguration;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AnonymousAWSCredentials;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProvider;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.GroupExpenseManager;
import teamhardcoder.y_fi.database.manager.GroupManager;
import teamhardcoder.y_fi.database.manager.ManagerFactory;

import teamhardcoder.y_fi.database.data.Group;
import teamhardcoder.y_fi.database.data.GroupExpense;
import teamhardcoder.y_fi.database.data.Message;
import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.manager.MessageManager;
import teamhardcoder.y_fi.database.manager.UserManager;
import teamhardcoder.y_fi.database.model.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.amazonaws.regions.Regions.US_WEST_2;


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

                //User user = mapper.load(User.class,"Andrew");
                UserManager userManager = ManagerFactory.getUserManager(getApplicationContext());
                //userManager.createUser(new User("Gary Gillespie", "garycse110", "BroGary", "garyg@ucsd.edu"));
                System.out.println("-------- Start --------");

                //System.out.println("Check create User: " + userManager.createUser(new User("Gary Gillespie", "gary1111", "GGgary", "test123@ucsd.edu")));
                System.out.println("Check login: " + userManager.login("Gary Gillespie", "garycse110"));
                /*

                System.out.println("-------- Start --------");

                GroupManager manager = ManagerFactory.getGroupManager(getApplicationContext());
                MessageManager msgmanager = ManagerFactory.getMessageManager(getApplicationContext());
                GroupExpenseManager gemanager = ManagerFactory.getGroupExpenseManager(getApplicationContext());
                */
                /*
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

                Set<String> family56 = new HashSet<String>();
                family56.add("Chris56");
                family56.add("otto5566");
                family56.add("Andrew5566");
                family56.add("taiwan5566");
                family56.add("aaron5566");

                Set<String> ptt_celeb = new HashSet<String>();
                ptt_celeb.add("mayaman");
                ptt_celeb.add("iphone8ss");
                ptt_celeb.add("birdman5566");
                ptt_celeb.add("obov");
                ptt_celeb.add("Rambo");
                ptt_celeb.add("fallred");
                ptt_celeb.add("XXXXCat");
                ptt_celeb.add("sumade");

                manager.createGroup(new Group("OMS Club", oms));
                manager.createGroup(new Group("Torrey Pines Village", torreyPines));
                manager.createGroup(new Group("TGSA", tgsa));
                manager.createGroup(new Group("Database Dev", database));
                manager.createGroup(new Group("56 Family", family56));
                manager.createGroup(new Group("PTT Celebrity", ptt_celeb));
                */

               /* Set<String> test = new HashSet<String>();
                test.add("test5566");

                manager.createGroup(new Group("testTable", test));*/

                /*
                List<Group> result2 = manager.getAllGroupsOfUser("otto");
                for (Group g: result2) {
                    System.out.println(g.getGroupName());
                }*/

                /*Set<String> teamhardcode = new HashSet<String>();
                teamhardcode.add("Donald");
                teamhardcode.add("Sean");
                teamhardcode.add("Bryon");

                Group teamhard = new Group("Hard Coder", teamhardcode);
                manager.createGroup(teamhard);
                msgmanager.sendMessage(new Message(teamhard.getGroupId(), "12345", "Chill at home"));
                gemanager.createExpense(new GroupExpense(teamhard.getGroupId(), 87.87, "Phil's BBQ", "Eat"));*/

                // test deleteGroup
                /*
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<Group> result = mapper.scan(Group.class, scanExpression);

                for(Group g: result){
                    System.out.println(g);



                    //if (g.getGroupName().equals("Torrey Pines Village")) {
                    //    String tmp_g = g.getGroupName();
                    //    g.setGroupName("UTC Nerds");
                    //    System.out.println("Change group name from '" + tmp_g + "' to '" + g.getGroupName() + "'");
                    //}

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
