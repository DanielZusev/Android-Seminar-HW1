package com.daniel.androidseminar_hw1.utils.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryAPI {

    @GET("all")
    Call<List<Country>> getAllCountries();

    @GET("alpha")
    Call<List<Country>> getBorderCountries(@Query("codes") String codes);
}
