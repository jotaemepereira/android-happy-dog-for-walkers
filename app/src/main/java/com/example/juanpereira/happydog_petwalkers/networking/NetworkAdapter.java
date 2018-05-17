package com.example.juanpereira.happydog_petwalkers.networking;

import com.example.juanpereira.happydog_petwalkers.BuildConfig;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkAdapter {

    public static NetworkService getNetworkService() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        JacksonConverterFactory factory = JacksonConverterFactory.create(objectMapper);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(factory)
                .build();

        return retrofit.create(NetworkService.class);
    }
}
