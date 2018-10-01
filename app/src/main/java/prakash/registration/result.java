package prakash.registration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class result extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private  ImageAdapterSearch mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;
    //private Spinner ptype;
    //private EditText pin;
    private List<post> mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        checkConnection();

        //ptype=(Spinner)findViewById(R.id.ptype1);
        //pin=(EditText)findViewById(R.id.pincode);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);

        mPost = new ArrayList<>();

        //final String ptype2=ptype.getSelectedItem().toString().trim();
        //final String pin1=pin.getText().toString().trim();



        //firebaseAuth=FirebaseAuth.getInstance();
        //Toast.makeText(search.this,firebaseAuth.getUid(),Toast.LENGTH_SHORT).show();
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("post/");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                        //if (ptype2 == postSnapshot1.getValue(post.class).getPtype()) {
                            post post = postSnapshot1.getValue(prakash.registration.post.class);
                            mPost.add(post);
                        //}

                    }
                }
                mAdapter = new ImageAdapterSearch(result.this,mPost);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(result.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
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
