package apod.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;

public class AstronomyPicOfDayServiceFactory {

    @Inject
    public AstronomyPicOfDayServiceFactory(){

    }

    public AstronomyPicOfDayService getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(AstronomyPicOfDayService.class);
    }
}
