package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
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
    private JTextArea description;
    private JButton prevYear;
    private JButton nextYear;

    private JPanel photoPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;



    private final APODPresenter presenter;


    public APODFrame() {

        setTitle("Astronomy Picture of the Day");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        topPanel = new JPanel();
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        photoPanel = new JPanel(new FlowLayout());
        leftPanel = new JPanel(new FlowLayout());
        rightPanel = new JPanel(new FlowLayout());
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(photoPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);


        datePicker = new DatePicker();
        topPanel.add(datePicker);

        submit = new JButton("Enter Date");
        submit.addActionListener(this::onSubmitClick);
        topPanel.add(submit);

        photoTitle = new JLabel();
        photoTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        bottomPanel.add(photoTitle);

        description = new JTextArea();
        Border border = BorderFactory.createLineBorder(Color.white);
        description.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 20, 20)));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setBackground(UIManager.getColor("Label.background"));
        bottomPanel.add(description);

        photo = new JLabel();
        photoPanel.add(photo);

        prevYear = new JButton("Previous Year");
        prevYear.addActionListener(this::onSubmitClickPrev);
        leftPanel.add(prevYear, BorderLayout.WEST);

        nextYear = new JButton("Following Year");
        nextYear.addActionListener(this::onSubmitClickNext);
        rightPanel.add(nextYear, BorderLayout.EAST);

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

    public void setPhoto(BufferedImage image){
        Image scaledImage = image.getScaledInstance(650, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        photo.setIcon(imageIcon);
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
