package com.tantuni.zamazon.controllers.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.AdminController;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.UserCallback;

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
        public void onClick(final View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder( view.getContext());
            final int position = getAdapterPosition();
            final User user = mUserList.get(position);
            String buttonString = "";
            if (user.getActive())
                buttonString = "Ban";
            else
                buttonString = "Active User";
            builder.setTitle("User Info");
            builder.setPositiveButton(buttonString, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AdminController.banUser(view.getContext(), new UserCallback<User>() {
                        @Override
                        public void onSuccess(User object, String message) {
                            Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
                            if(user.getActive()) {
                                user.setActive(false);
                                setData(user,position);
                            }


                            else {
                                user.setActive(true);
                                setData(user,position);
                            }
                        }

                        @Override
                        public void onError(Exception exception) {
                            Toast.makeText(view.getContext(),"Failed to ban user",Toast.LENGTH_LONG).show();
                        }
                    },user.getId());
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setMessage("User mail:"+user.getEmail()+"\n\n"+"User ID: "+user.getId()+"\n");
            AlertDialog alert1 = builder.create();
            alert1.show();
        }
    }
}
