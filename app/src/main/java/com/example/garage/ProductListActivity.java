package com.example.garage;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        TextView btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                finish();
                overridePendingTransition(0, 0); // Tắt hiệu ứng trượt màn hình
            });
        }

        TextView tvTitle = findViewById(R.id.tv_category_title);
        RecyclerView rvProducts = findViewById(R.id.rv_products);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        // Nhận cờ hiệu từ màn hình DocsActivity
        String categoryType = getIntent().getStringExtra("CATEGORY_TYPE");
        List<Product> productList = new ArrayList<>();

        if (categoryType != null) {
            switch (categoryType) {
                case "OIL":
                    tvTitle.setText("TOP 5 DẦU NHỚT");
                    productList.add(new Product("Motul 300V 10W40", "Dầu nhớt tổng hợp toàn phần, bảo vệ động cơ đua chuyên nghiệp.", "https://i.pinimg.com/1200x/d8/ef/6c/d8ef6c9dbd7619030b05fb711b7d3a62.jpg", "mua nhớt Motul 300V chính hãng"));
                    productList.add(new Product("Liqui Moly Motorbike 4T", "Nhớt nhập khẩu Đức, công nghệ ma sát thấp.", "https://i.pinimg.com/736x/07/75/78/077578ae38bb09e5c6ea1197e065a271.jpg", "mua Liqui Moly 4T 10W40"));
                    productList.add(new Product("Repsol Moto Racing 10W40", "Được phát triển từ MotoGP, tản nhiệt cực tốt.", "https://i.pinimg.com/1200x/f2/64/28/f264284549de60d99a8e7c59422d16dd.jpg", "mua nhớt Repsol Moto Racing"));
                    productList.add(new Product("Shell Advance Ultra", "Công nghệ PurePlus làm sạch động cơ vượt trội.", "https://i.pinimg.com/1200x/73/50/af/7350afe2a407ab980ff386c605966ed4.jpg", "mua Shell Advance Ultra 10W40"));
                    productList.add(new Product("Fuchs Silkolene Pro 4", "Công nghệ Ester chống bốc hơi nhớt hiệu quả.", "https://i.pinimg.com/736x/41/6a/10/416a10e84b70abb90f0da8e3c75951b7.jpg", "mua Fuchs Silkolene Pro 4"));
                    productList.add(new Product("Castrol Power1 Ultimate", "Tăng tốc cực nhanh với công thức 5 trong 1.", "https://i.pinimg.com/1200x/8c/c9/e0/8cc9e0cf3c4db269f4bd8367e43f716e.jpg", "mua Castrol Power1 Ultimate"));
                    break;

                case "COOLANT":
                    tvTitle.setText("TOP 10 NƯỚC LÀM MÁT");
                    productList.add(new Product("Engine Ice", "Tản nhiệt siêu nhanh, an toàn cho lốc máy.","https://i.pinimg.com/1200x/c4/41/0f/c4410f2c825d8496fb319c79a5e7201e.jpg", "mua nước mát Engine Ice"));
                    productList.add(new Product("Motul Motocool Factory", "Dung dịch OAT hữu cơ, chống ăn mòn cực tốt.", "https://i.pinimg.com/736x/3b/b4/73/3bb473bded4283ff8356ca30ac3eac0c.jpg", "mua nước mát Motul Motocool"));
                    productList.add(new Product("Liqui Moly RAF 12+", "Nước mát nhập Đức màu đỏ, độ bền cao.", "https://i.pinimg.com/1200x/35/a7/81/35a781f089111e2da466123721007df5.jpg", "mua nước mát Liqui Moly đỏ"));
                    productList.add(new Product("Voltronic R31", "Sản phẩm cao cấp từ Đức tản nhiệt thông minh.", "https://i.pinimg.com/1200x/b3/5d/62/b35d62e81d720d72768f2dada6f63d70.jpg", "mua nước mát Voltronic R31"));
                    productList.add(new Product("GoRacing Coolant", "Nước mát chuyên dụng cho xe độ, xe đua.", "https://nhotchinhhang.vn/images/thumbs/2025/09/nuoc-lam-mat-goracing-838-slide-1.jpg", "mua nước mát GoRacing"));
                    break;

                case "BRAKE":
                    tvTitle.setText("TOP 10 MÁ PHANH");
                    productList.add(new Product("Má phanh Brembo", "Công nghệ phanh đua, bám dính cực tốt.", R.drawable.brembo, "mua má phanh Brembo chính hãng"));
                    productList.add(new Product("Má phanh Elig", "Bố thắng Ceramic thân thiện môi trường.", R.drawable.abs, "mua má phanh Elig"));
                    productList.add(new Product("Má phanh Nissin", "Hàng chính hãng cho các dòng xe Nhật.", R.drawable.abs, "mua má phanh Nissin"));
                    productList.add(new Product("Má phanh Galfer", "Thiết kế rãnh xẻ thông minh chống bám bẩn.", R.drawable.brembo, "mua má phanh Galfer"));
                    productList.add(new Product("Má phanh EBC", "Vật liệu thiêu kết (Sintered) siêu bền.", R.drawable.abs, "mua má phanh EBC"));
                    productList.add(new Product("Má phanh Vesrah", "Phanh gốm Nhật Bản không kêu két.", R.drawable.brembo, "mua má phanh Vesrah"));
                    productList.add(new Product("Má phanh Frando", "Heo dầu và má phanh hiệu suất cao từ Đài Loan.", R.drawable.abs, "mua má phanh Frando"));
                    productList.add(new Product("Má phanh SBS", "Phanh đua Scandinavia, chịu nhiệt cực cao.", R.drawable.brembo, "mua má phanh SBS"));
                    productList.add(new Product("Má phanh RCB", "Thương hiệu Racing Boy quen thuộc của anh em.", R.drawable.abs, "mua má phanh RCB"));
                    productList.add(new Product("Má phanh Daytona", "Phụ kiện cao cấp nhập khẩu từ Nhật Bản.", R.drawable.brembo, "mua má phanh Daytona"));
                    break;

                case "BRAKE_FLUID":
                    tvTitle.setText("TOP 10 DẦU THẮNG");
                    productList.add(new Product("Liqui Moly DOT 4", "Điểm sôi cực cao, chống mất phanh khi đổ đèo.", R.drawable.oilpressure, "mua dầu thắng Liqui Moly DOT 4"));
                    productList.add(new Product("Motul DOT 4", "Dầu thắng tổng hợp 100% cho ABS.", R.drawable.oilpressure, "mua dầu thắng Motul DOT 4"));
                    productList.add(new Product("Brembo LCF 600+", "Dầu phanh chuyên dụng cho Racing.", R.drawable.oilpressure, "mua dầu thắng Brembo"));
                    productList.add(new Product("Bosch DOT 4", "Thương hiệu Đức uy tín, an toàn cho ống dẫn.", R.drawable.oilpressure, "mua dầu thắng Bosch"));
                    productList.add(new Product("Castrol React DOT 4", "Độ nhớt thấp, tương thích hoàn hảo với ABS.", R.drawable.oilpressure, "mua dầu phanh Castrol"));
                    productList.add(new Product("Repsol Moto DOT 4", "Chống oxy hóa và tạo bọt trong heo dầu.", R.drawable.oilpressure, "mua dầu thắng Repsol"));
                    productList.add(new Product("Maxima DOT 4", "Hiệu suất phanh ổn định ở nhiệt độ cao.", R.drawable.oilpressure, "mua dầu phanh Maxima"));
                    productList.add(new Product("Prestone DOT 4", "Sản phẩm Mỹ, kháng ẩm cực tốt.", R.drawable.oilpressure, "mua dầu thắng Prestone"));
                    productList.add(new Product("Honda DOT 4", "Dầu phanh chính hãng từ Honda.", R.drawable.oilpressure, "dầu phanh Honda DOT 4"));
                    productList.add(new Product("Yamalube DOT 4", "Sản xuất đặc biệt cho hệ thống phanh Yamaha.", R.drawable.oilpressure, "dầu phanh Yamalube"));
                    break;

                case "OIL_FILTER":
                    tvTitle.setText("TOP 10 LỌC NHỚT");
                    productList.add(new Product("Lọc nhớt K&N", "Màng lọc cường lực, tích hợp ốc tháo nhanh.", R.drawable.engine, "mua lọc nhớt K&N xe máy"));
                    productList.add(new Product("Lọc nhớt Hiflofiltro", "Thương hiệu Anh Quốc, lọc sạch bụi kim loại.", R.drawable.engine, "mua lọc nhớt Hiflofiltro"));
                    productList.add(new Product("Lọc nhớt Honda OEM", "Lọc giấy chính hãng, thay thế an toàn.", R.drawable.engine, "lọc nhớt Honda chính hãng"));
                    productList.add(new Product("Lọc nhớt Yamaha OEM", "Phụ tùng chính hãng cho các dòng Exciter/R15.", R.drawable.engine, "lọc nhớt Yamaha chính hãng"));
                    productList.add(new Product("Lọc nhớt BMC", "Thương hiệu Ý, tối ưu hóa lưu lượng dòng chảy.", R.drawable.engine, "mua lọc nhớt BMC"));
                    productList.add(new Product("Lọc nhớt Bosch", "Màng lọc đa lớp chống tắc nghẽn hiệu quả.", R.drawable.engine, "mua lọc nhớt Bosch"));
                    productList.add(new Product("Lọc nhớt WIX", "Khả năng bắt giữ cặn bẩn lên đến 99%.", R.drawable.engine, "mua lọc nhớt WIX"));
                    productList.add(new Product("Lọc nhớt Mahle", "Thương hiệu gốc Đức chất lượng cao.", R.drawable.engine, "mua lọc nhớt Mahle"));
                    productList.add(new Product("Lọc nhớt Fram", "Tráng lớp silicon chống khô ron cao su.", R.drawable.engine, "mua lọc nhớt Fram"));
                    productList.add(new Product("Lọc sắt nam châm", "Có lõi nam châm hút mạt sắt bảo vệ dên.", R.drawable.engine, "mua ốc từ hút mạt sắt xe máy"));
                    break;

                case "AIR_FILTER":
                    tvTitle.setText("TOP 10 LỌC GIÓ");
                    productList.add(new Product("Lọc gió K&N", "Màng lọc bông tẩm dầu, rửa và dùng lại trọn đời.", R.drawable.airfilter, "mua lọc gió K&N xe máy"));
                    productList.add(new Product("Lọc gió BMC", "Công nghệ đường đua, nạp gió cực bốc.", R.drawable.airfilter, "mua lọc gió BMC chính hãng"));
                    productList.add(new Product("Lọc gió DNA", "Thiết kế Full Contour siêu thoáng khí.", R.drawable.airfilter, "mua lọc gió DNA"));
                    productList.add(new Product("Lọc gió Sprint Filter", "Màng lưới polyester không cần tẩm dầu.", R.drawable.airfilter, "mua lọc gió Sprint Filter"));
                    productList.add(new Product("Lọc gió Honda OEM", "Lọc giấy tẩm dầu tiêu chuẩn Honda.", R.drawable.airfilter, "lọc gió zin Honda"));
                    productList.add(new Product("Lọc gió Yamaha OEM", "Lọc gió chính hãng không làm hụt ga.", R.drawable.airfilter, "lọc gió zin Yamaha"));
                    productList.add(new Product("Lọc gió Koso", "Lọc mút bọt biển chuyên dùng cho xe độ máy.", R.drawable.airfilter, "mua lọc gió Koso"));
                    productList.add(new Product("Lọc gió UNI Filter", "Thiết kế 2 lớp mút giữ bụi siêu sạch.", R.drawable.airfilter, "mua lọc gió UNI"));
                    productList.add(new Product("Lọc gió Ferrox", "Lọc gió lưới thép không gỉ, độ bền siêu việt.", R.drawable.airfilter, "mua lọc gió Ferrox"));
                    productList.add(new Product("Lọc gió Hurric", "Hiệu suất hút khí cao, tối ưu công suất cực đại.", R.drawable.airfilter, "mua lọc gió Hurric"));
                    break;

                default:
                    tvTitle.setText("DANH MỤC SẢN PHẨM");
                    break;
            }
        }

        ProductAdapter adapter = new ProductAdapter(this, productList);
        rvProducts.setAdapter(adapter);
    }
}