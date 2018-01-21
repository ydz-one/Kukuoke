package pennapps.com.kukuoke;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {

    FirebaseStorage mstorage = FirebaseStorage.getInstance();
    StorageReference mstorageRef = mstorage.getReference();

    private ImageView mChickenIconGlow;
    private Button mSignOutButton;
    private TextView mUsersName;
    private TextView mUsersEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mChickenIconGlow = (ImageView) findViewById(R.id.chickenIconGlow);
        mUsersName = (TextView) findViewById(R.id.usersNameText);
        mUsersEmail = (TextView) findViewById(R.id.usersEmailText);

        mSignOutButton = (Button) findViewById(R.id.signOutButton);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignOut();
            }
        });

        //Firebase fb = new Firebase("gs://kukuoke-team.appspot.com");


        mstorageRef.child("/chickeniconglow.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(SettingsActivity.this).load(uri).fit().centerCrop().into(mChickenIconGlow);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SettingsActivity.this, "Failed image in Settings", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
//            intent1.putExtra("uid", FBU.getUid());
//            startActivity(intent1);
//            return true;
//        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_home:
                Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);
                intent1.putExtra("uid", MainActivity.FBU.getUid());
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }

    private void startSignOut() {

        FirebaseAuth.getInstance().signOut();
        MainActivity.FBU = null;
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));

    }

}

