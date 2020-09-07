package bee.hb.testo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class ThirdFragment extends Fragment {
    ListView listView;
    int icon[];
    String title[];
    String description[];
    String cost[];
    String id[];
    String extra[];
    ArrayList<ShowModel> arrayList;
    ShowListViewAdapter showadapter;
    CartListViewAdapter cartadapter;
    View view;
    public static boolean CREATED = false;

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        listView = (ListView) view.findViewById(R.id.listViewdrinks);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayList = new ArrayList<ShowModel>();
        icon = new int[9];

        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("display_language", "1"));

        description = language == 0? getResources().getStringArray(R.array.drinkdescriptione): getResources().getStringArray(R.array.drinkdescriptiont);
        extra = language == 0? getResources().getStringArray(R.array.drinkextrae): getResources().getStringArray(R.array.drinkextrat);

        title = language == 0? getResources().getStringArray(R.array.drinknamese): getResources().getStringArray(R.array.drinknamest);

        cost = getResources().getStringArray(R.array.drinkprice);
        id = getResources().getStringArray(R.array.drinkid);
        for (int i = 0; i < 6; i++) {
            icon[i] = getResources().getIdentifier("drink" + i, "drawable", getActivity().getPackageName()); //R.drawable.piz0;
            cost[i] = cost[i];
            ShowModel showmodel = new ShowModel(title[i], description[i], icon[i], cost[i], "drink", id[i], extra[i]);
            //bind all strings in an array
            arrayList.add(showmodel);
        }
        //pass results to listViewAdapter class
        showadapter = new ShowListViewAdapter(getActivity(), arrayList);
//        cartadapter = new CartListViewAdapter(this, arrayList);

        //bind the showadapter to the listview
        listView.setAdapter(showadapter);
//        listView.setAdapter(cartadapter);
    }
}

