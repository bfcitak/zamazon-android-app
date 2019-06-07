package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.fragments.CartFragment;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Iterator;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.MyViewHolder> {

    ArrayList<Product> mProductList;
    LayoutInflater inflater;

    public CartProductAdapter(Context context, ArrayList<Product> products) {
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
        View view = inflater.inflate(R.layout.card_cart_product, parent, false);
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

        TextView productName, productDescription, productPrice, productCategory, textViewCartTotalPrice, textViewCartTotalTax, textViewCartTotalPayment;
        ImageView productImage, imageViewRemoveFromCart;


        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDescription = (TextView) itemView.findViewById(R.id.productDescription);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productCategory = (TextView) itemView.findViewById(R.id.productCategory);
            imageViewRemoveFromCart = (ImageView) itemView.findViewById(R.id.imageViewRemoveFromCart);
            // productImage = (ImageView) itemView.findViewById(R.id.productImage);

            imageViewRemoveFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int itemPosition = MyViewHolder.this.getAdapterPosition();
                    final Product item = mProductList.get(itemPosition);
                    CustomerController.removeProductFromCart(view.getContext(), SharedPrefManager.getInstance(view.getContext()).getUser().getId(), item.getId(), new ProductCallback<Cart>() {
                        @Override
                        public void onSuccess(Cart cart, String message) {
                            mProductList.remove(itemPosition);
                            CartProductAdapter.this.notifyDataSetChanged();

                            textViewCartTotalPrice = (TextView) view.findViewById(R.id.textViewCartTotalPrice);
                            textViewCartTotalTax = (TextView) view.findViewById(R.id.textViewCartTotalTax);
                            textViewCartTotalPayment = (TextView) view.findViewById(R.id.textViewCartTotalPayment);

                            //textViewCartTotalPrice.setText("Total Price: " + cart.getPayment().getTotal() + " TL");
                            //textViewCartTotalTax.setText("Total Tax: " + cart.getPayment().getTotal() + " TL");
                            //Double totalPayment = cart.getPayment().getTotal() + cart.getPayment().getTax();
                            //textViewCartTotalPayment.setText("Total Payment: " + totalPayment.toString() + " TL");

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
            Intent productDetailsActivity = new Intent(view.getContext(), ProductDetailsActivity.class);
            productDetailsActivity.putExtra("productId", item.getId());
            view.getContext().startActivity(productDetailsActivity);

        }

    }
}
