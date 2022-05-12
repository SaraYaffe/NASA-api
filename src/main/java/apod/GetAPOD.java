package apod;

import apod.service.ApodData;
import apod.service.AstronomyPictureOfDayService;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAPOD {

    private final AstronomyPictureOfDayService service;

    public GetAPOD(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(AstronomyPictureOfDayService.class);
    }

    public Observable<ApodData> getApod(String date){
        return service.getAPOD(date);
    }


}
