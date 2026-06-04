package com.example.garage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DocsActivity extends BaseActivity implements TodoAdapter.OnTodoActionListener {

    private RecyclerView rvTodoList;
    private TextView tvEmptyState;
    private FloatingActionButton fabAdd;

    private DatabaseHelper dbHelper;
    private TodoAdapter adapter;
    private List<TodoItem> currentDataList;
    private SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.bg_main));

        setContentView(R.layout.activity_docs);
        setupCommonUI(R.id.nav_docs);
        setupBottomNavigation();

        rvTodoList = findViewById(R.id.rv_todo_list);
        tvEmptyState = findViewById(R.id.tv_empty_state);
        fabAdd = findViewById(R.id.fab_add_doc_todo);

        dbHelper = new DatabaseHelper(this);

        if (rvTodoList != null) {
            rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        }

        refreshTodoList();

        // Gọi hàm setup logic giao diện mới (Maps, Banner, Lưới phụ tùng)
        setupNewLogic();

        // ==============================================================
        // BẮT LỆNH TỪ SERVICEACTIVITY TRUYỀN SANG
        // ==============================================================
        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {

            // 1. Lệnh tạo Todo tự động
            if (incomingIntent.hasExtra("AUTO_ADD_TODO")) {
                String autoTaskText = incomingIntent.getStringExtra("AUTO_ADD_TODO");
                if (autoTaskText != null && !autoTaskText.isEmpty()) {
                    String todayStr = currentDateFormat.format(new Date());
                    dbHelper.addTodo(autoTaskText, "Ngày tạo: " + todayStr);
                    refreshTodoList();
                    incomingIntent.removeExtra("AUTO_ADD_TODO");
                }
            }

            // 2. Lệnh mở ứng dụng Lịch (Kỹ thuật chuyền gậy)
            if (incomingIntent.hasExtra("OPEN_CALENDAR_AUTO")) {
                String partName = incomingIntent.getStringExtra("OPEN_CALENDAR_AUTO");
                if (partName != null) {
                    syncToSystemCalendarTomorrow(partName); // Mở lịch đè lên màn hình Docs
                    incomingIntent.removeExtra("OPEN_CALENDAR_AUTO");
                }
            }
        }

        // Bắt sự kiện của nút + do bạn code ban đầu
        if (fabAdd != null) {
            fabAdd.setOnClickListener(v -> showNewTaskInputDialog());
        }
    }

    // ==============================================================
    // KHỐI LOGIC MỚI: BẢN ĐỒ, QUẢNG CÁO, TRA CỨU PHỤ TÙNG
    // ==============================================================
    private void setupNewLogic() {
        // 1. NÚT MAPS THU NHỎ - TÌM TIỆM SỬA XE
        MaterialButton btnMapGarage = findViewById(R.id.btn_map_garage);
        if (btnMapGarage != null) {
            btnMapGarage.setOnClickListener(v -> openGoogleMaps("Tiệm sửa chữa xe máy gần đây"));
        }

        // 2. CÁC NÚT TÌM MUA TRÊN BANNER
        Button btnBuyMotul = findViewById(R.id.btn_buy_motul);
        Button btnBuyPirelli = findViewById(R.id.btn_buy_pirelli);
        Button btnBuyBrembo = findViewById(R.id.btn_buy_brembo);

        if (btnBuyMotul != null) btnBuyMotul.setOnClickListener(v -> searchGoogleWeb("mua Nhớt Motul 300V chính hãng"));
        if (btnBuyPirelli != null) btnBuyPirelli.setOnClickListener(v -> searchGoogleWeb("mua Lốp Pirelli Rosso chính hãng"));
        if (btnBuyBrembo != null) btnBuyBrembo.setOnClickListener(v -> searchGoogleWeb("mua Má phanh Brembo chính hãng"));

        // 3. LƯỚI DANH MỤC - GỌI ACTIVITY BẰNG CỜ HIỆU
        MaterialCardView btnOil = findViewById(R.id.btn_product_oil);
        MaterialCardView btnCoolant = findViewById(R.id.btn_product_coolant);
        MaterialCardView btnBrakePad = findViewById(R.id.btn_product_brake_pad);
        MaterialCardView btnBrakeFluid = findViewById(R.id.btn_product_brake_fluid);
        MaterialCardView btnOilFilter = findViewById(R.id.btn_product_oil_filter);
        MaterialCardView btnAirFilter = findViewById(R.id.btn_product_air_filter);

        if (btnOil != null) btnOil.setOnClickListener(v -> launchProductActivity("OIL"));
        if (btnCoolant != null) btnCoolant.setOnClickListener(v -> launchProductActivity("COOLANT"));
        if (btnBrakePad != null) btnBrakePad.setOnClickListener(v -> launchProductActivity("BRAKE"));
        if (btnBrakeFluid != null) btnBrakeFluid.setOnClickListener(v -> launchProductActivity("BRAKE_FLUID"));
        if (btnOilFilter != null) btnOilFilter.setOnClickListener(v -> launchProductActivity("OIL_FILTER"));
        if (btnAirFilter != null) btnAirFilter.setOnClickListener(v -> launchProductActivity("AIR_FILTER"));
    }

    private void launchProductActivity(String categoryType) {
        Intent intent = new Intent(DocsActivity.this, ProductListActivity.class);
        intent.putExtra("CATEGORY_TYPE", categoryType);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void searchGoogleWeb(String query) {
        try {
            String url = "https://www.google.com/search?q=" + Uri.encode(query);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở Trình duyệt Web!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openGoogleMaps(String searchQuery) {
        try {
            String uriString = "geo:0,0?q=" + Uri.encode(searchQuery);
            Uri gmmIntentUri = Uri.parse(uriString);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Xóa comment dòng dưới nếu cắm máy thật
            // mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                searchGoogleWeb(searchQuery);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở bản đồ!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ==============================================================
    // CÁC HÀM CŨ CỦA BẠN (GIỮ NGUYÊN 100%)
    // ==============================================================
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
    }

    private void refreshTodoList() {
        if (dbHelper == null || rvTodoList == null || tvEmptyState == null) return;
        currentDataList = dbHelper.getAllTodos();
        if (currentDataList.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            rvTodoList.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            rvTodoList.setVisibility(View.VISIBLE);
            adapter = new TodoAdapter(currentDataList, this);
            rvTodoList.setAdapter(adapter);
        }
    }

    private void showNewTaskInputDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_todo, null);
        EditText inputField = dialogView.findViewById(R.id.et_task_input);
        TextView btnCancel = dialogView.findViewById(R.id.btn_cancel_dialog);
        TextView btnSave = dialogView.findViewById(R.id.btn_save_dialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String taskText = inputField.getText().toString().trim();
            if (!taskText.isEmpty()) {
                String todayStr = currentDateFormat.format(new Date());
                dbHelper.addTodo(taskText, "Ngày tạo: " + todayStr);
                refreshTodoList();
                dialog.dismiss();

                Toast.makeText(this, "Đang mở ứng dụng Lịch...", Toast.LENGTH_SHORT).show();
                syncToSystemCalendarToday(taskText);
            } else {
                Toast.makeText(this, "Vui lòng nhập nội dung công việc!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    // Hàm tạo lịch ngay hôm nay (Dành cho tạo thủ công bằng nút +)
    private void syncToSystemCalendarToday(String taskTitle) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Calendar.HOUR_OF_DAY, 9);
        beginTime.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "🛠️ [DIY Garage] " + taskTitle)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Kế hoạch tự chăm sóc xe ghi chép qua app MyGarage.");
        startActivity(intent);
    }

    // Hàm tạo lịch vào ngày mai (Dành cho luồng tự động từ Service truyền qua)
    private void syncToSystemCalendarTomorrow(String taskTitle) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.add(Calendar.DAY_OF_YEAR, 1); // Cộng thêm 1 ngày
        beginTime.set(Calendar.HOUR_OF_DAY, 9);
        beginTime.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "🛠️ [Khẩn Cấp] Bảo dưỡng " + taskTitle)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Đã tới hạn bảo dưỡng. MyGarage tự động tạo lịch trình.");
        startActivity(intent);
    }

    @Override
    public void onStatusChanged(int id, boolean isChecked) {
        dbHelper.updateTodoStatus(id, isChecked ? 1 : 0);
        refreshTodoList();
    }

    @Override
    public void onDeleteRequested(int id) {
        dbHelper.deleteTodo(id);
        refreshTodoList();
        Toast.makeText(this, "Đã xóa bản ghi!", Toast.LENGTH_SHORT).show();
    }
}
