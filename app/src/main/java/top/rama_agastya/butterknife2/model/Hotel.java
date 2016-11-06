package top.rama_agastya.butterknife2.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Rama on 06/11/2016.
 */

public class Hotel {

    public String judul;
    public String deskripsi;
    public Drawable foto;

    public Hotel(String judul, String deskripsi, Drawable foto) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }
}
