package com.hk.loginsignupscreen.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hk.loginsignupscreen.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    //Get data variables
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd_class);

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.date_picker);
    }

    public void call3rdSignupScreen(View view) {

        if (!validateGender() | !validateAge()) {
            return;
        }

        //Get all values passed from previous screens using Intent
        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");

        //return the id of the selected radio button
        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());

        //local strings names starts with underscore "_"
        String _gender = selectedGender.getText().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String _date = day + "/" + month + "/" + year;

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);


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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private boolean validateGender() {
        // -1 means none of the buttons is being selected
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Selecteer geslacht a.u.b.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean validateAge() {
        //user must at least 12 years old to make an account
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 12) {
            Toast.makeText(this, "Gebruiker moet minimaal 12 jaar oud zijn!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void callLoginScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), Login.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(login, "transition_login");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
        startActivity(intent, options.toBundle());

    }
}