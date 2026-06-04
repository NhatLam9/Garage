package com.example.garage;

import java.util.Calendar;
import java.util.Date;

public class MaintenanceEngine {

    private static MaintenanceEngine instance;

    public static MaintenanceEngine getInstance() {
        if (instance == null) {
            instance = new MaintenanceEngine();
        }
        return instance;
    }

    private MaintenanceEngine() {}

    // Cấu trúc định mức vật tư
    public static class ComponentSpec {
        int maxDays;
        int maxKm;

        public ComponentSpec(int maxDays, int maxKm) {
            this.maxDays = maxDays;
            this.maxKm = maxKm;
        }
    }

    // Lấy định mức chuẩn (Whichever comes first)
    private ComponentSpec getComponentSpec(String componentType) {
        switch (componentType.toUpperCase()) {
            case "OIL":         return new ComponentSpec(180, 3000);   // Nhớt: 6 tháng hoặc 3000km
            case "COOLANT":     return new ComponentSpec(730, 20000);  // Nước mát: 2 năm hoặc 20.000km
            case "TIRES":       return new ComponentSpec(1095, 30000); // Lốp: 3 năm hoặc 30.000km
            case "BRAKE":       return new ComponentSpec(365, 15000);  // Phanh: 1 năm hoặc 15.000km
            default:            return new ComponentSpec(365, 10000);
        }
    }

    /**
     * THUẬT TOÁN 1: TÍNH NGÀY BẢO DƯỠNG TIẾP THEO
     */
    public Date calculateNextMaintenanceDate(Date lastChangeDate, String componentType, int dailyAvgKm) {
        if (lastChangeDate == null) return new Date();
        if (dailyAvgKm <= 0) dailyAvgKm = 20; // Tránh lỗi chia cho 0

        ComponentSpec spec = getComponentSpec(componentType);

        // Số ngày chạy được lý thuyết theo Quãng đường
        int daysByDistance = spec.maxKm / dailyAvgKm;

        // Chọn điều kiện đến trước (Thời gian hoặc Quãng đường)
        int actualRemainingDays = Math.min(spec.maxDays, daysByDistance);

        // Cộng vào ngày thay gần nhất
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastChangeDate);
        calendar.add(Calendar.DAY_OF_YEAR, actualRemainingDays);

        return calendar.getTime();
    }

    /**
     * THUẬT TOÁN 2: KIỂM TRA MỨC ĐỘ NGUY HIỂM (BÁO ĐỘNG)
     * Trả về true nếu chỉ còn <= 7 ngày là đến hạn, hoặc đã quá hạn
     */
    public boolean isComponentInDanger(Date nextMaintenanceDate) {
        if (nextMaintenanceDate == null) return false;

        Date today = new Date();
        long diffInMillies = nextMaintenanceDate.getTime() - today.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

        return diffInDays <= 7;
    }
}