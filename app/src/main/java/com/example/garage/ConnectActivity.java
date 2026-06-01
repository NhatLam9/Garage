package com.example.garage;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConnectActivity extends AppCompatActivity {

    private ImageView iconSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn thanh tiêu đề và làm trong suốt thanh trạng thái
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_connect);

        // Ánh xạ icon xoay
        iconSync = findViewById(R.id.icon_sync);

        // 1. Tạo hiệu ứng xoay (Rotation) liên tục cho icon Sync
        if (iconSync != null) {
            ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(iconSync, "rotation", 0f, 360f);
            rotateAnim.setDuration(1000); // 1 giây quay xong 1 vòng
            rotateAnim.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn
            rotateAnim.setInterpolator(new LinearInterpolator()); // Quay đều không bị giật
            rotateAnim.start();
        }

        // 2. Giả lập thời gian kết nối (Quét 3 giây rồi chuyển sang Dashboard)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
            startActivity(intent);

            // Hiệu ứng mờ dần khi chuyển cảnh
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Đóng trang kết nối
            finish();
        }, 3000); // 3000 milliseconds = 3 giây
    }
}