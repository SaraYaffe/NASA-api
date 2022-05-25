package apod;

import apod.service.AstronomyPicOfDayServiceFactory;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;

/*
To Do:
readme file
---video url
---downloadable photo
add screenshots to readme, re-run jar file
* */

public class ApodFrame extends JFrame {

    private final DatePicker datePicker;
    private final DatePickerSettings dateSettings;
    private final LocalDate firstApod = LocalDate.of(1995, 6, 16);


    private JLabel photo;
    private JLabel photoTitle;
    private JTextArea description;
    private JButton downloadButton;
    private JLabel video;

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

        video = new JLabel();
        photoPanel.add(video);

        downloadButton = new JButton("Save Photo");
        downloadButton.addActionListener(this::onSubmitClicked);
        topPanel.add(downloadButton);


    }

    private void onDateChosen(DateChangeEvent dateChangeEvent) {
        presenter.loadFromDate(datePicker.getDate().toString());
    }

    public void setPhoto(BufferedImage image) {
        video.setVisible(false);
        photo.setVisible(true);
        Image scaledImage = image.getScaledInstance(650, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        photo.setIcon(imageIcon);
        downloadButton.setVisible(true);
    }

    public void setVideoUrl(URL videoUrl) {   //May 9, 2022
        String videoStr = videoUrl.toString();
        photo.setVisible(false);
        downloadButton.setVisible(false);
        video.setVisible(true);
        video.setForeground(Color.BLUE);
        video.setText(videoStr);
        video.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        video.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(videoStr);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

    }



    public void setDescription(String photoDescription) {

        description.setText(photoDescription);
    }

    public void setPhotoTitle(String apodTitle) {

        photoTitle.setText(apodTitle);
    }

    private void onSubmitClicked(ActionEvent e) {
        presenter.download();

    }


    public static void main(String[] args) {
        JFrame frame = new ApodFrame();
        frame.setVisible(true);
    }


}
