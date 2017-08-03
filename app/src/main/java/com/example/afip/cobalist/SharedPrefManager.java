package com.example.afip.cobalist;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.afip.cobalist.Config.KEY_FULLNAME_PREF;
import static com.example.afip.cobalist.Config.KEY_JENIS_KELAMIN_PREF;
import static com.example.afip.cobalist.Config.KEY_USERNAME_PREF;
import static com.example.afip.cobalist.Config.KEY_USER_ID;
import static com.example.afip.cobalist.Config.SHARED_PREF_NAME;

/**
 * Created by afip on 7/15/2017.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public static final String KEY_ID_PENGKURAN = "id_pengkuuran";
    public static final String KEY_PERCOBAAN_KE = "percobaan_ke";
    public static final String KEY_IS_LOGGED_IN = "is_logged_in";
    public static final String KEY_TOKEN = "token";


    private SharedPrefManager(Context context) {
        mCtx = context;
    }


    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public int getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        int id_user = sharedPreferences.getInt(KEY_USER_ID,0);
        return  id_user;
    }
    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME_PREF,null);
        if(username!=null && !username.equals(""))
            return username;
        else return null;
    }
    public String getFullname() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String nama_panjang = sharedPreferences.getString(KEY_FULLNAME_PREF,null);
        if(nama_panjang!=null && !nama_panjang.equals(""))
            return nama_panjang;
        else return null;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return (sharedPreferences.contains(KEY_IS_LOGGED_IN) && sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false));
    }

    public int getIdPengkuruan(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        int id_pengkuran = sharedPreferences.getInt(KEY_ID_PENGKURAN,0);
        return id_pengkuran;
    }

    public int getPercobaanKe(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_PERCOBAAN_KE,0);
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN,null);
    }

    public final String getJenisKelamin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String jenis_kelamin = sharedPreferences.getString(KEY_JENIS_KELAMIN_PREF,null);
        if(jenis_kelamin!=null && !jenis_kelamin.equals(""))
            return jenis_kelamin;
        else return null;
    }

    public void setToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
        editor.commit();
    }


    public void login(int id_user, String username, String jenis_kelamin, String nama_panjang){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, id_user);
        editor.putString(KEY_USERNAME_PREF, username);
        editor.putString(KEY_JENIS_KELAMIN_PREF, jenis_kelamin);
        editor.putString(KEY_FULLNAME_PREF, nama_panjang);
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
        editor.commit();
    }

}
