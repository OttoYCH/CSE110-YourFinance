package teamhardcoder.y_fi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
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


public class MainActivity extends AppCompatActivity {

    private TextView txtOutput;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yfi);

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
                        User u = new User();
                        u.setUserId("12345");
                        u.setName("Huang");
                        u.setEmail("abc@ucsd.edu");
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

                GoodTask g =new GoodTask(getApplicationContext(),txtOutput);
                g.execute("12345");
                //System.out.println(l == null);
                //g.onPostExecute();
                //
                /*Runnable runnable = new Runnable() {
                    public void run() {

                        //System.out.println(l.size());
                        //txtOutput.setText(l.get(0).toString());
                    }
                };
                Thread mythread = new Thread(runnable);
                mythread.start();

                while (l==null) {
                    System.out.println("I'm null");
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {

                    }
                }*/
                //System.out.println("I'm not null");
                /**/

            }

            /*class GoodTask extends AsyncTask<String, Void, List> {
                PersonalExpenseManager pm;
                private List<PersonalExpense> l;
                /*public GoodTask(Context context) {
                    pm = ManagerFactory.getPersonalExpenseManager(context);
                    System.out.println(pm == null);
                }
                @Override
                public List<PersonalExpense> doInBackground(String... params) {
                    pm = ManagerFactory.getPersonalExpenseManager(getApplicationContext());
                    try {
                    System.out.println(params[0]);
                    l = pm.getPersonalExpense(params[0]);
                    System.out.println("Running doInBackground");
                    return l;
        } catch (Exception e) {
            System.out.println("Exception");
            return null;
        }
                }

                @Override
                protected void onPostExecute(List result) {
                    System.out.println("Running post");
                    txtOutput.setText(result.get(0).toString());
                    //return l;
                }

            }*/



        });



        /*Runnable runnable = new Runnable() {
            public void run() {


                //DynamoDBMapper mapper = DatabaseHelper.getDBMapper(getApplicationContext());


                //Group tmp = mapper.load(Group.class, "95b075ea-86ac-442c-abfc-878eeb8dd23e");
                System.out.println("-------- Start --------");
                //System.out.println(tmp);

                GroupManager manager = ManagerFactory.getGroupManager(getApplicationContext());
                UserManager um = ManagerFactory.getUserManager(getApplicationContext());
                PersonalExpenseManager pm = ManagerFactory.getPersonalExpenseManager(getApplicationContext());
                System.out.println(um);
                User u = new User();
                u.setUserId("12345");
                u.setName("Huang");
                u.setEmail("abc@ucsd.edu");
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
                l = pm.getPersonalExpense(u.getUserId());
                System.out.println(l.size());
                for (PersonalExpense g : l) {
                    System.out.println(g.toString());
                    g.setUserId("1234");
                    pm.editExpense(g);
                }
                l = pm.getPersonalExpense(u.getUserId());
                System.out.println(l.size());
                for (PersonalExpense g : l) {
                    System.out.println(g.toString());
                }
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
                manager.createGroup(new Group("PTT Celebrity", ptt_celeb));*/

                /*
                List<Group> result = manager.getAllGroupsOfUser("otto");
                for (Group g: result) {
                    System.out.println(g.getGroupName());
                }*/
                /*
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                PaginatedScanList<Group> result = mapper.scan(Group.class, scanExpression);


                for(Group g: result){
                    System.out.println(g);
                    if (g.getGroupName().equals("Torrey Pines Village")) {
                        String tmp_g = g.getGroupName();
                        g.setGroupName("UTC Nerds");
                        System.out.println("Change group name from '" + tmp_g + "' to '" + g.getGroupName() + "'");
                    }
                }



            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();*/

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
    private PersonalExpenseManager pm;
    private List<PersonalExpense> l;
    private TextView txtOutput;
    public GoodTask(Context context, TextView txtOutput) {
        pm = ManagerFactory.getPersonalExpenseManager(context);
        this.txtOutput = txtOutput;
        System.out.println(pm == null);
    }

    @Override
    public List<PersonalExpense> doInBackground(String... params) {

        //try {
        System.out.println(params[0]);
        l = pm.getPersonalExpense(params[0]);
        System.out.println("Running doInBackground");
        return l;
        /*} catch (Exception e) {
            System.out.println("Exception");
            return null;
        }*/
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

        //try {
        System.out.println(params[0]);
        //l = pm.getPersonalExpense(params[0]);
        System.out.println("Running doInBackground");
        return pm.getPersonalExpense(params[0]);
        /*} catch (Exception e) {
            System.out.println("Exception");
            return null;
        }*/
    }

    @Override
    public void onPostExecute(List<PersonalExpense> l) {
        txtOutput.setText(l.get(0).getExpenseId());
        /* return l; */
    }
}