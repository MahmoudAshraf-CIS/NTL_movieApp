package com.example.mannas.ntl_movieapp.Activity_Detail;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.content.MovieVideos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/12/2017.
 */

class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder>{
    MovieVideos videos;
    Integer max;
    public VideosAdapter(){}

    public VideosAdapter(MovieVideos movieVideos ,Integer maxViewed){
        this.videos = new MovieVideos();
        videos.id = movieVideos.id;

        max = maxViewed;
        int s=0;
        if(maxViewed>0)
            s = Math.min(maxViewed,movieVideos.results.size());
        else
            s = movieVideos.results.size();

        for(int i=0 ;i<s; i++){
            videos.results.add(movieVideos.results.get(i));
        }

    }
    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row,parent,false);
        return new VideoHolder(view);
    }
    @Override
    public void onBindViewHolder(VideoHolder holder, final int position) {
        Picasso.with( holder.itemView.getContext())
                .load("https://img.youtube.com/vi/"+videos.results.get(position).key +"/0.jpg")
                .placeholder(R.drawable.video)
                .error(R.drawable.video)
                .into(holder.thumbnail);

        if(videos.results.get(position).size >= 720 )
            holder.hd.setVisibility(View.VISIBLE);
        else
            holder.hd.setVisibility(View.GONE);

        holder.name.setText(videos.results.get(position).name);
        holder.iso.setText(videos.results.get(position).iso_639_1);
        holder.site.setText(videos.results.get(position).site);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.youtube.com/watch?v="+videos.results.get(position).key));
                 view.getContext().startActivity(i);
            }
        });
    }
    public void changeDataSet(MovieVideos movieVideos){
        this.videos = new MovieVideos();
        //max = 3
        //size = 4
        int s=0;
        if(max>0)
            s = Math.min(max,movieVideos.results.size());
        else
            s = movieVideos.results.size();

        for(int i=0 ;i<s; i++){
            videos.results.add(movieVideos.results.get(i));
        }
        super.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return this.videos.results.size();
    }
    class VideoHolder extends RecyclerView.ViewHolder{
        public ImageView thumbnail,hd;
        public TextView name,site,iso;
        public VideoHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            hd = (ImageView) itemView.findViewById(R.id.hd);
            name = (TextView) itemView.findViewById(R.id.name);
            site = (TextView) itemView.findViewById(R.id.site);
            iso = (TextView) itemView.findViewById(R.id.iso);
        }
    }
}

