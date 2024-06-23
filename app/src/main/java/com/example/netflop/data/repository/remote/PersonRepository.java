package com.example.netflop.data.repository.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netflop.constants.URLConstants;
import com.example.netflop.data.data_source.remote_data_source.APIService;
import com.example.netflop.data.models.remote.movies.CombinedCredit;
import com.example.netflop.data.models.remote.people.PersonDetail;
import com.example.netflop.data.models.remote.people.PersonImages;
import com.example.netflop.data.services.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {
    private APIService apiService;

    public PersonRepository() {
        apiService= APIClient.getRetrofitInstance(URLConstants.baseURL).create(APIService.class);
    }
    public LiveData<PersonDetail> getPersonDetailByPersonID(int personID) {
        MutableLiveData<PersonDetail> mutableLiveData = new MutableLiveData<>();
        Call<PersonDetail> call=apiService.getPersonDetail(personID);
        call.enqueue(new Callback<PersonDetail>() {
            @Override
            public void onResponse(Call<PersonDetail> call, Response<PersonDetail> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PersonDetail> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<PersonImages> getPersonImagesByPersonID(int personID) {
        MutableLiveData<PersonImages> mutableLiveData = new MutableLiveData<>();
        Call<PersonImages> call=apiService.getPersonImages(personID);
        call.enqueue(new Callback<PersonImages>() {
            @Override
            public void onResponse(Call<PersonImages> call, Response<PersonImages> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PersonImages> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
    public LiveData<CombinedCredit> getCombinedCreditByPersonID(int personID) {
        MutableLiveData<CombinedCredit> mutableLiveData = new MutableLiveData<>();
        Call<CombinedCredit> call=apiService.getCombinedCreditsOfPerson(personID);
        call.enqueue(new Callback<CombinedCredit>() {
            @Override
            public void onResponse(Call<CombinedCredit> call, Response<CombinedCredit> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CombinedCredit> call, Throwable t) {
                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }


}
