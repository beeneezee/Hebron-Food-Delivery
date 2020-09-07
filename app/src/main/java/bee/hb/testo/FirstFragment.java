package bee.hb.testo;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class FirstFragment extends Fragment {
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

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        listView = (ListView) view.findViewById(R.id.listViewpiz);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayList = new ArrayList<ShowModel>();
        icon = new int[9];
        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("display_language", "1"));

        title = language == 0? getResources().getStringArray(R.array.pizzanamese): getResources().getStringArray(R.array.pizzanamest);

        description = language == 0? getResources().getStringArray(R.array.burgerdescriptione): getResources().getStringArray(R.array.pizzadescriptiont);
        extra = language == 0? getResources().getStringArray(R.array.pizzaextrae): getResources().getStringArray(R.array.pizzaextrat);

        cost = getResources().getStringArray(R.array.pizzaprice);
        id = getResources().getStringArray(R.array.pizzaid);

        for (int i = 0; i < 9; i++) {
            icon[i] = getResources().getIdentifier("piz" + i, "drawable", getActivity().getPackageName()); //R.drawable.piz0;
            cost[i] = cost[i];
            ShowModel showmodel = new ShowModel(title[i], description[i], icon[i], cost[i], "piz", id[i], extra[i]);
            //bind all strings in an array
            arrayList.add(showmodel);
        }
        //pass results to listViewAdapter class
        showadapter = new ShowListViewAdapter(getActivity(), arrayList);
        listView.setAdapter(showadapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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
    }

}
