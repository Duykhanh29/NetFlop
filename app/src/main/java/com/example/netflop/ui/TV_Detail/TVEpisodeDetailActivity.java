package com.example.netflop.ui.TV_Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.netflop.data.models.TVs.CrewTV;
import com.example.netflop.data.models.TVs.GuestStar;
import com.example.netflop.data.models.TVs.TVEpisodeCredit;
import com.example.netflop.data.models.TVs.TVEpisodeDetail;
import com.example.netflop.data.models.TVs.TVSeasonsDetail;
import com.example.netflop.databinding.ActivityTvepisodeDetailBinding;
import com.example.netflop.ui.adapters.GridEpisodeAdapter;
import com.example.netflop.ui.adapters.ListCrewTVAdapter;
import com.example.netflop.ui.adapters.ListGuestStarAdapter;
import com.example.netflop.utils.OnClickIDListener;
import com.example.netflop.utils.SpacingItemDecorator;
import com.example.netflop.viewmodel.TVDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVEpisodeDetailActivity extends AppCompatActivity implements OnClickIDListener {
    ActivityTvepisodeDetailBinding binding;

    // view model
    TVDetailViewModel tvDetailViewModel;

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
        observeDataChange();
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
    }
    private void getData(){
        Intent intent=getIntent();
        tvSeriesID=intent.getIntExtra(StringConstants.tvSeriesIDKey,-1);
        seasonNumber=intent.getIntExtra(StringConstants.seasonNumberKey,-1);
        episodeNumber=intent.getIntExtra(StringConstants.episodeNumberKey,-1);
    }
    private void initialize(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.black_arrow_back);
        toolbar.setNavigationContentDescription("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDetailViewModel= new ViewModelProvider(this).get(TVDetailViewModel.class);
        listGuest=new ArrayList<>();
        listCast=new ArrayList<>();
        listCrew=new ArrayList<>();

        listCrewTVAdapter=new ListCrewTVAdapter(listCrew,this,this);
        listCastAdapter=new ListGuestStarAdapter(listCast,this,this);
        listGuestStarAdapter=new ListGuestStarAdapter(listGuest,this,this);


        guestTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        crewTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castTVEpisodeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
        guestTVEpisodeRecyclerView.addItemDecoration(itemDecorator);
        crewTVEpisodeRecyclerView.addItemDecoration(itemDecorator);
        castTVEpisodeRecyclerView.addItemDecoration(itemDecorator);

        guestTVEpisodeRecyclerView.setAdapter(listGuestStarAdapter);
        castTVEpisodeRecyclerView.setAdapter(listCastAdapter);
        crewTVEpisodeRecyclerView.setAdapter(listCrewTVAdapter);
    }
    private void callAPIs(){
        tvDetailViewModel.callTVEpisodeDetail(tvSeriesID,seasonNumber,episodeNumber);
        tvDetailViewModel.callTVEpisodeCredit(tvSeriesID,seasonNumber,episodeNumber);
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
    private void updateData(TVEpisodeDetail tvEpisodeDetail){
        toolbar.setTitle(tvEpisodeDetail.getName()!=null?tvEpisodeDetail.getName():"null");
        if(tvEpisodeDetail.getStillPath()!=null){
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
            listCastAdapter=new ListGuestStarAdapter(listCast,this,this);
            castTVEpisodeRecyclerView.setAdapter(listCastAdapter);
        }else{
            isHavingCastTextView.setVisibility(View.VISIBLE);
            castTVEpisodeRecyclerView.setVisibility(View.GONE);
        }
        if(tvEpisodeCredit.getGuestStars()!=null&&!tvEpisodeCredit.getGuestStars().isEmpty()){
            listGuest=tvEpisodeCredit.getGuestStars();
            listGuestStarAdapter=new ListGuestStarAdapter(listCast,this,this);
            guestTVEpisodeRecyclerView.setAdapter(listGuestStarAdapter);
        }else{
            isHavingGuestTextView.setVisibility(View.VISIBLE);
            guestTVEpisodeRecyclerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onCLick(int id, String type) {

    }
}