package bee.hb.testo;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BottomBarHolderActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener {

    public String pizzatext, burgertext, drinkstext, mycarttext, helptext, settingstext, actionbartext, infotext;

    List<NavigationPage> navigationPages;

    public void setUpLanguage() {
        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("display_language", "1"));
        switch (language) {
            case 0:
                pizzatext = getResources().getString(R.string.pizzae);
                burgertext = getResources().getString(R.string.burgere);
                drinkstext = getResources().getString(R.string.drinke);
                mycarttext = getResources().getString(R.string.mycarte);
                helptext = getResources().getString(R.string.helpe);
                settingstext = getResources().getString(R.string.settingse);
                actionbartext = getResources().getString(R.string.app_namee);
                infotext = getResources().getString(R.string.infoe);
                break;
            case 1:
                pizzatext = getResources().getString(R.string.pizzat);
                burgertext = getResources().getString(R.string.burgert);
                drinkstext = getResources().getString(R.string.drinkt);
                mycarttext = getResources().getString(R.string.mycartt);
                helptext = getResources().getString(R.string.helpt);
                settingstext = getResources().getString(R.string.settingst);
                actionbartext = getResources().getString(R.string.app_namet);
                infotext = getResources().getString(R.string.infot);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLanguage();
        Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("finish", true);
        startActivity(intent);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(actionbartext);

        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if(theme == 1){
            setTheme(R.style.AppThemeO);
        }else{
            setTheme(R.style.AppTheme);
        }

        List<NavigationPage> navigationPages = new ArrayList<>();
        NavigationPage page1 = new NavigationPage(pizzatext, ContextCompat.getDrawable(this, R.drawable.pizzas), FirstFragment.newInstance());
        NavigationPage page2 = new NavigationPage(burgertext, ContextCompat.getDrawable(this, R.drawable.burger), SecondFragment.newInstance());
        NavigationPage page3 = new NavigationPage(drinkstext, ContextCompat.getDrawable(this, R.drawable.drinks), ThirdFragment.newInstance());
        NavigationPage page4 = new NavigationPage(mycarttext, ContextCompat.getDrawable(this, R.drawable.cart), FourthFragment.newInstance());

        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
        navigationPages.add(page4);

        super.setupBottomBarHolderActivity(navigationPages);

    }
    @Override
    public void onClicked() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpLanguage();
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if(theme == 1){
            setTheme(R.style.AppThemeO);
        }else{
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mnu1 = menu.add(0, 0, 0, "");
        {
            mnu1.setIcon(R.drawable.phone);
            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, helptext);
        {
            mnu2.setIcon(R.drawable.ic_check_white_48dp);
            mnu2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, settingstext);
        {
            mnu3.setIcon(R.drawable.ic_check_white_48dp);
            mnu3.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }

        MenuItem mnu4 = menu.add(0, 3, 3, infotext);
        {
            mnu3.setIcon(R.drawable.ic_info_black_24dp);
            mnu3.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent callintent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:163855"));
                startActivity(callintent);
                break;
            case 1:
//                Intent settingintent = new Intent(getBaseContext(), SettingsActivity.class);
//                startActivity(settingintent);
                break;
            case 2:
                Intent settingintent = new Intent(getBaseContext(), SettingsActivity.class);
                settingintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(settingintent);
                break;
            case 3:
                Intent infointent = new Intent(getBaseContext(), InfoActivity.class);
                infointent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(infointent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}