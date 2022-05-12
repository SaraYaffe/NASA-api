package apod.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AstronomyPicOfDayService {

    String api_key = "DEMO_KEY";

    @GET("planetary/apod?api_key=" + api_key)
    Single<ApodData> getAPOD(@Query("date") String date);

}
