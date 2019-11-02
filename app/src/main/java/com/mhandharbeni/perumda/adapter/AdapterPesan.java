package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataPesan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static io.fabric.sdk.android.Fabric.TAG;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.ViewHolder> {
    private Context context;
    private List<DataPesan> listInfo;
    private AdapterInfoInterface adapterInfoInterface;

    public AdapterPesan(Context context, List<DataPesan> listInfo, AdapterInfoInterface adapterInfoInterface) {
        this.context = context;
        this.listInfo = listInfo;
        this.adapterInfoInterface = adapterInfoInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataPesan dataPesan = listInfo.get(position);
        holder.bind(dataPesan);
    }

    @Override
    public int getItemCount() {
        return listInfo.size();
    }

    public void update(List<DataPesan> listInfos){
        this.listInfo.addAll(listInfos);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.infotitle)
        TextView infotitle;
        @BindView(R.id.infodesc)
        TextView infodesc;
        @BindView(R.id.infostatus)
        TextView infostatus;
        @BindView(R.id.infodate)
        TextView infodate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(DataPesan dataPesan){
            Log.d(TAG, "onBindViewHolder bind: "+dataPesan.getTitle());
            infotitle.setText(dataPesan.getTitle());
            infodesc.setText(dataPesan.getDescription());
            infostatus.setText(dataPesan.getStatus());
            infodate.setText(dataPesan.getDate());
        }
    }

    public interface AdapterInfoInterface{
        void onInfoClick(DataPesan dataPesan);
    }
}
