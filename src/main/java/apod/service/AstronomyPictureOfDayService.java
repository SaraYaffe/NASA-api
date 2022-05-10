package apod.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AstronomyPictureOfDayService {

    String api_key = "DEMO_KEY";

    @GET("planetary/apod?api_key=" + api_key)
    Observable<ApodData> getAPOD(@Query("date") String date);

}
