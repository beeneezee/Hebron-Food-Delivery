package bee.hb.testo;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BottomBarHolderActivity extends AppCompatActivity implements BottomNavigationBar.BottomNavigationMenuClickListener {

    // helper class for handling UI and click events of bottom-nav-bar
    private BottomNavigationBar mBottomNav;

    // list of Navigation pages to be shown
    private List<NavigationPage> mNavigationPageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if (theme == 1) {
            setTheme(R.style.AppThemeO);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_bottom_bar_holder);
        View theview0 = findViewById(R.id.viewBar1);
        View theview1 = findViewById(R.id.viewBar2);
        View theview2 = findViewById(R.id.viewBar3);
        View theview3 = findViewById(R.id.viewBar4);
        AppCompatTextView appCompatTextView = (AppCompatTextView) findViewById(R.id.textViewBar1);
        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.imageViewBar1);
        if (theme == 1) {
            theview0.setBackgroundResource(R.color.colorNavAccentSelectedO);
            theview1.setBackgroundResource(R.color.colorNavAccentSelectedO);
            theview2.setBackgroundResource(R.color.colorNavAccentSelectedO);
            theview3.setBackgroundResource(R.color.colorNavAccentSelectedO);
//            appCompatTextView.setTextColor(Color.parseColor("#f80"));
        } else {
            theview0.setBackgroundResource(R.color.colorNavAccentSelected);
            theview1.setBackgroundResource(R.color.colorNavAccentSelected);
            theview2.setBackgroundResource(R.color.colorNavAccentSelected);
            theview3.setBackgroundResource(R.color.colorNavAccentSelected);
//            appCompatTextView.setTextColor(Color.parseColor("#011d16"));
        }
    }

    /**
     * initializes the BottomBarHolderActivity with sent list of Navigation pages
     *
     * @param pages
     */
    public void setupBottomBarHolderActivity(List<NavigationPage> pages) {

        // throw error if pages does not have 4 elements
        if (pages.size() != 4) {
            throw new RuntimeException("List of NavigationPage must contain 4 members.");
        } else {
            mNavigationPageList = pages;
            mBottomNav = new BottomNavigationBar(this, pages, this);
            setupFragments();
        }

    }

    /**
     * sets up the fragments with initial view
     */
    private void setupFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, mNavigationPageList.get(0).getFragment());
        fragmentTransaction.commit();
    }

    /**
     * handling onclick events of bar items
     *
     * @param menuType
     */
    @Override
    public void onClickedOnBottomNavigationMenu(int menuType) {

        // finding the selected fragment
        Fragment fragment = null;
        switch (menuType) {
            case BottomNavigationBar.MENU_BAR_1:
                fragment = mNavigationPageList.get(0).getFragment();
                break;
            case BottomNavigationBar.MENU_BAR_2:
                fragment = mNavigationPageList.get(1).getFragment();
                break;
            case BottomNavigationBar.MENU_BAR_3:
                fragment = mNavigationPageList.get(2).getFragment();
                break;
            case BottomNavigationBar.MENU_BAR_4:
                fragment = mNavigationPageList.get(3).getFragment();
                break;
        }

        // replacing fragment with the current one
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        if (theme == 1) {
            setTheme(R.style.AppThemeO);
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
