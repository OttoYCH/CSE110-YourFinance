package teamhardcoder.y_fi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import com.amazonaws.services.iotdata.model.ConflictException;
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
        new GoodTask(getApplicationContext()).execute();

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

class GoodTask extends AsyncTask<String, Void, List> {
    private UserManager um;
    private TextView txtOutput;
    private Context context;
    public GoodTask(Context context) {
        this.context = context;

    }

    @Override
    public List<PersonalExpense> doInBackground(String... params) {

        um = ManagerFactory.getUserManager(context);


        System.out.println(um.getUser());

        return null;

    }

    @Override
    public void onPostExecute(List l) {

        //return l;
    }
}
