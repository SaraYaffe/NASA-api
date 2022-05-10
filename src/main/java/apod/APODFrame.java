package apod;

import apod.service.ApodData;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class APODFrame extends JFrame {

    Disposable disposable;

    private final JLabel datePrompt;
    private final JTextField dateField;
    private final JButton submit;
    private final JLabel photo;

    private GetAPOD getAPOD = new GetAPOD();
    private final APODPresenter presenter;


    public APODFrame(){

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

        photo = new JLabel();
        add(photo);

    }

    private void onSubmitClick(ActionEvent actionEvent) {
        disposable = getAPOD.getApod(dateField.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(this::onNext, this::onError);
    }
    public void cancel(){
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void onNext(ApodData apodData){
        String picture = apodData.getDate();
        photo.setText(apodData.getUrl());
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


    public static void main(String[] args){
        JFrame frame = new APODFrame();
        frame.setVisible(true);
    }

}
