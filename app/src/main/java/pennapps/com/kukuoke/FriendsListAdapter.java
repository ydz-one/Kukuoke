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

public class FriendsListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of songs
    private final List<String> friends;

    private int resource;


    public FriendsListAdapter(Activity context, List<String> friends, int resource){

        super(context, R.layout.listview_row_friends, friends);

        this.context = context;
        this.friends = friends;
        this.resource = resource;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(resource, null,true);

        TextView songTextField = (TextView) rowView.findViewById(R.id.tv_friend_name);

        songTextField.setText(friends.get(position));

        return rowView;
    };
}