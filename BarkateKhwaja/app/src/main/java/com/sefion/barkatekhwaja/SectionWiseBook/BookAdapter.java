package com.sefion.barkatekhwaja.SectionWiseBook;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sefion.barkatekhwaja.R;

import java.util.List;

/**
 * Created by Hafsa on 1/9/2018.
 */


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    Context context;
    List<BookData> datas;
    FragmentManager fm;


    public BookAdapter(Context context, List<BookData> datas) {
        this.context = context;
        this.datas = datas;
    }

    public BookAdapter(Context context, List<BookData> datas, FragmentManager fm) {
        this.context = context;
        this.datas = datas;
        this.fm = fm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookData current = datas.get(position);
        holder.name.setText(current.getName());
        holder.nameGuj.setText(current.getName_guj());
        holder.bookNumber.setText(current.getBookNumber());

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView nameGuj;
        TextView bookNumber;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            nameGuj = (TextView) itemView.findViewById(R.id.name_guj);
            bookNumber = (TextView) itemView.findViewById(R.id.bookNumber);

        }


    }
}
