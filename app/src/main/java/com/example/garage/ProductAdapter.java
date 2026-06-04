package com.example.garage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp file giao diện item_product.xml cho từng thẻ
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvName.setText(product.getName());
        holder.tvDesc.setText(product.getDescription());

        // ==========================================
        // SỬ DỤNG GLIDE ĐỂ TẢI ẢNH TỪ MẠNG
        // ==========================================
        Glide.with(context)
                .load(product.getImageUrl()) // Link ảnh từ Model
                .into(holder.imgProduct); // Vào ImageView

        // ==========================================

        // Xử lý sự kiện bấm nút (giữ nguyên)
        holder.btnSearch.setOnClickListener(v -> {
            // ... (code cũ) ...
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvDesc;
        MaterialButton btnSearch;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID từ file item_product.xml
            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvDesc = itemView.findViewById(R.id.tv_product_desc);
            btnSearch = itemView.findViewById(R.id.btn_search_product);
        }
    }
}