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
                    tvTitle.setText("TOP 5 MÁ PHANH");
                    productList.add(new Product("Má phanh Brembo", "Công nghệ phanh đua, bám dính cực tốt.","https://i.pinimg.com/1200x/87/c8/13/87c81399ebb0a7fe48307d80370183cb.jpg", "mua má phanh Brembo chính hãng"));
                    productList.add(new Product("Má phanh Elig", "Bố thắng Ceramic thân thiện môi trường.", "https://i.pinimg.com/1200x/e9/f3/5d/e9f35d629f73fb2741fbd371a3be0a53.jpg", "mua má phanh Elig"));
                    productList.add(new Product("Má phanh Nissin", "Hàng chính hãng cho các dòng xe Nhật.", "https://i.pinimg.com/1200x/9a/93/e2/9a93e29ddaee018642c39fe602ea5b7e.jpg", "mua má phanh Nissin"));
                    productList.add(new Product("Má phanh Galfer", "Thiết kế rãnh xẻ thông minh chống bám bẩn.", "https://i.pinimg.com/1200x/d2/85/19/d285197743d2a615ebc224a1e8a2167b.jpg", "mua má phanh Galfer"));
                    productList.add(new Product("Má phanh EBC", "Vật liệu thiêu kết (Sintered) siêu bền.", "https://i.pinimg.com/1200x/a2/91/a7/a291a7756a1bc99add30842fce3b3fb8.jpg", "mua má phanh EBC"));
                    break;

                case "BRAKE_FLUID":
                    tvTitle.setText("TOP 5 DẦU THẮNG");
                    productList.add(new Product("Liqui Moly DOT 4", "Điểm sôi cực cao, chống mất phanh khi đổ đèo.", "https://i.pinimg.com/1200x/90/95/e5/9095e5840f7a10141b234ccc827f1f47.jpg", "mua dầu thắng Liqui Moly DOT 4"));
                    productList.add(new Product("Motul DOT 4", "Dầu thắng tổng hợp 100% cho ABS.", "https://i.pinimg.com/736x/37/80/0f/37800fe56cf9c993b2df67eaf0fa530a.jpg", "mua dầu thắng Motul DOT 4"));
                    productList.add(new Product("Brembo LCF 600+", "Dầu phanh chuyên dụng cho Racing.", "https://i.pinimg.com/1200x/27/c8/fe/27c8fe690e2b55b7bb5a70b1ef2df38b.jpg", "mua dầu thắng Brembo"));
                    productList.add(new Product("Bosch DOT 4", "Thương hiệu Đức uy tín, an toàn cho ống dẫn.", "https://i.pinimg.com/1200x/c8/e2/fd/c8e2fda60332125e52c5cb4565f47659.jpg", "mua dầu thắng Bosch"));
                    productList.add(new Product("Castrol React DOT 4", "Độ nhớt thấp, tương thích hoàn hảo với ABS.", "https://i.pinimg.com/1200x/41/10/03/41100335e6d2b535a833e77d64493433.jpg", "mua dầu phanh Castrol"));
                    break;

                case "OIL_FILTER":
                    tvTitle.setText("TOP 5 LỌC NHỚT");
                    productList.add(new Product("Lọc nhớt K&N", "Màng lọc cường lực, tích hợp ốc tháo nhanh.", "https://i.pinimg.com/1200x/83/aa/14/83aa14de5cdd2387817a4f24462ff41c.jpg", "mua lọc nhớt K&N xe máy"));
                    productList.add(new Product("Lọc nhớt Hiflofiltro", "Thương hiệu Anh Quốc, lọc sạch bụi kim loại.", "https://i.pinimg.com/1200x/ac/ee/17/acee172b8c108d6134d129f6bd05df77.jpg", "mua lọc nhớt Hiflofiltro"));
                    productList.add(new Product("Lọc nhớt Honda OEM", "Lọc giấy chính hãng, thay thế an toàn.","https://i.pinimg.com/1200x/38/8c/34/388c34b1a8ff2ffca8426490b659b23a.jpg", "lọc nhớt Honda chính hãng"));
                    productList.add(new Product("Lọc nhớt Yamaha OEM", "Phụ tùng chính hãng cho các dòng Exciter/R15.", "https://i.pinimg.com/736x/09/85/48/0985486fcc26b1cf02286b1ae1121e3f.jpg", "lọc nhớt Yamaha chính hãng"));
                    productList.add(new Product("Lọc nhớt BMC", "Thương hiệu Ý, tối ưu hóa lưu lượng dòng chảy.","https://i.pinimg.com/1200x/de/2a/3e/de2a3efc6a644e0e36c18558f660948d.jpg", "mua lọc nhớt BMC"));
                    break;

                case "AIR_FILTER":
                    tvTitle.setText("TOP 5 LỌC GIÓ");
                    productList.add(new Product("Lọc gió K&N", "Màng lọc bông tẩm dầu, rửa và dùng lại trọn đời.", "https://i.pinimg.com/736x/ad/96/21/ad9621ed4c89e38d6b1625c745f0aa7b.jpg", "mua lọc gió K&N xe máy"));
                    productList.add(new Product("Lọc gió BMC", "Công nghệ đường đua, nạp gió cực bốc.","https://i.pinimg.com/736x/cd/ff/98/cdff985e007a55b1d59d858afbaaefff.jpg", "mua lọc gió BMC chính hãng"));
                    productList.add(new Product("Lọc gió DNA", "Thiết kế Full Contour siêu thoáng khí.", "https://i.pinimg.com/736x/6f/ab/53/6fab536c412244610c0086dcde9af1db.jpg", "mua lọc gió DNA"));
                    productList.add(new Product("Lọc gió Honda OEM", "Lọc giấy tẩm dầu tiêu chuẩn Honda.", "https://i.pinimg.com/1200x/25/a6/6a/25a66a451a9a26fcbe9c3b7cff9308fd.jpg", "lọc gió zin Honda"));
                    productList.add(new Product("Lọc gió Yamaha OEM", "Lọc gió chính hãng không làm hụt ga.", "https://i.pinimg.com/1200x/84/7d/ce/847dceaf1e4e0ec24ce974ae085ef747.jpg", "lọc gió zin Yamaha"));

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