package com.example.nepalaya;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalaya.Login.Login;
import com.example.nepalaya.OrderList.OrderList;
import com.example.nepalaya.Table.TableHome;

import de.hdodenhof.circleimageview.CircleImageView;

public class Splash extends AppCompatActivity {
    public static int splash_time = 2500;
    CircleImageView imageView;
    TextView textViewCompanyName;

    Animation aniText, aniImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.company_logo);
        textViewCompanyName = findViewById(R.id.tv_company_name);

        aniText = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        aniImage = AnimationUtils.loadAnimation(this, R.anim.from_bottom_second);

        textViewCompanyName.setAnimation(aniText);
        imageView.setAnimation(aniImage);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SchedulePreference mPref = new SchedulePreference(Splash.this);
                if (!mPref.isUserLogIn()) {
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                    finish();

                /*    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();*/

                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new TableHome()).addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();

                }
            }
        }, splash_time);
    }
}