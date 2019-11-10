package com.sefion.barkatekhwaja.LanguageWiseBook;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sefion.barkatekhwaja.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hafsa on 3/1/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    List<LanguageData> datas;


    public Adapter(Context context, List<LanguageData> datas) {
        this.context = context;
        this.datas = datas;


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.islamic_book_list, parent, false);
        return new MyViewHolder(view);


    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            final LanguageData current = datas.get(position);
            Picasso.with(context).load(current.getImage()).resize(880, 1244).into(myViewHolder.image);

            myViewHolder.subject.setText(current.getSubject());
            myViewHolder.author.setText(current.getAuthor());
            myViewHolder.editor.setText(current.getEditor());
            myViewHolder.translator.setText(current.getTranslator());
            myViewHolder.section.setText(current.getSection());
            myViewHolder.language.setText(current.getLanguage());

            myViewHolder.note.setText("Note: " + current.getNote());
            myViewHolder.pagenumber.setText(current.getPageno() + " Pages");
            myViewHolder.download.setText(" DOWNLOAD  " + "( " + current.getBook_size() + " )");

            myViewHolder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show();


                    downloadFile(current.getDoc());

                }
            });
            myViewHolder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String more = "\nFor more\nWebsite:\nhttp://www.barkatekhwaja.net/\nApp:\nhttps://play.google.com/store/apps/details?id=com.sefion.barkatekhwaja";
                        String sAux = "Barkat-e-Khwaja: \n" + current.getSubject() + "\n";
                        sAux = sAux + "\nDownload: \n\n" + current.getDoc() + "\n" + more;
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        context.startActivity(Intent.createChooser(i, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }


                }
            });

        }
    }


    private void downloadFile(String pdflink) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdflink));
        String nameOfFile = URLUtil.guessFileName(pdflink, null,
                MimeTypeMap.getFileExtensionFromUrl(pdflink));
        request.setTitle(nameOfFile);
        request.setDescription("File is being downloaded..");
        //  request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);
        // request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory() + "BK/Media/", nameOfFile);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView subject;
        TextView author;
        TextView editor, translator, section, language, pagenumber, note;
        Button download, share;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img);
            subject = (TextView) itemView.findViewById(R.id.subject);
            author = (TextView) itemView.findViewById(R.id.author);
            editor = (TextView) itemView.findViewById(R.id.editor);
            translator = (TextView) itemView.findViewById(R.id.translator);
            section = (TextView) itemView.findViewById(R.id.sec);
            language = (TextView) itemView.findViewById(R.id.lang);
            pagenumber = (TextView) itemView.findViewById(R.id.pgno);
            download = (Button) itemView.findViewById(R.id.download);
            note = (TextView) itemView.findViewById(R.id.note);
            share = (Button) itemView.findViewById(R.id.share);

        }

    }


}