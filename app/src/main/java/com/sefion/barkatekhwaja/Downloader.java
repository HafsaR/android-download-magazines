package com.sefion.barkatekhwaja;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hafsa on 2/26/2018.
 */

public class Downloader {
    public static void DownloadFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void downloadfile(String mUrl, final File file,Context context){
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response!=null) {

                                FileOutputStream outputStream;
                                String name="";
                                outputStream = new FileOutputStream(file);
                                outputStream.write(response);
                                outputStream.close();
                                Log.e("Downloader","download complete");
                                //Toast.makeText(this, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.e("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
    }

}
