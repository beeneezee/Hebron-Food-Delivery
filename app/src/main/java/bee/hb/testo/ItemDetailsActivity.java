package bee.hb.testo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailsActivity extends AppCompatActivity {
    String name, description, picture, cost, id, extra;

    ImageView mPictureIv;
    TextView mExtraTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));

        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        picture = getIntent().getStringExtra("picture");
        cost = getIntent().getStringExtra("cost");
        id = getIntent().getStringExtra("id");
        extra = getIntent().getStringExtra("extra");

        if (theme == 1) {
            setTheme(R.style.AppTheme_NoActionBarO);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
        setContentView(R.layout.activity_item_details);
        mPictureIv = (ImageView) findViewById(R.id.viewitemimage);
        mExtraTv = (TextView) findViewById(R.id.viewextra);
        mExtraTv.setText(extra);
        if (theme == 1) {
            mExtraTv.setTextColor(Color.parseColor("#FFFF6600"));
        } else {
            mExtraTv.setTextColor(Color.parseColor("#033e30"));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);


        mPictureIv.setImageResource(getResources().getIdentifier(picture, "drawable", getBaseContext().getPackageName()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent i = new Intent(getBaseContext(), AddDialog.class);

                i.putExtra("name", name);
                i.putExtra("picture", picture);
                i.putExtra("cost", cost);
                i.putExtra("id", id);
                i.putExtra("description", description);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
