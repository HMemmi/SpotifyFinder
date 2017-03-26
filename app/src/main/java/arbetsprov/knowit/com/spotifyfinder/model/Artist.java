package arbetsprov.knowit.com.spotifyfinder.model;

import java.io.Serializable;

/**
 * Created by Hichem Memmi on 2017-03-26.
 */

public class Artist implements Serializable {
    private String name;
    private String artistUrl;

    public Artist(String name, String artistUrl) {
        this.name = name;
        this.artistUrl = artistUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }


}
