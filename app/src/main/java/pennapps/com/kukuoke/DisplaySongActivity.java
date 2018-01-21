package pennapps.com.kukuoke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplaySongActivity extends AppCompatActivity {

    FirebaseStorage mstorage = FirebaseStorage.getInstance();
    StorageReference mstorageRef = mstorage.getReference();
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_song);

        Intent i = getIntent();
        final String json = i.getStringExtra("JSON");
        Log.d("json", json);
        try {
            JSONObject J = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference sr = mstorageRef.child(MainActivity.FBU.getUid()).child("goodSongs");
                FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child("goodSongs").push().setValue(json);
            }
        });
    }
}
