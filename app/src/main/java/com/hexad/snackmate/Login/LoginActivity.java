package com.hexad.snackmate.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hexad.snackmate.HomePage;
import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseSession;
import com.parse.ParseUser;

import java.util.Locale;

/**
 * Created by Michael Ji on 11/9/2015.
 */
public class LoginActivity extends AppCompatActivity {
    private ConnectivityCheck connection;
    private boolean connected=false;
    Button btn_LoginIn = null;
    Button btn_SignUp = null;
    Button btn_ForgetPass = null;
    private EditText mUserNameEditText;
    private EditText mPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing Parse SDK
        onCreateParse();


        //Calling ParseAnalytics to see Analytics of our app
//        ParseAnalytics.trackAppOpened(getIntent());

        // creating connection detector class instance
        connection = new ConnectivityCheck(getApplicationContext());

        btn_LoginIn = (Button) findViewById(R.id.btn_login);
        btn_SignUp = (Button) findViewById(R.id.btn_signup);
        btn_ForgetPass = (Button) findViewById(R.id.btn_ForgetPass);
        mUserNameEditText = (EditText) findViewById(R.id.username);
        mPasswordEditText = (EditText) findViewById(R.id.password);

        setListenerForButtons();

    }


    public void onCreateParse() {
        //set up for parse objects
        ParseObject.registerSubclass(SnackItem.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "QrdsALfd999wYmrLRoD0sAuEnc7803FQ83bC9Dkn", "DYYdNF47NEUA4LXeRsLivt2RbTcwIhSevzo4iRVq");
        ParseUser.enableRevocableSessionInBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_forgot_password:
                forgotPassword();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void forgotPassword(){
		/*
		FragmentManager fm = getSupportFragmentManager();
	     ForgotPasswordDialogFragment forgotPasswordDialog = new ForgotPasswordDialogFragment();
	     forgotPasswordDialog.show(fm, null);
		 */
    }

    public void attemptLogin() {

        clearErrors();
        ParseUser.logOut();

        // Store values at the time of the login attempt.
        String username = mUserNameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.error_field_required));
            focusView = mPasswordEditText;
            cancel = true;
        } else if (password.length() < 4) {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            focusView =mPasswordEditText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserNameEditText.setError(getString(R.string.error_field_required));
            focusView = mUserNameEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // perform the user login attempt.
            login(username.toLowerCase(Locale.getDefault()), password);
        }
    }

    private void login(String lowerCase, String password) {
        // TODO Auto-generated method stub
        ParseUser.logInInBackground(lowerCase, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // TODO Auto-generated method stub
                if (e == null)
                    loginSuccessful();
                else
                    loginUnSuccessful();
            }
        });

    }


    protected void loginSuccessful() {
        // TODO Auto-generated method stub
        Intent in =  new Intent(LoginActivity.this,HomePage.class);
        startActivity(in);
    }
    protected void loginUnSuccessful() {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        showAlertDialog(LoginActivity.this,"Login", "Username or Password is invalid.", false);
    }

    private void clearErrors(){
        mUserNameEditText.setError(null);
        mPasswordEditText.setError(null);
    }

    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
//        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void setListenerForButtons(){


        btn_LoginIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // get Internet status
                connected = connection.isConnectingToInternet();
                // check for Internet status
                if (connected) {
                    // Internet Connection is Present
                    // make HTTP requests
                    attemptLogin();
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(LoginActivity.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }

            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent in =  new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(in);
            }
        });

        btn_ForgetPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent in =  new Intent(LoginActivity.this,ForgetPwdActivity.class);
                startActivity(in);
            }
        });

    }
}
