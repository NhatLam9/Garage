package com.example.garage;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import com.google.android.material.card.MaterialCardView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ServiceActivity extends BaseActivity {

    // Nút chọn ngày
    private TextView btnDateOil, btnDateCoolant, btnDateAbs, btnDateFilter, btnDateBattery, btnDateTires;

    // Thẻ dự đoán trung tâm
    private TextView tvPredictedServiceDate, btnAddToCalendar, tvPredictedMiniOil, tvPredictedMiniCoolant;
    private TextView btnAddComponentSchedule;

    // Formatting & State
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Date savedNextServiceDate = null;
    private String savedComponentName = "";

    // Thẻ linh kiện & Dòng trạng thái (Để đổi màu Tốt/Xấu)
    private MaterialCardView cardEngineOil, cardBrakePads, cardBattery;
    private TextView tvOilStatus, tvBrakeStatus, tvBatteryStatus, tvTiresStatus, tvCoolantStatus, tvFilterStatus;

    // Vòng tròn Progress (Chỉ dành cho Nhớt và Nước mát)
    private ProgressBar progressOil, progressCoolant;
    private TextView tvPercentOil, tvPercentCoolant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_service);
        setupCommonUI(R.id.nav_service);

        // --- 1. ÁNH XẠ UI ---
        btnDateOil = findViewById(R.id.btn_input_date_oil);
        btnDateCoolant = findViewById(R.id.btn_input_date_coolant);
        btnDateAbs = findViewById(R.id.btn_input_date_abs);
        btnDateFilter = findViewById(R.id.btn_input_date_filter);
        btnDateBattery = findViewById(R.id.btn_input_date_battery);
        btnDateTires = findViewById(R.id.btn_input_date_tires);

        tvPredictedServiceDate = findViewById(R.id.tv_predicted_service_date);
        btnAddToCalendar = findViewById(R.id.btn_add_to_calendar);
        tvPredictedMiniOil = findViewById(R.id.tv_predicted_mini_oil);
        tvPredictedMiniCoolant = findViewById(R.id.tv_predicted_mini_coolant);
        btnAddComponentSchedule = findViewById(R.id.btn_add_component_schedule);

        cardEngineOil = findViewById(R.id.card_engine_oil);
        cardBrakePads = findViewById(R.id.card_brake_pads);
        cardBattery = findViewById(R.id.card_battery);

        // Trạng thái các thẻ con
        tvOilStatus = findViewById(R.id.tv_oil_status);
        tvBrakeStatus = findViewById(R.id.tv_brake_status);
        tvBatteryStatus = findViewById(R.id.tv_battery_status);
        tvTiresStatus = findViewById(R.id.tv_tires_status); // Nhớ thêm ID này vào XML
        tvCoolantStatus = findViewById(R.id.tv_coolant_status); // Nhớ thêm ID này vào XML
        tvFilterStatus = findViewById(R.id.tv_filter_status); // Nhớ thêm ID này vào XML

        progressOil = findViewById(R.id.progress_oil);
        progressCoolant = findViewById(R.id.progress_coolant);
        tvPercentOil = findViewById(R.id.tv_percent_oil);
        tvPercentCoolant = findViewById(R.id.tv_percent_coolant);

        // Nút thêm lịch hẹn trực tiếp
        if (btnAddComponentSchedule != null) {
            btnAddComponentSchedule.setOnClickListener(this::showComponentScheduleMenu);
        }

        // --- 2. SỰ KIỆN CHỌN NGÀY VÀ ĐỒNG BỘ TOÀN HỆ THỐNG ---
        // Giả định tuổi thọ (Số ngày): Nhớt 180đ, Nước mát 365đ, Dầu phanh 365đ, Ắc quy 730đ, Lốp 730đ, Lọc gió 180đ.

        if (btnDateOil != null) {
            btnDateOil.setOnClickListener(v -> showDatePicker(date -> {
                // Tính % và Cập nhật Vòng tròn + Thẻ nhỏ
                processComponentLife(date, 180, btnDateOil, "Đã thay: ", tvOilStatus, progressOil, tvPercentOil);

                // Tiên đoán lịch trung tâm
                Date nextDate = MaintenanceEngine.getInstance().calculateNextMaintenanceDate(date, "OIL", 20);
                if (tvPredictedMiniOil != null) {
                    tvPredictedMiniOil.setText("Dự kiến: " + dateFormat.format(nextDate));
                    tvPredictedMiniOil.setVisibility(View.VISIBLE);
                }
                updatePredictedService(nextDate, "Dầu nhớt động cơ");
            }));
        }

        if (btnDateCoolant != null) {
            btnDateCoolant.setOnClickListener(v -> showDatePicker(date -> {
                // Tính % và Cập nhật Vòng tròn + Thẻ nhỏ
                processComponentLife(date, 365, btnDateCoolant, "Đã thay: ", tvCoolantStatus, progressCoolant, tvPercentCoolant);

                // Tiên đoán lịch trung tâm
                Date nextDate = MaintenanceEngine.getInstance().calculateNextMaintenanceDate(date, "COOLANT", 20);
                if (tvPredictedMiniCoolant != null) {
                    tvPredictedMiniCoolant.setText("Dự kiến: " + dateFormat.format(nextDate));
                    tvPredictedMiniCoolant.setVisibility(View.VISIBLE);
                }
            }));
        }

        if (btnDateBattery != null) {
            btnDateBattery.setOnClickListener(v -> showDatePicker(date -> {
                processComponentLife(date, 730, btnDateBattery, "Đã thay: ", tvBatteryStatus, null, null);
                Date nextDate = MaintenanceEngine.getInstance().calculateNextMaintenanceDate(date, "DEFAULT", 20);
                updatePredictedService(nextDate, "Bình ắc quy");
            }));
        }

        if (btnDateAbs != null) {
            btnDateAbs.setOnClickListener(v -> showDatePicker(date -> {
                processComponentLife(date, 365, btnDateAbs, "Dầu phanh: ", tvBrakeStatus, null, null);
            }));
        }

        if (btnDateFilter != null) {
            btnDateFilter.setOnClickListener(v -> showDatePicker(date -> {
                processComponentLife(date, 180, btnDateFilter, "Đã thay: ", tvFilterStatus, null, null);
            }));
        }

        if (btnDateTires != null) {
            btnDateTires.setOnClickListener(v -> showDatePicker(date -> {
                processComponentLife(date, 730, btnDateTires, "Đã thay: ", tvTiresStatus, null, null);
            }));
        }

        // --- 3. SỰ KIỆN LƯU VÀO LỊCH (CARD TRUNG TÂM) ---
        if (btnAddToCalendar != null) {
            btnAddToCalendar.setOnClickListener(v -> {
                if (savedNextServiceDate != null && !savedComponentName.isEmpty()) {
                    openAndroidCalendar(savedComponentName, savedNextServiceDate);
                }
            });
        }

        // --- 4. GIẢ LẬP CLICK VÀO CARD ĐỂ TEST CẢNH BÁO KHẨN CẤP ---
        if (cardEngineOil != null) cardEngineOil.setOnClickListener(v -> showStatusSelectionMenu(v, "Nhớt động cơ", tvOilStatus));
        if (cardBrakePads != null) cardBrakePads.setOnClickListener(v -> showStatusSelectionMenu(v, "Phanh ABS", tvBrakeStatus));
        if (cardBattery != null) cardBattery.setOnClickListener(v -> showStatusSelectionMenu(v, "Ắc quy", tvBatteryStatus));
    }

    // ==========================================
    // HÀM XỬ LÝ ĐỒNG BỘ: Tính % -> Đổi Vòng Tròn -> Đổi Màu Chữ
    // ==========================================
    private void processComponentLife(Date selectedDate, int maxLifespanDays, TextView tvDateBtn, String datePrefix, TextView tvStatusLabel, ProgressBar topProgress, TextView topPercent) {

        // 1. Ghi ngày hiển thị
        if (tvDateBtn != null) {
            tvDateBtn.setText(datePrefix + dateFormat.format(selectedDate));
        }

        // 2. Tính % còn lại
        int percent = calculateLifePercentage(selectedDate, maxLifespanDays);

        // 3. Nếu linh kiện này CÓ vòng tròn ở trên (Nhớt, Nước mát) -> Kích hoạt Animation vòng tròn
        if (topProgress != null && topPercent != null) {
            animateProgressBar(topProgress, topPercent, percent);
        }

        // 4. Nếu linh kiện này CÓ thẻ trạng thái -> Đổi màu chữ theo %
        if (tvStatusLabel != null) {
            if (percent > 60) {
                tvStatusLabel.setText("TỐT (" + percent + "%)");
                tvStatusLabel.setTextColor(Color.parseColor("#00E676")); // Màu xanh lá neon
            } else if (percent > 20) {
                tvStatusLabel.setText("CẦN THEO DÕI (" + percent + "%)");
                tvStatusLabel.setTextColor(Color.parseColor("#FFCA28")); // Màu Vàng/Cam
            } else {
                tvStatusLabel.setText("NÊN THAY THẾ (" + percent + "%)");
                tvStatusLabel.setTextColor(Color.parseColor("#FF5252")); // Màu Đỏ
            }
        }
    }

    private int calculateLifePercentage(Date lastChangeDate, int maxLifespanDays) {
        long diffInMillis = System.currentTimeMillis() - lastChangeDate.getTime();
        long daysPassed = diffInMillis / (1000 * 60 * 60 * 24);
        int percentLeft = 100 - (int) ((daysPassed * 100) / maxLifespanDays);
        return Math.max(0, Math.min(100, percentLeft));
    }

    private void animateProgressBar(ProgressBar progressBar, TextView tvPercent, int targetValue) {
        if (progressBar == null) return;
        ValueAnimator animator = ValueAnimator.ofInt(0, targetValue);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            progressBar.setProgress(currentValue);
            if (tvPercent != null) tvPercent.setText(currentValue + "%");
        });
        animator.start();
    }

    // ==========================================
    // CÁC HÀM XỬ LÝ LỊCH VÀ CHUYỂN TRANG (GIỮ NGUYÊN HOẠT ĐỘNG HOÀN HẢO)
    // ==========================================

    private void showComponentScheduleMenu(View anchorView) {
        PopupMenu popup = new PopupMenu(this, anchorView);
        popup.getMenu().add(0, 1, 0, "🛢️ Nhớt động cơ");
        popup.getMenu().add(0, 2, 1, "🔋 Bình ắc quy");
        popup.getMenu().add(0, 3, 2, "⚙️ Phanh ABS");
        popup.getMenu().add(0, 4, 3, "🌀 Lọc gió động cơ");
        popup.getMenu().add(0, 5, 4, "❌ Lốp xe (Trọn bộ)");

        popup.setOnMenuItemClickListener(item -> {
            String selectedPart = item.getTitle().toString().replaceAll("[\\uD83D\\uDCE6\\uD83D\\uDFE2\\uD83D\\uDD34\\uD83D\\uDEE2\\uD83D\\uDD0B\\u2699\\uD83C\\uDF00\\u274C\\uFE0F ]", "");
            triggerDirectScheduleWorkflow(selectedPart);
            return true;
        });
        popup.show();
    }

    private void triggerDirectScheduleWorkflow(String componentName) {
        Intent intent = new Intent(ServiceActivity.this, DocsActivity.class);
        intent.putExtra("SUGGESTED_PART", componentName);
        intent.putExtra("AUTO_ADD_TODO", "📅 Kế hoạch: Bảo dưỡng " + componentName);
        intent.putExtra("OPEN_CALENDAR_AUTO", componentName);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private void updatePredictedService(Date nextServiceDate, String displayComponentName) {
        this.savedNextServiceDate = nextServiceDate;
        this.savedComponentName = displayComponentName;
        if (tvPredictedServiceDate != null) tvPredictedServiceDate.setText(dateFormat.format(nextServiceDate));
        if (btnAddToCalendar != null) btnAddToCalendar.setVisibility(View.VISIBLE);
    }

    private void showDatePicker(OnDateSelectedListener listener) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog,
                (view, y, m, d) -> {
                    Calendar sel = Calendar.getInstance();
                    sel.set(y, m, d);
                    listener.onDateSelected(sel.getTime());
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void openAndroidCalendar(String componentName, Date targetDate) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTime(targetDate);
        beginTime.set(Calendar.HOUR_OF_DAY, 9);
        beginTime.set(Calendar.MINUTE, 0);
        Calendar endTime = (Calendar) beginTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 1);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "🛠️ MyGarage: Bảo dưỡng " + componentName)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Hạn kiểm tra hao mòn linh kiện định kỳ.")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }

    private void showStatusSelectionMenu(View anchorView, String componentName, TextView tvStatus) {
        PopupMenu popup = new PopupMenu(this, anchorView);
        popup.getMenu().add(0, 1, 0, "🟢 Trạng thái: GOOD (Bình thường)");
        popup.getMenu().add(0, 2, 1, "🔴 Trạng thái: BAD (Cần thay thế)");
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                if (tvStatus != null) { tvStatus.setText("GOOD"); tvStatus.setTextColor(Color.parseColor("#00FF00")); }
                return true;
            } else if (item.getItemId() == 2) {
                if (tvStatus != null) { tvStatus.setText("BAD"); tvStatus.setTextColor(Color.parseColor("#FF5252")); }
                triggerMaintenanceAlert(componentName);
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void triggerMaintenanceAlert(String componentName) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_maintenance_alert, null);
        TextView tvMessage = dialogView.findViewById(R.id.tv_alert_message);
        Button btnClose = dialogView.findViewById(R.id.btn_close_alert);
        if (tvMessage != null) tvMessage.setText("Linh kiện [" + componentName + "] trên xe của bạn đã chạm ngưỡng giới hạn hao mòn nghiêm trọng.\nVui lòng kiểm tra ngay lập tức!");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                alertDialog.dismiss();
                Intent intent = new Intent(ServiceActivity.this, DocsActivity.class);
                intent.putExtra("SUGGESTED_PART", componentName);
                intent.putExtra("AUTO_ADD_TODO", "⚠️ KHẨN CẤP: Kiểm tra và thay thế " + componentName);
                intent.putExtra("OPEN_CALENDAR_AUTO", componentName);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            });
        }
        alertDialog.show();
    }

    private interface OnDateSelectedListener { void onDateSelected(Date date); }
}