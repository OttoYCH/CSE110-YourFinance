package teamhardcoder.y_fi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import teamhardcoder.y_fi.database.data.User;
import teamhardcoder.y_fi.database.manager.ManagerFactory;
import teamhardcoder.y_fi.database.manager.UserManager;

public class Settings extends AppCompatActivity {

    TextView textView_setting;
    EditText editNickName;
    EditText editText_oldPassword;
    EditText edit_password;
    EditText edit_password_confirm;
    Button newNameBtn;
    Button newPasswordBtn;
    Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(teamhardcoder.y_fi.R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(teamhardcoder.y_fi.R.id.toolbar);
        setSupportActionBar(toolbar);

        textView_setting = (TextView) findViewById(R.id.textView_setting);
        setPageTitle();


        editNickName = (EditText) findViewById(R.id.editNickName);
        editText_oldPassword = (EditText) findViewById(R.id.editText_oldPassword);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_password_confirm = (EditText) findViewById(R.id.edit_password_confirm);

        newNameBtn = (Button) findViewById(R.id.newNameBtn);
        newNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNameBtn.setEnabled(false);
                new editNickNameTask(getApplicationContext()).execute(editNickName.getText().toString());
            }
        });

        newPasswordBtn = (Button) findViewById(R.id.newPasswordBtn);
        newPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPasswordMatch()) {
                    edit_password_confirm.setError("The password doesn't match!");
                    edit_password_confirm.requestFocus();
                } else if (!isPasswordValid(edit_password.getText().toString())) {
                    edit_password.setError(getString(teamhardcoder.y_fi.R.string.error_invalid_password));
                } else {
                    newPasswordBtn.setEnabled(false);
                    String[] passwords = new String[2];
                    passwords[0] = editText_oldPassword.getText().toString();
                    passwords[1] = edit_password.getText().toString();
                    new editPasswordTask(getApplicationContext()).execute(passwords);
                }
            }
        });

        logout_btn = (Button) findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout_btn.setEnabled(false);
                Intent intent = new Intent(Settings.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setPageTitle() {
        String userName = ManagerFactory.getUserManager(getApplicationContext()).getUser().getNickname();
        textView_setting.setText("Hello " + userName);
    }

    private boolean checkPasswordMatch() {
        return edit_password.getText().toString().equals(edit_password_confirm.getText().toString());
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private class editNickNameTask extends AsyncTask<String, Void, Boolean> {

        private Context context;

        editNickNameTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            UserManager um = ManagerFactory.getUserManager(context);
            User u = um.getUser();
            u.setNickname(params[0]);
            return um.editUser(u);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            editNickName.setText("");
            setPageTitle();
            newNameBtn.setEnabled(true);
        }
    }

    private class editPasswordTask extends AsyncTask<String, Void, Boolean> {

        private Context context;

        editPasswordTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            UserManager um = ManagerFactory.getUserManager(context);
            User u = um.getUser();
            boolean rightPassword  = um.login(u.getUserId(), params[0]);
            if (!rightPassword)
                return false;
            else {
                u.setPassword(params[1]);
                um.editUser(u);
                return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                editText_oldPassword.setText("");
                edit_password.setText("");
                edit_password_confirm.setText("");
            } else {
                editText_oldPassword.setError("The password is incorrect");
                editText_oldPassword.requestFocus();
            }
            newPasswordBtn.setEnabled(true);
        }
    }
}
