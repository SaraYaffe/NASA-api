package apod;

public class APODPresenter {

    private final APODFrame view;
    private final GetAPOD model;

    public APODPresenter(APODFrame view, GetAPOD model){
        this.view = view;
        this.model = model;
    }

}
