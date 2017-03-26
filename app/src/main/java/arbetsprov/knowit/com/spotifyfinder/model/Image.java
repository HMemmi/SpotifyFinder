package arbetsprov.knowit.com.spotifyfinder.model;

import java.io.Serializable;

/**
 * Created by Hichem Memmi on 2017-03-26.
 */

public class Image implements Serializable {

    private String urlSmallImage;
    private String urlBigImage;

    public Image(String urlSmallImage, String urlBigImage){
        this.urlSmallImage=urlSmallImage;
        this.urlBigImage=urlBigImage;
    }

    public String getUrlSmallImage() {
        return urlSmallImage;
    }

    public void setUrlSmallImage(String urlSmallImage) {
        this.urlSmallImage = urlSmallImage;
    }

    public String getUrlBigImage() {
        return urlBigImage;
    }

    public void setUrlBigImage(String urlBigImage) {
        this.urlBigImage = urlBigImage;
    }





}
