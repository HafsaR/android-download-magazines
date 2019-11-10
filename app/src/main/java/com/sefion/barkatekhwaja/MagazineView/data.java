package com.sefion.barkatekhwaja.MagazineView;

import android.content.Context;

import com.sefion.barkatekhwaja.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hafsa on 2/5/2018.
 */

public class data {

    String year;
    String imgURL, doc_link;
    String mag_name;

    public String getMag_name() {
        return mag_name;
    }

    public void setMag_name(String mag_name) {
        this.mag_name = mag_name;
    }

    public data() {
    }

    public data(String year, String imgURL) {
        this.year = year;
        this.imgURL = imgURL;
    }

    public data(String year, String imgURL, String doc_link) {
        this.year = year;
        this.imgURL = imgURL;
        this.doc_link = doc_link;
    }



    public String getDoc_link() {
        return doc_link;
    }

    public void setDoc_link(String doc_link) {
        this.doc_link = doc_link;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }




}

