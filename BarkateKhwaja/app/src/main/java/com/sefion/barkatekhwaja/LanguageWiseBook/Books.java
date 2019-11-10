package com.sefion.barkatekhwaja.LanguageWiseBook;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sefion.barkatekhwaja.LanguageWiseBook.BookView;
import com.sefion.barkatekhwaja.R;
import com.sefion.barkatekhwaja.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Hafsa on 1/9/2018.
 */

public class Books extends Fragment {
    public com.sefion.barkatekhwaja.LanguageWiseBook.Books books;
    RecyclerView recyclerView;
    BookAdapter adapter;
    TextView bookid;
    private static final String SB_DATA_URL = "http://barkatekhwaja.net/index.php?app/nolb/S51bkiwmIsMYAmk8Ts";
    private static final String LANGUAGE_URL = "http://barkatekhwaja.net/index.php?app/language/S51bkiwmIsMYAmk8Ts";


    private String TAG = com.sefion.barkatekhwaja.LanguageWiseBook.Books.class.getSimpleName();
    private List<BookData> bookDatas;
    TextView Sections;
    TextView Books;
    Button viewallbook;
    FragmentManager fragmentManager;

    String bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.books, container, false);
        Sections = (TextView) view.findViewById(R.id.section);
        Books = (TextView) view.findViewById(R.id.Book);
        bookid = (TextView) view.findViewById(R.id.bookid);
        viewallbook = (Button) view.findViewById(R.id.viewAllBook);


        bundle = getArguments().getString("Section").toString();
        bookid.setText(bundle);


        recyclerView = (RecyclerView) view.findViewById(R.id.book_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        isInternetOn();


        viewallbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = "All";
                BookView bookView = new BookView();
                Bundle b = new Bundle();
                b.putString("sectionName", s);
                bookView.setArguments(b);

                fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_main, bookView, "SectionWise");
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        //  recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView
                        .findViewById(R.id.name)).getText().toString();
                Log.d("name", s);


                BookView bookView = new BookView();
                Bundle b = new Bundle();
                b.putString("sectionName", s);
                bookView.setArguments(b);


                fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_main, bookView, "SectionWise");
                transaction.addToBackStack(null);
                transaction.commit();

                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main, bookView).commit();
            }
        }));
        bookDatas = new ArrayList<>();

        loadBookNo();
        loadRecyclerViewData();

        checkPermission();


        return view;
    }


    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            // if connected with internet

            //  Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No Internet Connection");
            builder.setCancelable(true);


            builder.setNegativeButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder.create();
            alert11.show();
            return false;
        }
        return false;
    }


    private void checkPermission() {
        int permission = checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readpermission = checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 404);
        }
        if (readpermission != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 504);
        }

    }

    private void loadRecyclerViewData() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                LANGUAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("languages");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                BookData bData = new BookData(o.getString("name"),
                                        o.getString("name_native"), o.getString("num_of_books")

                                );
                                bookDatas.add(bData);

                            }
                            adapter = new BookAdapter(getActivity(), bookDatas);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        //  Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void loadBookNo() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest sRequest = new StringRequest(Request.Method.GET,
                SB_DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("nolb");
                            // for (int i = 0; i < array.length(); i++) {
                            JSONObject o = array.getJSONObject(0);
                            String sec = o.getString("num_of_language");
                            String b = o.getString("num_of_book");


                            Sections.setText(sec + " Languages");
                            Books.setText(b);


                            //  }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getActivity(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sRequest);


    }


}

