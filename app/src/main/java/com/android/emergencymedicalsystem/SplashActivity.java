package com.android.emergencymedicalsystem;

import androidx.appcompat.app.AppCompatActivity;
import com.android.emergencymedicalsystem.user.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageViewSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageViewSplash = findViewById(R.id.splash);

        Animation myanim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
        imageViewSplash.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
//                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
