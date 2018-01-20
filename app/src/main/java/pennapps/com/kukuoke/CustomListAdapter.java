package pennapps.com.kukuoke;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pennapps.com.kukuoke.R;

/**
 * Created by zyud on 1/19/2018.
 */

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of songs
    private final String[] songArray;

    //to store the list of artists
    private final String[] artistArray;

    public CustomListAdapter(Activity context, String[] songArray, String[] artistArray){

        // in actual implementation, use a different array than songArray, because there can be
        // different songs with the same name
        super(context, R.layout.listview_row, songArray);

        this.context = context;
        this.songArray = songArray;
        this.artistArray = artistArray;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.tv_song_name);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.tv_artist_name);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(songArray[position]);
        infoTextField.setText(artistArray[position]);

        return rowView;

    };
}