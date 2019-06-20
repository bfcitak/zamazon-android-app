package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.MyViewHolder> {

    ArrayList<Product> mProductList;
    LayoutInflater inflater;

    public CheckoutProductAdapter(Context context, ArrayList<Product> products) {
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
        if (SharedPrefManager.getInstance(context).getUser()==null||SharedPrefManager.getInstance(context).getUser().getRoles().iterator().next().getRole().equals("CUSTOMER")) {
            for (Iterator<Product> iterator=mProductList.iterator();iterator.hasNext();) {
                Product product = iterator.next();
                if (!product.getActive()) {
                    iterator.remove();
                }
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_checkout_product, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productName, productDescription, productPrice, productCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.checkoutProductName);
            productDescription = (TextView) itemView.findViewById(R.id.checkoutProductDescription);
            productPrice = (TextView) itemView.findViewById(R.id.checkoutProductPrice);
            productCategory = (TextView) itemView.findViewById(R.id.checkoutProductCategory);
            // productImage = (ImageView) itemView.findViewById(R.id.productImage);

        }

        public void setData(Product selectedProduct, int position) {
            this.productName.setText(selectedProduct.getHeader());
            this.productDescription.setText(selectedProduct.getDescription());
            // this.productImage.setImageResource(selectedProduct.getImageId());
            this.productPrice.setText(selectedProduct.getPrice().intValue() + " TL");
            this.productCategory.setText(selectedProduct.getCategory().getName() + "/" + selectedProduct.getSubCategory().getName());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
