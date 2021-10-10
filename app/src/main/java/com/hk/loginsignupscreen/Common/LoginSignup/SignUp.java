package com.hk.loginsignupscreen.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hk.loginsignupscreen.R;

public class SignUp extends AppCompatActivity {


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    //Get data variables
    TextInputLayout fullname, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        fullname = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
    }

    public void call2ndSignupScreen(View view) {

        if (!validateFullName() | !validateUserName() | !validateEmail() | !validatePassword()) {
            return;
        }

        //Get all values entered
        String _fullName = fullname.getEditText().getText().toString().trim();
        String _email = email.getEditText().getText().toString().trim();
        String _username = username.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[4];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());

    }


    //Validation Functions

    private boolean validateFullName() {
        //trim to remove spaces in strings
        String val = fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Veld mag niet leeg zijn!");
            return false;
        } else {
            //remove the error
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUserName() {
        //trim to remove spaces in strings
        String val = username.getEditText().getText().toString().trim();
        //user may user any letter from A to z.
        //username may be only 20 characters long
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            username.setError("Veld mag niet leeg zijn!");
            return false;
        } else if (val.length() > 20) {
            username.setError("Gebruikersnaam mag maximaal 20 karakters lang zijn!");
            return false;
            // (!val.matches(checkspaces) = value doesn't match with value of the methode checkspaces
        } else if (!val.matches(checkspaces)) {
            username.setError("Spaties zijn niet toegestaan!");
            return false;
        } else {
            //remove the error
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {
        //trim to remove spaces in strings
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Veld mag niet leeg zijn!");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Ongeldig e-mail!");
            return false;
        } else {
            //remove the error
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        //trim to remove spaces in strings
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&*+=])" +   //as least 1 special character
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Veld mag niet leeg zijn!");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Wachtwoord moet minstens 4 tekens bevatten!");
            return false;
        } else {
            //remove the error
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }


}