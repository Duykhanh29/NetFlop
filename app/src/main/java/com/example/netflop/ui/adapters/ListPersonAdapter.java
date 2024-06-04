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

public class ListPersonAdapter extends RecyclerView.Adapter<ListPersonAdapter.ViewHolder> implements Filterable {
    List<Person> listPeople;
    Context context;
    int layout;
    ItemTouchHelperAdapter itemTouchHelperAdapter;

    public ListPersonAdapter(List<Person> listPeople, Context context, int layout,ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.listPeople = listPeople;
        this.context = context;
        this.layout = layout;
        this.itemTouchHelperAdapter=itemTouchHelperAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        return new ListPersonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person= listPeople.get(position);
        holder.namePersonTextView.setText(person.getName());
        String imageURL= URLConstants.imageURL+person.getProfilePath();
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.profileView);
        holder.personCardView.setOnClickListener(new View.OnClickListener() {
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView namePersonTextView;
        CardView personCardView;
        ImageView profileView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileView=(ImageView) itemView.findViewById(R.id.profilePersonView);
            personCardView=(CardView) itemView.findViewById(R.id.person_card_view);
            namePersonTextView=(TextView) itemView.findViewById(R.id.personNameTextView);
        }
    }
    @Override
    public Filter getFilter() {
        return null;
    }
}
