package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    List<User> mUserList;
    LayoutInflater inflater;

    public UserAdapter(Context context,List<User> userList){
        inflater = LayoutInflater.from(context);
        mUserList = userList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.card_user, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder myViewHolder, int i) {
        User selectedUser = mUserList.get(i);
        myViewHolder.setData(selectedUser, i);

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName, userMail, isActive, userType;
        ImageView userImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userMail = itemView.findViewById(R.id.userMail);
            isActive = itemView.findViewById(R.id.isActive);
            userType = itemView.findViewById(R.id.userType);
            itemView.setOnClickListener(this);
        }

        public void setData(User selectedUser, int position) {
            userName.setText(selectedUser.getFirstName());
            userMail.setText(selectedUser.getEmail());
            // this.productImage.setImageResource(selectedProduct.getImageId());
            isActive.setText(selectedUser.getActive().toString());
            userType.setText(selectedUser.getRoles().iterator().next().getRole());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
