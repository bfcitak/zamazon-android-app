package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.activities.ProductDetailsActivity;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.WishList;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class CreditCardsAdapter extends RecyclerView.Adapter<CreditCardsAdapter.MyViewHolder> {

    ArrayList<CreditCard> mCreditCards;
    LayoutInflater inflater;
    Context context;

    public CreditCardsAdapter(Context context, ArrayList<CreditCard> creditCards) {
        inflater = LayoutInflater.from(context);
        this.mCreditCards = creditCards;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_credit_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CreditCard selectedCreditCard = mCreditCards.get(position);
        holder.setData(selectedCreditCard, position);
    }

    @Override
    public int getItemCount() {
        return mCreditCards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewCreditCardNumber, textViewCreditCardNameOnCard, textViewCreditCardExpiryDate;
        ImageView imageViewRemoveUserCreditCard;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewCreditCardNumber = (TextView) itemView.findViewById(R.id.textViewCreditCardNumber);
            textViewCreditCardNameOnCard = (TextView) itemView.findViewById(R.id.textViewCreditCardNameOnCard);
            textViewCreditCardExpiryDate = (TextView) itemView.findViewById(R.id.textViewCreditCardExpiryDate);
            imageViewRemoveUserCreditCard = (ImageView) itemView.findViewById(R.id.imageViewRemoveUserCreditCard);

            imageViewRemoveUserCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int itemPosition = MyViewHolder.this.getAdapterPosition();
                    final CreditCard creditCard = mCreditCards.get(itemPosition);
                    CustomerController.removeUserCreditCard(view.getContext(), SharedPrefManager.getInstance(view.getContext()).getUser().getId(), creditCard.getId(), new ProductCallback<ArrayList<CreditCard>>() {
                        @Override
                        public void onSuccess(ArrayList<CreditCard> creditCards, String message) {
                            mCreditCards.remove(itemPosition);
                            CreditCardsAdapter.this.notifyDataSetChanged();
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

        public void setData(CreditCard selectedCreditCard, int position) {
            String secretCartNumber = selectedCreditCard.getCardNumber().substring(0, 4) + " **** **** " + selectedCreditCard.getCardNumber().substring(12, 16);
            this.textViewCreditCardNumber.setText(secretCartNumber);
            this.textViewCreditCardNameOnCard.setText(selectedCreditCard.getNameOnCard());
            this.textViewCreditCardExpiryDate.setText(selectedCreditCard.getExpiryDate());
        }


        @Override
        public void onClick(final View view) {

            int itemPosition = this.getAdapterPosition();
            final CreditCard creditCard = mCreditCards.get(itemPosition);
            //TODO: CLICK CREDIT CARD TO DETAILS PAGE
            //Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
            //productDetailsActivity.putExtra("creditCardId", creditCard.getId());
            //view.getContext().startActivity(productDetailsActivity);

        }

    }
}
