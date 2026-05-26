package com.example.garage;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RpmGaugeView rpmGauge;
    private TextView tvRpmValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cài đặt giao diện Dark Mode
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_main);

        // 2. Chạy hiệu ứng vòng tua máy
        rpmGauge = findViewById(R.id.rpm_gauge);
        tvRpmValue = findViewById(R.id.tv_rpm_value);

        if (rpmGauge != null && tvRpmValue != null) {
            ValueAnimator animator = ValueAnimator.ofFloat(8000f, 8470f);
            animator.setDuration(400);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);

            animator.addUpdateListener(animation -> {
                float currentRpm = (float) animation.getAnimatedValue();
                rpmGauge.setRpm(currentRpm);
                String formattedRpm = String.format(Locale.US, "%,.0f", currentRpm).replace(",", ".");
                tvRpmValue.setText(formattedRpm);
            });
            animator.start();
        }

        // 3. SỰ KIỆN CLICK: Chuyển sang trang Service
        TextView navService = findViewById(R.id.nav_service);
        if (navService != null) {
            navService.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Tắt hiệu ứng trượt
                finish(); // Đóng trang hiện tại
            });
        }

        // 4. SỰ KIỆN CLICK: Chuyển sang trang Trips
        TextView navTrips = findViewById(R.id.nav_trips);
        if (navTrips != null) {
            navTrips.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, TripsActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Tắt hiệu ứng trượt
                finish(); // Đóng trang hiện tại
            });
        }
    }
}