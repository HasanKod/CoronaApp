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

import com.hk.loginsignupscreen.R;

public class StartupScreen extends AppCompatActivity {

    //Variables
    Button login_btn, signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);

        //Hooks
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);


    }


    public void callLoginScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), Login.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(login_btn, "transition_login");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartupScreen.this, pairs);
        startActivity(intent, options.toBundle());

    }


    public void callSignUpScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(signup_btn, "transition_login");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartupScreen.this, pairs);
        startActivity(intent, options.toBundle());

    }
}