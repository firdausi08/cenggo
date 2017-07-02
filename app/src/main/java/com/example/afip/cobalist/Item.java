package com.example.afip.cobalist;

/**
 * Created by afip on 5/17/2017.
 */

public class Item {

    String animalName, date, kategori;
    int animalImage;

    public Item(String animalName,int animalImage, String date, String kategori)
    {
        this.animalImage=animalImage;
        this.animalName=animalName;
        this.date=date;
        this.kategori=kategori;
    }
    public String getAnimalName()
    {
        return animalName;
    }
    public int getAnimalImage()
    {
        return animalImage;
    }
    public String getDate(){
        return date;
    }
    public String getKategori()
    {
        return kategori;
    }
}