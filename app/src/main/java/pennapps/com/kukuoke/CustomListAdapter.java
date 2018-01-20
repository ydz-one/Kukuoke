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

import pennapps.com.kukuoke.R;

/**
 * Created by zyud on 1/19/2018.
 */

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of songs
    private final List<JSONObject> songs;


    public CustomListAdapter(Activity context, List<JSONObject> songs){

        // in actual implementation, use a different array than songArray, because there can be
        // different songs with the same name
        super(context, R.layout.listview_row, songs);

        this.context = context;
        this.songs = songs;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView songTextField = (TextView) rowView.findViewById(R.id.tv_song_name);
        TextView artistTextField = (TextView) rowView.findViewById(R.id.tv_artist_name);

        //this code sets the values of the objects to values from the arrays
        try {
            songTextField.setText(songs.get(position).get("name").toString());
            artistTextField.setText(songs.get(position).get("artist").toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        return rowView;

    };
}