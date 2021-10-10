package com.hk.loginsignupscreen.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    //Variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context; //the activity calling the java class

    //Session names
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";


    //User session variables
    private static final String IS_LOGIN = "isLoggedIn";     //static final >> the user shouldn't be able to change the variables from outside the calss
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";

    //Remember me variables
    private static final String IS_REMEMBERME = "isRememberMe";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNumber";
    public static final String KEY_SESSIONPASSWORD = "password";


    //Constructor
    public SessionManager(Context _context, String sessionName) {
        context = _context;
        //Check whether a session with name userLoginSession exists and create one if not
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);

        //allow the session to edit the data
        editor = userSession.edit();

    }

// ************  User login session functions ************

    public void creatLoginSession(String fullName, String username, String email, String phoneNumber, String password, String age, String gender) {

        //Store the values in key-value pairs in the session
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, age);
        editor.putString(KEY_GENDER, gender);

        editor.commit();
    }

    public HashMap<String, String> getUsersDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        userData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return userData;
    }

    public boolean checkLogin() {
        return userSession.getBoolean(IS_LOGIN, false);
    }

    public void logoutUserFromSession() {
        //Clear all data from the session
        editor.clear();
        editor.commit();
    }

    // ************  Remember me session functions ************
    public void createRememberMeSession(String phoneNumber, String password) {

        //Store the values in key-value pairs in the session
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONPHONENUMBER, phoneNumber);
        editor.putString(KEY_SESSIONPASSWORD, password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONPHONENUMBER, userSession.getString(KEY_SESSIONPHONENUMBER, null));
        userData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));


        return userData;
    }

    public boolean checkRememberMe() {
        return userSession.getBoolean(IS_REMEMBERME, false);
    }


}
