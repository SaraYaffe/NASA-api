package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApodPresenterTest {

    @Test
    void loadFromDate() {
//        //given
//        APODFrame view = mock(APODFrame.class);
//        AstronomyPicOfDayService model = mock(AstronomyPicOfDayService.class);
//        APODPresenter presenter = new APODPresenter(view, model);
//
//        ApodData apodData = mock(ApodData.class);
//
//        doReturn().when(apodData).getUrl();
//        doReturn().when(apodData).getTitle();
//        doReturn().when(apodData).getDescription();
//        doReturn(Single.just(apodData))
//                .when(model).getAPOD();
//
//
//        //when
//        //presenter.loadFromDate("2022-05-10");
//
//        //then
//        verify(view).setPhoto();
//        verify(view).setTitle();
//        verify(view).setDescription();
    }
}