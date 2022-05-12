package apod;

import apod.service.AstronomyPicOfDayService;

public class APODPresenter {

    private final APODFrame view;
    private final AstronomyPicOfDayService model;

    public APODPresenter(APODFrame view, AstronomyPicOfDayService model){
        this.view = view;
        this.model = model;
    }

}
