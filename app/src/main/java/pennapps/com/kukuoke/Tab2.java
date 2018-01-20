package pennapps.com.kukuoke;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import pennapps.com.kukuoke.R;

/**
 * Created by zyud on 1/19/2018.
 */

public class Tab2 extends Fragment {

    private String[] songArray = {"Fly Me to the Moon", "Hit Me Baby One More Time", "Let Me Hold You",
            "Lonely Tonight", "All Star"};
    private String[] artistArray = {"Frank Sinatra", "Britney Spears", "Bow Wow", "Blake Shelton",
            "Smash Mouth"};
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        CustomListAdapter cla = new CustomListAdapter(getActivity(), songArray, artistArray);
        listView = (ListView) rootView.findViewById(R.id.lv_tab2);
        listView.setAdapter(cla);

        return rootView;
    }
}
