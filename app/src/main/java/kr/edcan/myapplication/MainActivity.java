package kr.edcan.myapplication;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;
import kr.edcan.myapplication.utils.NMapPOIflagType;
import kr.edcan.myapplication.utils.NMapViewerResourceProvider;

public class MainActivity extends NMapActivity implements AppCompatCallback {

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    final static String CLIENT_ID = "tuEgBTpd6f3JrMcPCsnQ";
    NMapView view;
    NMapController controller;
    NMapOverlayManager manager;
    NMapResourceProvider provider;
    AppCompatDelegate delegate;
    DrawerLayout drawerLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefault(savedInstanceState);
    }

    private void setDefault(Bundle s) {
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(s);
        delegate.setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("슬슬 졸려진다");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        delegate.setSupportActionBar(toolbar);
        Drawable d = getResources().getDrawable(R.drawable.ic_menu_white_24dp);
        d.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
        delegate.getSupportActionBar().setHomeAsUpIndicator(d);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        delegate.getSupportActionBar().setElevation(3);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);

        imageView  = (ImageView) toolbar.findViewById(R.id.image);
        AnimationDrawable animate = (AnimationDrawable) imageView.getBackground();
        animate.start();
        view = (NMapView) findViewById(R.id.mapView);
        view.setClientId(CLIENT_ID);
        view.setClickable(true);
        view.setBuiltInZoomControls(true, null);
        controller = view.getMapController();
        view.setScalingFactor(1.5f);
        provider = new NMapViewerResourceProvider(this);
        manager = new NMapOverlayManager(this, view, provider);

        int markerId = NMapPOIflagType.PIN;

        NMapPOIdata poiData = new NMapPOIdata(2, provider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(126.9653530, 37.4860320, "우리집", markerId, 1);
        poiData.addPOIitem(126.9416700, 37.5140720, "노량진역", markerId, 1);
        poiData.addPOIitem(126.97117100000003, 37.540597, "남영역", markerId, 0);
        poiData.addPOIitem(126.9639160, 37.5464260, "대충 숙대", markerId, 0);
        poiData.endPOIdata();

        NMapPathData data = new NMapPathData(9);
        data.initPathData();
        data.addPathPoint(126.9653530, 37.4860320, 0);
        data.addPathPoint(126.9416700, 37.5140720, 0);
        data.addPathPoint(126.97117100000003, 37.540597, 0);
        data.addPathPoint(126.9639160, 37.5464260, 0);
        data.endPathData();
        NMapPathDataOverlay overlay = manager.createPathDataOverlay(data);
        overlay.showAllPathData(0);
        NMapPOIdataOverlay poiDataOverlay = manager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        view.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
                if (nMapError == null) {
                    controller.setMapCenter(new NGeoPoint(126.97117100000003, 37.540597), 12);
                } else {
                    Toast.makeText(MainActivity.this, nMapError.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
                Log.e("asdf", nGeoPoint.latitude + ", " + nGeoPoint.longitude);
            }

            @Override
            public void onMapCenterChangeFine(NMapView nMapView) {

            }

            @Override
            public void onZoomLevelChange(NMapView nMapView, int i) {

            }

            @Override
            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

            }
        });

    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}