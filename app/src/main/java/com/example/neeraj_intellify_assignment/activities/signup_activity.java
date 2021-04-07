package com.example.neeraj_intellify_assignment.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neeraj_intellify_assignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup_activity extends AppCompatActivity {


    EditText emailId, password;
    Button btnsignup;
    Button btnAlreadyAcc;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);


        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.etemailid);
        password = findViewById(R.id.etpassword);
        btnsignup = findViewById(R.id.SignUp_btn);
        btnAlreadyAcc = findViewById(R.id.having_acc_btn);

        btnsignup.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {

                final String emailstr = emailId.getText().toString();
                String passwordstr = password.getText().toString();
                if (emailstr.isEmpty() && passwordstr.isEmpty()) {
                    Toast.makeText(signup_activity.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
                }
                else if (emailstr.isEmpty()) {
                    emailId.setError("Please Enter email Id");
                    emailId.requestFocus();
                } else if (passwordstr.isEmpty()) {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                }  else if (!(emailstr.isEmpty() && passwordstr.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(signup_activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                emailId.requestFocus();
                                password.requestFocus();
                                Toast.makeText(signup_activity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();


                            } else {
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();


                                String emailS = emailstr;
                                String uid = user.getUid();

                                String full_name = "";
                                Character a;


                                char[] ch = new char[emailS.length()];

                                for (int i = 0; i < emailS.length(); i++) {
                                    ch[i] = emailS.charAt(i);
                                    a = ch[i];
                                    if (a.equals('@')) {
                                        break;
                                    }
                                    else {
                                        full_name = full_name + a;
                                    }
                                }


                                HashMap<Object , String> hashMap = new HashMap<>();
                                hashMap.put("email",emailS);
                                hashMap.put("name",full_name);
                                hashMap.put("image","https://www.google.com/search?q=user+icon+for+free&tbm=isch&ved=2ahUKEwjwzeOOp-vvAhV4k2MGHRMrAFsQ2-cCegQIABAA&oq=user+icon+for+free&gs_lcp=CgNpbWcQAzoECAAQQzoCCAA6BAgAEBhQx0NYtMkBYJfRAWgJcAB4AIABhQWIAbclkgEKMi0xNi4xLjAuMZgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=TTZtYLDzIfimjuMPk9aA2AU&bih=792&biw=1536&rlz=1C1CHBD_enIN883IN884#imgrc=apVUJn-ZSqwKhM");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference();
                                reference.child("Users").child(uid).setValue(hashMap);

                                Intent intent_login = new Intent(signup_activity.this, login_activity.class);
                                startActivity(intent_login);
                            }
                        }

                    });
                } else {
                    Toast.makeText(signup_activity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }}

        });

        btnAlreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(signup_activity.this,login_activity.class));
            }
        });





    }
}