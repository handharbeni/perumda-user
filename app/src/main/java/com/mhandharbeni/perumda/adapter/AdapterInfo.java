package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.ViewHolder> {
    private Context context;
    private List<DataInfo> listInfo = new ArrayList<>();
    private AdapterInfoInterface adapterInfoInterface;

    public AdapterInfo(Context context, List<DataInfo> listInfo, AdapterInfoInterface adapterInfoInterface) {
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
        DataInfo dataInfo = listInfo.get(position);
        holder.bind(dataInfo);
    }

    @Override
    public int getItemCount() {
        return listInfo.size();
    }

    public void update(List<DataInfo> listInfos){
        this.listInfo.clear();
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

        void bind(DataInfo dataInfo){
            infotitle.setText(dataInfo.getTitle());
            infodesc.setText(dataInfo.getDescription());
            infostatus.setText(dataInfo.getStatus());
            infodate.setText(dataInfo.getDate());
        }
    }

    public interface AdapterInfoInterface{
        void onInfoClick(DataInfo dataInfo);
    }
}
