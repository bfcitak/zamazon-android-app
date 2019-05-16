package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.activities.ProductDetailsActivity;
import com.tantuni.zamazon.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    ArrayList<Product> mProductList;
    LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_product, parent, false);
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
        ImageView productImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDescription = (TextView) itemView.findViewById(R.id.productDescription);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productCategory = (TextView) itemView.findViewById(R.id.productCategory);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(this);
        }

        public void setData(Product selectedProduct, int position) {
            this.productName.setText(selectedProduct.getHeader());
            this.productDescription.setText(selectedProduct.getDescription());
            // this.productImage.setImageResource(selectedProduct.getImageId());
            this.productPrice.setText(selectedProduct.getPrice().intValue() + " TL");
            this.productCategory.setText(selectedProduct.getCategory() + "/" + selectedProduct.getSubCategory());
        }


        @Override
        public void onClick(View view) {
            int itemPosition = this.getAdapterPosition();
            Product item = mProductList.get(itemPosition);
            Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
            productDetailsActivity.putExtra("productId", item.getId());
            view.getContext().startActivity(productDetailsActivity);
        }


    }
}
