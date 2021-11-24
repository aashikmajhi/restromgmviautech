package com.example.nepalaya;

import android.content.Context;
import android.content.SharedPreferences;

public class SchedulePreference {

    private static final String PREF_NAME = "pref_name";
    private static final String USER_ID = "id";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PROFILE_PICTUE = "picture";
    public static final String POWER_BY = "powerby";
    public static final String USER_LOGIN_STATUS = "login_status";
    public static final String tableid = "tableid"; // for cart check
    public static final String checkupdate = "checkupdate"; // for cart check
    public static final String orderid = "orderid"; //Order id for updates of cart

    private Context mContext;
    private final SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;

    public SchedulePreference(Context mContext) {
        this.mContext = mContext;
        this.mSharedPreference = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUserLogIn() {
        return mSharedPreference.getString(USER_ID, null) != null;
    }

    public void setUserEmail(String email) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(EMAIL, email);
        mEditor.apply();
    }

    public String getUserEmail() {
        return mSharedPreference.getString(EMAIL, null);
    }

    public void setUserFirstname(String firstname) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(USER_FIRSTNAME, firstname);
        mEditor.apply();
    }

    public String getUserFirstname() {
        return mSharedPreference.getString(USER_FIRSTNAME, null);
    }

    public void setUserLastName(String lastname) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(USER_LASTNAME, lastname);
        mEditor.apply();
    }

    public void setUserLoggedInStatus(boolean value) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putBoolean(USER_LOGIN_STATUS, value);
        mEditor.apply();
    }

    public boolean getUserLoggedInStatus() {
        return mSharedPreference.getBoolean(USER_LOGIN_STATUS, false);
    }

    public String getUserLastname() {
        return mSharedPreference.getString(USER_LASTNAME, null);
    }

    public void setTableid(String id) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(tableid, id);
        mEditor.apply();
    }

    public String gettableid() {
        return mSharedPreference.getString(tableid, null);

    }

    public void setOrderid(String orderidd) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(orderid, orderidd);
        mEditor.apply();
    }

    public String getOrderid() {
        return mSharedPreference.getString(orderid, null);
    }


    public void setcheckpdate(String checkupdatee) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(checkupdate, checkupdatee);
        mEditor.apply();
    }

    public String getcheckupdate() {
        return mSharedPreference.getString(checkupdate, null);
    }

    public void setUserId(String id) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(USER_ID, id);
        mEditor.apply();
    }

    public String getUserId() {
        return mSharedPreference.getString(USER_ID, null);

    }

    public void setProfilePictue(int pictue) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putInt(PROFILE_PICTUE, pictue);
        mEditor.apply();
    }

    public int getProfilePictue() {
        return mSharedPreference.getInt(PROFILE_PICTUE, 0);
    }

    public void setPowerBy(String powerby) {
        this.mEditor = mSharedPreference.edit();
        mEditor.putString(POWER_BY, powerby);
        mEditor.apply();
    }

    public String getPowerBy() {
        return mSharedPreference.getString(POWER_BY, null);
    }

    public void logOut() {
        this.mEditor = mSharedPreference.edit();
        mEditor.remove("id");
        mEditor.remove("email");
        // mEditor.putString(EMAIL,null);

        // mEditor.clear();
        mEditor.apply();
    }

    public void tableidremove() {
        this.mEditor = mSharedPreference.edit();
        mEditor.remove("tableid");

        mEditor.apply();
    }

    public void checkupdateremove() {
        this.mEditor = mSharedPreference.edit();
        mEditor.remove("checkupdate");

        mEditor.apply();
    }

    public void orderidremove() {
        this.mEditor = mSharedPreference.edit();
        mEditor.remove("orderid");

        mEditor.apply();
    }


}
