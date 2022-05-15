package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import com.github.lgooddatepicker.components.DatePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

/*
TO DO:
* fix presenter test class
* add google checks file
* gui
    resize photo
    print out description properly
    general layout
* horoscope api
* */


public class APODFrame extends JFrame {

    private final DatePicker datePicker;
    private final JButton submit;
    private JLabel photo;
    private JLabel description;


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

        description = new JLabel();
        add(description);

        photo = new JLabel();
        add(photo);


        AstronomyPicOfDayServiceFactory factory = new AstronomyPicOfDayServiceFactory();
        presenter = new APODPresenter(this, factory.getInstance());

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

    public void setDescription(String photoDescription) {

        description.setText(photoDescription);
    }


    public static void main(String[] args) {
        JFrame frame = new APODFrame();
        frame.setVisible(true);
    }

}
