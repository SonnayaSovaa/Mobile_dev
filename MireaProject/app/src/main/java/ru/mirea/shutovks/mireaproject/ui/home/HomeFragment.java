package ru.mirea.shutovks.mireaproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.shutovks.mireaproject.R;
import ru.mirea.shutovks.mireaproject.databinding.FragmentHomeBinding;
import ru.mirea.shutovks.mireaproject.network.HourlyWeatherResponse;
import ru.mirea.shutovks.mireaproject.network.OpenMeteoApi;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonCompass.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_compass));
        binding.buttonCamera.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_camera));
        binding.buttonMicrophone.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_microphone));

        loadHourlyWeather();

        return root;
    }

    private void loadHourlyWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenMeteoApi api = retrofit.create(OpenMeteoApi.class);

        Call<HourlyWeatherResponse> call = api.getHourlyTemperature(52.52, 13.41, "temperature_2m");

        call.enqueue(new Callback<HourlyWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<HourlyWeatherResponse> call,
                                   @NonNull Response<HourlyWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HourlyWeatherResponse body = response.body();
                    if (body.hourly != null
                            && body.hourly.time.size() > 0
                            && body.hourly.temperature_2m.size() > 0) {
                        String firstTime = body.hourly.time.get(0);                  // "2025-06-02T00:00"
                        Double firstTemp = body.hourly.temperature_2m.get(0);        // 17.1

                        binding.tvWeather.setText(
                                "Время: " + firstTime + "\n" +
                                        "Температура (°C): " + firstTemp
                        );
                    } else {
                        binding.tvWeather.setText("Данных о погоде нет");
                    }
                } else {
                    binding.tvWeather.setText("Ошибка HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HourlyWeatherResponse> call, @NonNull Throwable t) {
                binding.tvWeather.setText("Ошибка сети: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
