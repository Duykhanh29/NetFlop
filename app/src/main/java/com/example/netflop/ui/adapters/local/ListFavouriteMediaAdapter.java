package com.example.netflop.ui.adapters.local;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.local.SearchHistory;
import com.example.netflop.utils.listeners.FavouriteListener;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListFavouriteMediaAdapter extends RecyclerView.Adapter<ListFavouriteMediaAdapter.ViewHolder> {
    List<FavouriteMedia> listFavourite;
    Context context;
    FavouriteListener favouriteListener;
    FavouriteMediaViewModel favouriteMediaViewModel;
    private List<FavouriteMedia> originalListFavourite;

    public ListFavouriteMediaAdapter(List<FavouriteMedia> listFavourite, Context context, FavouriteListener favouriteListener,FavouriteMediaViewModel favouriteMediaViewModel) {
        this.listFavourite = listFavourite;
        this.context = context;
        this.favouriteListener = favouriteListener;
        this.favouriteMediaViewModel=favouriteMediaViewModel;
        this.originalListFavourite = new ArrayList<>(listFavourite);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_favourite_card_view,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteMedia favouriteMedia=listFavourite.get(position);
        holder.titleTV.setText(favouriteMedia.getTitle());
        holder.typeTV.setText(favouriteMedia.getTypeOfMedia().name());
        if(favouriteMedia.getTypeOfMedia()== TypeOfMedia.TVSeason){
            holder.tvDetailTV.setVisibility(View.VISIBLE);
            holder.tvDetailTV.setText("Sea: "+favouriteMedia.getSeasonNumber());
        }else if(favouriteMedia.getTypeOfMedia()== TypeOfMedia.TVEpisode){
            holder.tvDetailTV.setVisibility(View.VISIBLE);
            holder.tvDetailTV.setText("Sea: "+favouriteMedia.getSeasonNumber()+", Epi: "+favouriteMedia.getEpisodeNumber());
        }
        String imageURL= URLConstants.imageURL+favouriteMedia.getUrlImage();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.place_holder).into(holder.imageView);
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               favouriteListener.onClick(favouriteMedia);
           }
       });
       holder.removeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
               // Setting Alert Dialog Title
               alertDialogBuilder.setTitle("Alert");
               // Icon Of Alert Dialog
//               alertDialogBuilder.setIcon(R.drawable.question);
               // Setting Alert Dialog Message
               alertDialogBuilder.setMessage("Are you sure you want to delete this item?");
               alertDialogBuilder.setCancelable(false);

               alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                   @Override
                   public void onClick(DialogInterface arg0, int arg1) {
//                       finish();
                       favouriteMediaViewModel.deleteFavouriteMedia(favouriteMedia.getId());
                       arg0.dismiss();
                   }
               });

               alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
//                       Toast.makeText(view.getRootView().getContext(),"You clicked over No",Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
               });


               AlertDialog alertDialog = alertDialogBuilder.create();
               alertDialog.show();


           }
       });
       if(favouriteMedia.getTypeOfMedia()!=TypeOfMedia.person){
           holder.editWatchStatusButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onShowDialog(view.getRootView().getContext(),favouriteMedia);

               }

           });
       }else{
           holder.editWatchStatusButton.setVisibility(View.GONE);
       }

    }
    private void onShowDialog(Context context,FavouriteMedia favouriteMedia){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Watch Status");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dropdown_layout, null);
        Spinner spinner = dialogView.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.dropdown_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set the initial value of the Spinner based on the item's watchStatus
        String[] options = context.getResources().getStringArray(R.array.dropdown_options);
        String watchStatusName = favouriteMedia.getWatchStatus().name().replace("_", " ").toLowerCase();

        int initialPosition = -1;
        for (int i = 0; i < options.length; i++) {
            if (watchStatusName.equals(options[i].toLowerCase())) {
                initialPosition = i;
                break;
            }
        }
        if(initialPosition!=-1){
            spinner.setSelection(initialPosition);
            builder.setView(dialogView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String selectedOption = spinner.getSelectedItem().toString();
                    WatchStatus watchStatus=WatchStatus.valueOf(selectedOption.replace(" ", "_").toUpperCase());
                    favouriteMediaViewModel.updateWatchStatus(favouriteMedia.getId(),watchStatus);
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public int getItemCount() {
        return listFavourite.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTV,typeTV,tvDetailTV;
        ImageButton removeButton,editWatchStatusButton;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.single_favourite_card_view);
            imageView=(ImageView) itemView.findViewById(R.id.imageFavouriteView);
            titleTV=(TextView) itemView.findViewById(R.id.titleFavouriteMediaView);
            typeTV=(TextView) itemView.findViewById(R.id.typeOfMediaFavouriteView);
            tvDetailTV=(TextView) itemView.findViewById(R.id.tvDetailFavouriteView);
            removeButton=(ImageButton) itemView.findViewById(R.id.removeFavourite);
            editWatchStatusButton=(ImageButton) itemView.findViewById(R.id.editWatchStatusButton);
        }
    }
    public void filterItems(WatchStatus status) {
        listFavourite.clear(); // Xóa danh sách hiện tại
        List<FavouriteMedia> filteredList=new ArrayList<>();
        if (status == null) {
            // Hiển thị tất cả các mục khi status là null
            filteredList.addAll(originalListFavourite);
        } else {
            // Lọc danh sách theo WatchStatus
            for (FavouriteMedia media : originalListFavourite) {
                if (media.getWatchStatus() == status) {
                    filteredList.add(media);
                }
            }
        }
        listFavourite.addAll((ArrayList)filteredList);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }
    public void updateList(List<FavouriteMedia> list){
        listFavourite.clear();
        listFavourite.addAll(list);
        originalListFavourite.clear();
        originalListFavourite.addAll(listFavourite);
        Log.d("NUMBER IS","NUMBER: "+list.size());
        notifyDataSetChanged();
    }

}
