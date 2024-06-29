package com.example.map_chat_app;

import static com.mapbox.maps.ImageHolder.*;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;
import com.mapbox.maps.ImageHolder;


import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.geojson.Point;
import android.graphics.BitmapFactory;
import android.content.Context;



import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MapView mapView;
    ImageView toMainImg, toChatImg , logoutImg;
    String id;

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

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Position");
            Map<String, Double> position = new HashMap<>();
            position.put("latitude", point.latitude());
            position.put("longitude", point.longitude());
            userRef.setValue(position);
        }
    };

    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener_other = new OnIndicatorPositionChangedListener() {
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
        logoutImg = findViewById(R.id.logout);

        String username = getIntent().getStringExtra("username");
        id = getIntent().getStringExtra("userId");


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != getPackageManager().PERMISSION_GRANTED) {
            activityResultLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapView.getMapboxMap().setCamera(new CameraOptions.Builder()
                        .zoom(20.0)
                        .build());
                LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
                locationComponentPlugin.setEnabled(true);

                // Sử dụng IconGenerator để tạo Bitmap
                IconGenerator iconGenerator = new IconGenerator(MainActivity.this);
                TextView textView = new TextView(MainActivity.this);
                textView.setText(""+username);

                iconGenerator.setContentView(textView);
                Bitmap bitmap = iconGenerator.makeIcon();

                // Chuyển Bitmap thành ImageHolder

                ImageHolder bearingImage = from(bitmap);

                LocationPuck2D locationPuck2D = new LocationPuck2D();
                locationPuck2D.setBearingImage(bearingImage);
                locationComponentPlugin.setLocationPuck(locationPuck2D);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                getGestures(mapView).addOnMoveListener(onMoveListener);

                // Truy vấn dữ liệu từ Firebase
                /*DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                // Lấy thông tin vị trí của người dùng
                                Double latitude = userSnapshot.child("Position").child("latitude").getValue(Double.class);
                                Double longitude = userSnapshot.child("Position").child("longitude").getValue(Double.class);
                                String username = userSnapshot.child("name").getValue(String.class);

                                Toast.makeText(MainActivity.this, "username: " + username + " Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_SHORT).show();

                                if (latitude != null && longitude != null) {
                                    // Sử dụng IconGenerator để tạo Bitmap
                                    IconGenerator iconGenerator = new IconGenerator(MainActivity.this);
                                    TextView textView = new TextView(MainActivity.this);
                                    textView.setText(username);
                                    iconGenerator.setContentView(textView);
                                    Bitmap bitmap = iconGenerator.makeIcon();

                                    // Chuyển Bitmap thành ImageHolder
                                    ImageHolder bearingImage = from(bitmap);

                                    LocationPuck2D locationPuck2D = new LocationPuck2D();
                                    locationPuck2D.setBearingImage(bearingImage);
                                    locationComponentPlugin.setLocationPuck(locationPuck2D);


                                }
                            }
                        } else {
                            Log.d("Firebase", "No users found.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Failed to read value.", databaseError.toException());
                    }
                });*/




                //Chuyển về vị trí người dùng hiện tại
                toMainImg.setOnClickListener(v -> {
                    locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                    locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                    getGestures(mapView).addOnMoveListener(onMoveListener);
                });
            }
        });

        //Chat
        toChatImg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FindFriendActivity.class);
            intent.putExtra("userId", id);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        //Đăng xuất
        logoutImg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });


    }

    /*private void addPointAnnotation(MapView mapView, double longitude, double latitude, int iconResource) {
        // Lấy AnnotationPlugin từ MapView
        AnnotationPlugin annotationPlugin = (AnnotationPlugin) mapView.getPlugin(AnnotationPluginImpl.class);

        if (annotationPlugin != null) {
            // Tạo PointAnnotationManager
            PointAnnotationManager pointAnnotationManager = annotationPlugin.createPointAnnotationManager(new AnnotationConfig());

            // Tạo PointAnnotationOptions
            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(longitude, latitude))
                    .withIconImage(BitmapFactory.decodeResource(getResources(), iconResource));

            // Tạo point annotation
            pointAnnotationManager.create(pointAnnotationOptions);
        }
    }*/
}
