package com.hk.loginsignupscreen.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.hk.loginsignupscreen.Common.Adapter;
import com.hk.loginsignupscreen.Common.ApiUtilities;
import com.hk.loginsignupscreen.Common.LoginSignup.Login;
import com.hk.loginsignupscreen.Common.LoginSignup.StartupScreen;
import com.hk.loginsignupscreen.Common.ModelClass;
import com.hk.loginsignupscreen.Databases.SessionManager;
import com.hk.loginsignupscreen.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    TextView textView = findViewById(R.id.textView);

//    SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSION);
//    HashMap<String, String> userDetails = sessionManager.getUsersDetailsFromSession();
//
//    String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
//    String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

//    textView.setText(fullName + "\n" + phoneNumber);


    CountryCodePicker countryCodePicker;
    TextView mtodaytotal, mtotal, mactive, mrecovered, mtodayrecovered, mdeaths, mtodaydeaths;

    ImageView backBtn;
    String country;
    TextView mfilter;
    Spinner spinner;
    String[] types = {"Cases", "Deaths", "Recovered", "Active"};
    private List<ModelClass> modelClassList;
    private List<ModelClass> modelClassList2;
    PieChart mpiechart;
    private RecyclerView recyclerView;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        countryCodePicker = findViewById(R.id.ccp);
        mactive = findViewById(R.id.activecase);
        mdeaths = findViewById(R.id.totaldeath);
        mtodaydeaths = findViewById(R.id.todaydeath);
        mrecovered = findViewById(R.id.recoveredcase);
        mtodayrecovered = findViewById(R.id.todayrecovered);
        mtotal = findViewById(R.id.totalcase);
        mtodaytotal = findViewById(R.id.todaytotal);
        mpiechart = findViewById(R.id.piechart);
        spinner = findViewById(R.id.spinner);
        mfilter = findViewById(R.id.filter);
        recyclerView = findViewById(R.id.recyclerview);
        backBtn = findViewById(R.id.dashboard_back_button);
        modelClassList = new ArrayList<>();
        modelClassList2 = new ArrayList<>();


        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);


        ApiUtilities.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList2.addAll(response.body());
                //adapter.notify
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

        adapter = new Adapter(getApplicationContext(), modelClassList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        countryCodePicker.setDefaultCountryUsingNameCode("NL");
        countryCodePicker.resetToDefaultCountry();
        country = countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchdata();
            }
        });

        fetchdata();

    }

    private void fetchdata() {
        ApiUtilities.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassList.addAll(response.body());
                for (int i = 0; i < modelClassList.size(); i++) {
                    if (modelClassList.get(i).getCountry().equals(country)) {

                        mactive.setText((modelClassList.get(i).getActive()));
                        mtodaydeaths.setText((modelClassList.get(i).getTodayDeaths()));
                        mtodayrecovered.setText((modelClassList.get(i).getTodayRecovered()));
                        mtodaytotal.setText((modelClassList.get(i).getTodayCases()));
                        mtotal.setText((modelClassList.get(i).getCases()));
                        mdeaths.setText((modelClassList.get(i).getDeaths()));
                        mrecovered.setText((modelClassList.get(i).getRecovered()));


                        int active, total, recovered, deaths;

                        active = Integer.parseInt((modelClassList.get(i).getActive()));
                        total = Integer.parseInt((modelClassList.get(i).getCases()));
                        recovered = Integer.parseInt((modelClassList.get(i).getRecovered()));
                        deaths = Integer.parseInt((modelClassList.get(i).getDeaths()));


                        updategraph(active, total, recovered, deaths);


                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });
    }

    private void updategraph(int active, int total, int recovered, int deaths) {
        mpiechart.clearChart();
        mpiechart.addPieSlice(new PieModel("Confirmed", total, Color.parseColor("#FCC729")));
        mpiechart.addPieSlice(new PieModel("Active", active, Color.parseColor("#27db8a")));
        mpiechart.addPieSlice(new PieModel("Recovered", recovered, Color.parseColor("#2cbedb")));
        mpiechart.addPieSlice(new PieModel("Deaths", deaths, Color.parseColor("#990c2f")));
        mpiechart.setLegendColor(R.color.black);
        mpiechart.startAnimation();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = types[position];
        mfilter.setText(item);
        adapter.filter(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void callLoginScreen(View view) {

        Intent intent = new Intent(getApplicationContext(), Login.class);

        //Add transition
        //Number of elements we want to animate
        Pair[] pairs = new Pair[1];

        // View: the element in the xml (image, text, anything..)
        // String: the name of the transition
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_btn");

        //Call the next activity and add the transition to it
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Dashboard.this, pairs);
        startActivity(intent, options.toBundle());
        Toast.makeText(Dashboard.this, "U bent uitgelogd!", Toast.LENGTH_SHORT).show();

    }
}

