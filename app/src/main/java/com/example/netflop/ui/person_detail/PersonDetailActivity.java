package com.example.netflop.ui.person_detail;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.netflop.R;
import com.example.netflop.constants.GenderConstants;
import com.example.netflop.constants.StringConstants;
import com.example.netflop.constants.URLConstants;
import com.example.netflop.constants.enums.GenderEnums;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.remote.movies.CombinedCredit;
import com.example.netflop.data.models.remote.movies.MovieCast;
import com.example.netflop.data.models.remote.people.PersonDetail;
import com.example.netflop.data.models.remote.people.PersonImages;
import com.example.netflop.data.models.remote.people.Profile;
import com.example.netflop.databinding.ActivityPersonDetailBinding;
import com.example.netflop.helpers.NoInternetToastHelpers;
import com.example.netflop.ui.TV_Detail.TVSeriesDetailActivity;
import com.example.netflop.ui.adapters.remote.ListMovieCastAdapter;
import com.example.netflop.ui.base.BaseActivity;
import com.example.netflop.ui.movie_detail.MovieDetailActivity;
import com.example.netflop.utils.listeners.ItemMovieCastListener;
import com.example.netflop.utils.RecyclerViewUtils;
import com.example.netflop.viewmodel.connectivity.ConnectivityViewModel;
import com.example.netflop.viewmodel.local.FavouriteMediaViewModel;
import com.example.netflop.viewmodel.remote.PersonViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDetailActivity extends BaseActivity implements ItemMovieCastListener {
    ActivityPersonDetailBinding binding;
    int personID;
    MovieCast selectedMovieCast;

    List<MovieCast> castList,crewList;
    List<String> asKnownAsList;
    List<String> listImage;
    List<FavouriteMedia> listFavourite;

    List<SlideModel> slideModels;
    PersonImages personImagesData;
    PersonDetail personDetailData;

    // adapters


    ArrayAdapter<String> adapter;
    ListMovieCastAdapter castAdapter,crewAdapter;

    //view model
    PersonViewModel personViewModel;
    FavouriteMediaViewModel favouriteMediaViewModel;
    ConnectivityViewModel connectivityViewModel;

    // UI
    RecyclerView castRecyclerView,crewRecyclerView;
    Toolbar toolbar;
    TextView knownForDepartmentTV,popularityTV,birthdayTV,deadDayTV,genderTV,placeOfBirthTV,biographyTV,homePagePersonTV;
    ImageSlider imageSlider;
    ListView asKnownAsListView;
    TextView isCrewHavingTV,isCastHavingTV;


    ImageView addFavouriteView;
    boolean isFavourite=false;

    String personName,personAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPersonDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getBinding();
        getData();
        initialize();
        callAPIs();
        loadFavouriteData();
        observeFavouriteDataChange();
        observeDataChange();
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
        castRecyclerView=binding.castMovieRecyclerView;
        crewRecyclerView=binding.crewMovieRecyclerView;
        toolbar=binding.toolBarView;
        knownForDepartmentTV=binding.knownForDepartmentView;
        popularityTV=binding.personPopularityView;
        birthdayTV=binding.birthdayView;
        deadDayTV=binding.deathDayView;
        genderTV=binding.genderView;
        placeOfBirthTV=binding.placeOfBirthView;
        biographyTV=binding.biographyView;
        homePagePersonTV=binding.homePagePersonView;
        imageSlider=binding.imageSliderPersonDetail;
        asKnownAsListView=binding.alsoKnownAsView;
        isCrewHavingTV=binding.isHavingCrewView;
        isCastHavingTV=binding.isHavingCastView;
        addFavouriteView=binding.addFavouriteImage;
    }
    private void getData(){
        Intent intent=getIntent();
        personID=intent.getIntExtra(StringConstants.personDetailDataKey,-1);
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
        personViewModel= new ViewModelProvider(this).get(PersonViewModel.class);
        favouriteMediaViewModel=new ViewModelProvider(this).get(FavouriteMediaViewModel.class);
        connectivityViewModel=new ViewModelProvider(this).get(ConnectivityViewModel.class);
        castList=new ArrayList<>();
        crewList=new ArrayList<>();
        listFavourite=new ArrayList<>();
        asKnownAsList=new ArrayList<>();
        listImage=new ArrayList<>();
        slideModels=new ArrayList<>();
        crewAdapter=new ListMovieCastAdapter(crewList,this,this);
        castAdapter=new ListMovieCastAdapter(castList,this,this);
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                asKnownAsList
        );

        RecyclerViewUtils.setupHorizontalRecyclerView(this,castRecyclerView,castAdapter,LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewUtils.setupHorizontalRecyclerView(this,crewRecyclerView,crewAdapter,LinearLayoutManager.HORIZONTAL,false);

//        castRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        SpacingItemDecorator itemDecorator=new SpacingItemDecorator(0,20);
//        castRecyclerView.addItemDecoration(itemDecorator);
//        crewRecyclerView.addItemDecoration(itemDecorator);
        asKnownAsListView.setAdapter(adapter);
//        castRecyclerView.setAdapter(castAdapter);
//        crewRecyclerView.setAdapter(crewAdapter);
    }
    private void callAPIs(){
        personViewModel.loadPersonDetailByPersonID(personID,this);
        personViewModel.loadPersonImagesByPersonID(personID,this);
        personViewModel.loadCombinedCreditByPersonID(personID,this);
    }
    private void observeDataChange(){
        personViewModel.getPersonDetailData().observe(this, new Observer<PersonDetail>() {
            @Override
            public void onChanged(PersonDetail personDetail) {
                if(personDetail!=null){
                    personDetailData=personDetail;
                    updatePersonDetailDataChanged();
                }
            }
        });
        personViewModel.getPersonImageData().observe(this, new Observer<PersonImages>() {
            @Override
            public void onChanged(PersonImages personImages) {
                updatePersonImageDataChanged(personImages);
            }
        });
        personViewModel.getCombinedCreditData().observe(this, new Observer<CombinedCredit>() {
            @Override
            public void onChanged(CombinedCredit combinedCredit) {
                updateCombinedCreditDataChanged(combinedCredit);
            }
        });
    }

    // update functions
    private void updatePersonDetailDataChanged(){
        if(personDetailData.getDeathday()!=null){
            deadDayTV.setText(personDetailData.getDeathday());
        }
        birthdayTV.setText(personDetailData.getBirthday());
        popularityTV.setText(personDetailData.getPopularity()+"");
        if(personDetailData.getHomepage()!=null){
            //            personDetailData.setAutoLinkMask(Linkify.WEB_URLS);   // enable autoLink
        }else{
            homePagePersonTV.setAutoLinkMask(0); // disable autoLink
        }
        homePagePersonTV.setText(personDetailData.getHomepage()!=null ?personDetailData.getHomepage():"null" );
        biographyTV.setText(personDetailData.getBiography());
        placeOfBirthTV.setText(personDetailData.getPlaceOfBirth());
        if(personDetailData.getGender()== GenderConstants.female){
            genderTV.setText(GenderEnums.Female.toString());
        }else if(personDetailData.getGender()== GenderConstants.male){
            genderTV.setText(GenderEnums.Male.toString());
        }else if(personDetailData.getGender()== GenderConstants.nonBinary){
            genderTV.setText(GenderEnums.nonBinary.toString());
        }else{
            genderTV.setText(GenderEnums.notSpecified.toString());
        }
        knownForDepartmentTV.setText(personDetailData.getKnownForDepartment());
        toolbar.setTitle(personDetailData.getName());
        personName=personDetailData.getName();
        if(personDetailData.getAlsoKnownAs()!=null&&!personDetailData.getAlsoKnownAs().isEmpty()){
            asKnownAsList=personDetailData.getAlsoKnownAs();
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    asKnownAsList
            );
            asKnownAsListView.setAdapter(adapter);
        }
        if(personDetailData.getProfilePath()!=null){
            personAvatar=personDetailData.getProfilePath();
            addListImage(personDetailData.getProfilePath());
            setSlideImageList();
        }
    }
    private void updatePersonImageDataChanged(PersonImages personImages){
        if(personImages.getProfiles()!=null&&!personImages.getProfiles().isEmpty()){
            for (Profile profile:personImages.getProfiles()) {
                if(profile.getFilePath()!=null){
                    addListImage(profile.getFilePath());
                }
            }
            setSlideImageList();
        }
    }
    private void updateCombinedCreditDataChanged(CombinedCredit combinedCredit){
        if(combinedCredit!=null){
            if(combinedCredit.getCast()!=null&&!combinedCredit.getCast().isEmpty()){
                castList=combinedCredit.getCast();
                castAdapter=new ListMovieCastAdapter(castList,this,this);
                castRecyclerView.setAdapter(castAdapter);
            }else{
                isCastHavingTV.setVisibility(View.VISIBLE);
            }
            if(combinedCredit.getCrew()!=null&&!combinedCredit.getCrew().isEmpty()){
                crewList=combinedCredit.getCrew();
                crewAdapter=new ListMovieCastAdapter(crewList,this,this);
                crewRecyclerView.setAdapter(crewAdapter);
            }else{
                isCrewHavingTV.setVisibility(View.VISIBLE);
            }
        }
    }
    private void addListImage(String newData){
        listImage.add(URLConstants.imageURL+newData);
    }
    private void addToSlideModels(){
        for (int i = 0; i <listImage.size() ; i++) {
            slideModels.add(new SlideModel(listImage.get(i), ScaleTypes.FIT));
        }
    }
    private void setSlideImageList(){
        addToSlideModels();
        imageSlider.setImageList(slideModels);
    }



    @Override
    public void onMovieCastClick(MovieCast movieCast) {
        if(connectivityViewModel.getState()){
            selectedMovieCast=movieCast;
            // do sth
            if(Objects.equals(selectedMovieCast.getMediaType(), "movie")){
                Intent intent=new Intent(this, MovieDetailActivity.class);
                intent.putExtra(StringConstants.movieDetailPageDataKey,selectedMovieCast.getId());
                startActivity(intent);
            }else{
                Intent intent=new Intent(this, TVSeriesDetailActivity.class);
                intent.putExtra(StringConstants.tvSeriesIDKey,selectedMovieCast.getId());
                startActivity(intent);
            }
        }else{
            NoInternetToastHelpers.show(this);
        }

    }
    private  void loadFavouriteData(){
        favouriteMediaViewModel.fetchPeopleData();
    }

    private  void observeFavouriteDataChange(){
        favouriteMediaViewModel.getFavouriteMovies().observe(this, new Observer<List<FavouriteMedia>>() {
            @Override
            public void onChanged(List<FavouriteMedia> favouriteMedia) {
                listFavourite=favouriteMedia;
                for (int i = 0; i < favouriteMedia.size(); i++) {
                    if(favouriteMedia.get(i).getMediaID()==personID){
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
                        if(listFavourite.get(i).getMediaID()==personID){
                            index=i;
                            break;
                        }
                    }
                    if(index!=0){
                        favouriteMediaViewModel.deleteFavouriteMedia(listFavourite.get(index).getId());
                        isFavourite=false;
                    }
                }else{
                    favouriteMediaViewModel.insertFavouriteMedia(personID,personName, TypeOfMedia.person,null,null,personAvatar);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID= item.getItemId();
        if(itemID==R.id.addFavouritePersonMenu){
            Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}