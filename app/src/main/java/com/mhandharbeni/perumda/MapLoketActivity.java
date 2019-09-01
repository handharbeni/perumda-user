package com.mhandharbeni.perumda;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhandharbeni.perumda.room.db.AppDb;
import com.mhandharbeni.perumda.room.entity.data.DataUnit;
import com.mhandharbeni.perumda.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapLoketActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private GoogleMap mMap = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSelectedStyleId = savedInstanceState.getInt(SELECTED_STYLE);
        }
        setContentView(R.layout.activity_maps_loket);
        ButterKnife.bind(this);
        hideAppBar();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setOnMyLocationChangeListener(location ->
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(), location.getLongitude()),
                                10
                        )
                )
        );
        setSelectedStyle();
        initDataUnit();
        map.setOnMarkerClickListener(marker -> false);
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
//                    String msg = getString(R.string.style_set_to, getString(mSelectedStyleId));
//                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, msg);
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

    private void addMarker(GoogleMap map, LatLng location, String title, String snippet){
        map.addMarker(
                new MarkerOptions()
                    .position(location)
                .title(title)
                .snippet(snippet)
        );
    }
}