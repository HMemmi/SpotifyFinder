package arbetsprov.knowit.com.spotifyfinder.model;



import java.io.Serializable;

/**
 * Created by Hichem Memmi on 2017-03-26.
 */

public class Track implements Serializable {
    private String name;
    private Artist artist;
    private Image image;
    private String id;


    public Track(String id, String name, Artist artist, Image image){
        this.id=id;
        this.name=name;
        this.artist=artist;
        this.image=image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
