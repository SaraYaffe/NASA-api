package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import com.github.lgooddatepicker.components.DatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

/*
TO DO:
* fix presenter test class
* gui
    resize photo
    format title and description properly
    general layout
* fix checkstyle error
* horoscope api - find alternative or ditch it
* */


public class APODFrame extends JFrame {

    private final DatePicker datePicker;
    private final JButton submit;
    private JLabel photo;
    private JLabel photoTitle;
    private JLabel description;
    private JButton prevYear;
    private JButton nextYear;


    private final APODPresenter presenter;


    public APODFrame() {

        setTitle("Astronomy Picture of the Day");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        datePicker = new DatePicker();
        add(datePicker);

        submit = new JButton("enter date");
        submit.addActionListener(this::onSubmitClick);
        add(submit);

        photoTitle = new JLabel();
        add(photoTitle);

        description = new JLabel();
        add(description);

        photo = new JLabel();
        add(photo);

        prevYear = new JButton("Previous");
        prevYear.addActionListener(this::onSubmitClickPrev);
        add(prevYear);

        nextYear = new JButton("Next");
        nextYear.addActionListener(this::onSubmitClickNext);
        add(nextYear);

        AstronomyPicOfDayServiceFactory factory = new AstronomyPicOfDayServiceFactory();
        presenter = new APODPresenter(this, factory.getInstance());

    }

    private void onSubmitClickPrev(ActionEvent actionEvent) {
        LocalDate newDate = datePicker.getDate().minusYears(1);
        datePicker.setDate(newDate);
        presenter.loadFromDate(datePicker.getDate().toString());
    }

    private void onSubmitClickNext(ActionEvent actionEvent) {
        LocalDate newDate = datePicker.getDate().plusYears(1);
        datePicker.setDate(newDate);
        presenter.loadFromDate(datePicker.getDate().toString());
    }

    private void onSubmitClick(ActionEvent actionEvent) {
        presenter.loadFromDate(datePicker.getDate().toString());
    }

    public void setPhoto(String photoUrl){
        try {
            URL url = new URL(photoUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIcon imageIcon = new ImageIcon(image);
            photo.setIcon(imageIcon);

        } catch (Exception exp) {
            //if media type is not image, will get null pointer exception? get pic from day before instead
            exp.printStackTrace();
        }
    }

//    public void setVideo(String videoURL) {
//
//    }

    public void setDescription(String photoDescription) {

        description.setText(photoDescription);
    }

    public void setPhotoTitle(String apodTitle) {

        photoTitle.setText(apodTitle);
    }


    public static void main(String[] args) {
        JFrame frame = new APODFrame();
        frame.setVisible(true);
    }

}
