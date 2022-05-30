package apod.dagger;

import apod.service.AstronomyPicOfDayService;
import apod.service.AstronomyPicOfDayServiceFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApodDataModule {

    @Singleton
    @Provides
    public AstronomyPicOfDayService providesAstronomyPicOfDayService(
            AstronomyPicOfDayServiceFactory factory){
        return factory.getInstance();
    }

}
