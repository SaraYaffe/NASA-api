package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class APODPresenter {

    private final APODFrame view;
    private final AstronomyPicOfDayService model;
    private Disposable disposable;

    public APODPresenter(APODFrame view, AstronomyPicOfDayService model) {
        this.view = view;
        this.model = model;
    }

    public void loadFromDate(String date) {

        disposable = model.getAPOD(date)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(ApodData apodData) throws IOException {
        URL photoUrl = apodData.getUrl();
        BufferedImage image = null;
        try {
            image = ImageIO.read(photoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            view.setPhoto(image);
        }
        //if media type is not image, will get null pointer exception? get pic from day before instead

        String photoDescription = apodData.getDescription();
        view.setDescription(photoDescription);

        String photoTitle = apodData.getTitle();
        view.setPhotoTitle(photoTitle);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


}


