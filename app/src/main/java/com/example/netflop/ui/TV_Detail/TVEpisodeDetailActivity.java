package com.example.netflop.ui.TV_Detail;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netflop.R;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.constants.enums.WatchStatus;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.TVs.CrewTV;
import com.example.netflop.data.models.remote.TVs.GuestStar;
import com.example.netflop.data.models.remote.TVs.TVEpisodeCredit;
import com.example.netflop.data.models.remote.TVs.TVEpisodeDetail;
import com.example.netflop.databinding.ActivityTvepisodeDetailBinding;
import com.example.netflop.ui.adapters.remote.ListCrewTVAdapter;
import com.example.netflop.ui.adapters.remote.ListGuestStarAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.person_detail.PersonDetailActivity;
import com.example.netflop.utils.listeners.OnClickIDListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.utils.ToolBarUtils;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.TVDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVEpisodeDetailActivity extends BaseActivity implements OnClickIDListener {
    ActivityTvepisodeDetailBinding binding;

    // view model
    TVDetailViewModel tvDetailViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;

    // primitive variable
    int tvSeriesID;
    int seasonNumber;
    int episodeNumber;

    // list data
    List<GuestStar> listGuest,listCast;
    List<CrewTV> listCrew;
    // adapter
    ListCrewTVAdapter listCrewTVAdapter;
    ListGuestStarAdapter listGuestStarAdapter,listCastAdapter;
    // UI
    Toolbar toolbar;
    ImageView imageView;
    TextView tvEpisodeRuntimeTextView,voteRatedTextView,tvEpisodeNumberTextView,tvSeasonNumberInEpisodeTextView,overviewTextView,dateTVEpisodeTextView;
    RecyclerView guestTVEpisodeRecyclerView,crewTVEpisodeRecyclerView,castTVEpisodeRecyclerView;
    TextView isHavingGuestTextView,isHavingCastTextView,isHavingCrewTextView;

    ImageView addFavouriteView;
    List<FavouriteMedia> listFavourite;
    boolean isFavourite=false;
    String tvTitle,imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tvepisode_detail);
        binding=ActivityTvepisodeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        getData();
        initialize();
        callAPIs();
        loadFavouriteData();
        observeDataChange();
        observeFavouriteDataChange();
        handleFavouriteCLick();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    finish();
                }
            }
        });
    }

    private void getBinding(){
        toolbar=binding.toolBarTVEpisodeView;
        imageView=binding.tvEpisodeImageView;
        tvEpisodeRuntimeTextView=binding.tvEpisodeRuntimeView;
        voteRatedTextView=binding.voteRatedTVEpisodeDetailTV;
        tvEpisodeNumberTextView=binding.tvEpisodeNumberView;
        tvSeasonNumberInEpisodeTextView=binding.tvSeasonNumberInEpisodeView;
        overviewTextView=binding.overviewTVEpisodeView;
        dateTVEpisodeTextView=binding.dateTVEpisodeView;

        guestTVEpisodeRecyclerView=binding.guestTVEpisodeView;
        crewTVEpisodeRecyclerView=binding.crewTVEpisodeView;
        castTVEpisodeRecyclerView=binding.castTVEpisodeView;

        isHavingGuestTextView=binding.isHavingGuestEpisodeView;
        isHavingCastTextView=binding.isHavingCastEpisodeView;
        isHavingCrewTextView=binding.isHavingCrewEpisodeView;
        addFavouriteView=binding.addFavouriteImage;
    }
    private void getData(){
        Intent intent=getIntent();
        tvSeriesID=intent.getIntExtra(StringConstants.tvSeriesIDKey,-1);
        seasonNumber=intent.getIntExtra(StringConstants.seasonNumberKey,-1);
        episodeNumber=intent.getIntExtra(StringConstants.episodeNumberKey,-1);
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        ToolBarUtils.setupBasicToolbar(toolbar,() -> finish());
        tvDetailViewModel= new ViewModelProvider(this).get(TVDetailViewModel.class);
        favouriteMediaViewModel= new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        listGuest=new ArrayList<>();
        listCast=new ArrayList<>();
        listCrew=new ArrayList<>();
        listFavourite=new ArrayList<>();

        listCrewTVAdapter=new ListCrewTVAdapter(listCrew,this,this);
        listCastAdapter=new ListGuestStarAdapter(listCast,this,this,favouriteMediaViewModel);
        listGuestStarAdapter=new ListGuestStarAdapter(listGuest,this,this,favouriteMediaViewModel);


//        guestTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        crewTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        castTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RecyclerViewUtils.setupHorizontalRecyclerView(this,guestTVEpisodeRecyclerView,listGuestStarAdapter, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,crewTVEpisodeRecyclerView,listCrewTVAdapter, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,castTVEpisodeRecyclerView,listCastAdapter, LinearLayoutManager.HORIZONTAL, false);

//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
//        guestTVEpisodeRecyclerView.addItemDecoration(itemDecorator);
//        crewTVEpisodeRecyclerView.addItemDecoration(itemDecorator);
//        castTVEpisodeRecyclerView.addItemDecoration(itemDecorator);
//
////        guestTVEpisodeRecyclerView.setAdapter(listGuestStarAdapter);
//        castTVEpisodeRecyclerView.setAdapter(listCastAdapter);
//        crewTVEpisodeRecyclerView.setAdapter(listCrewTVAdapter);
    }
    private void callAPIs(){
        tvDetailViewModel.loadTVEpisodeDetail(tvSeriesID,seasonNumber,episodeNumber,this);
        tvDetailViewModel.loadTVEpisodeCredit(tvSeriesID,seasonNumber,episodeNumber,this);
    }
    private void observeDataChange(){
        tvDetailViewModel.getTvEpisodeDetailData().observe(this, new Observer<TVEpisodeDetail>() {
            @Override
            public void onChanged(TVEpisodeDetail tvEpisodeDetail) {
                if(tvEpisodeDetail!=null){
                    updateData(tvEpisodeDetail);
                }
            }
        });
        tvDetailViewModel.getTvEpisodeCreditData().observe(this, new Observer<TVEpisodeCredit>() {
            @Override
            public void onChanged(TVEpisodeCredit tvEpisodeCredit) {
                if(tvEpisodeCredit!=null){
                    updateCreditData(tvEpisodeCredit);
                }
            }
        });

    }
    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouritePeople().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                listFavourite=favouriteMedia;
                for (int i = 0; i < favouriteMedia.size(); i++) {
                    if(favouriteMedia.get(i).getMediaID()==tvSeriesID){
                        isFavourite=true;
                        addFavouriteView.setImageResource(R.drawable.favoourite_no_border);
                    }
                }
                if(!isFavourite){
                    addFavouriteView.setImageResource(R.drawable.favorite_red_border);
                }
            }
        });
    }
    private  void handleFavouriteCLick(){
        addFavouriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite){
                    int index=0;
                    for (int i = 0; i <listFavourite.size() ; i++) {
                        if(listFavourite.get(i).getMediaID()==tvSeriesID&&listFavourite.get(i).getSeasonNumber()==seasonNumber&&listFavourite.get(i).getEpisodeNumber()==episodeNumber){
                            index=i;
                            break;
                        }
                    }
                    if(index!=0){
                        favouriteMediaViewModel.deleteFavouriteMedia(listFavourite.get(index).getId());
                        isFavourite=false;
                    }
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(tvSeriesID,tvTitle, TypeOfMedia.TVSeason,seasonNumber,episodeNumber,imagePath, WatchStatus.UNWATCH);
                }

            }
        });
    }
    private  void loadFavouriteData(){
        favouriteMediaViewModel.fetchTVEpisodesData();
    }
    private void updateData(TVEpisodeDetail tvEpisodeDetail){
        toolbar.setTitle(tvEpisodeDetail.getName()!=null?tvEpisodeDetail.getName():"null");
        tvTitle=tvEpisodeDetail.getName()!=null?tvEpisodeDetail.getName():"null";
        if(tvEpisodeDetail.getStillPath()!=null){
            imagePath=tvEpisodeDetail.getStillPath();
            String url= URLConstants.imageURL+tvEpisodeDetail.getStillPath();
            Glide.with(this).load(url).placeholder(R.drawable.place_holder).into(imageView);
        }
        tvEpisodeRuntimeTextView.setText(tvEpisodeDetail.getRuntime()+" minutes");
        voteRatedTextView.setText(tvEpisodeDetail.getVoteAverage()+" ("+tvEpisodeDetail.getVoteCount()+")");
        tvEpisodeNumberTextView.setText("Episode: "+tvEpisodeDetail.getEpisodeNumber());
        tvSeasonNumberInEpisodeTextView.setText("Season: "+tvEpisodeDetail.getSeasonNumber());
        overviewTextView.setText(tvEpisodeDetail.getOverview());
        dateTVEpisodeTextView.setText(tvEpisodeDetail.getAirDate());
        if(tvEpisodeDetail.getCrew()!=null&&!tvEpisodeDetail.getCrew().isEmpty()){
            listCrew=tvEpisodeDetail.getCrew();
            listCrewTVAdapter=new ListCrewTVAdapter(listCrew,this,this);
            crewTVEpisodeRecyclerView.setAdapter(listCrewTVAdapter);
        }else{
            isHavingCrewTextView.setVisibility(View.VISIBLE);
            crewTVEpisodeRecyclerView.setVisibility(View.GONE);
        }
    }
    private void updateCreditData(TVEpisodeCredit tvEpisodeCredit){
        if(tvEpisodeCredit.getCast()!=null&&!tvEpisodeCredit.getCast().isEmpty()){
            listCast=tvEpisodeCredit.getCast();
            listCastAdapter=new ListGuestStarAdapter(listCast,this,this,favouriteMediaViewModel);
            castTVEpisodeRecyclerView.setAdapter(listCastAdapter);
        }else{
            isHavingCastTextView.setVisibility(View.VISIBLE);
            castTVEpisodeRecyclerView.setVisibility(View.GONE);
        }
        if(tvEpisodeCredit.getGuestStars()!=null&&!tvEpisodeCredit.getGuestStars().isEmpty()){
            listGuest=tvEpisodeCredit.getGuestStars();
            listGuestStarAdapter=new ListGuestStarAdapter(listCast,this,this,favouriteMediaViewModel);
            guestTVEpisodeRecyclerView.setAdapter(listGuestStarAdapter);
        }else{
            isHavingGuestTextView.setVisibility(View.VISIBLE);
            guestTVEpisodeRecyclerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onCLick(int id, String type) {
        if(type.equals(StringConstants.personType)){
            Intent intent=new Intent(this, PersonDetailActivity.class);
            intent.putExtra(StringConstants.personDetailDataKey,id);
            startActivity(intent);
        }
    }
}