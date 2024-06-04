package com.example.netflop.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.SearchMultiModel;
import com.example.netflop.utils.listeners.SearchItemOnClickListener;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<SearchMultiModel> listSearch;
    Context context;
    SearchItemOnClickListener searchItemOnClickListener;

    public SearchAdapter(List<SearchMultiModel> listSearch, Context context, SearchItemOnClickListener searchItemOnClickListener) {
        this.listSearch = listSearch;
        this.context = context;
        this.searchItemOnClickListener = searchItemOnClickListener;
    }
    public void clearData() {
        if (listSearch != null) {
            listSearch.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_search_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchMultiModel searchMultiModel= listSearch.get(position);
        holder.typeTV.setText(searchMultiModel.getMediaType());
        String imageURL= URLConstants.imageURL;
        if(searchMultiModel.getMediaType()=="movie"){
            if(searchMultiModel.getTitle()!=null&&searchMultiModel.getTitle()!=""){
                holder.nameTV.setText(searchMultiModel.getTitle());
            }else{
                holder.nameTV.setText(searchMultiModel.getOriginalTitle());
            }
            imageURL+=searchMultiModel.getPosterPath();
        }else if(searchMultiModel.getMediaType()=="person"){
            if(searchMultiModel.getName()!=null&&searchMultiModel.getName()!=""){
                holder.nameTV.setText(searchMultiModel.getName());
            }else{
                holder.nameTV.setText(searchMultiModel.getOriginalName());
            }
            imageURL+=searchMultiModel.getProfilePath();
        }else{
            holder.nameTV.setText(searchMultiModel.getName());
            imageURL+=searchMultiModel.getPosterPath();
        }
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              searchItemOnClickListener.onClick(searchMultiModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        CardView cardView;
        TextView nameTV,typeTV;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.search_card_view);
            nameTV=(TextView) itemView.findViewById(R.id.nameSearchView);
            typeTV=(TextView) itemView.findViewById(R.id.mediaSearchTypeView);
            imageView=(ImageView) itemView.findViewById(R.id.searchImageView);
        }
    }
}
