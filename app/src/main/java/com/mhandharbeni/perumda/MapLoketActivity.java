package com.mhandharbeni.perumda;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.data.DataLoket;
import com.mhandharbeni.perumda.room.entity.data.DataUnit;
import com.mhandharbeni.perumda.utils.BaseActivity;
import com.mhandharbeni.perumda.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapLoketActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.grupPilihan)
    ChipGroup grupPilihan;

    @BindView(R.id.chipLoket)
    Chip chipLoket;

    @BindView(R.id.chipUnit)
    Chip chipUnit;

    private GoogleMap mMap = null;

    boolean firstUp = true;

    private static final String TAG = MapLoketActivity.class.getSimpleName();

    private static final String SELECTED_STYLE = "selected_style";

    private int mSelectedStyleId = R.string.style_label_default;

    private int mStyleIds[] = {
            R.string.style_label_retro,
            R.string.style_label_night,
            R.string.style_label_grayscale,
            R.string.style_label_no_pois_no_transit,
            R.string.style_label_default,
    };

    private static final LatLng SYDNEY = new LatLng(-33.8688, 151.2093);
    private MapLoketActivity mapLoketActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapLoketActivity = this;

        if (savedInstanceState != null) {
            mSelectedStyleId = savedInstanceState.getInt(SELECTED_STYLE);
        }
        setContentView(R.layout.activity_maps_loket);
        ButterKnife.bind(mapLoketActivity);
        hideAppBar();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapLoketActivity);


        Dexter.withActivity(this).withPermissions(Constant.listPermission).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_STYLE, mSelectedStyleId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMyLocationChangeListener(location ->{
                if (firstUp){
                    firstUp = false;
                    mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()),
                                    10
                            )
                    );
                }
            }
        );
        setSelectedStyle();
        initDataLoket();
    }

    @OnClick(R.id.fab)
    public void showStylesDialog() {
        List<String> styleNames = new ArrayList<>();
        for (int style : mStyleIds) {
            styleNames.add(getString(style));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.style_choose));
        builder.setItems(styleNames.toArray(new CharSequence[styleNames.size()]),
                (dialog, which) -> {
                    mSelectedStyleId = mStyleIds[which];
                    setSelectedStyle();
                });
        builder.show();
    }

    private void setSelectedStyle() {
        MapStyleOptions style;
        switch (mSelectedStyleId) {
            case R.string.style_label_retro:
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro);
                break;
            case R.string.style_label_night:
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night);
                break;
            case R.string.style_label_grayscale:
                style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale);
                break;
            case R.string.style_label_no_pois_no_transit:
                style = new MapStyleOptions("[" +
                        "  {" +
                        "    \"featureType\":\"poi.business\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }," +
                        "  {" +
                        "    \"featureType\":\"transit\"," +
                        "    \"elementType\":\"all\"," +
                        "    \"stylers\":[" +
                        "      {" +
                        "        \"visibility\":\"off\"" +
                        "      }" +
                        "    ]" +
                        "  }" +
                        "]");
                break;
            case R.string.style_label_default:
                style = null;
                break;
            default:
                return;
        }
        mMap.setMapStyle(style);
    }

    private void initDataUnit(){
        List<DataUnit> listUnit = AppDb.getInstance(getApplicationContext()).unitInterfaceDao().getAllUnit();
        for (DataUnit dataUnit: listUnit){
            LatLng latLng = new LatLng(Double.valueOf(dataUnit.getGpslat()), Double.valueOf(dataUnit.getGpslon()));
            addMarker(mMap, latLng, dataUnit.getUnit(), dataUnit.getTelp());
        }
    }

    private void initDataLoket(){
        List<DataLoket> listLoket = AppDb.getInstance(getApplicationContext()).loketInterfaceDao().getAll();
        for (DataLoket dataLoket : listLoket){
            LatLng latLng = new LatLng(Double.valueOf(dataLoket.getGpslat()), Double.valueOf(dataLoket.getGpslon()));
            addMarker(mMap, latLng, dataLoket.getLoket(), dataLoket.getPhone());
        }
    }

    private void addMarker(GoogleMap map, LatLng location, String title, String snippet){
        map.addMarker(
                new MarkerOptions()
                    .position(location)
                .title(title)
                .snippet(snippet)
        );
    }

    @OnClick({R.id.chipUnit, R.id.chipLoket})
    public void changeChoice(View view){
        Chip chip = findViewById(view.getId());
        if (chip.isChecked()){
            switch (chip.getText().toString()){
                case "LOKET" :
                    firstUp = true;
                    clearMaps();
                    initDataLoket();
                    break;
                case "UNIT" :
                    firstUp = true;
                    clearMaps();
                    initDataUnit();
                    break;
            }
        }else{
            clearMaps();
        }
    }

    private void clearMaps(){
        if (mMap != null){
            mMap.clear();
        }
    }

}