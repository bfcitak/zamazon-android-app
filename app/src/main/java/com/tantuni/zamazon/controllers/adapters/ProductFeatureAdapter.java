package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tantuni.zamazon.R;

import java.util.ArrayList;

public class ProductFeatureAdapter extends RecyclerView.Adapter<ProductFeatureAdapter.MyViewHolder> {

    ArrayList<String> featureKeys;
    ArrayList<String> featureValues;
    LayoutInflater inflater;

    public ProductFeatureAdapter(Context context, ArrayList<String> featureKeys, ArrayList<String> featureValues) {
        inflater = LayoutInflater.from(context);
        this.featureKeys = featureKeys;
        this.featureValues = featureValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_product_feature, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String selectedFeatureKey = featureKeys.get(position);
        String selectedFeatureValue = featureValues.get(position);
        holder.setData(selectedFeatureKey, selectedFeatureValue, position);
    }

    @Override
    public int getItemCount() {
        return featureKeys.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewProductFeatureKey, textViewProductFeatureValue;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewProductFeatureKey = (TextView) itemView.findViewById(R.id.textViewProductFeatureKey);
            textViewProductFeatureValue = (TextView) itemView.findViewById(R.id.textViewProductFeatureValue);
            itemView.setOnClickListener(this);
        }

        public void setData(String selectedFeatureKey, String selectedFeatureValue, int position) {
            this.textViewProductFeatureKey.setText(selectedFeatureKey);
            this.textViewProductFeatureValue.setText(selectedFeatureValue);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
