package pennapps.com.kukuoke;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivityAPI extends AppCompatActivity {

    //TextView tv;
    //ImageView iv;
    public String picPath;
    EditText trackSearch;
    Button trackSearchBtn;
    EditText artistSearch;
    Button artistSearchBtn;
    EditText albumSearch;
    Button albumSearchBtn;
    Button getSimilarTrackBtn;
    Button getSimilarArtistBtn;

    Button getArtistInfoBtn;
    Button getTrackInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trackSearch = (EditText) findViewById(R.id.trackSearch);
        trackSearchBtn = (Button) findViewById(R.id.trackSearchBtn);
        trackSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trackName = trackSearch.getText().toString();
                try {
                    JSONArray ja = new TrackSearchJob().execute(trackName).get();
                    Log.d("track json length", "" + ja.length());
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getSimilarTrackBtn = (Button) findViewById(R.id.getSimilarTrack);
        getSimilarTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistSearch.getText().toString();
                String trackName = trackSearch.getText().toString();
                try {
                    JSONArray ja = new SimilarTrackJob().execute(artistName, trackName).get();
                    Log.d("similar tracks json len", "" + ja.length());
                    Log.d("first track name", ((JSONObject)ja.get(0)).get("name").toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        getSimilarArtistBtn = (Button) findViewById(R.id.getSimilarArtist);
        getSimilarArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistSearch.getText().toString();
                try {
                    JSONArray ja = new SimilarArtistJob().execute(artistName).get();
                    Log.d("sim artists json len", "" + ja.length());
                    Log.d("first artist name", ((JSONObject)ja.get(0)).get("name").toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getArtistInfoBtn = (Button) findViewById(R.id.getArtistInfo);
        getArtistInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artistName = artistSearch.getText().toString();
                try {
                    JSONObject jo = new ArtistInfoJob().execute(artistName).get();
                    Log.d("artist bio", ((JSONObject)jo.get("bio")).get("summary").toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        getTrackInfoBtn = (Button) findViewById(R.id.getTrackInfo);
        getTrackInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trackName = trackSearch.getText().toString();
                String artistName = artistSearch.getText().toString();
                try {
                    JSONObject jo = new TrackInfoJob().execute(trackName, artistName).get();
                    Log.d("duration is ", jo.get("duration").toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        /*
        tv = (TextView) findViewById(R.id.textView);
        tv.setText("hahahahah");
        try {
            picPath = new SendRequest().execute().get();
            Log.d("~~~~~~~~~~~~~", picPath);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        iv = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(picPath).into(iv);
        */
    }
}
