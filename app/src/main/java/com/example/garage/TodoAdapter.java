package com.example.garage;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<TodoItem> todoList;
    private OnTodoActionListener listener;

    public interface OnTodoActionListener {
        void onStatusChanged(int id, boolean isChecked);
        void onDeleteRequested(int id);
    }

    public TodoAdapter(List<TodoItem> todoList, OnTodoActionListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem item = todoList.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText("Ngày lập: " + item.getDate());

        // Hủy bỏ sự kiện check cũ trước khi gán trạng thái tránh lỗi tái sử dụng hàng cuộn
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.getIsCompleted() == 1);

        // Hiệu ứng gạch ngang chữ nếu công việc đã hoàn thành
        if (item.getIsCompleted() == 1) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTitle.setTextColor(0xFF888888);
        } else {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTitle.setTextColor(0xFFFFFFFF);
        }

        // Bắt sự kiện Checkbox thay đổi trạng thái
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onStatusChanged(item.getId(), isChecked);
        });

        // Bắt sự kiện nhấn nút Xóa công việc
        holder.btnDelete.setOnClickListener(v -> {
            listener.onDeleteRequested(item.getId());
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvTitle, tvDate;
        ImageView btnDelete;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_todo_status);
            tvTitle = itemView.findViewById(R.id.tv_todo_title);
            tvDate = itemView.findViewById(R.id.tv_todo_date);
            btnDelete = itemView.findViewById(R.id.btn_delete_todo);
        }
    }
}