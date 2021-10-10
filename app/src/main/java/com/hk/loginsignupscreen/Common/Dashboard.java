package com.hk.loginsignupscreen.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.hk.loginsignupscreen.Databases.SessionManager;
import com.hk.loginsignupscreen.R;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails = sessionManager.getUsersDetailsFromSession();

        String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
        String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

        textView.setText(fullName + "\n" + phoneNumber);
    }
}