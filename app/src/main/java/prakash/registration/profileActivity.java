package prakash.registration;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import android.content.ContentResolver;
import android.webkit.MimeTypeMap;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.firebase.storage.OnProgressListener;


public class profileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST =1;
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button buttondelete;
    private ProgressDialog progressDialog;
    private ProgressBar mProgressbar;
    private ImageButton chngimage;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ImageView mProfileImage;
    private StorageTask mUploadTask;
    private Uri mImageuri;
    ConstraintLayout profileactivity;
    private List<upload> mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), login.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


checkConnection();

        profileactivity =(ConstraintLayout)findViewById(R.id.profileactivity);

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mUpload = new ArrayList<>();
        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        //mProgressbar = findViewById(R.id.mProgess);
        mStorage = FirebaseStorage.getInstance().getReference("profile_pic");
        mDatabase =FirebaseDatabase.getInstance().getReference("profile_pic");
        chngimage = (ImageButton) findViewById(R.id.chngimage);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(user.getDisplayName());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttondelete = (Button) findViewById(R.id.buttondelete);
        progressDialog = new ProgressDialog(this);
        buttonLogout.setOnClickListener(this);
        buttondelete.setOnClickListener(this);
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        load();
        chngimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });
    }
    private void load(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mStorage.child(user.getUid()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(profileActivity.this).load(uri).into(mProfileImage);
                //Toast.makeText(profileActivity.this,"sucess load ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //finish();
                //Toast.makeText(profileActivity.this,"failed to load profile pic ",Toast.LENGTH_SHORT).show();
                // Handle any errors
            }
        });
    }
        private void openFileChooser(){
            Intent intent =new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageuri = data.getData();
            uploadFile();
        }
        else{
            Toast.makeText(this,"failed ",Toast.LENGTH_SHORT).show();
        }
    }
   /* private String getFileExtension(Uri uri){
            ContentResolver cr = getContentResolver();
            MimeTypeMap mime =MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));
    }
*/
    private void uploadFile(){
        if (mImageuri != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            StorageReference filereference = mStorage.child(user.getUid()+".png");
            filereference.putFile(mImageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(profileActivity.this,"Sucessfully upload",Toast.LENGTH_SHORT).show();
                            upload upload=new upload(taskSnapshot.getDownloadUrl().toString());
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            mDatabase.child(user.getUid()).setValue(upload);
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            Picasso.with(profileActivity.this).load(downloadUri).into(mProfileImage);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(profileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Uploading...");
                            progressDialog.show();
                        }
                    });
        }
        else{
            Toast.makeText(this,"no file selected ",Toast.LENGTH_SHORT).show();
        }
    }

    private void deactivate(){
        progressDialog.setMessage("Deactivating...");
        progressDialog.show();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Account deleted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Couldn't delete account",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
                finish();
                startActivity(new Intent(profileActivity.this,login.class));
            }
        });
    }

   @Override
    public void onClick(View view) {
          if(view==buttonLogout){

              /*AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      Snackbar.make(profileactivity,"You have been successfully Signed Out ",Snackbar.LENGTH_SHORT).show();
                      finish();
                  }
              });*/


              firebaseAuth.signOut();
              startActivity(new Intent(this,login.class));
              Snackbar.make(profileactivity,"You have been successfully Signed Out ",Snackbar.LENGTH_SHORT).show();
              finish();
          }
          else if(view==buttondelete){
              deactivate();
          }
    }
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
}
