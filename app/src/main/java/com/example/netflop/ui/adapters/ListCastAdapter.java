package com.example.netflop.ui.adapters;

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
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Cast;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;

import java.util.List;

public class ListCastAdapter extends RecyclerView.Adapter<ListCastAdapter.ViewHolder> {
    Context context;
    List<Cast> listCast;
    ItemTouchHelperAdapter itemTouchHelperAdapter;

    public ListCastAdapter(Context context, List<Cast> listCast, ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.context = context;
        this.listCast = listCast;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cast_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast=listCast.get(position);
        if(cast.getCharacter()!=null){
            holder.castName.setText(cast.getCharacter());
        }else{
            holder.castName.setText(cast.getName());
        }
        if(cast.getCharacter()!=null){
            holder.secondCastTitle.setText(cast.getName());
        }else{
            holder.secondCastTitle.setText(cast.getJob());
        }
        String imageURL= URLConstants.imageURL+cast.getProfilePath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.castImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onCastClick(cast);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCast.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView castImage;

        TextView castName,secondCastTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.cast_view);
            castImage=(ImageView) itemView.findViewById(R.id.cast_avatar_image);
            castName=(TextView) itemView.findViewById(R.id.cast_name);
            secondCastTitle=(TextView) itemView.findViewById(R.id.second_cast_name);
        }
    }

}
