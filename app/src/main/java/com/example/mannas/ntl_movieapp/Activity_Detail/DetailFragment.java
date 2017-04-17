package com.example.mannas.ntl_movieapp.Activity_Detail;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mannas.ntl_movieapp.Activity_Main.MainFragment;
import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.Utility;
import com.example.mannas.ntl_movieapp.content.Contract;
import com.example.mannas.ntl_movieapp.content.MovieDetail;
import com.example.mannas.ntl_movieapp.content.MovieFullDetails;
import com.example.mannas.ntl_movieapp.content.MovieReviews;
import com.example.mannas.ntl_movieapp.content.MovieVideos;
import com.example.mannas.ntl_movieapp.content.LoaderManager;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;


public class DetailFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks {
    ImageView poster_backdrop;

    MovieDetail movieDetail;
    MovieFullDetails movieFullDetails;
    MovieReviews movieReviews;
    MovieVideos movieVideos;

    ReviewsAdapter reviewsAdapter;
    VideosAdapter videosAdapter;

    final Integer maxReviews = 5, maxVideos =5;
    Boolean inFav=false;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            movieDetail = getArguments().getParcelable(Utility.movieDetail_key);
        }

        //getLoaderManager().restartLoader(LoaderManager.IDMovieFullDetail,null,this).forceLoad();

        getLoaderManager().restartLoader(LoaderManager.IDMovieReviews,null,this).forceLoad();
        getLoaderManager().restartLoader(LoaderManager.IDMoviewVideos,null,this).forceLoad();

        reviewsAdapter = new ReviewsAdapter(new MovieReviews(),maxReviews);
        videosAdapter = new VideosAdapter(new MovieVideos() , maxVideos);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        poster_backdrop = (ImageView) root.findViewById(R.id.poster_backdrop);
        TextView movie_title = (TextView) root.findViewById(R.id.movie_title);
        TextView movie_releaseDate = (TextView) root.findViewById(R.id.movie_releaseDate);
        TextView movie_originalLang = (TextView) root.findViewById(R.id.movie_originalLang);
        ImageView movie_isAdult = (ImageView) root.findViewById(R.id.movie_isAdult);
        ImageView movie_noAdult = (ImageView) root.findViewById(R.id.movie_noAdult);
        SmileRating vote_average = (SmileRating) root.findViewById(R.id.vote_average);
        TextView movie_overview_content = (TextView) root.findViewById(R.id.movie_overview_content);

        RecyclerView reviews = (RecyclerView) root.findViewById(R.id.reviews_recycler);
        reviews.setNestedScrollingEnabled(false);
        reviews.setAdapter(reviewsAdapter);
        reviews.setLayoutManager(new LinearLayoutManager(getContext(),1,false));
        View moreReviews = root.findViewById(R.id.moreReviews);
        moreReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.reviews_more,null,false);
                RecyclerView moreRevRecy = (RecyclerView) v.findViewById(R.id.reviews_recycler);
                moreRevRecy.setAdapter(new ReviewsAdapter(movieReviews,-1) );
                moreRevRecy.setNestedScrollingEnabled(false);
                moreRevRecy.setLayoutManager(new LinearLayoutManager(getContext(),1,false));
                new MaterialDialog.Builder(getContext())
                        .customView(v,true)
                        .show();
            }
        });


        RecyclerView videos = (RecyclerView) root.findViewById(R.id.videos_recycler);
        videos.setNestedScrollingEnabled(false);
        videos.setAdapter(videosAdapter);
        videos.setLayoutManager(new LinearLayoutManager(getContext(),1,false));
        View moreVideos = root.findViewById(R.id.moreVideos);
        moreVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.videos_more,null,false);
                RecyclerView moreVidRecy = (RecyclerView) v.findViewById(R.id.videos_recycler);
                moreVidRecy.setAdapter(new VideosAdapter(movieVideos,-1));
                moreVidRecy.setNestedScrollingEnabled(false);
                moreVidRecy.setLayoutManager(new LinearLayoutManager(getContext(),1,false));
                new MaterialDialog.Builder(getContext())
                        .customView(v,true)
                        .show();
            }
        });


        if(movieDetail!=null){
            Picasso.with(getContext()).load(movieDetail.getPosterURL()).into(poster_backdrop);
            poster_backdrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"poster",Toast.LENGTH_LONG).show();
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.poster,null,false);
                    Picasso.with(getContext()).load(movieDetail.getPosterURL()).into(((ImageView) v.findViewById(R.id.poster)));
                    new MaterialDialog.Builder(getContext())
                            .customView(v,false)
                            .show();
                }
            });

            movie_title.setText(movieDetail.title);
            movie_releaseDate.setText(movieDetail.release_date);
            movie_originalLang.setText(movieDetail.original_language);
            if(movieDetail.adult){
                movie_isAdult.setVisibility(View.VISIBLE);
                movie_noAdult.setVisibility(View.GONE);
            }else{
                movie_isAdult.setVisibility(View.GONE);
                movie_noAdult.setVisibility(View.VISIBLE);
            }

            Float ranking  = movieDetail.vote_average;
            if(ranking >=0 && ranking < 2){vote_average.setSelectedSmile(BaseRating.TERRIBLE,true);;
            }else if(ranking >=2 && ranking < 4){vote_average.setSelectedSmile(BaseRating.BAD,true);;
            }else if(ranking >= 4&& ranking <7){vote_average.setSelectedSmile(BaseRating.OKAY,true);}
            else if(ranking >=7 && ranking <9){vote_average.setSelectedSmile(BaseRating.GOOD,true);}
            else if(ranking >=9 && ranking <10){vote_average.setSelectedSmile(BaseRating.GREAT,true);}
            vote_average.setActivated(true);

            movie_overview_content.setText(movieDetail.overview);
        }
        final FloatingActionButton fav_FAB = (FloatingActionButton) root.findViewById(R.id.fav_FAB);

        fav_FAB.setClickable(false);
        fav_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inFav = !inFav;
                if(inFav){
                    Toast.makeText(getContext(),"Added To Your favourites",Toast.LENGTH_LONG).show();
                    ContentValues v = new ContentValues(1);
                    v.put(Contract.movie_Fav.Columns.movie_id, movieDetail.id);
                    v.put(Contract.movie_Fav.Columns.json , movieDetail.toJsonString());
                    getContext().getContentResolver().insert(Contract.movie_Fav.uri,v);
                    fav_FAB.setImageResource(R.drawable.delete);
                }else{
                    Toast.makeText(getContext(),"Deleted from Your favourites",Toast.LENGTH_LONG).show();
                    getContext().getContentResolver().delete(Contract.movie_Fav.uri, Contract.movie_Fav.Columns.movie_id+" = "+movieDetail.id, null);
                    fav_FAB.setImageResource(R.drawable.add);
                }
                if(MainFragment.tabletView){
                      if(  getActivity().getSupportFragmentManager().findFragmentByTag(MainFragment.class.getName()) instanceof MainFragment){
                          ((MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(MainFragment.class.getName())).refresh();
                      }
                }
            }
        });

        new AsyncTask<Void,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                int c = getContext().getContentResolver().delete(Contract.movie_Fav.uri,
                        Contract.movie_Fav.Columns.movie_id+" = "+movieDetail.id,
                        null
                        );
                return c >= 1;
            }

            @Override
            protected void onPostExecute(Boolean isInFav) {
                super.onPostExecute(isInFav);
                inFav = isInFav;
                if(isInFav)
                    fav_FAB.setImageResource(R.drawable.delete);
                else
                    fav_FAB.setImageResource(R.drawable.add);
                fav_FAB.setClickable(true);
            }
        }.execute();

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     *  Loaders Callbacks
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if(id == LoaderManager.IDMovieFullDetail){
            return LoaderManager.getMovieFullDetailsLoader(getContext(),movieDetail.id,MainFragment.isoffline);
        }
        else if(id == LoaderManager.IDMovieReviews){
            return LoaderManager.getMovieReviewsLoader(getContext(),movieDetail.id,1, MainFragment.isoffline);
        }
        else if(id == LoaderManager.IDMoviewVideos){
            return LoaderManager.getMovieVideosLoader(getContext(),movieDetail.id,MainFragment.isoffline);
        }
        return null;
    }
    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(data!=null && loader.getId() == LoaderManager.IDMovieFullDetail){
            if(data instanceof MovieFullDetails){

            }
        }else if(data!=null && loader.getId() == LoaderManager.IDMovieReviews){
            if(data instanceof MovieReviews){
                movieReviews = ((MovieReviews) data);
                reviewsAdapter.changeDataSet(((MovieReviews) data));
            }
        }
        else if(data!=null && loader.getId() == LoaderManager.IDMoviewVideos){
            if (data instanceof MovieVideos) {
                movieVideos = ((MovieVideos) data);
                videosAdapter.changeDataSet(((MovieVideos) data));
            }
        }
    }
    @Override
    public void onLoaderReset(Loader loader) {

    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
