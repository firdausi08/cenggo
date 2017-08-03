package com.example.afip.cobalist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Config {
   // public static final String BASE_URL = "http://192.168.8.101";
    public static final String BASE_URL = "http://ecenggondokpens.esy.es";
   public  static final String DATA_URL = "http://ecenggondokpens.esy.es/cenggoserver/sungai";
   public  static final String DATA_URL_percobaan = "https://cenggo.000webhostapp.com``/cenggoserver/getpercobaan";
//   public  static final String DATA_URL = "http://192.168.43.51/cenggoserver/sungai";

    public static final String GET_PENGUKURAN = BASE_URL + "/cenggoserver/getpengukuran";
    public static final String LOGIN = BASE_URL + "/cenggoserver/login";
    public static final String ISIDATA = BASE_URL + "/cenggoserver/isidata";
    public static final String GET_SUNGAI = BASE_URL + "/cenggoserver/sungai";
    public static final String INPUT_PENGUKURAN = BASE_URL + "/cenggoserver/inputpengukuran";
    public static final String INPUT_PERCOBAAN = BASE_URL + "/cenggoserver/inputpercobaan";
    public static final String GET_FULL_PENGUKURAN = BASE_URL + "/cenggoserver/getfullpengukuran";

    //Tags used in the JSON String
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NAME = "name";
    public static final String TAG_COURSE = "course";
    public static final String TAG_SESSION = "session";

    public static final String SHARED_PREF_NAME = "mysharedprefcenggo";
    public static final String KEY_USER_ID = "id_user";
    public static final String KEY_USERNAME_PREF = "username";
    public static final String KEY_FULLNAME_PREF = "nama_lengkap";
    public static final String KEY_JENIS_KELAMIN_PREF = "jenis_kelamin";


    public static final String PARAM_STATUS = "status";
    public static final String PARAM_MESSAGE = "message";
    public static final String PARAM_DATA = "data";
    public static final String PARAM_ID_PENGUKURAN = "id_pengukuran";
    public static final String PARAM_ID_PERCOBAAN = "id_percobaan";
    public static final String PARAM_WAKTU = "waktu";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_DESKRIPSI = "deskripsi";
    public static final String PARAM_ID_USER = "id_user";
    public static final String PARAM_ID_SUNGAI = "id_sungai";
    public static final String PARAM_NAMA_SUNGAI = "nama_sungai";
    public static final String PARAM_DATA_PERCOBAAN = "data_percobaan";
    public static final String PARAM_PH = "ph";
    public static final String PARAM_SUHU_AIR = "suhuair";
    public static final String PARAM_SUHU_UDARA = "suhuudara";
    public static final String PARAM_LONGITUDE = "longitude";
    public static final String PARAM_LATITUDE = "latitude";
    public static final String PARAM_TDS = "tds";
    public static final String PARAM_DHL = "dhl";
    public static final String PARAM_INDEKS_PENCEMARAN = "indeks_pencemaran";
    public static final String PARAM_KATEGORI_PENCEMARAN = "kategori_pencemaran";
    public static final String PARAM_PROBABILITAS = "probcenggo";
    public static final String PARAM_TOKEN= "token";
    public static final String PARAM_LOKASI= "lokasi";



    //JSON array name
    public static final String JSON_ARRAY = "result";

}


