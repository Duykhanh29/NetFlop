package com.example.netflop.ui.adapters.remote;

import android.content.Context;
import android.util.Log;
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
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.remote.TVs.CreatedBy;
import com.example.netflop.utils.listeners.OnClickIDListener;

import java.util.List;

public class ListCreatedByAdapter extends RecyclerView.Adapter<ListCreatedByAdapter.ViewHolder> {
    List<CreatedBy> listData;
    Context context;
    OnClickIDListener itemTouchHelperAdapter;

    public ListCreatedByAdapter(List<CreatedBy> listData, Context context,OnClickIDListener itemTouchHelperAdapter) {
        this.listData = listData;
        this.context = context;
        this.itemTouchHelperAdapter=itemTouchHelperAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_created_by_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreatedBy createdBy=listData.get(position);
        holder.nameTextView.setText(createdBy.getName()!=null ? createdBy.getName() :"null");

        String imageURL= URLConstants.imageURL+createdBy.getProfilePath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 itemTouchHelperAdapter.onCLick(createdBy.getId(), StringConstants.personType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.createdByNameTextView) ;
            imageView=(ImageView)  itemView.findViewById(R.id.profileCreatedByView) ;
            cardView=(CardView)  itemView.findViewById(R.id.created_by_card_view) ;
        }
    }
}
