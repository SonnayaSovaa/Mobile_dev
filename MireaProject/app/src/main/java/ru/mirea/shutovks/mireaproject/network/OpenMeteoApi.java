package ru.mirea.shutovks.mireaproject.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoApi {
    @GET("v1/forecast")
    Call<HourlyWeatherResponse> getHourlyTemperature(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("hourly") String hourlyFields
    );
}
