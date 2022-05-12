package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

/*
TO DO:
* datepicker - add calender in new frame for user to choose date
* fix presenter test class
* add google checks file
* fix layout
* horoscope api
* */


public class APODFrame extends JFrame {

    private final JLabel datePrompt;
    private final JTextField dateField;
    private final JButton submit;
    private JLabel photo;
    private JLabel description;


    private final APODPresenter presenter;


    public APODFrame() {

        setTitle("Astronomy Picture of the Day");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        datePrompt = new JLabel("enter Date");
        datePrompt.setPreferredSize(new Dimension(40, 20));
        add(datePrompt);

        dateField = new JTextField();
        dateField.setPreferredSize(new Dimension(40, 30));
        add(dateField);

        submit = new JButton("submit");
        submit.addActionListener(this::onSubmitClick);
        add(submit);

        description = new JLabel();
        add(description);


        AstronomyPicOfDayServiceFactory factory
                = new AstronomyPicOfDayServiceFactory();
        presenter = new APODPresenter(this, factory.getInstance());

    }

    private void onSubmitClick(ActionEvent actionEvent) {
        presenter.loadFromDate("2022-05-10");
    }

    public void setPhoto(String photoUrl){
        try {
            URL url = new URL(photoUrl);
            BufferedImage image = ImageIO.read(url);
            photo = new JLabel(new ImageIcon(image));
            add(photo);
        } catch (Exception exp) {
            //if media type is not image, will get null pointer exception. get pic from day before instead
            exp.printStackTrace();
        }
    }

    public void setDescription(String photoDescription){
        description.setText(photoDescription);
    }



    public static void main(String[] args) {
        JFrame frame = new APODFrame();
        frame.setVisible(true);
    }

}
