package com.example.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cài đặt giao diện Dark Mode
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_service);
        setupCommonUI(R.id.nav_service);

        // 2. SỰ KIỆN CLICK: Quay lại trang Dashboard
        TextView navDashboard = findViewById(R.id.nav_dashboard);
        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Tắt hiệu ứng trượt
                finish(); // Đóng trang Service
            });
        }

        TextView navTrips = findViewById(R.id.nav_trips);
        if (navTrips != null) {
            navTrips.setOnClickListener(v -> {
                Intent intent = new Intent(ServiceActivity.this, TripsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Tắt hiệu ứng trượt
                finish(); // Đóng trang hiện tại
            });
        }
    }
}