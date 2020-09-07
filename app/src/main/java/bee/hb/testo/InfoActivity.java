package bee.hb.testo;

import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    public String hebron;

    public void setUpLanguage() {
        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("display_language", "1"));
        switch (language) {
            case 0:
                hebron = getResources().getString(R.string.app_namee);
                break;
            case 1:
                hebron = getResources().getString(R.string.app_namet);
                break;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLanguage();
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if(theme == 1){
            setTheme(R.style.AppThemeO);
        }else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_info);
        TextView hbapps = (TextView) findViewById(R.id.hbapps);
        if(theme == 1){
            hbapps.setTextColor(Color.parseColor("#FFFF8800"));
        }else{
            hbapps.setTextColor(Color.parseColor("#033e30"));
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(hebron);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
