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
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.TVs.GuestStar;
import com.example.netflop.utils.listeners.OnClickIDListener;

import java.util.List;

public class ListGuestStarAdapter extends RecyclerView.Adapter<ListGuestStarAdapter.ViewHolder> {
    List<GuestStar> listGuestStar;
    OnClickIDListener itemTouchHelperAdapter;
    Context context;

    public ListGuestStarAdapter(List<GuestStar> listGuestStar,OnClickIDListener itemTouchHelperAdapter, Context context) {
        this.listGuestStar = listGuestStar;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_guest_star_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuestStar guestStar=listGuestStar.get(position);
        holder.nameTextView.setText(guestStar.getName()!=null ? guestStar.getName() :guestStar.getOriginalName()!=null?guestStar.getOriginalName():"null");

        String imageURL= URLConstants.imageURL+guestStar.getProfilePath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.characterNameTextView.setText(guestStar.getCharacter()!=null?guestStar.getCharacter():"null");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onCLick(guestStar.getId(), StringConstants.personType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGuestStar.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameTextView,characterNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.guestStarNameTextView) ;
            imageView=(ImageView)  itemView.findViewById(R.id.guestStarProfileView) ;
            cardView=(CardView)  itemView.findViewById(R.id.singleGuestStarCardView) ;
            characterNameTextView=(TextView)itemView.findViewById(R.id.guestStarCharacterTextView) ;
        }
    }
}
