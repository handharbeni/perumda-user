package com.mhandharbeni.perumda.sub_activity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mhandharbeni.perumda.MainActivity;
import com.mhandharbeni.perumda.R;
import com.mhandharbeni.perumda.room.entity.data.DataGangguan;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGangguanActivity extends BaseActivity {
    @BindView(R.id.imageGangguan)
    ImageView imageGangguan;

    @BindView(R.id.txtjudulgangguan)
    TextView txtJudulGangguan;
    @BindView(R.id.txtunitgangguan)
    TextView txtUnitGangguan;
    @BindView(R.id.txtwilayahterdampak)
    TextView txtWilayahTerdampak;
    @BindView(R.id.txtketerangangangguan)
    TextView txtKeteranganGangguan;
    @BindView(R.id.txttanggalgangguan)
    TextView txtTanggalGangguan;
    @BindView(R.id.txtstatusgangguan)
    TextView txtStatusGangguan;

    DataGangguan dataGangguan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_gangguan);
        supportStartPostponedEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        dataGangguan = extras.getParcelable(MainActivity.EXTRA_GANGGUAN_ITEM);
        initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = extras.getString(MainActivity.EXTRA_GANGGUAN_IMAGE_TRANSITION_NAME);
            imageGangguan.setTransitionName(imageTransitionName);
        }
        hideAppBar();

        Picasso.get()
                .load(dataGangguan.getFoto())
                .noFade()
                .into(imageGangguan, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //To support reverse transition when user clicks the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        if (dataGangguan != null){
            txtJudulGangguan.setText(dataGangguan.getJudulGangguan());
            txtKeteranganGangguan.setText(dataGangguan.getKetGangguan());
            txtStatusGangguan.setText(dataGangguan.getStatus());
            txtTanggalGangguan.setText(dataGangguan.getTglGangguan());
            txtUnitGangguan.setText(dataGangguan.getUnitGangguan());
            txtWilayahTerdampak.setText(dataGangguan.getWilTerdampak());
        }
    }
}
