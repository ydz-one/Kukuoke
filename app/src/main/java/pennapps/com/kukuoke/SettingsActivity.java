package pennapps.com.kukuoke;

import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class SettingsActivity extends AppCompatActivity {

    FirebaseStorage mstorage = FirebaseStorage.getInstance();
    StorageReference mstorageRef = mstorage.getReference();

    private ImageView mChickenIconGlow;
    private Button mSignOutButton;

    private TextView mUsersName;
    private TextView mUsersEmail;
    private TextView mUsersUniqueId;

    private String usersUniqueID = MainActivity.FBU.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mChickenIconGlow = (ImageView) findViewById(R.id.chickenIconGlow);

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String usersName = snapshot.child(MainActivity.FBU.getUid()).child("name").getValue().toString();
                mUsersName = (TextView) findViewById(R.id.usersNameText);
                mUsersName.setText(usersName);

                String usersEmail = snapshot.child(MainActivity.FBU.getUid()).child("email").getValue().toString();
                mUsersEmail = (TextView) findViewById(R.id.usersEmailText);
                mUsersEmail.setText(usersEmail);

                mUsersUniqueId = (TextView) findViewById(R.id.getUniqueIdText);
                mUsersUniqueId.setText(usersUniqueID);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mUsersName = (TextView) findViewById(R.id.usersNameText);
        mUsersEmail = (TextView) findViewById(R.id.usersEmailText);
        mUsersUniqueId = (TextView) findViewById(R.id.getUniqueIdText);

        mSignOutButton = (Button) findViewById(R.id.signOutButton);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignOut();
            }
        });


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

        mUsersUniqueId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyUniqueId();
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


        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_image:
                Intent intent1 = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_user:
                Intent intent2 = new Intent(SettingsActivity.this, FriendsListActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void startSignOut() {

        FirebaseAuth.getInstance().signOut();
        MainActivity.FBU = null;
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));

    }

    private void copyUniqueId() {
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText("myUID", usersUniqueID);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "ID copied", Toast.LENGTH_SHORT).show();

    }

}

