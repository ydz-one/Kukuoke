package pennapps.com.kukuoke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplaySongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_song);

        Intent i = getIntent();
        String json = i.getStringExtra("JSON");
        Log.d("json", json);
        try {
            JSONObject J = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
