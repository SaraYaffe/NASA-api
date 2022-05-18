package apod.service;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class ApodData {

    String date;
    @SerializedName("explanation")
    String description;
    @SerializedName("media_type")
    String mediaType;
    String title;
    URL url;

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }



}
