package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSliderPromo extends RecyclerView.Adapter<AdapterSliderPromo.ViewHolder> {
    Context context;
    List<DataImageSlider> dataImageSliders = new ArrayList<>();

    public AdapterSliderPromo(Context context, List<DataImageSlider> dataImageSliders) {
        this.context = context;
        this.dataImageSliders = dataImageSliders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataImageSlider dataImageSlider = dataImageSliders.get(position);
        holder.bindData(dataImageSlider);
    }

    @Override
    public int getItemCount() {
        return dataImageSliders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imagePromo)
        ImageView imagePromo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(DataImageSlider dataImageSlider){
            try {
                Glide.with(context).load(dataImageSlider.getFoto()).into(imagePromo);
            }catch (Exception e){
                Glide.with(context).load(R.drawable.wp_profile).into(imagePromo);
            }

        }
    }
}
