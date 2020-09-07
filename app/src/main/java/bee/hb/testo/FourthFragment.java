package bee.hb.testo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Adib on 13-Apr-17.
 */

public class FourthFragment extends Fragment implements View.OnClickListener {
    ListView listView;

    String id[];
    String title[];
    String description[];
    String icons[];
    int icon[];
    String cost[];
    String count[];

    int totalpricedispaly = 0;

    ArrayList<CartModel> arrayList;
    ShowListViewAdapter showadapter;
    CartListViewAdapter cartadapter;

    View view;
    TextView totalprice, totalcount, cartempty;
    Button order, cartlocation;
    CardView cartsummary;
    ImageView emptycart;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;

    public static int COUNTER = 0;
    HandleDatabase handleDatabase;

    String themessage = "HD:";

    int language;
    public String nfatext, passwrodtext, ordertext, itemtext, itemstext, itemstextfinal, oktext, canceltext, sendingordertext, pleasewaittext, areyousureordertext, setlocationtext;

    public void setUpLanguage() {
        language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("display_language", "1"));
        switch (language) {
            case 0:
                itemtext = getActivity().getResources().getString(R.string.iteme);
                itemstext = getActivity().getResources().getString(R.string.itemse);
                ordertext = getActivity().getResources().getString(R.string.ordere);
                nfatext = getActivity().getResources().getString(R.string.nfae);
                oktext = getActivity().getResources().getString(R.string.oke);
                canceltext = getActivity().getResources().getString(R.string.cancele);
                sendingordertext = getActivity().getResources().getString(R.string.sendingordere);
                pleasewaittext = getActivity().getResources().getString(R.string.pleasewaite);
                areyousureordertext = getActivity().getResources().getString(R.string.areyousureordere);
                setlocationtext = getActivity().getResources().getString(R.string.setlocatione);
                passwrodtext = getActivity().getResources().getString(R.string.passworde);
                break;
            case 1:
                itemtext = getActivity().getResources().getString(R.string.itemt);
                itemstext = getActivity().getResources().getString(R.string.itemst);
                ordertext = getActivity().getResources().getString(R.string.ordert);
                nfatext = getActivity().getResources().getString(R.string.nfat);
                oktext = getActivity().getResources().getString(R.string.okt);
                canceltext = getActivity().getResources().getString(R.string.cancelt);
                sendingordertext = getActivity().getResources().getString(R.string.sendingordert);
                pleasewaittext = getActivity().getResources().getString(R.string.pleasewaitt);
                areyousureordertext = getActivity().getResources().getString(R.string.areyousureordert);
                setlocationtext = getActivity().getResources().getString(R.string.setlocationt);
                passwrodtext = getActivity().getResources().getString(R.string.passwordt);
                break;

        }
    }


    public static FourthFragment newInstance() {
        return new FourthFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        listView = (ListView) view.findViewById(R.id.listViewcart);
        totalprice = (TextView) view.findViewById(R.id.carttotalprice);
        totalcount = (TextView) view.findViewById(R.id.cartotaltitemcount);
        cartempty = (TextView) view.findViewById(R.id.cartempty);
        order = (Button) view.findViewById(R.id.cartorder);
        cartsummary = (CardView) view.findViewById(R.id.cartsummary);
        cartlocation = (Button) view.findViewById(R.id.cartlocation);
        emptycart = (ImageView) view.findViewById(R.id.cartemptyimg);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.fourth_fragment_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpLanguage();
        COUNTER++;

        arrayList = new ArrayList<CartModel>();
        handleDatabase = new HandleDatabase(getActivity());
        handleDatabase.openDataBase();

        id = handleDatabase.allIdsInTheCart();
        title = handleDatabase.allNamesInTheCart();
        description = handleDatabase.allDescriptionsInTheCart();
        icons = handleDatabase.allPicturesInTheCart();
        cost = handleDatabase.allPricesInTheCart();
        count = handleDatabase.allCountsInTheCart();
        totalpricedispaly = handleDatabase.totalPrice();

        for (int i = 0; i < id.length; i++) {
            themessage = themessage + "ID:" + id[i] + "QTY:" + count[i];
        }

//        Toast.makeText(getActivity(), count.length + "", Toast.LENGTH_LONG).show();
        handleDatabase.close();

        icon = new int[9];

        if (count.length == 0) {
            listView.setVisibility(View.GONE);
            emptycart.setVisibility(View.VISIBLE);
            cartempty.setVisibility(View.VISIBLE);
            cartsummary.setVisibility(View.GONE);
            String cartemptytext = language == 0 ? getResources().getString(R.string.nothingselectede) : getResources().getString(R.string.nothingselectedt);
            cartempty.setText(cartemptytext);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptycart.setVisibility(View.GONE);
            cartempty.setVisibility(View.GONE);
            cartsummary.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < count.length; i++) {
            icon[i] = getResources().getIdentifier(icons[i], "drawable", getActivity().getPackageName());
            CartModel cartModel = new CartModel(title[i], description[i], icon[i], cost[i], count[i], id[i]);
            //bind all strings in an array
            arrayList.add(cartModel);
        }
        if (count.length > 1) {
            itemstextfinal = itemstext;
        } else {
            itemstextfinal = itemtext;
        }
//        Toast.makeText(getActivity(), itemstextfinal, Toast.LENGTH_LONG).show();
        totalcount.setText(count.length + " " + itemstextfinal);
        totalprice.setText(totalpricedispaly + " " + nfatext);
        //pass results to listViewAdapter class
        cartadapter = new CartListViewAdapter(getActivity(), arrayList);

        //bind the showadapter to the listview
        listView.setAdapter(cartadapter);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("app_theme", "1"));
        if (theme == 1) {
            cartlocation.setBackgroundResource(R.drawable.button_rounded_background_orange);
        } else {
            cartlocation.setBackgroundResource(R.drawable.button_rounded_background);
        }
        cartlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!themessage.contains("LOC:"))
                    themessage = themessage + " LOC:L143567 a345678";
                Toast.makeText(getActivity(), themessage, Toast.LENGTH_LONG).show();
            }
        });
        totalcount.setOnClickListener(this);
        totalprice.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        order.setText(ordertext);
        order.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {


                                         id = handleDatabase.allIdsInTheCart();
                                         title = handleDatabase.allNamesInTheCart();
                                         description = handleDatabase.allDescriptionsInTheCart();
                                         icons = handleDatabase.allPicturesInTheCart();
                                         cost = handleDatabase.allPricesInTheCart();
                                         count = handleDatabase.allCountsInTheCart();
                                         totalpricedispaly = handleDatabase.totalPrice();
                                         if (id.length > 0) {
                                             if (totalpricedispaly < 100) {
                                                 Toasty.error(getActivity(), "too small amount of money, bitch we aint coming for that little ass money. You Suck! Beat IT.", Toast.LENGTH_LONG).show();
                                             } else if (themessage.contains("LOC:")) {
                                                 LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                                 linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.activity_input_password, null);

                                                 final TextView titleText = (TextView) linearLayout.findViewById(R.id.conformpasswordtitle);
                                                 titleText.setText(areyousureordertext);
                                                 final ShowHidePasswordEditText userInputPassword = (ShowHidePasswordEditText) linearLayout.findViewById(R.id.conformpassword);
                                                 userInputPassword.setHint(passwrodtext);
                                                 int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("app_theme", "1"));
                                                 if (theme == 1) {
                                                     titleText.setBackgroundResource(R.color.colorAccentO);
                                                 } else {
                                                     titleText.setBackgroundResource(R.color.colorAccent);
                                                 }
                                                 final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                 builder.setView(linearLayout);
                                                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         if (userInputPassword.getText().toString().trim().equals(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("password", "1"))) {
                                                             final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), sendingordertext, pleasewaittext, true);
                                                             new Thread(new Runnable() {
                                                                 public void run() {
                                                                     try {
//---simulate doing something lengthy---
                                                                         Thread.sleep(600);
//---dismiss the dialog---
                                                                         progressDialog.dismiss();
                                                                     } catch (InterruptedException e) {
                                                                         e.printStackTrace();
                                                                     }
                                                                 }
                                                             }).start();
                                                             HandleDatabase truncatecart = new HandleDatabase(getActivity());
                                                             truncatecart.openDataBase();
                                                             truncatecart.deleteAllFromCart();

                                                             listView.setVisibility(View.GONE);
                                                             cartsummary.setVisibility(View.GONE);
                                                             emptycart.setVisibility(View.VISIBLE);
                                                             cartempty.setVisibility(View.VISIBLE);
                                                             String thanks = language == 0 ? getResources().getString(R.string.thankse) : getResources().getString(R.string.thankst);

                                                             emptycart.setImageResource(getResources().getIdentifier("thanks", "drawable", getActivity().getPackageName()));

                                                             cartempty.setText(thanks);
                                                             cartempty.setTextColor(Color.parseColor("#6cbaf9"));
                                                             truncatecart.close();
                                                             sendSMSMessage();
                                                             uiFeedbacks();
                                                             builder.setCancelable(true);
                                                         } else

                                                         {
                                                             Toasty.error(getActivity(), "INCORRECT PASSWORD", Toast.LENGTH_LONG).show();
                                                         }
                                                     }
                                                 });
                                                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {

                                                     }
                                                 });

                                                 builder.show();
                                             } else {
                                                 Toast.makeText(getActivity(), setlocationtext, Toast.LENGTH_LONG).show();
                                             }
                                         } else

                                         {
                                             listView.setVisibility(View.GONE);
                                             emptycart.setVisibility(View.VISIBLE);
                                             cartempty.setVisibility(View.VISIBLE);
                                             cartsummary.setVisibility(View.GONE);
                                             String cartemptytext = language == 0 ? getResources().getString(R.string.nothingselectede) : getResources().getString(R.string.nothingselectedt);
                                             cartempty.setText(cartemptytext);
                                         }
                                     }
                                 }

        );
    }

    protected void sendSMSMessage() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("07160262", null, themessage, null, null);
            themessage = "HD:";
        } catch (Exception e) {
        }
    }

    protected void uiFeedbacks() {

        boolean vibrate = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("vibrations", false);
        boolean sound = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("sounds", false);
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(150);

        }
        if (sound) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.cash);
            mediaPlayer.start();
        }
    }

    @Override
    public void onClick(View v) {
//        if (id.length > 0) {
//            listView.setVisibility(View.GONE);
//            emptycart.setVisibility(View.VISIBLE);
//            cartempty.setVisibility(View.VISIBLE);
//            cartsummary.setVisibility(View.GONE);
//            String cartemptytext = language == 0 ? getResources().getString(R.string.nothingselectede) : getResources().getString(R.string.nothingselectedt);
//            cartempty.setText(cartemptytext);
//        }
    }
}
