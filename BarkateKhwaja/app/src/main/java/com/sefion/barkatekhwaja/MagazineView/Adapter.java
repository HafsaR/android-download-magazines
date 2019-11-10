package com.sefion.barkatekhwaja.MagazineView;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sefion.barkatekhwaja.BuildConfig;
import com.sefion.barkatekhwaja.Downloader;
import com.sefion.barkatekhwaja.OnBtnClickListener;
import com.sefion.barkatekhwaja.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafsa on 2/12/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    List<data> datas;


    public Adapter(Context context, List<data> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.magazine_view_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final data current = datas.get(position);
        holder.month.setText(current.getYear());


        Picasso.with(context).load(current.getImgURL()).resize(724, 1024).into(holder.Img);

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();

                File file = createDirectory(current.getYear());
                downloadFile(current.getDoc_link(), file);
                // showPdf(current.getYear());
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String more = "\nFor more\nWebsite:\nhttp://www.barkatekhwaja.net/\nApp:\nhttps://play.google.com/store/apps/details?id=com.sefion.barkatekhwaja";
                    String sAux = "Barkat-e-Khwaja: \n" + current.getYear() + "\n";
                    sAux = sAux + "\nDownload: \n" + current.getDoc_link() + "\n" + more;
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    context.startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });


    }


    private void downloadFile(String pdflink, File file) {
        //Downloader.DownloadFile(pdflink, file);
        //   Downloader.downloadfile(pdflink, file, context);
        //  File file = new File(Environment.getExternalStorageDirectory() + "/BK/Media/" + magname + ".pdf");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdflink));
        String nameOfFile = URLUtil.guessFileName(pdflink, null,
                MimeTypeMap.getFileExtensionFromUrl(pdflink));
        request.setTitle(nameOfFile);
        request.setDescription("File is being downloaded..");
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
        // request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory() + "BK/Media/", nameOfFile);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


    }

    public void showPdf(String magname) {
        File file = new File(Environment.getExternalStorageDirectory() + "/BK/Media/" + magname + ".pdf");
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //Uri uri = Uri.fromFile(file);
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);


        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
    }

    private File createDirectory(String magname) {


        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "BK");
        folder.mkdir();
        File fold = new File(extStorageDirectory, "BK/Media");
        fold.mkdir();
        File file = new File(folder, magname + ".pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return file;
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        ImageView Img;
        Button download, share;
        private OnBtnClickListener clickListener;


        public MyViewHolder(View itemView) {
            super(itemView);
            month = (TextView) itemView.findViewById(R.id.month);
            Img = (ImageView) itemView.findViewById(R.id.img);
            download = (Button) itemView.findViewById(R.id.download);
            share = (Button) itemView.findViewById(R.id.share);


        }


    }
}


