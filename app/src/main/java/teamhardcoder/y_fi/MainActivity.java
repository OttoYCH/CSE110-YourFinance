package teamhardcoder.y_fi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

import com.amazonaws.ClientConfiguration;
import android.util.Log;
import android.widget.TextView;

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
import teamhardcoder.y_fi.database.manager.PersonalExpenseManager;
import teamhardcoder.y_fi.database.manager.UserManager;
import teamhardcoder.y_fi.database.model.DatabaseHelper;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import teamhardcoder.y_fi.database.data.PersonalExpense;
import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.*;

import static com.amazonaws.regions.Regions.US_WEST_2;


public class MainActivity extends AppCompatActivity {

    private TextView txtOutput;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private static final String userPoolId = "us-east-1_wEcepq0Ae";
    private static final String clientId = "cont3titmir4n9s9tl28uc2vc";
    private static final String clientSecret = "iijc04v0aon1hugng6d3u44siobcch3t105m7le2uv6fm0gra4s";
    private static final Regions cognitoRegion = Regions.US_EAST_1;
    private static CognitoUserPool userPool;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yfi);


        ClientConfiguration clientConfiguration = new ClientConfiguration();
        AmazonCognitoIdentityProvider cipClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
        cipClient.setRegion(Region.getRegion(cognitoRegion));
        userPool = new CognitoUserPool(getApplicationContext(), userPoolId, clientId, clientSecret, cipClient);


        // Create a CognitoUserAttributes object and add user attributes
        /*CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        // Add the user attributes. Attributes are added as key-value pairs
        // Adding user's given name.
        // Note that the key is "given_name" which is the OIDC claim for given name
        userAttributes.addAttribute("given_name", "Yu-Ching");

        // Adding user's phone number
        userAttributes.addAttribute("phone_number", "+18582304615");

        // Adding user's email address
        userAttributes.addAttribute("email", "yuh198@ucsd.edu");

        SignUpHandler signupCallback = new SignUpHandler() {

            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful
                System.out.println("Test Success!");
                // Check if this user (cognitoUser) needs to be confirmed
                if(!userConfirmed) {
                    // This user must be confirmed and a confirmation code was sent to the user
                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                    // Get the confirmation code from user
                }
                else {
                    // The user has already been confirmed
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-up failed, check exception for the cause
                System.out.println("Test Failed");
                System.out.println(exception.getMessage());
            }
        };

        userPool.signUpInBackground("ottohu", "Ott@1234", userAttributes, null, signupCallback);
*/
        CognitoUser user = userPool.getUser("ottohu");
        AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

            public void onSuccess(CognitoUserSession cognitoUserSession) {
                System.out.println("Sucess");
            }

            public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice cognitoDevice) {
                System.out.println("Sucess");
            }

            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                // The API needs user sign-in credentials to continue
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, "Ott@1234", null);

                // Pass the user sign-in credentials to the continuation
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                // Allow the sign-in to continue
                authenticationContinuation.continueTask();
            }

            public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
                System.out.println("Sucess");
            }

            public void authenticationChallenge(ChallengeContinuation challengeContinuation) {
                System.out.println("Sucess");
            }

            public void onFailure(Exception exception) {
                // Sign-in failed, check exception for the cause
                System.out.println("Sign in failed");
                System.out.println(exception.getMessage());
            }
        };

        // Sign in the user
        user.getSessionInBackground(authenticationHandler);
        System.out.println("123");

        System.out.println(user.getUserId());

        System.out.println("456");



















        txtOutput = (TextView) findViewById(R.id.textView2);
        btn1 = (Button) findViewById(R.id.button2);
        btn2 = (Button) findViewById(R.id.button3);
        btn3 = (Button) findViewById(R.id.button4);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                GoodTask2 g =new GoodTask2(getApplicationContext(),txtOutput);
                g.execute("12345");
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            /**
             * The method for the click website button action
             * @param V the view of the click action
             */
            @Override
            public void onClick(View V) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        GroupManager manager = ManagerFactory.getGroupManager(getApplicationContext());
                        UserManager um = ManagerFactory.getUserManager(getApplicationContext());
                        PersonalExpenseManager pm = ManagerFactory.getPersonalExpenseManager(getApplicationContext());
                        System.out.println(um);
                        User u = new User("12345","abc@ucsd.edu","5566","abc@ucsd.edu");
                        System.out.println(u.getUserId());
                        PersonalExpense p;
                        List<PersonalExpense> l = pm.getPersonalExpense(u.getUserId());
                        System.out.println(l.size());

                        for (PersonalExpense g : l) {
                            pm.deleteExpense(g.getExpenseId());
                        }
                        for (int i = 0; i < 5; i++) {
                            boolean x = pm.createExpense(new PersonalExpense(u.getUserId(), 100.00 * i, "Ralphs", "Grocery"));
                            System.out.println(x);
                        }

                    }
                };
                Thread mythread = new Thread(runnable);
                mythread.start();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            private List<PersonalExpense> l;
            /**
             * The method for the click website button action
             * @param V the view of the click action
             */
            @Override
            public void onClick(View V) {

                GoodTask g = new GoodTask(getApplicationContext(), txtOutput);
                g.execute(new PersonalExpense("123",500,"test","food"));


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


            }
        });
        //Thread mythread = new Thread(runnable);

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

class GoodTask extends AsyncTask<PersonalExpense, Void, List> {
    private PersonalExpenseManager pm;
    private List<PersonalExpense> l;
    private TextView txtOutput;
    public GoodTask(Context context, TextView txtOutput) {
        pm = ManagerFactory.getPersonalExpenseManager(context);
        this.txtOutput = txtOutput;
        System.out.println(pm == null);
    }

    @Override
    public List<PersonalExpense> doInBackground(PersonalExpense... params) {

        //try {
        System.out.println(params[0]);
        pm.createExpense(params[0]);

        //l = pm.getPersonalExpense(params[0]);

        System.out.println("Running doInBackground");
        return l;

    }

    @Override
    public void onPostExecute(List l) {
        txtOutput.setText(l.get(0).toString());
        //return l;
    }
}


class GoodTask2 extends AsyncTask<String, Void, List<PersonalExpense>> {
    private PersonalExpenseManager pm;
    //private List<PersonalExpense> l;
    private TextView txtOutput;
    public GoodTask2(Context context, TextView txtOutput) {
        pm = ManagerFactory.getPersonalExpenseManager(context);
        this.txtOutput = txtOutput;
        System.out.println(pm == null);
    }

    @Override
    public List<PersonalExpense> doInBackground(String... params) {

        System.out.println(params[0]);
        System.out.println("Running doInBackground");
        return pm.getPersonalExpense(params[0]);
    }

    @Override
    public void onPostExecute(List<PersonalExpense> l) {
        txtOutput.setText(l.get(0).getExpenseId());
        /* return l; */
    }
}
