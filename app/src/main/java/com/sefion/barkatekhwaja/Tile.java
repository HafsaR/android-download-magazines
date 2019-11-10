package com.sefion.barkatekhwaja;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sefion.barkatekhwaja.SectionWiseBook.Books;


public class Tile extends Fragment {
    Button section, language;
    FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tile, container, false);
        section = (Button) view.findViewById(R.id.section_wise);
        language = (Button) view.findViewById(R.id.language_wise);
        manager = getActivity().getSupportFragmentManager();

        final String s = section.getText().toString();
        final String l = language.getText().toString();


        section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Books sectionWise = new Books();
                Bundle b = new Bundle();
                b.putString("Section", s);
                sectionWise.setArguments(b);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_main, sectionWise);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com.sefion.barkatekhwaja.LanguageWiseBook.Books languages = new com.sefion.barkatekhwaja.LanguageWiseBook.Books();
                Bundle b = new Bundle();
                b.putString("Section", l);
                languages.setArguments(b);
                languages.setArguments(b);
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.content_main, languages);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });
        return view;
    }
}
