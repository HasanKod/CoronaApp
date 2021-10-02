package com.hk.loginsignupscreen.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hk.loginsignupscreen.R;

public class SignUp3rdClass extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);
        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);
    }

    public void callNextSignupScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);

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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
        startActivity(intent, options.toBundle());

    }
}