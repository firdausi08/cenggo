package com.example.afip.cobalist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.afip.cobalist.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afip on 5/31/2017.
 */

public class Percobaan  implements Parcelable{
    private String title, deskripsi;
    private String waktu;
    private int id_percobaan;
    private int id_pengukuran;
    private String latitude;
    private String longitude;
    private double ph;
    private int tds;
    private int dhl;
    private double suhu_air;
    private double suhu_udara;
    private double indeks_pencemaran;
    private String kategori_pencemaran;
    private double probabilitas;
    private String lokasi;

    public Percobaan(String title, String deskripsi, String waktu, int id_percobaan, int id_pengukuran, String latitude, String longitude, double ph,
                     int tds, int dhl, double suhu_air, double suhu_udara, double indeks_pencemaran, String kategori_pencemaran, double probcenggo, String lokasi) {
        this.title = title;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.id_percobaan = id_percobaan;
        this.id_pengukuran = id_pengukuran;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ph = ph;
        this.tds = tds;
        this.dhl = dhl;
        this.suhu_air = suhu_air;
        this.suhu_udara = suhu_udara;
        this.indeks_pencemaran = indeks_pencemaran;
        this.kategori_pencemaran = kategori_pencemaran;
        this.probabilitas = probcenggo;
        this.lokasi = lokasi;
    }

    public Percobaan(JSONObject jsonObject){
        try {
            this.title = jsonObject.getString(Config.PARAM_TITLE);
            this.deskripsi = jsonObject.getString(Config.PARAM_DESKRIPSI);
            this.waktu = jsonObject.getString(Config.PARAM_WAKTU);
            this.id_percobaan = jsonObject.getInt(Config.PARAM_ID_PERCOBAAN);
            this.id_pengukuran = jsonObject.getInt(Config.PARAM_ID_PENGUKURAN);
            this.latitude = jsonObject.getString(Config.PARAM_LATITUDE);
            this.longitude = jsonObject.getString(Config.PARAM_LONGITUDE);
            this.suhu_air = jsonObject.getDouble(Config.PARAM_SUHU_AIR);
            this.suhu_udara = jsonObject.getDouble(Config.PARAM_SUHU_UDARA);
            this.indeks_pencemaran = jsonObject.getDouble(Config.PARAM_INDEKS_PENCEMARAN);
            this.kategori_pencemaran = jsonObject.getString(Config.PARAM_KATEGORI_PENCEMARAN);
            this.tds = jsonObject.getInt(Config.PARAM_TDS);
            this.ph = jsonObject.getDouble(Config.PARAM_PH);
            this.dhl = jsonObject.getInt(Config.PARAM_DHL);
            this.probabilitas = jsonObject.getDouble(Config.PARAM_PROBABILITAS);
            this.lokasi = jsonObject.getString(Config.PARAM_LOKASI);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Percobaan(Parcel in) {
        title = in.readString();
        deskripsi = in.readString();
        waktu = in.readString();
        id_percobaan = in.readInt();
        id_pengukuran = in.readInt();
        latitude = in.readString();
        longitude = in.readString();
        ph = in.readDouble();
        tds = in.readInt();
        dhl = in.readInt();
        suhu_air = in.readDouble();
        suhu_udara = in.readDouble();
        indeks_pencemaran = in.readDouble();
        kategori_pencemaran = in.readString();
        probabilitas = in.readDouble();
        lokasi = in.readString();
    }

    public static final Creator<Percobaan> CREATOR = new Creator<Percobaan>() {
        @Override
        public Percobaan createFromParcel(Parcel in) {
            return new Percobaan(in);
        }

        @Override
        public Percobaan[] newArray(int size) {
            return new Percobaan[size];
        }
    };

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public int getId_percobaan() {
        return id_percobaan;
    }

    public void setId_percobaan(int id_percobaan) {
        this.id_percobaan = id_percobaan;
    }

    public int getId_pengukuran() {
        return id_pengukuran;
    }

    public void setId_pengukuran(int id_pengukuran) {
        this.id_pengukuran = id_pengukuran;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }

    public int getDhl() {
        return dhl;
    }

    public void setDhl(int dhl) {
        this.dhl = dhl;
    }

    public double getSuhu_air() {
        return suhu_air;
    }

    public void setSuhu_air(double suhu_air) {
        this.suhu_air = suhu_air;
    }

    public double getSuhu_udara() {
        return suhu_udara;
    }

    public void setSuhu_udara(double suhu_udara) {
        this.suhu_udara = suhu_udara;
    }

    public double getIndeks_pencemaran() {
        return indeks_pencemaran;
    }

    public void setIndeks_pencemaran(double indeks_pencemaran) {
        this.indeks_pencemaran = indeks_pencemaran;
    }

    public String getKategori_pencemaran() {
        return kategori_pencemaran;
    }

    public void setKategori_pencemaran(String kategori_pencemaran) {
        this.kategori_pencemaran = kategori_pencemaran;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public double getProbabilitas() {
        return probabilitas;
    }

    public void setProbabilitas(double probabilitas) {
        this.probabilitas = probabilitas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(deskripsi);
        parcel.writeString(waktu);
        parcel.writeInt(id_percobaan);
        parcel.writeInt(id_pengukuran);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeDouble(ph);
        parcel.writeInt(tds);
        parcel.writeInt(dhl);
        parcel.writeDouble(suhu_air);
        parcel.writeDouble(suhu_udara);
        parcel.writeDouble(indeks_pencemaran);
        parcel.writeString(kategori_pencemaran);
        parcel.writeDouble(probabilitas);
        parcel.writeString(lokasi);
    }
}
