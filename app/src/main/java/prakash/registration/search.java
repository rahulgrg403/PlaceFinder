package prakash.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import prakash.registration.R;
import prakash.registration.result;

public class search extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ImageButton buttonSearch;
    private EditText pin;
    private Spinner ptype;
    private DatabaseReference databaseReference;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        checkConnection();
        Spinner property = (Spinner)findViewById(R.id.ptype1);
        ArrayAdapter<String> AdapterA = new ArrayAdapter<String>(search.this,R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.property));
        AdapterA.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        property.setAdapter(AdapterA);

        ptype=(Spinner) findViewById(R.id.ptype1);
        pin=(EditText) findViewById(R.id.pincode);
        buttonSearch=(ImageButton) findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(search.this,result.class));
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
            Toast.makeText(this,"You Are Offline ... ! ", Toast.LENGTH_LONG).show();
        }
    }
}