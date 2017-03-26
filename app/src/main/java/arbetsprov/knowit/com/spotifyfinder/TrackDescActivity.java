package arbetsprov.knowit.com.spotifyfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arbetsprov.knowit.com.spotifyfinder.model.Track;
import arbetsprov.knowit.com.spotifyfinder.utils.CustomVolleyRequest;


/**
 * Created by Hichem Memmi on 2017-03-26.
 */

public class TrackDescActivity extends AppCompatActivity {

    private TextView songName;
    private TextView artistName;
    private NetworkImageView songImage;
    private NetworkImageView artistImage;
    private ImageLoader imageLoader;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.track_desc);

        Intent it = getIntent();
        Track track = (Track) it.getSerializableExtra("track");
        songName = (TextView) findViewById(R.id.songNameDesc);
        artistName = (TextView) findViewById(R.id.artistNameDesc);
        songImage = (NetworkImageView) findViewById(R.id.songImage);
        artistImage = (NetworkImageView) findViewById(R.id.artistImage);

        songName.setText(track.getName());
        artistName.setText(track.getArtist().getName());


        loadImage(track.getImage().getUrlBigImage(), songImage);

        String url = track.getArtist().getArtistUrl();
        RequestQueue queue = Volley.newRequestQueue(TrackDescActivity.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ArtistImages = response.getJSONArray("images");
                            JSONObject imageObject = (JSONObject) ArtistImages.get(0);
                            String imageUrl = imageObject.getString("url");
                            loadImage(imageUrl, artistImage);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Nothing!", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    private void loadImage(String urlImage, NetworkImageView networkImageView) {

        imageLoader = CustomVolleyRequest.getInstance(getBaseContext())
                .getImageLoader();
        imageLoader.get(urlImage, ImageLoader.getImageListener(networkImageView,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        networkImageView.setImageUrl(urlImage, imageLoader);

    }
}
