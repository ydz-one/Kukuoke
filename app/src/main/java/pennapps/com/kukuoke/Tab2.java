package pennapps.com.kukuoke;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import pennapps.com.kukuoke.R;

/**
 * Created by zyud on 1/19/2018.
 */

public class Tab2 extends Fragment {
    private UserSongs userSongs;
    private int tabNum;
    private ListView listView;
    public static CustomListAdapter claTab2;
    private boolean multiSelect = false;
    private int selectedItem;
    private LinearLayout linearLayoutListViewItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        tabNum = 2;
        userSongs = UserSongs.getUserSongsInstance();

        if (!MainActivity.tab2AdapterInitialized) {
            fillSongs();
            MainActivity.tab2AdapterInitialized = true;
        }

        selectedItem = -1;

        claTab2 = new CustomListAdapter(getActivity(), userSongs.getList(tabNum));
        listView = (ListView) rootView.findViewById(R.id.lv_tab2);
        listView.setAdapter(claTab2);

        linearLayoutListViewItem = (LinearLayout) rootView.findViewById(R.id.ll_listview_item);

        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext()
                .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        listView.addFooterView(footerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == userSongs.getList(tabNum).size()) {
                    Intent intent = new Intent(getActivity(), AddSongActivity.class);
                    intent.putExtra("tabNum","tab2");
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
            menu.add(0, 0, 0,"Delete");
            menu.add(0, 1, 1,"G");
            menu.add(0, 2, 2,"N");
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
                        claTab2.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    case 1:
                        userSongs.moveSong(getContext(), selectedItem, tabNum, 1);
                        claTab2.notifyDataSetChanged();
                        Tab1.claTab1.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    case 2:
                        userSongs.moveSong(getContext(), selectedItem, tabNum, 3);
                        claTab2.notifyDataSetChanged();
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
        userSongs.addSong(getContext(), "Never Gonna Give You Up",
                "Rick Astley", tabNum);
        userSongs.addSong(getContext(), "Kiss", "Prince", tabNum);
        userSongs.addSong(getContext(), "Born to be Wild", "Steppenwolf", tabNum);
//        userSongs.addSong(getContext(), "Faith", "George Michael", tabNum);
//        userSongs.addSong(getContext(), "Thriller", "Michael Jackson", tabNum);
//        userSongs.addSong(getContext(), "Total Eclipse of the Heart", "Bonnie Tyler", tabNum);
//        userSongs.addSong(getContext(), "Girls Just Wanna Have Fun", "Cyndi Lauper", tabNum);
//        userSongs.addSong(getContext(), "Come Together", "The Beatles", tabNum);
    }
}
