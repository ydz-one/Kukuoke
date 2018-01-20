package pennapps.com.kukuoke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import pennapps.com.kukuoke.R;

/**
 * Created by zyud on 1/19/2018.
 */

public class Tab1 extends Fragment {
    private String[] songArray = {"L.O.V.E", "Sorry", "Out of Reach", "Truly Madly Deeply",
            "Falling into You", "Careless Whisper", "Faith"};
    private String[] artistArray = {"Nat King Cole", "Justin Bieber", "Gabrielle", "Savage Garden",
            "Celine Dion", "George Michael", "George Michael"};
    private ListView listView;
    private Button addSongBotton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        CustomListAdapter cla = new CustomListAdapter(getActivity(), songArray, artistArray);
        listView = (ListView) rootView.findViewById(R.id.lv_tab1);
//        addSongBotton = (Button) rootView.findViewById(R.id.btn_tab1_add_song);
        listView.setAdapter(cla);

        View footerView =  ((LayoutInflater)getContext().getSystemService(getContext()
                .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        listView.addFooterView(footerView);

//        addSongBotton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//                Intent intent = new Intent(getContext(), AddSongActivity.class);
//
//                // currentContext.startActivity(activityChangeIntent);
//                startActivity(intent);
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AddSongActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
