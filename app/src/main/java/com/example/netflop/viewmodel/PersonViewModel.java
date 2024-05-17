package com.example.netflop.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.models.CombinedCredit;
import com.example.netflop.data.models.Credit;
import com.example.netflop.data.models.Person;
import com.example.netflop.data.models.PersonDetail;
import com.example.netflop.data.models.PersonImages;
import com.example.netflop.data.services.APIClient;
import com.example.netflop.data.services.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonViewModel extends ViewModel {
    private MutableLiveData<Person> personData;
    private MutableLiveData<PersonDetail> personDetailData;
    private MutableLiveData<PersonImages> personImageData;
    private MutableLiveData<CombinedCredit> combinedCreditData;
    public PersonViewModel(){
        personData=new MutableLiveData<>();
        personDetailData=new MutableLiveData<>();
        personImageData=new MutableLiveData<>();
        combinedCreditData=new MutableLiveData<>();

    }
    public MutableLiveData<CombinedCredit> getCombinedCreditData(){
        return  combinedCreditData;
    }
    public MutableLiveData<Person> getPersonData(){
        return  personData;
    }
    public MutableLiveData<PersonDetail> getPersonDetailData(){
        return  personDetailData;
    }
    public MutableLiveData<PersonImages> getPersonImageData(){
        return  personImageData;
    }
    public void callAPIPersonDetailByPersonID(int personID){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<PersonDetail> call=apiService.getPersonDetail(personID);
        call.enqueue(new Callback<PersonDetail>() {
            @Override
            public void onResponse(Call<PersonDetail> call, Response<PersonDetail> response) {
                if(response.code()==200){
                    personDetailData.postValue(response.body());
                }else{
                    Log.e("callAPIPersonDetail","Error has occurred: "+response.message());
                    personDetailData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PersonDetail> call, Throwable t) {
                personDetailData.postValue(null);
            }
        });
    }
    public void callAPIPersonImageByPersonID(int personID){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<PersonImages> call=apiService.getPersonImages(personID);
        call.enqueue(new Callback<PersonImages>() {
            @Override
            public void onResponse(Call<PersonImages> call, Response<PersonImages> response) {
                if(response.code()==200){
                    personImageData.postValue(response.body());
                }else{
                    Log.e("callAPIPersonImage","Error has occurred: "+response.message());
                    personImageData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PersonImages> call, Throwable t) {
                personImageData.postValue(null);
            }
        });
    }

    public void callAPICombinedCreditByPersonID(int personID){
        APIService apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
        Call<CombinedCredit> call=apiService.getCombinedCreditsOfPerson(personID);
        call.enqueue(new Callback<CombinedCredit>() {
            @Override
            public void onResponse(Call<CombinedCredit> call, Response<CombinedCredit> response) {
                if(response.code()==200){
                    combinedCreditData.postValue(response.body());
                }else{
                    Log.e("callAPICombinedCredit","Error has occurred: "+response.message());
                    combinedCreditData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CombinedCredit> call, Throwable t) {
                combinedCreditData.postValue(null);
            }
        });
    }
}
