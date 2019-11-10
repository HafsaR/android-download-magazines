package com.sefion.barkatekhwaja.Magazines;

import android.Manifest;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sefion.barkatekhwaja.HttpHandler;
import com.sefion.barkatekhwaja.MagazineView.MagView;
import com.sefion.barkatekhwaja.MainActivity;
import com.sefion.barkatekhwaja.OnFragmentInteractionListener;
import com.sefion.barkatekhwaja.R;
import com.sefion.barkatekhwaja.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Hafsa on 1/9/2018.
 */

public class Magazines extends Fragment {

    RecyclerView recyclerView;
    MagazinesAdapter adapter;
    FragmentManager manager;
    private static final String URL_DATA = "http://barkatekhwaja.net/?app/year/S51bkiwmIsMYAmk8Ts";
    private String TAG = Magazines.class.getSimpleName();
    private List<Mdata> mdatas = new ArrayList<>();

    public Magazines() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.magazines, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.magazine_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mdatas = new ArrayList<>();


        loadRecyclerViewData();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String y = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).
                        itemView.findViewById(R.id.year)).getText().toString();
                MagView m = new MagView();
                Bundle b = new Bundle();
                b.putString("year", y);
                m.setArguments(b);


                manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.content_main, m, "MagazineView");
                transaction.addToBackStack(null);
                transaction.commit();


                //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main, m).commit();

            }
        }));

        isInternetOn();
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
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("years");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                Mdata datas = new Mdata(o.getString("year")
                                );
                                mdatas.add(datas);

                            }
                            adapter = new MagazinesAdapter(getActivity(), mdatas);
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
}
