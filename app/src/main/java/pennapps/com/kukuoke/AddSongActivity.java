package pennapps.com.kukuoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddSongActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        String savedExtra = getIntent().getStringExtra("tabNum");
        textView = (TextView) findViewById(R.id.tv_add_song_display);

        textView.setText("Implement song add screen for " + savedExtra);
    }
}