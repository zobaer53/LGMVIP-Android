package com.zobaer53.covid19tracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.zobaer53.covid19tracker.R;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private int positionCountry;
    TextView tvcountry,tvcases,tvtodaycases,tvdeaths,tvtodaydeaths,tvrecovered,tvactive,tvcritical,txtp1,txtp2;
    ImageView imageviewflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvcountry = findViewById(R.id.tv_detailcn);
        tvcases = findViewById(R.id.tv_detailcases);
        tvtodaycases = findViewById(R.id.tv_detailtodaycases);
        tvdeaths = findViewById(R.id.tv_detaildeaths);
        tvtodaydeaths = findViewById(R.id.tv_detailtodaydeaths);
        tvrecovered = findViewById(R.id.tv_detailrecovered);
        tvactive = findViewById(R.id.tv_detailactive);
        tvcritical = findViewById(R.id.tv_detailcritical);
        imageviewflag = findViewById(R.id.imageviewflag);
        txtp1 = findViewById(R.id.txtrecpercent);
        txtp2 = findViewById(R.id.txtdeathpercent);

/*
        tvcountry.setText(CountryActivity.countryModelList.get(positionCountry).getCountry());
        tvcases.setText(CountryActivity.countryModelList.get(positionCountry).getCases());
        tvtodaycases.setText(CountryActivity.countryModelList.get(positionCountry).getTodaycases());
        tvdeaths.setText(CountryActivity.countryModelList.get(positionCountry).getDeaths());
        tvtodaydeaths.setText(CountryActivity.countryModelList.get(positionCountry).getTodaydeaths());
        tvrecovered.setText(CountryActivity.countryModelList.get(positionCountry).getRecovered());
        tvactive.setText(CountryActivity.countryModelList.get(positionCountry).getActive());
        tvcritical.setText(CountryActivity.countryModelList.get(positionCountry).getCritical());
        Glide.with(getApplicationContext()).load(CountryActivity.countryModelList.get(positionCountry).getFlag()).into(imageviewflag);

        int cases = Integer.parseInt(CountryActivity.countryModelList.get(positionCountry).getCases());
        int rec = Integer.parseInt(CountryActivity.countryModelList.get(positionCountry).getRecovered());
        int death = Integer.parseInt(CountryActivity.countryModelList.get(positionCountry).getDeaths());


        int pdeath = (death*100)/cases;
        int prec = (rec*100)/cases;

        txtp1.setText("Recovered = "+prec+"%");
        txtp2.setText("Death = "+pdeath+"%");

 */
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
