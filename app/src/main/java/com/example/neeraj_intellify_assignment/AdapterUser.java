package com.example.neeraj_intellify_assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.neeraj_intellify_assignment.activities.User_details_Activity;
import com.example.neeraj_intellify_assignment.models.Results;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.userViewHolder> {

    Context context;
    List<Results> userList;

    public AdapterUser(Context context, List<Results> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup,false);
        return new userViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int i) {



        String user_Image = userList.get(i).getPicture().getMedium();
        String user_name = userList.get(i).getName().getFirst();
        String user_email = userList.get(i).getEmail();
        String user_id = userList.get(i).getId().getValue();
        String user_city = userList.get(i).getLocation().getCity();
        String user_gender = userList.get(i).getGender();
        String user_age = userList.get(i).getDob().getAge().toString();
        String user_phone = userList.get(i).getPhone();


        holder.usernameA.setText(user_name);
        holder.useremailA.setText(user_email);

        try {
            Glide.with(context).load(user_Image).circleCrop().into(holder.userImageA);
        }
        catch (Exception e){
            Glide.with(context).load(R.drawable.ic_person_24).circleCrop().into(holder.userImageA);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent clicked_Profile = new Intent(context, User_details_Activity.class);

                clicked_Profile.putExtra("USER_ID",user_id);
                clicked_Profile.putExtra("USER_NAME",user_name);
                clicked_Profile.putExtra("USER_IMAGE",user_Image);
                clicked_Profile.putExtra("USER_EMAIL",user_email);
                clicked_Profile.putExtra("USER_GENDER",user_gender);
                clicked_Profile.putExtra("USER_AGE",user_age);
                clicked_Profile.putExtra("USER_PHONE",user_phone);
                clicked_Profile.putExtra("USER_CITY",user_city);

                clicked_Profile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(clicked_Profile);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class userViewHolder extends RecyclerView.ViewHolder{

        ImageView userImageA;
        TextView usernameA,useremailA;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageA = itemView.findViewById(R.id.useriamgeS);
            usernameA = itemView.findViewById(R.id.usernameS);
            useremailA = itemView.findViewById(R.id.useremailS);



        }
    }

}
