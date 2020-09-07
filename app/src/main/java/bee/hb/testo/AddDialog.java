package bee.hb.testo;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AddDialog extends Activity implements View.OnClickListener {
    String[] quantities = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Spinner mCountSp;
    Button mAddBt;
    TextView mCostTv, mTitleTv;
    ImageView mPictureIv;
    String name, description, picture, cost, id;
    int count = 1, prevcount = 0;
    boolean alreadyadded;
    HandleDatabase handleDatabase;


    int language;
    public String nfatext, addtocarttext, updatecarttext, numberoftext, changedtotext, addedtocartt;

    public void setUpLanguage() {
        language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("display_language", "1"));
        switch (language) {
            case 0:
                nfatext = getResources().getString(R.string.nfae);
                addtocarttext = getResources().getString(R.string.addtocarte);
                updatecarttext = getResources().getString(R.string.updatecarte);
                numberoftext = getResources().getString(R.string.numberofe);
                changedtotext = getResources().getString(R.string.changedtoe);
                addedtocartt = getResources().getString(R.string.addedtocarte);
                break;
            case 1:
                nfatext = getResources().getString(R.string.nfat);
                addtocarttext = getResources().getString(R.string.addtocartt);
                updatecarttext = getResources().getString(R.string.updatecartt);
                numberoftext = getResources().getString(R.string.numberoft);
                changedtotext = getResources().getString(R.string.changedtot);
                addedtocartt = getResources().getString(R.string.addedtocartt);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setUpLanguage();

//get the details of the selected item from the previous screen
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        picture = getIntent().getStringExtra("picture");
        cost = getIntent().getStringExtra("cost");
        id = getIntent().getStringExtra("id");

        //create the view items
        mTitleTv = (TextView) findViewById(R.id.dialogitemtitle);
        mPictureIv = (ImageView) findViewById(R.id.dialogitemimage);
        mCountSp = (Spinner) findViewById(R.id.dialogcounter);
        mAddBt = (Button) findViewById(R.id.dialogadd);
        mCostTv = (TextView) findViewById(R.id.dialogcost);


        //set the detials
        mTitleTv.setText(name);
        mPictureIv.setImageResource(getResources().getIdentifier(picture, "drawable", getBaseContext().getPackageName()));
//        Glide.with(getBaseContext()).load(getResources().getIdentifier(picture, "drawable", getBaseContext().getPackageName())).into(mPictureIv);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if (theme == 1) {
            mTitleTv.setBackgroundResource(R.color.colorAccentO);
            mAddBt.setBackgroundResource(R.drawable.button_rounded_background_orange);
        } else {
            mTitleTv.setBackgroundResource(R.color.colorAccent);
            mAddBt.setBackgroundResource(R.drawable.button_rounded_background);
        }

        //add click listner
        mAddBt.setOnClickListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter mCountSpadapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_item, quantities);
        mCountSpadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mCountSp.setAdapter(mCountSpadapter);

        mCountSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int dispaly = Integer.parseInt(quantities[position]) * Integer.parseInt(cost);
                count = Integer.parseInt(quantities[position]);
                mCostTv.setText("+" + dispaly + " " + nfatext);
                mAddBt.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAddBt.setEnabled(false);
            }

        });

        handleDatabase = new HandleDatabase(getBaseContext());
        handleDatabase.openDataBase();
        alreadyadded = handleDatabase.checkItemInTheCart(id);

        try {

            if (alreadyadded) {
                prevcount = handleDatabase.getItemCountInTheCart(id);
                mCountSp.setSelection(prevcount);
                mAddBt.setText(updatecarttext);
            } else {
                mAddBt.setText(addtocarttext);
            }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
//            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogadd:
                try {
                    if (alreadyadded) {
                        databaseUpdater(id, count + "");
                        //handleDatabase.updateItemInTheCart(id, count + "");
                        if (language == 0)
                            Toasty.warning(getBaseContext(), numberoftext + " " + name + " " + changedtotext + count, Toasty.LENGTH_LONG, true).show();
                        else
                            Toasty.warning(getBaseContext(), numberoftext + " " + name + " " + getResources().getString(R.string.to) + " " + count + " " + changedtotext, Toasty.LENGTH_LONG, true).show();
                    } else {
                        handleDatabase.addToCart(id, name, description, count + "", cost, picture);
                        Toasty.success(getBaseContext(), name + " " + addedtocartt, Toasty.LENGTH_SHORT, true).show();
                    }
                    handleDatabase.close();
                    uiFeedbacks();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void databaseUpdater(String id, String count) {
        handleDatabase.updateItemInTheCart(id, count);
    }

    protected void uiFeedbacks() {

        boolean vibrate = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vibrations", false);
        boolean sound = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("sounds", false);
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(150);

        }
        if (sound) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.cash);
            mediaPlayer.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
