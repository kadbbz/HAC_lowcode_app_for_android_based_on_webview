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
    private List<OfflinePlusListCardItem> _cardItems;
    private Context _context;

    public OfflinePlusCardAdapter(List<OfflinePlusListCardItem> cardItems, Context context) {
        this._cardItems = cardItems;
        _context = context;
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
        OfflinePlusListCardItem item = _cardItems.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.dateTextView.setText(item.getStatus());


        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(_context, CustomFormActivity.class);
            intent.putExtra("patternId", item.getPatternId());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("status", item.getStatus());
            _context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return _cardItems.size();
    }

    public void updateData(List<OfflinePlusListCardItem> newItems) {
        _cardItems = newItems;
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
