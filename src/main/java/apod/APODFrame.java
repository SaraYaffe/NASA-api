package apod;

import apod.service.ApodData;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;


public class APODFrame extends JFrame {

    Disposable disposable;

    private final JLabel datePrompt;
    private final JTextField dateField;
    private final JButton submit;
    private JLabel photo;

    private GetAPOD getAPOD = new GetAPOD();
    private final APODPresenter presenter;


    public APODFrame(){

        //fix layout - separate text fields for day/month/year
        //validate user input

        presenter = new APODPresenter(this, getAPOD);

        setTitle("Astronomy Picture of the Day");
        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        datePrompt = new JLabel("enter your birthday");
        datePrompt.setPreferredSize(new Dimension(40, 20));
        add(datePrompt);

        dateField = new JTextField();
        dateField.setPreferredSize(new Dimension(40, 30));
        add(dateField);

        submit = new JButton("submit");
        submit.addActionListener(this::onSubmitClick);
        add(submit);

//        photo = new JLabel();
//        add(photo);

    }

    private void onSubmitClick(ActionEvent actionEvent) {

        //parse date field to correct format
        String formattedDate;

        disposable = getAPOD.getApod("2022-05-04") //formattedDate
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }
    public void cancel(){
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void onNext(ApodData apodData) {
        String picture = apodData.getUrl();

        try {
            URL url = new URL(picture);
            BufferedImage image = ImageIO.read(url);
            photo = new JLabel(new ImageIcon(image));
            add(photo);
            photo.setVisible(true);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


    public static void main(String[] args){
        JFrame frame = new APODFrame();
        frame.setVisible(true);
    }

}
