package com.example.nepalaya;

import static com.example.nepalaya.R.id.nav_logout;
import static com.example.nepalaya.R.id.nav_menu;
import static com.example.nepalaya.R.id.nav_order_list;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nepalaya.Floor.FloorHome;
import com.example.nepalaya.Login.Login;
import com.example.nepalaya.OrderList.OrderList;
import com.example.nepalaya.Table.TableHome;
import com.example.nepalaya.utils.CallbackMessage;
import com.example.nepalaya.utils.CustomDialogBox;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvname, tvemail, tvPowerBy;
    CircleImageView imageProfile;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    SchedulePreference schedulePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schedulePreference = new SchedulePreference(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        tvemail = headerLayout.findViewById(R.id.tv_person_email);
        imageProfile = headerLayout.findViewById(R.id.iv_profile);
        tvname = headerLayout.findViewById(R.id.tv_name);
        tvPowerBy = findViewById(R.id.tvPower_By1);

        //navigation call back
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

      /*  Picasso.with(getApplicationContext()) //setting user profile from api
                .load(baseline)
                .placeholder(R.drawable.load)
                .error(sym_def_app_icon)
                .into(imageProfile);*/
        tvPowerBy.setText(schedulePreference.getPowerBy());
        tvname.setText(schedulePreference.getUserFirstname() + " " + schedulePreference.getUserLastname());
        tvemail.setText(schedulePreference.getUserEmail());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FloorHome(), "Food Menu").addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white)); /* for changing the color of the hamburger icon of menu*/
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      /*  switch (item.getItemId()) {
            case R.id.nav_scanner:
                scanToPay();
                break;
        }*/
        return true;
    }

/*    public void scanToPay() {
        Intent intent = new Intent(this, ScanToPay.class);
        startActivity(intent);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case nav_logout:
                Dialog dialog = new CustomDialogBox(MainActivity.this).buidDialogExit(new CallbackMessage() {
                    @Override
                    public void onSuccess(Dialog dialog) {
                        SchedulePreference schedulePreference = new SchedulePreference(getApplicationContext());
                        if (schedulePreference.isUserLogIn()) {
                            schedulePreference.logOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        dialog.dismiss();
                    }
                }, "Are you sure you want to logout?");
                dialog.show();
                break;


            case nav_order_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new OrderList()).addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();
                break;

        /*    case nav_mycart:
                Intent gotocart = new Intent(MainActivity.this, Cart.class);
                startActivity(gotocart);
                break;*/


            case nav_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new FloorHome()).addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();

                break;



            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }
}