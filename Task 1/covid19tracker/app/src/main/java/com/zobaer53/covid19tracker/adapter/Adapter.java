package com.zobaer53.covid19tracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.zobaer53.covid19tracker.R;
import com.zobaer53.covid19tracker.model.Model;

import java.util.List;

public class Adapter extends ArrayAdapter<Model> {
    private final List<Model> ModelList;

    public Adapter(Context context, List<Model> ModelList) {
        super(context, R.layout.list_view_item, ModelList);

        this.ModelList = ModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, null,true);


        TextView state = v.findViewById(R.id.cityname);
        TextView active = v.findViewById(R.id.active);
        TextView confirmed = v.findViewById(R.id.confirm);
        TextView deceased = v.findViewById(R.id.deceased);
        TextView recovered = v.findViewById(R.id.recovered);





        state.setText(ModelList.get(position).getName());
        active.setText(ModelList.get(position).getActive());
        confirmed.setText(ModelList.get(position).getConfirmed());
        deceased.setText(ModelList.get(position).getDeceased());
        recovered.setText(ModelList.get(position).getRecovered());


        return v;
    }
}