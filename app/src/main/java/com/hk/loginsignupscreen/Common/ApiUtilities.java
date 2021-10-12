package com.hk.loginsignupscreen.Common;

import com.hk.loginsignupscreen.Common.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    public static Retrofit retrofit = null;


    public static com.hk.loginsignupscreen.Common.ApiInterface getAPIInterface() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(com.hk.loginsignupscreen.Common.ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
