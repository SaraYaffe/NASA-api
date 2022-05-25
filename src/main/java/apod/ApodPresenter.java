package apod;

import apod.service.ApodData;
import apod.service.AstronomyPicOfDayService;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ApodPresenter {

    private final ApodFrame view;
    private final AstronomyPicOfDayService model;
    private Disposable disposable;

    private URL url; //not sure this is where this should be

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
        url = apodData.getUrl();
        BufferedImage image = null;

        if (apodData.getMediaType().equals("image")) {
            try {
                image = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            view.setPhoto(image);

            String photoDescription = apodData.getDescription();
            view.setDescription(photoDescription);

            String photoTitle = apodData.getTitle();
            view.setPhotoTitle(photoTitle);
        } else {
            view.setVideoUrl(url);
        }


    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


    public void download() {
        try {
            File file = new File("C:\\Users\\sarab\\IdeaProjects\\nasa_api\\saved_photos\\photo1.jpg");
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


