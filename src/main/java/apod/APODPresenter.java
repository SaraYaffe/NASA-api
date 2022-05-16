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

    public void loadFromDate(String date){

        disposable = model.getAPOD(date)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(ApodData apodData) {
        String photoUrl = apodData.getUrl();
        view.setPhoto(photoUrl);

        String photoDescription = apodData.getDescription();
        view.setDescription(photoDescription);

        String photoTitle = apodData.getTitle();
        view.setPhotoTitle(photoTitle);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


}


