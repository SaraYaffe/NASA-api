package apod.dagger;

import apod.ApodFrame;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ApodDataModule.class})
public interface ApodDataComponent {

    ApodFrame getApodFrame();
}
