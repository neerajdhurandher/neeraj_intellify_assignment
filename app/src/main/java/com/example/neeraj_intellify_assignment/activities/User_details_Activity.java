package com.example.neeraj_intellify_assignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.neeraj_intellify_assignment.R;

public class User_details_Activity extends AppCompatActivity {

    TextView name,email,phone,age,gender,city;
    ImageView imageView;

    String user_Image,user_name,user_email,user_id,user_city,user_gender,user_age,user_phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_);



        ProgressDialog pd = new ProgressDialog(this);
       pd.setMessage("Please Wait..");
       pd.show();

        user_name = getIntent().getExtras().getString("USER_NAME");
        user_email = getIntent().getExtras().getString("USER_EMAIL");
        user_age = getIntent().getExtras().getString("USER_AGE");
        user_gender = getIntent().getExtras().getString("USER_GENDER");
        user_phone = getIntent().getExtras().getString("USER_PHONE");
        user_Image = getIntent().getExtras().getString("USER_IMAGE");
        user_city = getIntent().getExtras().getString("USER_CITY");
        user_id = getIntent().getExtras().getString("USER_ID");




        name = findViewById(R.id.name_tv);
        email = findViewById(R.id.email_tv);
        phone = findViewById(R.id.phone_tv);
        age = findViewById(R.id.age_tv);
        gender = findViewById(R.id.gender_tv);
        city = findViewById(R.id.city_tv);
        imageView = findViewById(R.id.image_view);


        name.setText(user_name);
        email.setText(user_email);
        phone.setText(user_phone);
        age.setText(user_age);
        gender.setText(user_gender);
        city.setText(user_city);


        try {
            Glide.with(getApplicationContext()).load(user_Image).circleCrop().into(imageView);
        }
        catch (Exception e){
            Glide.with(getApplicationContext()).load(R.drawable.ic_person_24).circleCrop().into(imageView);
        }

        pd.dismiss();

    }
}