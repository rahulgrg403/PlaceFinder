package prakash.registration;



        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;

        import org.w3c.dom.Text;

public class ContactUs extends AppCompatActivity {
    TextView contact, name;
    TextView email;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),login.class));
        }
        name = (TextView)findViewById(R.id.Name);
        contact = (TextView)findViewById(R.id.Contact_number);
        email=(TextView)findViewById(R.id.Email_id);

    }
    public void navin(View view)
    {
        name.setText("Navin Kumar");
        contact.setText("9905023750");
        email.setText("navinlovema30@gmail.com");
    }
    public void ChandraPrakash(View view)
    {
        name.setText("Chandra Prakash (Team Lead)");
        contact.setText("8891375488");
        email.setText("prakashchandra378@gmail.com");
    }    public void rahul1(View view)
    {
        name.setText("Rahul");
        contact.setText("9417264977");
        email.setText("rahulgarg2014@hotmail.com");
    }    public void rahul(View view)
    {
        name.setText("Rahul Kumar");
        contact.setText("8235266754");
        email.setText("rahulpoddar671@gmail.com");
    }    public void manish(View view)
    {
        name.setText("Manish Sahu");
        contact.setText("8602811878");
        email.setText("manishsahu8696@gmail.com");
    }
}
