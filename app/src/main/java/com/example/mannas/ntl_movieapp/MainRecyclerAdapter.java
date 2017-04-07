package com.example.mannas.ntl_movieapp;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mannas.ntl_movieapp.content.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mannas on 4/7/2017.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.Holder> {
    ArrayList<Movie> data;

    public MainRecyclerAdapter() {
        data = new ArrayList<>();
    }

    public MainRecyclerAdapter(ArrayList<Movie> data) {
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler_movie_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
        Picasso.with(holder.itemView.getContext())
                .load(data.get(i).getPosterURL())
                .into(holder.poster);

        String s = data.get(i).vote_average.toString();
       // holder.vote_average.setText(s);
       // holder.ic_Adult.setVisibility(View.VISIBLE);
//        if(data.get(i).adult){
//            holder.ic_Adult.setVisibility(View.VISIBLE);
//        }else{
//            holder.ic_Adult.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void changeDataSet(ArrayList<Movie> nData){
        if(nData!=null){
            data = nData;
            super.notifyDataSetChanged();
        }
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView poster,ic_Adult;
        TextView vote_average;
        public Holder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
           // ic_Adult = (ImageView) itemView.findViewById(R.id.ic_adult);
           // vote_average = (TextView) itemView.findViewById(R.id.vote_average);
        }
    }
}
