package com.sefion.barkatekhwaja.IslamicBookView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.sefion.barkatekhwaja.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Hafsa on 3/1/2018.
 */

public class BookView extends Fragment {

    TextView sname, bookno;
    RecyclerView recyclerView;
    Adapter adapter;
    SearchView searchView;
    List<Data> datas;
    List<BookData> bookData;
    String bundle, value;
    public String URL_DATA;
    EditText search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.islamic_books, container, false);
        sname = (TextView) rootView.findViewById(R.id.sName);
        bookno = (TextView) rootView.findViewById(R.id.bookNo);
        search = (EditText) rootView.findViewById(R.id.search);

        datas = new ArrayList<>();
        bookData = new ArrayList<>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.islamicBookRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        bundle = getArguments().getString("sectionName");
        value = bundle.toString();
        URL_DATA = "http://barkatekhwaja.net/index.php?app/books/" + value + "/S51bkiwmIsMYAmk8Ts";
        Log.d("URL", URL_DATA);
        String h = value + " Books";
        sname.setText(h);

        isInternetOn();
        checkPermission();
        loadRecyclerViewData();
        return rootView;
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


    public void loadRecyclerViewData() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading......");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("books");

                            int count = 0, size;

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject o = array.getJSONObject(i);
                                count = count + 1;
                                BookData data = new BookData(o.getString("image_link"), o.getString("doc_link"), o.getString("name"), o.getString("author"), o.getString("editor"),
                                        o.getString("translator"), o.getString("section_name"), o.getString("book_language"), o.getString("note"), o.getString("pages"), o.getString("book_size"));

                                bookData.add(data);

                            }

                            String c = Integer.toString(count) + " Books";
                            bookno.setText(c);

                            adapter = new Adapter(getActivity(), bookData);

                            recyclerView.setAdapter(adapter);
                            search.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    filter(editable.toString());

                                }

                                private void filter(String text) {
                                    ArrayList<BookData> filteredBooks = new ArrayList<>();
                                    for (BookData s : bookData) {
                                        if (s.getSubject().toString().toLowerCase().contains(text.toLowerCase())) {
                                            filteredBooks.add(s);
                                        }
                                    }
                                    adapter.filterList(filteredBooks);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        //    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 404) {
            //   Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_LONG).show();
        }
    }


}
