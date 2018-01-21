package pennapps.com.kukuoke;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddSongActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listViewSearch;
    private CustomListAdapter claSearch;
    private Activity thisActivity;
    private List<JSONObject> joLists;
    private int tabNum;
    private Class<?> nextActivity;
    private String firebaseString;

    EditText trackSearch;
    Button trackSearchBtn;
    Button addSongBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        listViewSearch = (ListView) findViewById(R.id.lv_search_results);
        thisActivity = this;
        joLists = new ArrayList<>();

        final String savedExtra = getIntent().getStringExtra("tabNum");

        if (savedExtra.equals("tab1")){
            tabNum = 1;
            nextActivity = Tab1.class;
            firebaseString = "goodSongs";
        } else if (savedExtra.equals("tab2")){
            tabNum = 2;
            nextActivity = Tab2.class;
            firebaseString = "maybeSongs";
        } else {
            tabNum = 3;
            nextActivity = Tab3.class;
            firebaseString = "nopeSongs";
        }

        trackSearch = (EditText) findViewById(R.id.trackSearch);
        trackSearchBtn = (Button) findViewById(R.id.trackSearchBtn);
        trackSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joLists.clear();
                String trackName = trackSearch.getText().toString();
                try {
                    final JSONArray ja = new TrackSearchJob().execute(trackName).get();
                    Log.d("track json length", "" + ja.length());

                    for (int i = 0; i < ja.length(); i++) {
                        try {
                            joLists.add((JSONObject) ja.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    listViewSearch.setAdapter(null);
                    displaySearchResults(joLists);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

//        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                JSONObject song = joLists.get(position);
//                FirebaseStorage mstorage = FirebaseStorage.getInstance();
//                StorageReference mstorageRef = mstorage.getReference();
//                StorageReference sr = mstorageRef.child(MainActivity.FBU.getUid()).child(firebaseString);
//                FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child(firebaseString).push().setValue(song);
//                Intent intent = new Intent(thisActivity, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    System.out.println(String.valueOf(position));
                    JSONObject song = joLists.get(position);
                    String songName = song.get("name").toString();
                    String artistName = song.get("artist").toString();
                    UserSongs.getUserSongsInstance().addSong(thisActivity, songName, artistName, tabNum);
//                FirebaseStorage mstorage = FirebaseStorage.getInstance();
//                StorageReference mstorageRef = mstorage.getReference();
//                StorageReference sr = mstorageRef.child(MainActivity.FBU.getUid()).child(firebaseString);
//                FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child(firebaseString).push().setValue(song);
                    Intent intent = new Intent(thisActivity, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    private void displaySearchResults(List<JSONObject> joLists){
        claSearch = new CustomListAdapter(thisActivity, joLists, R.layout.listview_row_search);
        listViewSearch.setAdapter(claSearch);
    }
}