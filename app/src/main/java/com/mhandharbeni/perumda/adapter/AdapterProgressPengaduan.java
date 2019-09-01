package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataProgressPengaduan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterProgressPengaduan extends RecyclerView.Adapter<AdapterProgressPengaduan.ViewHolder> {
    List<DataProgressPengaduan> listProgress;
    Context context;

    public AdapterProgressPengaduan(List<DataProgressPengaduan> listProgress, Context context) {
        this.listProgress = listProgress;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_progress_pengaduan, null);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listProgress.get(position));
    }

    @Override
    public int getItemCount() {
        return listProgress.size();
    }

    public void updateData(List<DataProgressPengaduan> newList){
        this.listProgress.clear();
        this.listProgress.addAll(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.timeline)
        TimelineView timelineView;
        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            timelineView.initLine(viewType);
        }

        public void bindData(DataProgressPengaduan dataProgressPengaduan){
            title.setText(dataProgressPengaduan.getTindakan());
        }
    }
}
