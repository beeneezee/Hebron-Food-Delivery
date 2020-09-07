package bee.hb.testo;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class SecondFragment extends Fragment{
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
    private OnFragmentInteractionListener listener;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        listView = (ListView) view.findViewById(R.id.listViewburger);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayList = new ArrayList<ShowModel>();
        icon = new int[9];


        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("display_language", "1"));

        title = language == 0? getResources().getStringArray(R.array.burgernamese): getResources().getStringArray(R.array.burgernamest);

        description = language == 0? getResources().getStringArray(R.array.burgerdescriptione): getResources().getStringArray(R.array.burgerdescriptiont);
        extra = language == 0? getResources().getStringArray(R.array.burgerextrae): getResources().getStringArray(R.array.burgerextrat);

        cost = getResources().getStringArray(R.array.burgerid);
        id = getResources().getStringArray(R.array.pizzaid);
        for (int i = 0; i < 7; i++) {
            icon[i] = getResources().getIdentifier("burger" + i, "drawable", getActivity().getPackageName()); //R.drawable.piz0;
            cost[i] = cost[i];
            ShowModel showmodel = new ShowModel(title[i], description[i], icon[i], cost[i], "burger", id[i], extra[i]);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragment.OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {

        void onClicked();

    }

}
