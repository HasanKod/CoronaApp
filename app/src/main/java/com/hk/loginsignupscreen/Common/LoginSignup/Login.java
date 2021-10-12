package com.hk.loginsignupscreen.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.hk.loginsignupscreen.Common.Dashboard;
import com.hk.loginsignupscreen.Databases.SessionManager;
import com.hk.loginsignupscreen.R;

import java.util.HashMap;

public class Login extends AppCompatActivity {


    //Variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    CheckBox rememberMe;
    TextInputEditText phoneNumberEditText, passwordEditText;
    Button signup_btn;
    ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);
        rememberMe = findViewById(R.id.remember_me);
        phoneNumberEditText = findViewById(R.id.login_phone_number_edit_text);
        passwordEditText = findViewById(R.id.login_password_edit_text);
        signup_btn = findViewById(R.id.signup_btn);
        backBtn = findViewById(R.id.login_back_button);

        //Check whether phone number and password are already stored in shared preferences or not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }

    public void letTheUserLogin(View view) {

        //Check internet connection
        if (!isConnected(Login.this)) {
            showCustomDialog();
        }

        //Validate username and password
        if (!validateFields()) {
            return;
        }
        //make the progressbar visible
        progressbar.setVisibility(View.VISIBLE);
        //Get data
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();

        //If the user enters 0 at the beginning of the phone number, the 0 will be deleted automatically
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        //Add the country code to the phone number
        final String _completePhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + _phoneNumber;

        //Check whether Remember me is checked and create a session if it is
        if (rememberMe.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(_phoneNumber, _password);
        }

        //Check whether the user exists in the database or not
        Query checkUser = FirebaseDatabase.getInstance("https://covid-19-tracker-2e278-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if there is some data arrives in this data snapshot, show no error
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    //data snapshot contains the data of all users, so we define the specific user we need the data for
                    //in the snapshot look for user with user id = complete Phone Number, look for the column password and get it's value
                    String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);

                    // check whether  the password entered by the user is the same as the password in the database
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dateOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                        String _username = snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                        String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);

                        //Create a session
                        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                        sessionManager.creatLoginSession(_fullname, _username, _email, _phoneNo, _password, _dateOfBirth, _gender);

                        startActivity(new Intent(getApplicationContext(), Dashboard.class));


                        Toast.makeText(Login.this, "Welkom terug\t"+_fullname, Toast.LENGTH_SHORT).show();


                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Het ingevoerde wachtwoord is onjuist!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Geen gebruiker gevonden met deze combinatie van gebruikersnaam en wachtwoord!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //make the progressbar unvisible when getting an error
                progressbar.setVisibility(View.GONE);
                //Show DatabaseError if there is an error
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        // setCancelable(false): so that the user cannot cancel the popup
        builder.setMessage("Maak a.u.b. verbinding met internet om verder te gaan!")
                .setCancelable(false)
                .setPositiveButton("Verbinden", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //open wifi settings
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Annuleren", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), StartupScreen.class));
                        finish();
                    }
                }).show();
    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Telefoonnummer mag niet leeg zijn!");
            phoneNumber.requestFocus();
            return false;
        } else if (_password.isEmpty()) {
            password.setError("Wachtwoord mag niet leeg zijn!");
            password.requestFocus();
            return false;
        } else {
            return true;
        }
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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public void callStartUpScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), StartupScreen.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(backBtn, "login_back_button_transition");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());
    }
}
