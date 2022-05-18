package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static org.mockito.Mockito.*;

class ApodPresenterTest {

    ApodFrame view = mock(ApodFrame.class);
    AstronomyPicOfDayService model = mock(AstronomyPicOfDayService.class);
    ApodPresenter presenter = new ApodPresenter(view, model);

    ApodData apodData = mock(ApodData.class);

    @BeforeAll
    static void beforeAllTests(){
        //runs before all tests in this class
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    void loadFromDate() throws IOException {
        //given
        doReturn(new URL("https://apod.nasa.gov/apod/image/2205/DiamondMoonWSMALL1024.jpg")).when(apodData).getUrl();
        doReturn("NGC 1316: After Galaxies Collide").when(apodData).getTitle();
        doReturn(Single.just(apodData))
                .when(model).getApod("2022-05-17");


        //when
        presenter.loadFromDate("2022-05-17");

        //then
        //verify(view).setPhoto(ImageIO.read(new URL("https://apod.nasa.gov/apod/image/2205/DiamondMoonWSMALL1024.jpg")));
        verify(view).setPhotoTitle("NGC 1316: After Galaxies Collide");
    }
}