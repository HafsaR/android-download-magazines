package com.sefion.barkatekhwaja.MagazineView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sefion.barkatekhwaja.OnBtnClickListener;
import com.sefion.barkatekhwaja.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Hafsa on 2/4/2018.
 */

public class MagView extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    List<data> datas;
    Button download;
    String bundle, value;
    TextView header;
    public OnBtnClickListener btnClickListener;
    View view;


    String URL = "http://www.pdf995.com/samples/pdf.pdf";
    public String URL_DATA;
    private String TAG = MagView.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.magazine_view, container, false);
        header = (TextView) rootView.findViewById(R.id.headerYear);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.magviewrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        datas = new ArrayList<>();
        isInternetOn();
        bundle = getArguments().getString("year");
        value = bundle.toString();
        URL_DATA = "http://barkatekhwaja.net/?app/magazine/" + value + "/S51bkiwmIsMYAmk8Ts";
        String h = "Year " + value + " Magazines";
        header.setText(h);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 404) {
            Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnclick() {
        final ProgressDialog p = new ProgressDialog(getActivity());
        p.setMessage("Loading...");
        p.show();
        StringRequest strRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        p.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("magazines");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String link = o.getString("doc_link");
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse(link), "");
                                startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                p.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(strRequest);
    }


    private void loadRecyclerViewData() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.show();
        StringRequest strrequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("magazines");


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                data d = new data(o.getString("magazine_name"),
                                        o.getString("image_link"), o.getString("doc_link")
                                );


                                datas.add(d);
                            }


                            adapter = new Adapter(getActivity(), datas);

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
        requestQueue.add(strrequest);
    }


}
