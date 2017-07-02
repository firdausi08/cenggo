package com.example.afip.cobalist.model;

/**
 * Created by afip on 5/31/2017.
 */

public class Percobaan {
    private String title, deskripsi;
    private Integer id_sungai, id_user;

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

    public Integer getId_sungai() {
        return id_sungai;
    }

    public void setId_sungai(Integer id_sungai) {
        this.id_sungai = id_sungai;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}
