package com.example.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DocsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Ẩn Action Bar và cài đặt Dark Mode cho Status Bar
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // Tái sử dụng màu nền tối từ file colors.xml (nếu bạn có màu R.color.bg_main)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_docs);
        setupCommonUI(R.id.nav_docs);

        // 2. Xử lý chuyển tab ở Bottom Navigation
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        TextView navDashboard = findViewById(R.id.nav_dashboard);
        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> {
                startActivity(new Intent(DocsActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }

        TextView navService = findViewById(R.id.nav_service);
        if (navService != null) {
            navService.setOnClickListener(v -> {
                startActivity(new Intent(DocsActivity.this, ServiceActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }

        TextView navTrips = findViewById(R.id.nav_trips);
        if (navTrips != null) {
            navTrips.setOnClickListener(v -> {
                startActivity(new Intent(DocsActivity.this, TripsActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }

    }
}