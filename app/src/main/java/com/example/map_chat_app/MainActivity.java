package com.example.map_chat_app;

import static com.mapbox.maps.ImageHolder.*;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.map_chat_app.Activity.MessageActivity;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;
import com.mapbox.maps.ImageHolder;

public class MainActivity extends AppCompatActivity {

    MapView mapView;
    ImageView toMainImg ,toChatImg;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if (o) {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(
                    new CameraOptions.Builder()
                            .bearing(v)
                            .build()
            );
        }
    };

    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera(
                    new CameraOptions.Builder()
                            .center(point)
                            .zoom(20.0)
                            .build()
            );

            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
        }
    };

    private final OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        toMainImg = findViewById(R.id.toMain);
        toChatImg = findViewById(R.id.toChat);
        String userId = getIntent().getStringExtra("userid");

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != getPackageManager().PERMISSION_GRANTED){
            activityResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS ,new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapView.getMapboxMap().setCamera(new CameraOptions.Builder()
                        .zoom(20.0)
                        .build());
                LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
                locationComponentPlugin.setEnabled(true);

                // Convert Drawable to Bitmap
                Drawable drawable = AppCompatResources.getDrawable(MainActivity.this, R.drawable.baseline_location_on_24);
                Bitmap bitmap = convertDrawableToBitmap(drawable);



                // Convert Bitmap to ImageHolder
                ImageHolder bearingImage = from(bitmap);

                LocationPuck2D locationPuck2D = new LocationPuck2D();
                locationPuck2D.setBearingImage(bearingImage);
                locationComponentPlugin.setLocationPuck(locationPuck2D);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                getGestures(mapView).addOnMoveListener(onMoveListener);

                toMainImg.setOnClickListener(v -> {
                    locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                    locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                    getGestures(mapView).addOnMoveListener(onMoveListener);
                });
            }
        });

        toChatImg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MessageActivity.class);
            intent.putExtra("userid", userId);
            startActivity(intent);
        });

    }

    private Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
