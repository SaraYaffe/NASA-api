package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class ApodPresenter {

    private final ApodFrame view;
    private final AstronomyPicOfDayService model;
    private Disposable disposable;

    public ApodPresenter(ApodFrame view, AstronomyPicOfDayService model) {
        this.view = view;
        this.model = model;
    }

    public void loadFromDate(String date) {

        disposable = model.getApod(date)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(ApodData apodData) {
        URL photoUrl = apodData.getUrl();
        BufferedImage image = null;
        try {
            image = ImageIO.read(photoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setPhoto(image);

        String photoDescription = apodData.getDescription();
        view.setDescription(photoDescription);

        String photoTitle = apodData.getTitle();
        view.setPhotoTitle(photoTitle);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


}


