package teamhardcoder.y_fi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

public class Register extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mNameView;
    private EditText mPasswordView;
    private EditText mReEnterPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (EditText) findViewById(R.id.Register_email);
        mNameView = (EditText) findViewById(R.id.Register_name);
        mPasswordView = (EditText) findViewById(R.id.Register_password);
        mReEnterPasswordView = (EditText) findViewById(R.id.Register_Repassword);

        Button mRegisterButton = (Button) findViewById(R.id.Register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPasswordMatch()) {
                    mReEnterPasswordView.setError("The password doesn't match!");
                } else if (!isEmailValid(mEmailView.getText().toString())) {
                    mEmailView.setError(getString(teamhardcoder.y_fi.R.string.error_invalid_email));
                } else {
                    UserRegisterTask mRegisTask = new UserRegisterTask(mEmailView,
                            mPasswordView.getText().toString(), mNameView.getText().toString(), getApplicationContext());
                    mRegisTask.execute();
                }
            }
        });
    }

    boolean checkPasswordMatch() {
        return mPasswordView.getText().toString().equals(mReEnterPasswordView.getText().toString());
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private EditText emailView;
        private Context context;
        private User u;

        UserRegisterTask(EditText email, String password, String name, Context context) {
            u = new User(email.getText().toString(), password, name);
            emailView = email;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            UserManager um = ManagerFactory.getUserManager(context);
            return um.createUser(u);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!success)
                emailView.setError("The email is already in use!");
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle("Congratulations");
                builder.setMessage("You successfully sign up for\nYour Finance!");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent((Register.this), Login.class);
                        startActivity(intent);
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent intent = new Intent((Register.this), Login.class);
                            startActivity(intent);
                        }
                    });
                }
                builder.create().show();

            }
        }
    }
}
