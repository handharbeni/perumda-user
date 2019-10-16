package com.mhandharbeni.perumda.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataPasangBaru;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPasangBaru extends RecyclerView.Adapter<AdapterPasangBaru.ViewHolder> {
    Context context;
    List<DataPasangBaru> listPasangBaru;
    Activity activity;

    public AdapterPasangBaru(Context context, List<DataPasangBaru> listPasangBaru, Activity activity) {
        this.context = context;
        this.listPasangBaru = listPasangBaru;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasangbaru, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listPasangBaru.get(position));
    }

    public void update(List<DataPasangBaru> listPasangBaru){
        this.listPasangBaru.clear();
        this.listPasangBaru.addAll(listPasangBaru);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listPasangBaru.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fotoktp)
        ImageView fotoktp;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.alamat)
        TextView alamat;
        @BindView(R.id.noktp)
        TextView noktp;
        @BindView(R.id.lokasipasang)
        TextView lokasipasang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bindData(DataPasangBaru dataPasangBaru){
            Glide.with(context)
                    .load(dataPasangBaru.getFotoktp())
                    .error(context.getResources().getDrawable(R.drawable.wp_profile))
                    .into(fotoktp);
            nama.setText(dataPasangBaru.getNama());
            alamat.setText(dataPasangBaru.getAlamat());
            noktp.setText(dataPasangBaru.getNoktp());
            lokasipasang.setText(dataPasangBaru.getLokasipasang());
        }
    }
}
