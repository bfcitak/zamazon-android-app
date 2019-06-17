package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.activities.AddressesActivity;
import com.tantuni.zamazon.activities.ChangePasswordActivity;
import com.tantuni.zamazon.activities.CreditCardsActivity;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.MyViewHolder> {

    ArrayList<String> itemArray;
    LayoutInflater inflater;

    public ProfileMenuAdapter(Context context, ArrayList<String> itemArray) {
        inflater = LayoutInflater.from(context);
        this.itemArray = itemArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_profile_menu, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String selectedItem = itemArray.get(position);
        holder.setData(selectedItem, position);
    }

    @Override
    public int getItemCount() {
        return itemArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewMenuItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewMenuItem = (TextView) itemView.findViewById(R.id.textViewMenuItem);
            itemView.setOnClickListener(this);
        }

        public void setData(String selectedItem, int position) {
            this.textViewMenuItem.setText(selectedItem);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = this.getAdapterPosition();
            switch (itemPosition) {
                case 0:
                    Intent changePasswordActivity = new Intent(view.getContext(), ChangePasswordActivity.class);
                    view.getContext().startActivity(changePasswordActivity);
                    break;
                case 1:
                    break;
                case 2:
                    Intent addressActivity = new Intent(view.getContext(), AddressesActivity.class);
                    view.getContext().startActivity(addressActivity);
                    break;
                case 3:
                    Intent creditCardsActivity = new Intent(view.getContext(), CreditCardsActivity.class);
                    view.getContext().startActivity(creditCardsActivity);
                    break;
                case 4:
                    SharedPrefManager.getInstance(view.getContext()).logout();
                    break;
            }
        }
    }
}
