package com.example.netflop.viewmodel.remote;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.remote.TVs.TVEpisodeCredit;
import com.example.netflop.data.models.remote.TVs.TVEpisodeDetail;
import com.example.netflop.data.models.remote.TVs.TVEpisodeImage;
import com.example.netflop.data.models.remote.TVs.TVEpisodeVideo;
import com.example.netflop.data.models.remote.TVs.TVSeasonsDetail;
import com.example.netflop.data.models.remote.TVs.TVSeriesDetail;
import com.example.netflop.data.repository.remote.TVRepository;
import com.example.netflop.data.services.APIClient;

public class TVDetailViewModel extends ViewModel {
    private MutableLiveData<TVSeriesDetail> tvSeriesDetailData;
    private MutableLiveData<TVSeasonsDetail> tvSeasonDetailData;
    private MutableLiveData<TVEpisodeDetail> tvEpisodeDetailData;
    private MutableLiveData<TVEpisodeVideo> tvEpisodeVideoData;
    private MutableLiveData<TVEpisodeImage> tvEpisodeImageData;
    private MutableLiveData<TVEpisodeCredit> tvEpisodeCreditData;
    APIService apiService;
    TVRepository tvRepository;
    public TVDetailViewModel(){
        tvSeriesDetailData=new MutableLiveData<>();
        tvSeasonDetailData=new MutableLiveData<>();
        tvEpisodeDetailData=new MutableLiveData<>();
        tvEpisodeVideoData=new MutableLiveData<>();
        tvEpisodeImageData=new MutableLiveData<>();
        tvEpisodeCreditData=new MutableLiveData<>();
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        tvRepository=new TVRepository();
    }


    public MutableLiveData<TVSeriesDetail> getTvSeriesDetailData() {
        return tvSeriesDetailData;
    }

    public MutableLiveData<TVSeasonsDetail> getTvSeasonDetailData() {
        return tvSeasonDetailData;
    }

    public MutableLiveData<TVEpisodeDetail> getTvEpisodeDetailData() {
        return tvEpisodeDetailData;
    }

    public MutableLiveData<TVEpisodeVideo> getTvEpisodeVideoData() {
        return tvEpisodeVideoData;
    }

    public MutableLiveData<TVEpisodeImage> getTvEpisodeImageData() {
        return tvEpisodeImageData;
    }

    public MutableLiveData<TVEpisodeCredit> getTvEpisodeCreditData() {
        return tvEpisodeCreditData;
    }

    public void loadTVSeriesDetail(int id, LifecycleOwner lifecycleOwner){
        tvRepository.getTVSeriesDetail(id).observe(lifecycleOwner, new Observer<TVSeriesDetail>() {
            @Override
            public void onChanged(TVSeriesDetail tvSeriesDetail) {
                tvSeriesDetailData.postValue(tvSeriesDetail);
            }
        });
    }


    public void loadTVSeasonDetail(int id,int number, LifecycleOwner lifecycleOwner){
        tvRepository.getTVSeasonDetail(id,number).observe(lifecycleOwner, new Observer<TVSeasonsDetail>() {
            @Override
            public void onChanged(TVSeasonsDetail tvSeasonsDetail) {
                tvSeasonDetailData.postValue(tvSeasonsDetail);
            }
        });
    }


    public void loadTVEpisodeDetail(int id,int seasonNumber,int episodeNumber, LifecycleOwner lifecycleOwner){
        tvRepository.getTVEpisodeDetail(id,seasonNumber,episodeNumber).observe(lifecycleOwner, new Observer<TVEpisodeDetail>() {
            @Override
            public void onChanged(TVEpisodeDetail tvEpisodeDetail) {
                tvEpisodeDetailData.postValue(tvEpisodeDetail);
            }
        });
    }
    public void loadTVEpisodeImage(int id,int seasonNumber,int episodeNumber, LifecycleOwner lifecycleOwner){
        tvRepository.getTVEpisodeImage(id,seasonNumber,episodeNumber).observe(lifecycleOwner, new Observer<TVEpisodeImage>() {
            @Override
            public void onChanged(TVEpisodeImage tvEpisodeImage) {
                tvEpisodeImageData.postValue(tvEpisodeImage);
            }
        });
    }
    public void loadTVEpisodeVideo(int id,int seasonNumber,int episodeNumber, LifecycleOwner lifecycleOwner){
        tvRepository.getTVEpisodeVideo(id,seasonNumber,episodeNumber).observe(lifecycleOwner, new Observer<TVEpisodeVideo>() {
            @Override
            public void onChanged(TVEpisodeVideo tvEpisodeVideo) {
                tvEpisodeVideoData.postValue(tvEpisodeVideo);
            }
        });
    }
    public void loadTVEpisodeCredit(int id,int seasonNumber,int episodeNumber, LifecycleOwner lifecycleOwner){
        tvRepository.getTVEpisodeCredit(id,seasonNumber,episodeNumber).observe(lifecycleOwner, new Observer<TVEpisodeCredit>() {
            @Override
            public void onChanged(TVEpisodeCredit tvEpisodeCredit) {
                tvEpisodeCreditData.postValue(tvEpisodeCredit);
            }
        });
    }
//    public void callTVSeriesDetail(int id){
//        Call<TVSeriesDetail> call=apiService.getTVSeriesDetail(id);
//        call.enqueue(new Callback<TVSeriesDetail>() {
//            @Override
//            public void onResponse(Call<TVSeriesDetail> call, Response<TVSeriesDetail> response) {
//                if(response.code()==200){
//                    tvSeriesDetailData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVSeriesDetail> call, Throwable t) {
//                tvSeriesDetailData.postValue(null);
//            }
//        });
//    }
//    public void callTVSeasonDetail(int id,int number){
//        Call<TVSeasonsDetail> call=apiService.getTVSeasonsDetail(id,number);
//        call.enqueue(new Callback<TVSeasonsDetail>() {
//            @Override
//            public void onResponse(Call<TVSeasonsDetail> call, Response<TVSeasonsDetail> response) {
//                if(response.code()==200){
//                    tvSeasonDetailData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVSeasonsDetail> call, Throwable t) {
//                tvSeasonDetailData.postValue(null);
//            }
//        });
//    }
//
//    public void callTVEpisodeDetail(int id,int seasonNumber,int episodeNumber){
//        Call<TVEpisodeDetail> call=apiService.getTVEpisodeDetail(id,seasonNumber,episodeNumber);
//        call.enqueue(new Callback<TVEpisodeDetail>() {
//            @Override
//            public void onResponse(Call<TVEpisodeDetail> call, Response<TVEpisodeDetail> response) {
//                if(response.code()==200){
//                    tvEpisodeDetailData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVEpisodeDetail> call, Throwable t) {
//                tvEpisodeDetailData.postValue(null);
//            }
//        });
//    }
//    public void callTVEpisodeImage(int id,int seasonNumber,int episodeNumber){
//        Call<TVEpisodeImage> call=apiService.getTVEpisodeImages(id,seasonNumber,episodeNumber);
//        call.enqueue(new Callback<TVEpisodeImage>() {
//            @Override
//            public void onResponse(Call<TVEpisodeImage> call, Response<TVEpisodeImage> response) {
//                if(response.code()==200){
//                    tvEpisodeImageData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVEpisodeImage> call, Throwable t) {
//                tvEpisodeImageData.postValue(null);
//            }
//        });
//    }
//
//    public void callTVEpisodeVideo(int id,int seasonNumber,int episodeNumber){
//        Call<TVEpisodeVideo> call=apiService.getTVEpisodeVideos(id,seasonNumber,episodeNumber);
//        call.enqueue(new Callback<TVEpisodeVideo>() {
//            @Override
//            public void onResponse(Call<TVEpisodeVideo> call, Response<TVEpisodeVideo> response) {
//                if(response.code()==200){
//                    tvEpisodeVideoData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVEpisodeVideo> call, Throwable t) {
//                tvEpisodeVideoData.postValue(null);
//            }
//        });
//    }
//
//    public void callTVEpisodeCredit(int id,int seasonNumber,int episodeNumber){
//        Call<TVEpisodeCredit> call=apiService.getTVEpisodeCredits(id,seasonNumber,episodeNumber);
//        call.enqueue(new Callback<TVEpisodeCredit>() {
//            @Override
//            public void onResponse(Call<TVEpisodeCredit> call, Response<TVEpisodeCredit> response) {
//                if(response.code()==200){
//                    tvEpisodeCreditData.postValue(response.body());
//                }else{
//                    Log.e("TAG","An error has occurred "+response.code()+": "+response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TVEpisodeCredit> call, Throwable t) {
//                tvEpisodeCreditData.postValue(null);
//            }
//        });
//    }
}
