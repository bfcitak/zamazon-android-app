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
import com.tantuni.zamazon.models.WishList;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Iterator;

public class WishListProductAdapter extends RecyclerView.Adapter<WishListProductAdapter.MyViewHolder> {

    ArrayList<Product> mProductList;
    LayoutInflater inflater;

    public WishListProductAdapter(Context context, ArrayList<Product> products) {
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
        View view = inflater.inflate(R.layout.wishlist_cart_product, parent, false);
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

        TextView productNameWishList, productDescriptionWishList, productPriceWishList, productCategoryWishList;
        ImageView productImage, imageViewRemoveFromWishList;


        public MyViewHolder(View itemView) {
            super(itemView);
            productNameWishList = (TextView) itemView.findViewById(R.id.productNameWishList);
            productDescriptionWishList = (TextView) itemView.findViewById(R.id.productDescriptionWishList);
            productPriceWishList = (TextView) itemView.findViewById(R.id.productPriceWishList);
            productCategoryWishList = (TextView) itemView.findViewById(R.id.productCategoryWishList);
            imageViewRemoveFromWishList = (ImageView) itemView.findViewById(R.id.imageViewRemoveFromWishList);
            // productImage = (ImageView) itemView.findViewById(R.id.productImage);

            imageViewRemoveFromWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int itemPosition = MyViewHolder.this.getAdapterPosition();
                    final Product item = mProductList.get(itemPosition);
                    CustomerController.removeProductFromWishList(view.getContext(), SharedPrefManager.getInstance(view.getContext()).getUser().getId(), item.getId(), new ProductCallback<WishList>() {
                        @Override
                        public void onSuccess(WishList wishList, String message) {
                            mProductList.remove(itemPosition);
                            WishListProductAdapter.this.notifyDataSetChanged();
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
            this.productNameWishList.setText(selectedProduct.getHeader());
            this.productDescriptionWishList.setText(selectedProduct.getDescription());
            // this.productImage.setImageResource(selectedProduct.getImageId());
            this.productPriceWishList.setText(selectedProduct.getPrice().intValue() + " TL");
            this.productCategoryWishList.setText(selectedProduct.getCategory().getName() + "/" + selectedProduct.getSubCategory().getName());
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
