package com.sefion.barkatekhwaja.LanguageWiseBook;

import android.content.Context;

import com.sefion.barkatekhwaja.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafsa on 1/9/2018.
 */

public class BookData {
    String name;
    String name_guj;
    String bookNumber;


    public BookData() {
    }

    public BookData(String name, String name_guj, String bookNumber) {
        this.name = name;
        this.name_guj = name_guj;
        this.bookNumber = bookNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_guj() {
        return name_guj;
    }

    public void setName_guj(String name_guj) {
        this.name_guj = name_guj;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }


}
