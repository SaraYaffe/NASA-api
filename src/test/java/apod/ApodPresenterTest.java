package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.*;

class ApodPresenterTest {

    ApodFrame view = mock(ApodFrame.class);
    AstronomyPicOfDayService model = mock(AstronomyPicOfDayService.class);
    Provider<ApodFrame> viewProvider = () -> view;
    ApodPresenter presenter = new ApodPresenter(viewProvider, model);

    ApodData apodData = mock(ApodData.class);

    @BeforeAll
    static void beforeAllTests() {
        //runs before all tests in this class
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    void loadFromDate_photo() throws IOException {
        //given
        doReturn(new URL("https://apod.nasa.gov/apod/image/2205/DiamondMoonWSMALL1024.jpg")).when(apodData).getUrl();
        doReturn("NGC 1316: After Galaxies Collide").when(apodData).getTitle();
        doReturn("image").when(apodData).getMediaType();
        doReturn(Single.just(apodData))
                .when(model).getApod("2022-05-17");


        //when
        presenter.loadFromDate("2022-05-17");

        //then
        verify(view).setPhoto(any(BufferedImage.class));
        verify(view).setPhotoTitle("NGC 1316: After Galaxies Collide");
    }

    void loadFromDate_video() throws IOException {
        //given
        doReturn(new URL("https://www.youtube.com/embed/aKK7vS2CHC8?rel=0")).when(apodData).getUrl();
        doReturn("A Martian Eclipse: Phobos Crosses the Sun").when(apodData).getTitle();
        doReturn(Single.just(apodData))
                .when(model).getApod("2022-05-09");


        //when
        presenter.loadFromDate("2022-05-09");

        //then
        verify(view).setVideoUrl(new URL("https://www.youtube.com/embed/aKK7vS2CHC8?rel=0"));
        verify(view).setPhotoTitle("A Martian Eclipse: Phobos Crosses the Sun");
    }
}