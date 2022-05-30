package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Provider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ApodPresenter {

    private final Provider<ApodFrame> viewProvider;
    private final AstronomyPicOfDayService model;
    private Disposable disposable;

    private URL url;

    @Inject
    public ApodPresenter(Provider<ApodFrame> viewProvider, AstronomyPicOfDayService model) {
        this.viewProvider = viewProvider;
        this.model = model;
    }

    public void loadFromDate(String date) {

        disposable = model.getApod(date)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(ApodData apodData) {
        url = apodData.getUrl();

        BufferedImage image = null;

        if (apodData.getMediaType().equals("image")) {
            try {
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            viewProvider.get().setPhoto(image);
        } else {
            viewProvider.get().setVideoUrl(url);
        }

        String photoDescription = apodData.getDescription();
        viewProvider.get().setDescription(photoDescription);

        String photoTitle = apodData.getTitle();
        viewProvider.get().setPhotoTitle(photoTitle);


    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


    public void downloadPhoto() {
        try {
            File desktop = new File(System.getProperty("user.home"), "/Desktop");
            FileUtils.copyURLToFile(url, new File
                    (desktop, "apod " + viewProvider.get().datePicker.getDate()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


