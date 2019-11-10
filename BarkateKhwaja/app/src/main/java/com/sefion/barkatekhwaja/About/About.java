package com.sefion.barkatekhwaja.About;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sefion.barkatekhwaja.R;

public class About extends Fragment {
    TextView develop;
    ImageView sefion;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);
        develop = (TextView) view.findViewById(R.id.develop);
        sefion = (ImageView) view.findViewById(R.id.sefion);
        develop.setMovementMethod(LinkMovementMethod.getInstance());
        sefion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sefion.com/team");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        return view;
    }
}
