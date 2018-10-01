package prakash.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class addpost extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonChoose;
    private Button buttonPost, buttonReset;
    private DatabaseReference databaseReference;

    private EditText state, city, landmark, pin, rent, contact, description;
    private Spinner ptype;
    private Uri Imageuri;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private ImageView uploadImage;
    private static final int PICK_IMAGE_REQUEST =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        checkConnection();

        Spinner property = (Spinner)findViewById(R.id.ptype);
        ArrayAdapter<String> AdapterA = new ArrayAdapter<String>(addpost.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.property));
        AdapterA.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        property.setAdapter(AdapterA);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("post/"+user.getUid());
        mStorage = FirebaseStorage.getInstance().getReference("post/"+user.getUid());
        //databaseReference1 = FirebaseDatabase.getInstance().getReference("post_pic");
        //mStorage1 = FirebaseStorage.getInstance().getReference("post_pic");
        //FirebaseUser user=firebaseAuth.getCurrentUser();

        state=(EditText) findViewById(R.id.state);
        city=(EditText) findViewById(R.id.city);
        landmark=(EditText) findViewById(R.id.landmark);
        pin=(EditText) findViewById(R.id.pincode);
        rent=(EditText) findViewById(R.id.rent);
        uploadImage=(ImageView) findViewById(R.id.uploadImage);
        description=(EditText) findViewById(R.id.description);
        contact=(EditText) findViewById(R.id.contact);
        ptype=(Spinner) findViewById(R.id.ptype);
        buttonChoose=(Button) findViewById(R.id.buttonChoose);
        buttonPost=(Button) findViewById(R.id.buttonPost);
        buttonReset=(Button) findViewById(R.id.buttonReset);
        progressDialog = new ProgressDialog(this);
        buttonPost.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
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
            Imageuri = data.getData();
            //Picasso.with(addpost.this).load(mImageuri).into(uploadImage);
            uploadImage.setImageURI(Imageuri);
            //uploadFile();
        }
        else{
            Toast.makeText(this,"failed ",Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(){
        if (Imageuri != null){
            //FirebaseUser user = firebaseAuth.getCurrentUser();
            StorageReference filereference = mStorage.child(System.currentTimeMillis()+".png");
            filereference.putFile(Imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            String city1=city.getText().toString().trim();
                            String ptype1=ptype.getSelectedItem().toString().trim();
                            String state1=state.getText().toString().trim();
                            String landmark1=landmark.getText().toString().trim();
                            String pin1=pin.getText().toString().trim();
                            String rent1=rent.getText().toString().trim();
                            String contact1=contact.getText().toString().trim();
                            String description1=description.getText().toString().trim();
                            post post = new post(ptype1, city1, state1, landmark1, pin1, rent1, contact1, description1, taskSnapshot.getDownloadUrl().toString());
                            String postId = databaseReference.push().getKey();
                            databaseReference.child(postId).setValue(post);

                            Toast.makeText(addpost.this,"Sucessfully Post Advertisement",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(addpost.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Processing...");
                            progressDialog.show();
                        }
                    });
        }
        else{
            Toast.makeText(this,"no file selected ",Toast.LENGTH_SHORT).show();
        }
    }
    /*private void saveUserInformation(){
        String city1=city.getText().toString().trim();
        String ptype1=ptype.getSelectedItem().toString().trim();
        String state1=state.getText().toString().trim();
        String landmark1=landmark.getText().toString().trim();
        String pin1=pin.getText().toString().trim();
        String rent1=rent.getText().toString().trim();
        String description1=description.getText().toString().trim();
        post post = new post(ptype1, city1, state1, landmark1, pin1, rent1, description1);
        FirebaseUser user =firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(post);
        Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show();

    }*/

    @Override
    public void onClick(View view) {
        if(view == buttonPost){
            uploadFile();
            //saveUserInformation();
            Intent i = new Intent(this, Menu.class);
            startActivity(i);

        }
        if (view == buttonReset){
            state.setText("");city.setText(""); landmark.setText(""); pin.setText(""); rent.setText("");description.setText("");
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
