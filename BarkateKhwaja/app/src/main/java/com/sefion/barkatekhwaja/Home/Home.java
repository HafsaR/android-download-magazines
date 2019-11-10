package com.sefion.barkatekhwaja.Home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sefion.barkatekhwaja.R;
import com.sefion.barkatekhwaja.ViewPagerAdapter;

public class Home extends Fragment {

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    private String[] imageUrls = new String[]{
            "http://www.barkatekhwaja.net/assets/img/001.jpg",
            "http://www.barkatekhwaja.net/assets/img/002.jpg",
            "http://www.barkatekhwaja.net/assets/img/003.jpg",
            "http://www.barkatekhwaja.net/assets/img/004.jpg",
            "http://www.barkatekhwaja.net/assets/img/005.jpg",
            "http://www.barkatekhwaja.net/assets/img/006.jpg"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getActivity(), imageUrls);
        viewPager.setAdapter(adapter);


        return view;
    }
}
