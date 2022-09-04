package com.zobaer53.covid19tracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zobaer53.covid19tracker.R;
import com.zobaer53.covid19tracker.adapter.Adapter;
import com.zobaer53.covid19tracker.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ImageButton button;

    public static List<Model> ModelList = new ArrayList<>();
    Model model;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button =findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.super.onBackPressed();
            }
        });

        listView = findViewById(R.id.listView);
        fetchData();

    }

    private void fetchData() {

        String url = "https://data.covid19india.org/state_district_wise.json";

        StringRequest req = new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject object = new JSONObject(response);
                Iterator<String> keys=object.keys();

                while(keys.hasNext()){
                    String key=keys.next();
                    JSONObject obj1 = object.getJSONObject(key);
                    JSONObject obj2 = obj1.getJSONObject("districtData");
                    Iterator<String> subkeys=obj2.keys();


                    while(subkeys.hasNext()){
                        String subkey=subkeys.next();
                        Log.i("Tag","sub keys= "+subkey);
                        JSONObject obj3 = obj2.getJSONObject(subkey);
                        JSONObject obj4 = obj3.getJSONObject("delta");
                        String active = obj3.getString("active");
                        String confirmed = obj3.getString("confirmed");
                        String migratedother = obj3.getString("migratedother");
                        String deceased = obj3.getString("deceased");
                        String recovered = obj3.getString("recovered");
                        String dconfirmed = obj4.getString("confirmed");
                        String ddeceased = obj4.getString("deceased");
                        String drecovered = obj4.getString("recovered");

                        model = new Model(subkey, active, confirmed, migratedother,deceased,recovered,
                                dconfirmed,ddeceased,drecovered);
                        ModelList.add(model);
                    }
                }
                adapter = new Adapter(HomeActivity.this, ModelList);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(req);
    }
}