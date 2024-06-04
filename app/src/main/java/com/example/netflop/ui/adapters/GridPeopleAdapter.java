package com.example.netflop.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Person;
import com.example.netflop.utils.listeners.ItemTouchHelperAdapter;

import java.util.List;

public class GridPeopleAdapter extends RecyclerView.Adapter<GridPeopleAdapter.ViewHolder>  implements Filterable {
    List<Person> listPeople;
    Context context;
    ItemTouchHelperAdapter itemTouchHelperAdapter;

    public GridPeopleAdapter(List<Person> listPeople, Context context, ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.listPeople = listPeople;
        this.context = context;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.a_grid_person_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person= listPeople.get(position);
        holder.personNameView.setText(person.getName());
        String imageURL= URLConstants.imageURL+person.getProfilePath();
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchHelperAdapter.onPersonClick(person);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView personNameView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.aGridPersonCardView);
            personNameView=(TextView) itemView.findViewById(R.id.personGridNameTextView);
            imageView=(ImageView) itemView.findViewById(R.id.profileGridPersonView);
        }
    }
}
