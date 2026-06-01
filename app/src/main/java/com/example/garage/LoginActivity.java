package com.example.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Ẩn thanh tiêu đề (ActionBar) và làm trong suốt thanh trạng thái (StatusBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // 2. Gọi file giao diện XML
        setContentView(R.layout.activity_login);

        // 3. Ánh xạ nút Đăng Nhập
        btnLogin = findViewById(R.id.btn_login);

        // 4. Xử lý sự kiện bấm Đăng Nhập
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                // ĐÃ SỬA: Chuyển sang ConnectActivity
                Intent intent = new Intent(LoginActivity.this, ConnectActivity.class);
                startActivity(intent);

                // Hiệu ứng mờ dần khi chuyển trang (tùy chọn)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                // Đóng trang Login để người dùng ấn nút Back không quay lại đây nữa
                finish();
            });
        }
    }
}