package com.example.afip.cobalist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.afip.cobalist.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afip on 7/19/2017.
 */

public class Pengkuran implements Parcelable {
    private int id;
    private int id_user;
    private String title;
    private String deskripsi;
    private String date;
    private String nama_sungai;
    private ArrayList<Percobaan> listPercobaan;

    public Pengkuran(int id, int id_user, String title, String deskripsi, String date, String nama_sungai) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.deskripsi = deskripsi;
        this.date = date;
        this.nama_sungai = nama_sungai;
    }

    public Pengkuran(JSONObject jsonObject){
        try {
            listPercobaan = new ArrayList<>();
            this.id = jsonObject.getInt(Config.PARAM_ID_PENGUKURAN);
            this.id_user = jsonObject.getInt(Config.PARAM_ID_USER);
            this.title = jsonObject.getString(Config.PARAM_TITLE);
            this.deskripsi = jsonObject.getString(Config.PARAM_DESKRIPSI);
            this.date = jsonObject.getString(Config.PARAM_WAKTU);
            this.nama_sungai = jsonObject.getString(Config.PARAM_NAMA_SUNGAI);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.PARAM_DATA_PERCOBAAN);
            for(int i=0;i<jsonArray.length();i++){
                listPercobaan.add(new Percobaan(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Pengkuran(Parcel in) {
        id = in.readInt();
        id_user = in.readInt();
        title = in.readString();
        deskripsi = in.readString();
        date = in.readString();
        listPercobaan = in.readArrayList(Percobaan.class.getClassLoader());
        nama_sungai = in.readString();
    }

    public ArrayList<Percobaan> getListPercobaan() {
        return listPercobaan;
    }

    public void setListPercobaan(ArrayList<Percobaan> listPercobaan) {
        this.listPercobaan = listPercobaan;
    }

    public static final Creator<Pengkuran> CREATOR = new Creator<Pengkuran>() {
        @Override
        public Pengkuran createFromParcel(Parcel in) {
            return new Pengkuran(in);
        }

        @Override
        public Pengkuran[] newArray(int size) {
            return new Pengkuran[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNama_sungai() {

        return nama_sungai;
    }

    public void setNama_sungai(String nama_sungai) {
        this.nama_sungai = nama_sungai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(id_user);
        parcel.writeString(title);
        parcel.writeString(deskripsi);
        parcel.writeString(date);
        parcel.writeList(listPercobaan);
        parcel.writeString(nama_sungai);
    }
}
