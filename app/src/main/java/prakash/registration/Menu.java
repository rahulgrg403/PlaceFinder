package prakash.registration;




        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity {
        private FirebaseAuth firebaseAuth;
        TextView userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,login.class));
        }

      /*  FirebaseUser user = firebaseAuth.getCurrentUser();

        userid=(TextView) findViewById(R.id.user);
        userid.setText(user.getEmail());
*/

    }
    public void addpost(View view)
    {
        Intent i = new Intent(this,addpost.class);
        startActivity(i);
    }
    public void chat(View view)
    {
        Intent i = new Intent(this,Chat.class);
        startActivity(i);
    }
    public void updatepost(View view)
    {
        Intent i = new Intent(this,delete.class);
        startActivity(i);
    }
    public void searchpost(View view)
    {
        Intent i = new Intent(this,result.class);
        startActivity(i);
    }
    public void aboutus(View view)
    {
        Intent i = new Intent(this,ContactUs.class);
        startActivity(i);
    }
    public void profile(View view)
    {
        Intent i = new Intent(this,profileActivity.class);
        startActivity(i);
    }
}
