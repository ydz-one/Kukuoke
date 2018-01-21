package pennapps.com.kukuoke;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    private Activity activity;
    private List<JSONObject> joLists;

    EditText trackSearch;
    Button trackSearchBtn;
    Button addSongBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        listViewSearch = (ListView) findViewById(R.id.lv_search_results);
        activity = this;
        joLists = new ArrayList<>();

        String savedExtra = getIntent().getStringExtra("tabNum");

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

        addSongBtn = (Button) findViewById(R.id.btn_lv_search);
    }

    private void displaySearchResults(List<JSONObject> joLists){
        claSearch = new CustomListAdapter(activity, joLists, R.layout.listview_row_search);
        listViewSearch.setAdapter(claSearch);
    }
}