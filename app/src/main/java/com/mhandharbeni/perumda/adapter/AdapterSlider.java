package com.mhandharbeni.perumda.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;
import com.mhandharbeni.perumda.utils.Constant;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSlider extends SliderViewAdapter<AdapterSlider.ViewHolder> {
    Context context;
    List<DataImageSlider> listImageSlider = new ArrayList<>();
    ListenerSlider listenerSlider;

    public AdapterSlider(Context context, List<DataImageSlider> listImageSlider, ListenerSlider listenerSlider) {
        this.context = context;
        this.listImageSlider = listImageSlider;
        this.listenerSlider = listenerSlider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindData(listImageSlider.get(position));
        viewHolder.imageViewBackground.setOnClickListener(v -> {
            this.listenerSlider.onSliderClick();
        });
    }

    @Override
    public int getCount() {
        return listImageSlider.size();
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder {
        @BindView(R.id.iv_auto_image_slider)
        ImageView imageViewBackground;
        @BindView(R.id.tv_auto_image_slider)
        TextView textViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(DataImageSlider dataImageSlider){
            Glide.with(context).load(dataImageSlider.getFoto()).optionalCenterCrop().into(imageViewBackground);
            textViewDescription.setText(dataImageSlider.getKeterangan());
        }
    }
    public interface ListenerSlider{
        void onSliderClick();
    }
}
