package apod.service;

import com.google.gson.annotations.SerializedName;

public class ApodData {

    String date;
    @SerializedName("explanation")
    String description;
    @SerializedName("media_type")
    String mediaType;
    String title;
    String url;

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

    public String getUrl() {
        return url;
    }



}
