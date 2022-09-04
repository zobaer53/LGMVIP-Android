package com.zobaer53.covid19tracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zobaer53.covid19tracker.R;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    TextView  recovered, active, confirmed, deceased,covidStatusTextView;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button btntrack;
    Iterator<String> subkeys;
    JSONObject obj2;
    EditText search;
    ArrayList<String> stateNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recovered = findViewById(R.id.tv_recovered);
        active = findViewById(R.id.tv_active);
        confirmed = findViewById(R.id.tv_confirmed);
        deceased = findViewById(R.id.tv_deceased);
        covidStatusTextView  = findViewById(R.id.covidStatusTextView);
        search = findViewById(R.id.searchCity);

        fetchDataState();

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollViewstats);
        pieChart = findViewById(R.id.piechart);
        btntrack = findViewById(R.id.btntrack);

        simpleArcLoader.refreshArcLoaderDrawable(new ArcConfiguration(this));

        btntrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    for(String s :stateNames){
                        if(s.equalsIgnoreCase(charSequence.toString())){
                            pieChart.clearChart();
                            fetchData(charSequence.toString());
                            if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                                search.setText("");
                                search.setHint("Search State");

                            }
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fetchData("Goa");
    }

    private void fetchData(String s) {
        String url = "https://data.covid19india.org/state_district_wise.json";
        simpleArcLoader.start();

        @SuppressLint("SetTextI18n") StringRequest request = new StringRequest(Request.Method.GET, url, response -> {

            try {
                covidStatusTextView.setText(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()+" Covid Status");
                JSONObject jsonObject = new JSONObject(response);
                Log.i("Tag","sub keys perimeter= "+s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
                    JSONObject obj1 = jsonObject.getJSONObject(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
                     obj2 = obj1.getJSONObject("districtData");
                     subkeys = obj2.keys();

                JSONObject obj3 = obj2.getJSONObject(subkeys.next());

                recovered.setText(obj3.getString("recovered"));
                active.setText(obj3.getString("active"));
                deceased.setText(obj3.getString("deceased"));
                confirmed.setText(obj3.getString("confirmed"));

                Log.i("Tag","objects= "+obj3.getString("recovered")+" ,"+obj3.getString("active")
                +" ,"+obj3.getString("deceased")+" ,"+obj3.getString("confirmed"));

                pieChart.addPieSlice(new PieModel("confirmed", Integer.parseInt(confirmed.getText().toString()), Color.parseColor("#FFA726")));
                pieChart.addPieSlice(new PieModel("recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                pieChart.addPieSlice(new PieModel("deceased", Integer.parseInt(deceased.getText().toString()), Color.parseColor("#EF5350")));
                pieChart.addPieSlice(new PieModel("active", Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6F6")));
                pieChart.startAnimation();

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);


            }
            catch (JSONException e) {
                e.printStackTrace();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }

        }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            simpleArcLoader.stop();
            simpleArcLoader.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void fetchDataState() {

        String url = "https://data.covid19india.org/state_district_wise.json";

        StringRequest req = new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject object = new JSONObject(response);
                Iterator<String> keys=object.keys();
                    while(keys.hasNext()){
                        String subkey=keys.next();
                      //  Log.i("Tag","sub keys= "+subkey);
                        stateNames.add(subkey);
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(req);
    }
}
