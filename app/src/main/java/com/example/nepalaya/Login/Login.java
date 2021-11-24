package com.example.nepalaya.Login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.nepalaya.ApiService.APIService;
import com.example.nepalaya.ApiService.ApiHelper;
import com.example.nepalaya.MainActivity;
import com.example.nepalaya.R;
import com.example.nepalaya.SchedulePreference;
import com.example.nepalaya.Table.TableHome;
import com.example.nepalaya.utils.CallbackMessage;
import com.example.nepalaya.utils.CustomDialogBox;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @NotEmpty(messageId = R.string.validation_field, order = 1)
    @BindView(R.id.etEmail)
    EditText etemail;

/*    @BindView(R.id.tv_shine)
    TextView tvShine;*/



    @SuppressLint("NonConstantResourceId")
    @NotEmpty(messageId = R.string.validation_field, order = 2)
    @BindView(R.id.etPassword)
    EditText etpassword;

    SchedulePreference mpref;
    String email, password, id, firstname, lastname, utech;
    int profileImage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading_spinner)
    ProgressBar pb;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.image_view)
    ImageView company_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    Animation animationShine;
    SchedulePreference schedulePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        tvShine = findViewById(R.id.tv_shine);
        animationShine = AnimationUtils.loadAnimation(this, R.anim.left_right);
        animationShine.setRepeatCount(Animation.INFINITE);
//        tvShine.startAnimation(animationShine);

        schedulePreference = new SchedulePreference(getApplicationContext());

    }

    @OnClick(R.id.btn_login)
    public void btnClick() {
        if (FormValidator.validate(this, new SimpleErrorPopupCallback(getApplicationContext(), true))) {
            getData();
        }
    }

    public void getData() {
        company_image.setAlpha(0.5f);
        pb.setVisibility(View.VISIBLE);
        etemail.setEnabled(false);
        etpassword.setEnabled(false);
        btnLogin.setEnabled(false);

        email = etemail.getText().toString();
        password = etpassword.getText().toString();

        APIService apiService = (APIService) ApiHelper.getInstance().getService(APIService.class);
        Call<JsonElement> call = apiService.login(email, password);

        call.enqueue(new Callback<JsonElement>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JSONObject data = new JSONObject();
                if (response.isSuccessful()) {
                    String responseValue = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(responseValue);
                        data = jsonObject.getJSONObject("data");

                        id = data.getString("id");
                        email = data.getString("email");
                        firstname = data.getString("firstname");
                        lastname = data.getString("lastname");
                        // profileImage = Integer.parseInt(data.getString("picture"));
                        utech = data.getString("PowerBy");

                        // int id1 = Integer.parseInt(id);
                        mpref = new SchedulePreference(Login.this);
                        mpref.setUserId(id);
                        mpref.setUserEmail(email);
                        mpref.setUserFirstname(firstname);
                        mpref.setUserLastName(lastname);
                        mpref.setPowerBy(utech);
                        mpref.setProfilePictue(profileImage);
                        mpref.setUserLoggedInStatus(true);
                        etemail.setText(email);
                        etpassword.setText("");

                        /*Intent intent = new Intent(Login.this, MainActivity.class);
                        //  intent.putExtra("profile_picture",profileImage);
                        startActivity(intent);*/

                        Intent gotoTable = new Intent(Login.this, MainActivity.class);
                        startActivity(gotoTable);
                        finish();

//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TableHome(), "").addToBackStack(null).addToBackStack(null).commitAllowingStateLoss();
                        Toasty.success(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        frontendLogic();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toasty.custom(getApplicationContext(),"Please, check your email and password",null,R.color.blue,R.color.orange,Toasty.LENGTH_SHORT,false,true).show();
                        Toasty.error(getApplicationContext(), "Please, check your email and password", Toasty.LENGTH_SHORT).show();
                        frontendLogic();
                    }


                } else {
                    frontendLogic();
                    Toast.makeText(getApplicationContext(), "Login credential mismatched!", Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                frontendLogic();
                Toast.makeText(getApplicationContext(), "Please, check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void frontendLogic() {
        pb.setVisibility(View.GONE);
        company_image.setAlpha(1f);
        etemail.setEnabled(true);
        etpassword.setEnabled(true);
        btnLogin.setEnabled(true);
        etemail.setText(email);
        etpassword.setText("");
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        exitDialog();
    }

    public void exitDialog() {
        Dialog dialog = new CustomDialogBox(Login.this).buidDialogExit(new CallbackMessage() {
            @Override
            public void onSuccess(Dialog dialog) {
                dialog.dismiss();
                finish();
                System.exit(0);
            }

            @Override
            public void onCancel(Dialog dialog) {
                dialog.dismiss();
            }
        }, "Are you sure you want to exit?");
        dialog.show();
    }

}