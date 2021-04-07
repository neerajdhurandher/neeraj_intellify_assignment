package com.example.neeraj_intellify_assignment.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neeraj_intellify_assignment.MainActivity;
import com.example.neeraj_intellify_assignment.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login_activity extends AppCompatActivity {


    public static final String loginkeyLA = "com.example.apptrail4.loginwelcome";

    private SignInButton googleSigninbtn;
    private int RC_SIGN_IN = 1;
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private GoogleSignInClient getSignInIntent;
    private GoogleSignInClient googleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);


        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){

//                    Toast.makeText(login_activity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login_activity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else{
//                    Toast.makeText(login_activity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };


        emailId = findViewById(R.id.etemailid);
        password = findViewById(R.id.etpassword);
        btnSignIn = findViewById(R.id.btnSignUp);
        tvSignUp = findViewById(R.id.textView);
        googleSigninbtn = findViewById(R.id.google_button);


        GoogleSignInOptions googleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(this,googleSignInOptions);




        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(login_activity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty()  &&  pwd.isEmpty())){

                        mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(login_activity.this, "Incorrect Email Id or Password", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intTSignIn = new Intent(login_activity.this, MainActivity.class);
                                    String welstr = emailId.getText().toString();
                                    String massage = " Welcome Back \t " + welstr;
                                    intTSignIn.putExtra(loginkeyLA, massage);

                                    intTSignIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intTSignIn);
                                    finish();
                                }
                            }
                        });

                }

                else{
                    Toast.makeText(login_activity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_signup =  new Intent(login_activity.this,signup_activity.class);
                startActivity(intent_signup);
                finish();
            }
        });


        googleSigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SigIn();

            }
        });


    }


    private void SigIn(){
        Intent GsignIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(GsignIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                handleSignInResult(task);
            }
            catch (Exception e){
            }
        }



    }
    private void handleSignInResult(Task <GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText( login_activity.this,"Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);

        }
        catch (ApiException e){
            Toast.makeText( login_activity.this,"Signed In Failed", Toast.LENGTH_SHORT).show();

        }
    }

    private void FirebaseGoogleAuth (GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    String  emailS = user.getEmail();
                    String uid = user.getUid();
                    String image = user.getPhotoUrl().toString();
                    String displayS = user.getDisplayName();
                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {


                        HashMap<String, String> hashmapbasic = new HashMap<>();
                        hashmapbasic.put("name",displayS);
                        hashmapbasic.put("email", emailS);
                        hashmapbasic.put("image", image);



                        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();

                        db_ref.child("Users").child(uid).setValue(hashmapbasic);


                    }

                    Toast.makeText(login_activity.this, "User Signed In", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(login_activity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }


}