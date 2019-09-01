package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataListPengaduan;
import com.mhandharbeni.perumda.sub_activity.DetailPengaduanActivity;
import com.mhandharbeni.perumda.utils.Constant;
import com.mhandharbeni.perumda.utils.Tools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPengaduan extends RecyclerView.Adapter<AdapterPengaduan.ViewHolder> {
    private Context context;
    private List<DataListPengaduan> listPengaduan;

    public AdapterPengaduan(Context context, List<DataListPengaduan> listPengaduan) {
        this.context = context;
        this.listPengaduan = listPengaduan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengaduan, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listPengaduan.get(position));
        holder.itemView.setOnClickListener(view -> {
            new DataListPengaduan();
            DataListPengaduan dataListPengaduan = listPengaduan.get(position);
            Intent intent = new Intent(context, DetailPengaduanActivity.class);
            intent.putExtra(Constant.SERIALIZABLE_PENGADUAN, dataListPengaduan);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listPengaduan.size();
    }

    public void updateData(List<DataListPengaduan> listPengaduans){
        this.listPengaduan.clear();
        this.listPengaduan.addAll(listPengaduans);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtNomorPengaduan)
        TextView txtNomorPengaduan;
        @BindView(R.id.txtNosal)
        TextView txtNosal;
        @BindView(R.id.txtNama)
        TextView txtNama;
        @BindView(R.id.txtAlamat)
        TextView txtAlamat;
        @BindView(R.id.imageCover)
        ImageView imageCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(DataListPengaduan dataListPengaduan){
            txtNomorPengaduan.setText(dataListPengaduan.getNopengaduan());
            txtNosal.setText(dataListPengaduan.getNosal());
            txtNama.setText(dataListPengaduan.getNama());
            txtAlamat.setText(dataListPengaduan.getAlamat());

            Glide.with(context)
                    .load(Tools.decodeString(dataListPengaduan.getImage()))
                    .error(R.drawable.water_wallpaper)
                    .into(imageCover);
        }
    }
}
