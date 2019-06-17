package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.models.Address;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Calendar;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.MyViewHolder> {

    ArrayList<Address> mAddresses;
    LayoutInflater inflater;
    Context context;

    public AddressesAdapter(Context context, ArrayList<Address> addresses) {
        inflater = LayoutInflater.from(context);
        this.mAddresses = addresses;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_address, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Address selectedAddress = mAddresses.get(position);
        holder.setData(selectedAddress, position);
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewAddressTitle, textViewAddressAddress, textViewAddressReceiverName;
        ImageView imageViewRemoveUserAddress;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewAddressTitle = (TextView) itemView.findViewById(R.id.textViewAddressTitle);
            textViewAddressAddress = (TextView) itemView.findViewById(R.id.textViewAddressAddress);
            textViewAddressReceiverName = (TextView) itemView.findViewById(R.id.textViewAddressReceiverName);
            imageViewRemoveUserAddress = (ImageView) itemView.findViewById(R.id.imageViewRemoveUserAddress);

            imageViewRemoveUserAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int itemPosition = MyViewHolder.this.getAdapterPosition();
                    final Address address = mAddresses.get(itemPosition);
                    CustomerController.removeUserAddress(view.getContext(), SharedPrefManager.getInstance(view.getContext()).getUser().getId(), address.getId(), new ProductCallback<ArrayList<Address>>() {
                        @Override
                        public void onSuccess(ArrayList<Address> addresses, String message) {
                            mAddresses.remove(itemPosition);
                            AddressesAdapter.this.notifyDataSetChanged();
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception exception) {
                            Toast.makeText(view.getContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            itemView.setOnClickListener(this);
        }

        public void setData(Address selectedAddress, int position) {
            this.textViewAddressTitle.setText(selectedAddress.getTitle());
            this.textViewAddressAddress.setText(selectedAddress.getAddress());
            this.textViewAddressReceiverName.setText(selectedAddress.getReceiverName());
        }


        @Override
        public void onClick(final View view) {

            int itemPosition = this.getAdapterPosition();
            final Address address = mAddresses.get(itemPosition);
            //TODO: CLICK ADDRESS TO DETAILS PAGE
            //Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
            //productDetailsActivity.putExtra("creditCardId", creditCard.getId());
            //view.getContext().startActivity(productDetailsActivity);

        }

    }
}
