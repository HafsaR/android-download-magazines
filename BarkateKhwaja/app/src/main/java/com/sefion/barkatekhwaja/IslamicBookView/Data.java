package com.sefion.barkatekhwaja.IslamicBookView;

import android.content.Context;

import com.sefion.barkatekhwaja.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafsa on 3/1/2018.
 */

public class Data {
    String sectionName;
    int pageNo;

    public Data() {
    }

    public Data(String sectionName, int pageNo) {
        this.sectionName = sectionName;
        this.pageNo = pageNo;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }


}
