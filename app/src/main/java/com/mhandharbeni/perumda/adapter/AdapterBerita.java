package com.mhandharbeni.perumda.adapter;

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
import com.mhandharbeni.perumda.room.entity.data.DataGangguan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.ViewHolder> {
    List<DataGangguan> listGangguan = new ArrayList<>();
    Context context;
    ListenerGangguan listenerGangguan;

    public AdapterBerita(List<DataGangguan> listGangguan, Context context, ListenerGangguan listenerGangguan) {
        this.listGangguan = listGangguan;
        this.context = context;
        this.listenerGangguan = listenerGangguan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gangguan, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listGangguan.get(position));
        holder.itemView.setOnClickListener(view -> {
            this.listenerGangguan.onGangguanClick(listGangguan.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listGangguan.size();
    }

    public void updateData(List<DataGangguan> listGangguan){
        this.listGangguan.clear();
        this.listGangguan.addAll(listGangguan);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gangguancover)
        ImageView gangguancover;
        @BindView(R.id.gangguantitle)
        TextView gangguantitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(DataGangguan dataGangguan){
            try {
                Glide.with(context).load(dataGangguan.getFoto()).into(gangguancover);
            }catch (Exception e){
                Glide.with(context).load(R.drawable.water_wallpaper).into(gangguancover);
            }
            gangguantitle.setText(dataGangguan.getJudulGangguan());
        }
    }

    public interface ListenerGangguan{
        void onGangguanClick(DataGangguan dataGangguan);
    }
}
