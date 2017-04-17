package com.example.mannas.ntl_movieapp.Activity_Detail;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.content.MovieReviews;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/12/2017.
 */
class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>{
    MovieReviews reviews;
    Integer max;
    public ReviewsAdapter(MovieReviews reviews, Integer maxViews){
        max = maxViews;
        this.reviews = new MovieReviews();
        this.reviews.id = reviews.id;
        this.reviews.page = reviews.page;
        this.reviews.total_pages = reviews.total_pages;
        this.reviews.total_results = reviews.total_results;
        this.reviews.results = new ArrayList<>();

        int s=0;
        if(maxViews>0)
            s = Math.min(maxViews,reviews.results.size());
        else
            s = reviews.results.size();

        for(int i=0 ;i<s; i++){
            this.reviews.results.add(reviews.results.get(i));
        }
    }
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row,parent,false);
        return new ReviewHolder(view);
    }
    @Override
    public void onBindViewHolder(ReviewHolder holder, final int position) {
        holder.author.setText(reviews.results.get(position).author);
        holder.author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(reviews.results.get(position).url));
                 view.getContext().startActivity(i);
            }
        });
        holder.content.setText(reviews.results.get(position).content);
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title(reviews.results.get(position).author)
                        .content(reviews.results.get(position).content)
                        .positiveText(R.string.dismiss)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    public void changeDataSet(MovieReviews r){
        this.reviews.results = new ArrayList<>();

        int s=0;
        if(max>0)
            s = Math.min(max,r.results.size());
        else
            s = r.results.size();

        for(int i=0 ;i<s; i++){
            reviews.results.add(r.results.get(i));
        }
        super.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return reviews.results.size();
    }
    class ReviewHolder extends RecyclerView.ViewHolder{
        public TextView author,content;
        public ReviewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
