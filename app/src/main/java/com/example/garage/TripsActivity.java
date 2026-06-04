package com.example.garage;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class TripsActivity extends BaseActivity {

    private MapView map;
    private MyLocationNewOverlay locationOverlay;
    private ImageView lastUploadedImage; // Dùng để hiển thị ảnh vừa chọn

    // Launcher để mở thư viện ảnh
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (lastUploadedImage != null) {
                        lastUploadedImage.setImageURI(selectedImageUri);
                        Toast.makeText(this, "Đã cập nhật ảnh hành trình!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_trips);
        setupCommonUI(R.id.nav_trips);

        // 1. Bản đồ (Không nhuộm đen)
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(15.0);

        // 2. Định vị
        enableUserLocation();

        // 3. Nút Upload ảnh (Giả sử bạn có 1 cái ImageView trong XML ID là iv_trip_photo)
        lastUploadedImage = findViewById(R.id.iv_trip_photo);
        Button btnUpload = findViewById(R.id.btn_upload_photo);

        if (btnUpload != null) {
            btnUpload.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            });
        }
    }

    private void enableUserLocation() {
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        map.getOverlays().add(locationOverlay);
    }

    @Override
    public void onResume() { super.onResume(); map.onResume(); }
    @Override
    public void onPause() { super.onPause(); map.onPause(); }
}