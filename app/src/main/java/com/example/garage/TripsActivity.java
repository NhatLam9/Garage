package com.example.garage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity {

    private MapView map;
    private MyLocationNewOverlay locationOverlay;
    private static final int LOCATION_REQUEST_CODE = 100; // Mã kiểm tra quyền

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cấu hình OSMDroid
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        // 2. Giao diện Dark Mode
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_trips);

        // 3. Cài đặt Bản đồ
        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);
        map.getController().setZoom(15.0);

        // Nhuộm đen bản đồ
        ColorMatrix inverseMatrix = new ColorMatrix(new float[] {
                -1.0f, 0.0f, 0.0f, 0.0f, 255f,
                0.0f, -1.0f, 0.0f, 0.0f, 255f,
                0.0f, 0.0f, -1.0f, 0.0f, 255f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        });
        map.getOverlayManager().getTilesOverlay().setColorFilter(new ColorMatrixColorFilter(inverseMatrix));

        // 4. Vẽ lộ trình ảo (Mulholland)
        List<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(new GeoPoint(34.103, -118.805));
        geoPoints.add(new GeoPoint(34.110, -118.818));
        geoPoints.add(new GeoPoint(34.120, -118.835));
        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        line.getOutlinePaint().setColor(Color.WHITE);
        line.getOutlinePaint().setStrokeWidth(12f);
        map.getOverlays().add(line);

        // 5. YÊU CẦU QUYỀN ĐỊNH VỊ VÀ TÌM VỊ TRÍ HIỆN TẠI
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa có quyền -> Bật bảng hỏi người dùng
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            // Nếu đã có quyền -> Bật vị trí
            enableUserLocation();
        }

        // 6. Nút chuyển trang
        TextView navDashboard = findViewById(R.id.nav_dashboard);
        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                startActivity(new Intent(TripsActivity.this, MainActivity.class));
                overridePendingTransition(0, 0); finish();
            });
        }
        TextView navService = findViewById(R.id.nav_service);
        if (navService != null) {
            navService.setOnClickListener(v -> {
                startActivity(new Intent(TripsActivity.this, ServiceActivity.class));
                overridePendingTransition(0, 0); finish();
            });
        }
    }

    // Hàm bật dấu chấm GPS và kéo Camera theo người dùng
    private void enableUserLocation() {
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        locationOverlay.enableMyLocation(); // Bật chấm xanh
        locationOverlay.enableFollowLocation(); // Ép bản đồ luôn chạy theo vị trí của bạn
        map.getOverlays().add(locationOverlay);
    }

    // Hàm bắt sự kiện khi người dùng bấm nút "Cho phép" trên bảng hỏi quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        }
    }

    // Quản lý vòng đời
    @Override
    public void onResume() {
        super.onResume();
        if (map != null) map.onResume();
        if (locationOverlay != null) locationOverlay.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (map != null) map.onPause();
        if (locationOverlay != null) locationOverlay.disableMyLocation();
    }
}