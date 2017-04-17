package com.example.mannas.ntl_movieapp.Activity_Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.content.MovieDetail;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/7/2017.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.Holder> {
    ArrayList<MovieDetail> data;
    MainRecyclerAdapter_MoreDataListener mMoreDateRequestlistener;
    MovieItemOnClickListener itemOnClickListener;
    public MainRecyclerAdapter(MainRecyclerAdapter_MoreDataListener listener,MovieItemOnClickListener movieItemOnClickListener) {
        data = new ArrayList<>();
        mMoreDateRequestlistener = listener;
        itemOnClickListener = movieItemOnClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_recycler_movie_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int i) {

        if( data!=null && i == data.size()-2 && data.size()>5){
            mMoreDateRequestlistener.OnRequestMoreDate();
        }

        Picasso.with(holder.itemView.getContext())
                .load(data.get(i).getPosterURL()).placeholder(R.drawable.loading)
                .into(holder.poster);


     //  holder.vote_average.setRating(Float.valueOf(MovieFullDetails_data.get(i).vote_average.toString()));
      // holder.vote_average.setRating( 0.1f);
        Float ranking  = data.get(i).vote_average;

        if(ranking >=0 && ranking < 2){ holder.vote_average.setSelectedSmile(BaseRating.TERRIBLE,true);;
        }else if(ranking >=2 && ranking < 4){holder.vote_average.setSelectedSmile(BaseRating.BAD,true);;
        }else if(ranking >= 4&& ranking <7){holder.vote_average.setSelectedSmile(BaseRating.OKAY,true);}
        else if(ranking >=7 && ranking <9){holder.vote_average.setSelectedSmile(BaseRating.GOOD,true);}
        else if(ranking >=9 && ranking <10){holder.vote_average.setSelectedSmile(BaseRating.GREAT,true);}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnClickListener.OnItemClick(data.get(i));
            }
        });
        holder.ic_Adult.setVisibility(View.VISIBLE);
        if(data.get(i).adult){
            holder.ic_Adult.setVisibility(View.VISIBLE);
        }else{
            holder.ic_Adult.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public MovieDetail getItemData(int index){
        if(data !=null){
            if(index >=0 && index < data.size())
                return data.get(index);
            else
                return null;
        }
        return null;
    }

    public void changeDataSet(ArrayList<MovieDetail> nData){
        if(nData!=null){
            data = nData;
            super.notifyDataSetChanged();
        }
    }

    public void addToDataSet(ArrayList<MovieDetail> ls){
        int count =0;
        if(ls!=null){
            count = ls.size();
            for(int i=0 ; i<ls.size() ;i++)
                data.add(ls.get(i));
        }
        super.notifyItemRangeInserted(data.size(),count);
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView poster,ic_Adult;
        SmileRating vote_average;
        public Holder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            ic_Adult = (ImageView) itemView.findViewById(R.id.ic_adult);
            vote_average = (SmileRating) itemView.findViewById(R.id.vote_average);
        }
    }

    public interface MainRecyclerAdapter_MoreDataListener{
        void OnRequestMoreDate();
    }
    public interface MovieItemOnClickListener{
        void OnItemClick(MovieDetail m);
    }

    public ArrayList<MovieDetail> getDataSet(){
        return data;
    }
}
