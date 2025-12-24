package com.huozige.lab.container.proxy.support.offlinecustomform;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.CustomFormActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflinePlusListCardItem;

import java.util.List;

public class OfflinePlusCardAdapter extends RecyclerView.Adapter<OfflinePlusCardAdapter.ViewHolder> {
    private List<OfflinePlusListCardItem> cardItems;
    private Context context;

    public OfflinePlusCardAdapter(List<OfflinePlusListCardItem> cardItems, Context context) {
        this.cardItems = cardItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfflinePlusListCardItem item = cardItems.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.dateTextView.setText(item.getStatus());


        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CustomFormActivity.class);
            intent.putExtra("id", item.getPatternId());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("date", item.getStatus());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public void updateData(List<OfflinePlusListCardItem> newItems) {
        cardItems = newItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
