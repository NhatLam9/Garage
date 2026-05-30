package com.example.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public abstract class BaseActivity extends AppCompatActivity {
    protected void setupCommonUI(int currentMenuId) {
        // 1. Cài đặt nút chuyển ngôn ngữ
        TextView btnLanguage = findViewById(R.id.btn_language);
        if (btnLanguage != null) {
            btnLanguage.setOnClickListener(v -> {
                LocaleListCompat currentLocales = AppCompatDelegate.getApplicationLocales();
                String currentLang = currentLocales.isEmpty() ? "en" : currentLocales.get(0).getLanguage();
                if (currentLang.equals("vi")) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("vi"));
                }
            });
        }

        // 2. Cài đặt click chuyển trang cho Bottom Nav
        setupNavButton(R.id.nav_dashboard, MainActivity.class, currentMenuId);
        setupNavButton(R.id.nav_service, ServiceActivity.class, currentMenuId);
        setupNavButton(R.id.nav_docs, DocsActivity.class, currentMenuId);
        setupNavButton(R.id.nav_trips, TripsActivity.class, currentMenuId);
    }

    private void setupNavButton(int buttonId, Class<?> targetActivity, int currentMenuId) {
        TextView button = findViewById(buttonId);
        if (button == null) return;

        // Nếu đây là tab đang đứng -> Bôi nền màu cam để làm nổi bật
        if (buttonId == currentMenuId) {
            button.setBackgroundResource(R.drawable.bg_nav_active);
            button.setTextColor(getResources().getColor(R.color.bg_main, null));
        } else {
            // Tab khác thì xóa nền, để màu xám
            button.setBackground(null);
            button.setTextColor(getResources().getColor(R.color.text_gray, null));

            // Xử lý chuyển trang
            button.setOnClickListener(v -> {
                startActivity(new Intent(this, targetActivity));
                overridePendingTransition(0, 0); // Tắt hiệu ứng giật màn hình
                finish();
            });
        }
    }
}