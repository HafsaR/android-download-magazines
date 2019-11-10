package com.sefion.barkatekhwaja.Donate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sefion.barkatekhwaja.R;

public class Donate extends Fragment {
    Button call, email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donate, container, false);
        call = (Button) view.findViewById(R.id.call);
        email = (Button) view.findViewById(R.id.email);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = "+919427464411";
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + phone_number;
                i.setData(Uri.parse(p));
                startActivity(i);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"anjuman2006@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Donate");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}
