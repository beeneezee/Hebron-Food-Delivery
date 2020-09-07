package bee.hb.testo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int theme = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("app_theme", "1"));
        setContentView(R.layout.activity_splash);
        ImageView img = (ImageView) findViewById(R.id.splash);
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.activity_splash);
        if(theme == 1){
            linearLayout.setBackgroundResource(R.color.colorPrimaryO);
        }else{
            linearLayout.setBackgroundResource(R.color.colorPrimary);
        }
//        img.setBackgroundResource(R.drawable.anim);
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
//        frameAnimation.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(420);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                }
            }
        }.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
