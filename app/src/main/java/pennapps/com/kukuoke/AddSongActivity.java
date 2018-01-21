package pennapps.com.kukuoke;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddSongActivity extends AppCompatActivity {
    private TextView textView;


    EditText trackSearch;
    Button trackSearchBtn;
    EditText artistSearch;
    Button artistSearchBtn;
    EditText albumSearch;
    Button albumSearchBtn;

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        String savedExtra = getIntent().getStringExtra("tabNum");

        ll = (LinearLayout) findViewById(R.id.songsdislpay);

        final List<JSONObject> joLists = new ArrayList<>();

        trackSearch = (EditText) findViewById(R.id.trackSearch);
        trackSearchBtn = (Button) findViewById(R.id.trackSearchBtn);
        trackSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    for (int i = 0; i < ja.length(); i++) {
                        TextView tv = new TextView(getApplicationContext());
                        try {
                            String artist = ((JSONObject)joLists.get(i)).get("artist").toString();
                            String name = ((JSONObject)joLists.get(i)).get("name").toString();

                            tv.setText(name + "\n    " + artist);

                            Log.d(""+i, ((JSONObject)joLists.get(i)).get("artist").toString());
                            tv.setTextColor(Color.GREEN);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final int index = i;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AddSongActivity.this, DisplaySongActivity.class);
                                try {
                                    intent.putExtra("JSON", ((JSONObject)ja.get(index)).toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        });
                        ll.addView(tv);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        artistSearch = (EditText) findViewById(R.id.artistSearch);
        artistSearchBtn = (Button) findViewById(R.id.artistSearchBtn);
        artistSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistSearch.getText().toString();
                try {
                    JSONArray ja = new ArtistSearchJob().execute(artistName).get();
                    Log.d("artist json length", "" + ja.length());
                    Log.d("first artist name", ((JSONObject)ja.get(0)).get("name").toString());
                    for (int i = 0; i < ja.length(); i++) {
                        try {
                            joLists.add((JSONObject) ja.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        albumSearch = (EditText) findViewById(R.id.albumSearch);
        albumSearchBtn = (Button) findViewById(R.id.albumSearchBtn);
        albumSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String albumName = albumSearch.getText().toString();
                try {
                    JSONArray ja = new AlbumSearchJob().execute(albumName).get();
                    Log.d("album json length", "" + ja.length());
                    Log.d("first album name", ((JSONObject)ja.get(0)).get("name").toString());
                    for (int i = 0; i < ja.length(); i++) {
                        try {
                            joLists.add((JSONObject) ja.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}