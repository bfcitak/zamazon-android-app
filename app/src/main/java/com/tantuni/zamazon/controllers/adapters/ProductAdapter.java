package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.activities.ProductDetailsActivity;
import com.tantuni.zamazon.controllers.AdminController;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    ArrayList<Product> mProductList;
    LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> products) {
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
            this.productCategory.setText(selectedProduct.getCategory().getName() + "/" + selectedProduct.getSubCategory().getName());
        }


        @Override
        public void onClick(final View view) {

            int itemPosition = this.getAdapterPosition();
            final Product item = mProductList.get(itemPosition);

            if (SharedPrefManager.getInstance(view.getContext()).isLoggedIn()) {
                if (SharedPrefManager.getInstance(view.getContext()).getUser().getRoles().iterator().next().getRole().equals("ADMIN")){
                    AlertDialog.Builder builder = new AlertDialog.Builder( view.getContext());
                    String buttonString = "";
                    if (item.getActive())
                        buttonString = "Deactive Item";
                    else
                        buttonString = "Active Item";
                        builder.setTitle("Manipulate Item");
                        builder.setPositiveButton(buttonString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            AdminController.banItem(view.getContext(), new ProductCallback<Product>() {
                                @Override
                                public void onSuccess(Product object, String message) {
                                    setData(item,i);
                                }

                                @Override
                                public void onError(Exception exception) {
                                    Toast.makeText(view.getContext(),"Failed manipulate item",Toast.LENGTH_LONG).show();
                                }
                            },item.getId());
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setMessage("Product header:"+item.getHeader()+"\n\n"+"Seller: "+
                            item.getSeller().getEmail()+"\n\n"+"Product ID: "+item.getId()+"\n");
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                }
                else {
                    Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
                    productDetailsActivity.putExtra("productId", item.getId());
                    view.getContext().startActivity(productDetailsActivity);
                }
            } else {
                Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
                productDetailsActivity.putExtra("productId", item.getId());
                view.getContext().startActivity(productDetailsActivity);
            }
        }

    }
}
