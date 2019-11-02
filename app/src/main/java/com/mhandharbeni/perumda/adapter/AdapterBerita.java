package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataBerita;
import com.mhandharbeni.perumda.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.ViewHolder> {
    private Context context;
    private List<DataBerita> listBerita;
    private AdapterBeritaInterface adapterBeritaInterface;

    public AdapterBerita(Context context, List<DataBerita> listBerita, AdapterBeritaInterface adapterBeritaInterface) {
        this.context = context;
        this.listBerita = listBerita;
        this.adapterBeritaInterface = adapterBeritaInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_berita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataBerita dataBerita = listBerita.get(position);
        holder.bind(dataBerita);
    }

    @Override
    public int getItemCount() {
        return listBerita.size();
    }

    public void update(List<DataBerita> listBerita){
        this.listBerita.addAll(listBerita);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.beritacategory)
        TextView beritaCategory;
        @BindView(R.id.beritadate)
        TextView beritaDate;
        @BindView(R.id.beritatitle)
        TextView beritaTitle;
        @BindView(R.id.beritadesc)
        TextView beritaDesc;
        @BindView(R.id.imageCover)
        ImageView imageCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(DataBerita dataBerita){
            beritaCategory.setText(dataBerita.getCategory());
            beritaDate.setText(dataBerita.getDate());
            beritaTitle.setText(dataBerita.getTitle());
            beritaDesc.setText(dataBerita.getDescription());
            Tools.DrawImage(context, imageCover, dataBerita.getImage());
        }
    }

    public interface AdapterBeritaInterface{
        void onBeritaClick(DataBerita dataBerita);
    }
}
