package pennapps.com.kukuoke;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zyud on 1/19/2018.
 */

public class StringListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of songs
    private final List<String> songs;

    private List<String> artists;

    private int resource;


    public StringListAdapter(Activity context, List<String> songs, List<String> artists, int resource){

        // in actual implementation, use a different array than songArray, because there can be
        // different songs with the same name
        super(context, R.layout.listview_row, songs);

        this.context = context;
        this.songs = songs;
        this.artists = artists;
        this.resource = resource;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(resource, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView songTextField = (TextView) rowView.findViewById(R.id.tv_song_name_duet);
        TextView artistTextField = (TextView) rowView.findViewById(R.id.tv_artist_name_duet);

        //this code sets the values of the objects to values from the arrays
        songTextField.setText(songs.get(position));
        artistTextField.setText(artists.get(position));

        return rowView;

    };
}