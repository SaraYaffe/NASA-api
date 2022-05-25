package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

/*
To Do:
readme file
provide video url
downloadable photo
add screenshots to readme, re-run jar file
* */

public class ApodFrame extends JFrame {

    private final DatePicker datePicker;
    private final DatePickerSettings dateSettings;
    private final LocalDate firstApod = LocalDate.of(1995, 6, 16);

    private JLabel photo;
    private JLabel photoTitle;
    private JTextArea description;

    private JPanel photoPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;


    private final ApodPresenter presenter;


    public ApodFrame() {

        AstronomyPicOfDayServiceFactory factory = new AstronomyPicOfDayServiceFactory();
        presenter = new ApodPresenter(this, factory.getInstance());

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

        dateSettings = new DatePickerSettings();

        datePicker = new DatePicker(dateSettings);
        dateSettings.setDateRangeLimits(firstApod, LocalDate.now());
        datePicker.setDateToToday();
        presenter.loadFromDate(datePicker.getDate().toString());
        datePicker.addDateChangeListener(this::onDateChosen);
        topPanel.add(datePicker);

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

    }

    private void onDateChosen(DateChangeEvent dateChangeEvent) {
        presenter.loadFromDate(datePicker.getDate().toString());
    }

    public void setPhoto(BufferedImage image) {
        Image scaledImage = image.getScaledInstance(650, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        photo.setIcon(imageIcon);
    }

    public void setDescription(String photoDescription) {

        description.setText(photoDescription);
    }

    public void setPhotoTitle(String apodTitle) {

        photoTitle.setText(apodTitle);
    }


    public static void main(String[] args) {
        JFrame frame = new ApodFrame();
        frame.setVisible(true);
    }

}
