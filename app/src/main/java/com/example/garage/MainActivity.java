package com.example.garage;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private RpmGaugeView rpmGauge;
    private TextView tvRpmValue;
    private TextView tvMode;
    private String[] drivingModes = {"RAIN", "CITY", "SPORT", "SPORT+", "RACE"};
    private int currentModeIndex = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cài đặt giao diện Dark Mode
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_main);
        setupCommonUI(R.id.nav_dashboard);

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

        // --- SỰ KIỆN CLICK ĐỂ ĐỔI MODE ---
        tvMode = findViewById(R.id.tvMode);
        if (tvMode != null) {
            tvMode.setOnClickListener(v -> cycleDrivingMode());
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

    // --- HÀM ĐỔI CHẾ ĐỘ LÁI ---
    private void cycleDrivingMode() {
        // Tăng index, nếu vượt quá số lượng chế độ thì quay lại 0
        currentModeIndex = (currentModeIndex + 1) % drivingModes.length;
        String newMode = drivingModes[currentModeIndex];

        tvMode.setText(newMode);

        // Đổi màu chữ tương ứng với chế độ lái
        int colorResId;
        if (newMode.equals("RACE") || newMode.equals("SPORT+")) {
            colorResId = R.color.colorPrimary; // Màu đỏ/cam
        } else if (newMode.equals("RAIN")) {
            colorResId = R.color.status_blue; // Màu xanh dương
        } else {
            colorResId = R.color.status_green; // Màu xanh lá
        }

        tvMode.setTextColor(ContextCompat.getColor(this, colorResId));
    }
}