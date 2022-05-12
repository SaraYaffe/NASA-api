package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class APODPresenter {

    private final APODFrame view;
    private final AstronomyPicOfDayService model;
    private Disposable disposable;

    public APODPresenter(APODFrame view, AstronomyPicOfDayService model){
        this.view = view;
        this.model = model;
    }

    public void loadPhotoFromDate(String date){
        //parse date field text to correct format
        String formattedDate;

        disposable = model.getAPOD(date) //formattedDate
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(ApodData apodData) {
        String urlPhoto = apodData.getUrl();
        view.setPhoto(urlPhoto);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

}


