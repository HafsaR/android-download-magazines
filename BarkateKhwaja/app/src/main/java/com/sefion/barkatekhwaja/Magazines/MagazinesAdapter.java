package com.sefion.barkatekhwaja.Magazines;

import android.content.Context;
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

public class MagazinesAdapter extends RecyclerView.Adapter<MagazinesAdapter.MyViewHolder> {
    Context context;
    List<Mdata> data;

    public MagazinesAdapter(Context context, List<Mdata> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.magazines_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mdata current = data.get(position);
        holder.year.setText(current.getYear());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView year;

        public MyViewHolder(View itemView) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.year);
        }
    }
}
