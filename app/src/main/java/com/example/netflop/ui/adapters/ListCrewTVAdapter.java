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
import com.example.netflop.data.models.TVs.CrewTV;
import com.example.netflop.data.models.TVs.GuestStar;
import com.example.netflop.utils.OnClickIDListener;

import java.util.List;

public class ListCrewTVAdapter extends RecyclerView.Adapter<ListCrewTVAdapter.ViewHolder> {
    List<CrewTV> listCrew;
    OnClickIDListener itemTouchHelperAdapter;
    Context context;

    public ListCrewTVAdapter(List<CrewTV> listCrew, OnClickIDListener itemTouchHelperAdapter, Context context) {
        this.listCrew = listCrew;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_crew_tv_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CrewTV crewTV=listCrew.get(position);
        holder.nameTextView.setText(crewTV.getName()!=null ? crewTV.getName() :crewTV.getOriginalName()!=null?crewTV.getOriginalName():"null");
        String imageURL= URLConstants.imageURL+crewTV.getProfilePath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.departmentTextView.setText(crewTV.getDepartment()!=null?crewTV.getDepartment():"null");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onCLick(crewTV.getId(), StringConstants.personType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCrew.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView nameTextView,departmentTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.crewTVNameTextView) ;
            imageView=(ImageView)  itemView.findViewById(R.id.crewTVProfileView) ;
            cardView=(CardView)  itemView.findViewById(R.id.singleCrewTVCardView) ;
            departmentTextView=(TextView)itemView.findViewById(R.id.crewTVDepartmentTextView) ;
        }
    }
}
