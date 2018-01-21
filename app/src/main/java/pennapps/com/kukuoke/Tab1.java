package pennapps.com.kukuoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by zyud on 1/19/2018.
 */

public class Tab1 extends Fragment {
    private UserSongs userSongs;
    private int tabNum;
    private ListView listView;
    public static CustomListAdapter claTab1;
    private boolean multiSelect = false;
    private int selectedItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        tabNum = 1;
        userSongs = UserSongs.getUserSongsInstance();

        if (!MainActivity.tab1AdapterInitialized) {
            fillSongs();
            MainActivity.tab1AdapterInitialized = true;
        }

        selectedItem = -1;

        claTab1 = new CustomListAdapter(getActivity(), userSongs.getList(tabNum), R.layout.listview_row);
        listView = (ListView) rootView.findViewById(R.id.lv_tab1);
        listView.setAdapter(claTab1);

        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext()
                .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        listView.addFooterView(footerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == userSongs.getList(tabNum).size()) {
                    Intent intent = new Intent(getActivity(), AddSongActivity.class);
                    intent.putExtra("tabNum","tab1");
                    startActivity(intent);
                } else {
                    selectedItem = position;
                    ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                }
            }
        });

        return rootView;
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu){
            menu.add(0, 0, 2, "DEL");
            menu.add(0, 1, 0,">");
            menu.add(0, 2, 1,">>");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (selectedItem >= 0) {
                switch(item.getItemId()) {
                    case 0:
                        userSongs.deleteSong(selectedItem, tabNum);
                        claTab1.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    case 1:
                        userSongs.moveSong(getContext(), selectedItem, tabNum, 2);
                        claTab1.notifyDataSetChanged();
                        Tab2.claTab2.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    case 2:
                        userSongs.moveSong(getContext(), selectedItem, tabNum, 3);
                        claTab1.notifyDataSetChanged();
                        Tab3.claTab3.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            } else {
                mode.finish();
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    private void fillSongs(){

        String str = FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child("goodSongs").getKey();
        Log.d("KEY", str);

        FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.FBU.getUid()).child("goodSongs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();

                ArrayList<JSONObject> arr = new ArrayList<>();
                while (itr.hasNext()) {
                    String s = itr.next().getValue().toString();
                    try {
                        arr.add(new JSONObject(s));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                for (JSONObject jo : arr) {
                    try {
                        userSongs.addSong(getContext(), jo.get("name").toString(), jo.get("artist").toString(), tabNum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
