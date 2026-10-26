package com.huozige.lab.container.offlineform;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormSearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 搜索结果列表适配器：按字段展示命中的表单项，点击后由页面定位并高亮该字段。
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    public interface OnResultClickListener {
        void onResultClick(int position);
    }

    private final List<OfflineFormSearchResult> items = new ArrayList<>();
    private String keyword = "";
    private final OnResultClickListener clickListener;

    public SearchResultAdapter(OnResultClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setResults(List<OfflineFormSearchResult> results, String keyword) {
        items.clear();
        if (results != null) {
            items.addAll(results);
        }
        this.keyword = keyword == null ? "" : keyword;
        notifyDataSetChanged();
    }

    public int getResultCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_form_search_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfflineFormSearchResult result = items.get(position);
        holder.title.setText(buildHighlightedTitle(holder.title.getContext(), result.getTitle()));
        holder.required.setVisibility(result.isRequired() ? View.VISIBLE : View.GONE);
        bindResultType(holder.type, result.getType());
        holder.sub.setText(buildSubText(result));
        boolean hasPage = result.getPageLabel() != null && !result.getPageLabel().isEmpty();
        holder.page.setText(result.getPageLabel());
        holder.page.setVisibility(hasPage ? View.VISIBLE : View.GONE);
        holder.chevron.setVisibility(View.VISIBLE);
        holder.divider.setVisibility(position == items.size() - 1 ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(v -> {
            int clickedPosition = holder.getBindingAdapterPosition();
            if (clickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                clickListener.onResultClick(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void bindResultType(TextView typeView, String type) {
        if (OfflineFormSearchResult.TYPE_GROUP.equals(type)) {
            typeView.setText(R.string.offline_search_result_type_group);
            typeView.setVisibility(View.VISIBLE);
        } else if (OfflineFormSearchResult.TYPE_TEXT.equals(type)) {
            typeView.setText(R.string.offline_search_result_type_text);
            typeView.setVisibility(View.VISIBLE);
        } else {
            typeView.setVisibility(View.GONE);
        }
    }

    private CharSequence buildHighlightedTitle(android.content.Context context, String title) {
        String safeTitle = title == null ? "" : title;
        if (keyword.isEmpty()) {
            return safeTitle;
        }
        int start = safeTitle.toLowerCase(Locale.CHINA).indexOf(keyword.toLowerCase(Locale.CHINA));
        if (start < 0) {
            return safeTitle;
        }
        SpannableString spannable = new SpannableString(safeTitle);
        int end = Math.min(safeTitle.length(), start + keyword.length());
        spannable.setSpan(new ForegroundColorSpan(context.getColor(R.color.huozige_blue)),
                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private String buildSubText(OfflineFormSearchResult result) {
        String path = result.getPath() == null ? "" : result.getPath();
        String value = result.getValueText() == null ? "" : result.getValueText();
        if (path.isEmpty() && value.isEmpty()) {
            return "";
        }
        if (path.isEmpty()) {
            return value;
        }
        if (value.isEmpty()) {
            return path;
        }
        return path + " · " + value;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView required;
        final TextView type;
        final TextView sub;
        final TextView page;
        final ImageView chevron;
        final View divider;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_search_result_title);
            required = itemView.findViewById(R.id.text_search_result_required);
            type = itemView.findViewById(R.id.text_search_result_type);
            sub = itemView.findViewById(R.id.text_search_result_sub);
            page = itemView.findViewById(R.id.text_search_result_page);
            chevron = itemView.findViewById(R.id.image_search_result_chevron);
            divider = itemView.findViewById(R.id.divider_search_result_line);
        }
    }
}
