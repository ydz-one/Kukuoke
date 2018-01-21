package pennapps.com.kukuoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuetSongsActivity extends AppCompatActivity {
    private StringListAdapter sla;
    private ListView listViewDuets;
    private List<String> songsList;
    private List<String> artistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duet_song);

            String[] songs = {"Thriller", "Come Together", "Total Eclipse of the Heart",
                    "Girls Just Want To Have Fun"};

            String[] artists = {"Michael Jackson", "The Beatles", "Bonnie Tyler", "Cyndi Lauper"};

            songsList = new ArrayList<>();
            artistsList = new ArrayList<>();

            songsList = Arrays.asList(songs);
            artistsList = Arrays.asList(artists);

        sla = new StringListAdapter(this, songsList, artistsList, R.layout.listview_row_duet);
        listViewDuets = (ListView) findViewById(R.id.lv_duetlist);
        listViewDuets.setAdapter(sla);
    }
}