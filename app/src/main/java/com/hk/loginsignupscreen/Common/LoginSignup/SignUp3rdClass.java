package com.hk.loginsignupscreen.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.hk.loginsignupscreen.R;

public class SignUp3rdClass extends AppCompatActivity {

    //Variables
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;
    Button login;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
        login = findViewById(R.id.signup_login_btn);
        backBtn = findViewById(R.id.signup_back_button);

    }

    public void callVerifyOTPScreen(View view) {

        //Validate fields
        if (!validatePhoneNumber()) {
            return;
        }

        //When validation succeeded, move to next screen to verify phone number and save data

        //Get all values passed from previous screens using Intent
        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");

        //Get phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();

        //Add the country code to the phone number
        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("username", _username);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);


        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private boolean validatePhoneNumber() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Telefoonnummer mag niet leeg zijn!");
            phoneNumber.requestFocus();
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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public void call2ndSignupScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
        startActivity(intent, options.toBundle());

    }
}