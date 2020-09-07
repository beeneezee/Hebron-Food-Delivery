package bee.hb.testo;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
//                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("finish", false)) {
            finish();
        }
        setUpLanguage();
        setupActionBar();
        mContext = SettingsActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpLanguage();
        setupActionBar();
        mContext = SettingsActivity.this;
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(settingstext);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            SettingsActivity.this.finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
//        loadHeadersFromResource(R.xml.pref_headers, target);


        Header General = new Header();
        General.title = generaltext;
        General.iconRes = R.drawable.ic_info_black_24dp;
        General.fragment = "bee.hb.testo.SettingsActivity$GeneralPreferenceFragment";
        target.add(General);


        Header FeedBack = new Header();
        FeedBack.title = uifeedbacktext;
        FeedBack.iconRes = R.drawable.ic_notifications_black_24dp;
        FeedBack.fragment = "bee.hb.testo.SettingsActivity$FeedBacksPreferenceFragment";
        target.add(FeedBack);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || FeedBacksPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            ListPreference language = (ListPreference) findPreference("display_language");
            language.setTitle(languagetext);
            language.setDialogTitle(languagetext);


            ListPreference apptheme = (ListPreference) findPreference("app_theme");
            apptheme.setTitle(themetext);
            apptheme.setEntries(themeEntries);
            apptheme.setDialogTitle(themetext);

            EditTextPreference passwordedettext = (EditTextPreference) findPreference("password");
            passwordedettext.setTitle(passwordtext);
            passwordedettext.setDialogTitle(passwordtext);

            bindPreferenceSummaryToValue(findPreference("password"));
            bindPreferenceSummaryToValue(findPreference("app_theme"));
            bindPreferenceSummaryToValue(findPreference("display_language"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                 return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onPause() {
            super.onPause();
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class FeedBacksPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);


            SwitchPreference sounds = (SwitchPreference) findPreference("sounds");
            sounds.setTitle(soundstext);
            sounds.setSummary(soundsummarytext);


            SwitchPreference vibrations = (SwitchPreference) findPreference("vibrations");
            vibrations.setTitle(vibrationstext);
            vibrations.setSummary(vibrationsummarytext);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onPause() {
            super.onPause();
        }
    }

    public static String[] themeEntries;
    public static String generaltext;
    public static String uifeedbacktext;
    public static String languagetext;
    public static String vibrationstext;
    public static String soundstext;
    public static String themetext;
    public static String passwordtext;
    public static String vibrationsummarytext;
    public static String soundsummarytext;
    public static String settingstext;
    public static Context mContext;

    public void setUpLanguage() {
        int language = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("display_language", "1"));
        switch (language) {
            case 0:
                themeEntries = getResources().getStringArray(R.array.theme_list_titlese);
                generaltext = getResources().getString(R.string.generale);
                uifeedbacktext = getResources().getString(R.string.feedbacke);
                languagetext = getResources().getString(R.string.lange);
                vibrationstext = getResources().getString(R.string.vibratione);
                soundstext = getResources().getString(R.string.soundse);
                themetext = getResources().getString(R.string.themee);
                passwordtext = getResources().getString(R.string.passworde);
                vibrationsummarytext = getResources().getString(R.string.vibrationsummurye);
                soundsummarytext = getResources().getString(R.string.soundsummurye);
                settingstext = getResources().getString(R.string.settingse);
                break;
            case 1:
                themeEntries = getResources().getStringArray(R.array.theme_list_titlest);
                generaltext = getResources().getString(R.string.generalt);
                uifeedbacktext = getResources().getString(R.string.feedbackt);
                languagetext = getResources().getString(R.string.langt);
                vibrationstext = getResources().getString(R.string.vibrationt);
                soundstext = getResources().getString(R.string.soundst);
                themetext = getResources().getString(R.string.themet);
                passwordtext = getResources().getString(R.string.passwordt);
                vibrationsummarytext = getResources().getString(R.string.vibrationsummuryt);
                soundsummarytext = getResources().getString(R.string.soundsummuryt);
                settingstext = getResources().getString(R.string.settingst);
                break;
            default:

        }
//                Toast.makeText(getBaseContext(), language, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
    }
}
