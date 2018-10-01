package prakash.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword1;
    private TextView textViewSignin;
    private EditText editTextName;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //databaseReference = FirebaseDatabase.getInstance().getReference();


    protected boolean isOnline()
    {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo =cm.getActiveNetworkInfo();
        if(netinfo!=null && netinfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
            return false;
    }

    public void checkConnection()
    {
        if(isOnline())
        {
            //Toast.makeText(this,"You Are Online ... ! ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"You Are Offline ... ! ",Toast.LENGTH_LONG).show();
        }
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection();
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),profileActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        buttonRegister = (ImageButton) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        textViewSignin = (TextView) findViewById(R.id.textViewSignIn);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    private void registerUser(){

        String email=editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String password1 = editTextPassword1.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Emailid",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(password1)){
            Toast.makeText(this, "Password doesn't match",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(this, "Password must be atleast size of 6",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //databaseReference = FirebaseDatabase.getInstance().getReference();
                            String name = editTextName.getText().toString().trim();
                           // UserInformation userInformation =new UserInformation(name);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            user.updateProfile(profileUpdates);
                           // databaseReference.child(user.getUid()).setValue(userInformation);
                            sendEmailVerification();
                            progressDialog.dismiss();
                       

                        }
                        else{
                            Toast.makeText(MainActivity.this,"This email Id is already in use", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }


                });
    }


    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Check your email for verification",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this,login.class));
                    }
                }
            });
        }
    }

    @Override
    public  void onClick(View view){
        if(view==buttonRegister){
            registerUser();
        }
        if(view==textViewSignin){
            startActivity(new Intent(this,login.class));
        }

    }
}
