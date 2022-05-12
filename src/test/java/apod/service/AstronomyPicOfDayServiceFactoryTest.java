package apod.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstronomyPicOfDayServiceFactoryTest {

    @Test
    void getInstance() {
        //given
        AstronomyPicOfDayServiceFactory factory = new AstronomyPicOfDayServiceFactory();
        AstronomyPicOfDayService service = factory.getInstance();

        //when
        ApodData apodData = service.getAPOD("2022-05-10")
                .blockingGet();

        //then
        assertNotNull(apodData.description);
        assertNotNull(apodData.url);
    }
}