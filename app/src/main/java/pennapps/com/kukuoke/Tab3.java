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

public class Tab3 extends Fragment {

    private String[] songArray = {"Heart Break Hotel", "Karma Chameleon", "People Equal Shit"};
    private String[] artistArray = {"Elvis Presley", "Culture Club", "Slipknot"};
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        CustomListAdapter cla = new CustomListAdapter(getActivity(), songArray, artistArray);
        listView = (ListView) rootView.findViewById(R.id.lv_tab3);
        listView.setAdapter(cla);

        return rootView;
    }
}
