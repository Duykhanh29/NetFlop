package com.example.netflop.ui.adapters.search_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflop.R;
import com.example.netflop.data.models.local.SearchHistory;
import com.example.netflop.utils.listeners.OnSearchHistoryListener;
import com.example.netflop.viewmodel.SearchHistoryViewModel;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
    Context context;
    List<SearchHistory> listSearch;
    OnSearchHistoryListener onSearchHistoryListener;
    SearchHistoryViewModel searchHistoryViewModel;

    public SearchHistoryAdapter(Context context, List<SearchHistory> listSearch, OnSearchHistoryListener onSearchHistoryListener, SearchHistoryViewModel searchHistoryViewModel) {
        this.context = context;
        this.listSearch = listSearch;
        this.onSearchHistoryListener = onSearchHistoryListener;
        this.searchHistoryViewModel=searchHistoryViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_search_history_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchHistory searchHistory=listSearch.get(position);
        int index=position;
        holder.searchKeyTV.setText(searchHistory.getSearchKey());
        holder.isAdultTV.setText(searchHistory.isAdult()==1?"Only adult":"Any age");
        holder.typeTV.setText("Type: "+searchHistory.getSearchType().name());
        holder.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchHistoryViewModel.deleteSearchHistory(searchHistory.getId());
                listSearch.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, listSearch.size());
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchHistoryListener.onClick(searchHistory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView searchKeyTV,typeTV,isAdultTV;
        ImageButton clearButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=(LinearLayout) itemView.findViewById(R.id.single_search_history);
            searchKeyTV=(TextView) itemView.findViewById(R.id.searchKeyHistoryView);
            typeTV=(TextView) itemView.findViewById(R.id.typeSearchHistoryView);
            isAdultTV=(TextView) itemView.findViewById(R.id.isAdultSearchHistoryView);
            clearButton=(ImageButton) itemView.findViewById(R.id.clearSearchHistoryView);
        }
    }
}
