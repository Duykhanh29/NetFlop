package com.example.netflop.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.Review;
import com.example.netflop.utils.DateHelpers;
import com.webtoonscorp.android.readmore.ReadMoreTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListReviewAdapter extends RecyclerView.Adapter<ListReviewAdapter.ViewHolder> {
    List<Review> listReview;
    Context context;

    public ListReviewAdapter(List<Review> listReview, Context context) {
        this.listReview = listReview;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_review_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review=listReview.get(position);
        Log.d("Datetime",review.getCreatedAt());
        String createdAt= DateHelpers.convertIsoToReadableDateTime(review.getCreatedAt());
        holder.createdAtTextView.setText("Created at: "+createdAt);
        if(review.getUpdatedAt()!=null){
            String updatedAt= DateHelpers.convertIsoToReadableDateTime(review.getUpdatedAt());
            holder.updatedAtTextView.setText("Updated at: "+updatedAt);
        }else{
            holder.updatedAtTextView.setVisibility(View.GONE);
        }
        holder.contentTV.setText(review.getContent());
        holder.userNameTV.setText(review.getAuthorDetails()!=null ? review.getAuthorDetails().getUsername():review.getAuthor());
        holder.ratingTV.setText(review.getAuthorDetails()!=null ? review.getAuthorDetails().getRating()+"" :"null");
        String imageURL= URLConstants.imageURL+review.getAuthorDetails().getAvatarPath();
        Log.d("TAG","URL is"+imageURL);
        Glide.with(context).load(imageURL).placeholder(R.drawable.circel_avatar).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView userNameTV,ratingTV,updatedAtTextView,createdAtTextView;
        ReadMoreTextView contentTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=(CircleImageView)itemView.findViewById(R.id.authorAvatarView);
            userNameTV=(TextView) itemView.findViewById(R.id.authorUserNameView);
            ratingTV=(TextView) itemView.findViewById(R.id.reviewRatingView);
            updatedAtTextView=(TextView) itemView.findViewById(R.id.updatedAtView);
            createdAtTextView=(TextView) itemView.findViewById(R.id.createdAtView);
            contentTV=(ReadMoreTextView) itemView.findViewById(R.id.reviewContentView);
        }
    }
}
