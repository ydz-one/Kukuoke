package pennapps.com.kukuoke;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static pennapps.com.kukuoke.Tab1.claTab1;

public class FriendsListActivity extends AppCompatActivity {

    private ListView listViewFriends;
    private FriendsListAdapter fla;
    private List<String> friendNames;
    private List<Integer> friendIds;
    private Button duetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        duetBtn = findViewById(R.id.btn_friend_duet);
        friendNames = new ArrayList<>();
        friendIds = new ArrayList<>();

        friendNames.add("Alice");
        friendNames.add("Olivia");
        friendNames.add("Felix");

        friendIds.add(0);
        friendIds.add(1);
        friendIds.add(2);

        fla = new FriendsListAdapter(this, friendNames, R.layout.listview_row_friends);
        listViewFriends = (ListView) findViewById(R.id.lv_friendslist);
        listViewFriends.setAdapter(fla);

        duetBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DuetSongsActivity.class);
                startActivity(i);
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
                Intent intent1 = new Intent(FriendsListActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_image:
                return true;
            case R.id.action_user:
                Intent intent2 = new Intent(FriendsListActivity.this, FriendsListActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

//        return super.onOptionsItemSelected(item);
    }
}

