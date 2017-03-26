package arbetsprov.knowit.com.spotifyfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import arbetsprov.knowit.com.spotifyfinder.model.Artist;
import arbetsprov.knowit.com.spotifyfinder.model.Image;
import arbetsprov.knowit.com.spotifyfinder.model.Track;


/**
 * Created by Hichem Memmi on 2017-03-25.
 */

public class SearchActivity extends AppCompatActivity {


    public static final String TRACK_LIST = "trainingspotify.com.trainingspotify.HashMap";
    private Button searchButton;
    private EditText songNameEdit;
    private EditText artistEdit;
    private EditText limitEdit;
    private ProgressDialog sDialog;
    private int limit = 0;
    private String songName;
    private Intent it;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.search_music);
        createComponents();

        it = new Intent(this.getBaseContext(), ListTrack.class);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songName = songNameEdit.getText().toString();
                songName = songName.replaceAll(" ", "+");

                if (limitEdit.getText().length() > 0) {
                    String limitString = limitEdit.getText().toString();
                    limitString = limitString.replaceAll(" ", "");
                    limit = Integer.valueOf(limitString);
                }

                new Search().execute();

            }
        });
    }

    private void createComponents() {
        searchButton = (Button) findViewById(R.id.search_button);
        songNameEdit = (EditText) findViewById(R.id.song);
        limitEdit = (EditText) findViewById(R.id.limit);
    }

    private class Search extends AsyncTask<Void, Void, Void> {

        private HashMap<Integer, Track> trackHashMap = new HashMap<>();
        private String limitUrl;


        protected void onPreExecute() {
            super.onPreExecute();
            sDialog = new ProgressDialog(SearchActivity.this);
            sDialog.setMessage("Please wait ...");
            sDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {

            limitUrl = (limit > 0) ? "&limit=" + limit : "";
            String url = "https://api.spotify.com/v1/search?q=" + songName + "&type=track" + limitUrl;
            RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String artistName = null;
                                if (limitUrl.equals("")) {
                                    limit = 20; //Eftersom, per default limit är 20
                                }

                                for (int i = 0; i < limit; i++) {
                                    JSONObject tracksObject = response.getJSONObject("tracks");
                                    JSONArray itemsArray = tracksObject.getJSONArray("items");
                                    JSONObject elementArray = (JSONObject) itemsArray.get(i);
                                    JSONObject albumObject = elementArray.getJSONObject("album");
                                    JSONArray artistArray = albumObject.getJSONArray("artists");
                                    JSONObject artists = artistArray.getJSONObject(0);
                                    artistName = artists.getString("name");
                                    String artistUrl = artists.getString("href");


                                    String id = albumObject.getString("id");


                                    JSONArray imagesArray = albumObject.getJSONArray("images");
                                    JSONObject imagesSmallObject = (JSONObject) imagesArray.get(1);
                                    String smallImage = imagesSmallObject.getString("url");

                                    JSONObject imagesBigObject = (JSONObject) imagesArray.get(0);
                                    String bigImage = imagesBigObject.getString("url");


                                    String nameSong = albumObject.getString("name");
                                    System.out.println(nameSong);
                                    trackHashMap.put(i, new Track(id, nameSong, new Artist(artistName, artistUrl), new Image(smallImage, bigImage)));
                                }

                                if (trackHashMap.size() > 0) {
                                    it.putExtra(TRACK_LIST, trackHashMap);
                                    startActivity(it);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String msg = (limit > 50) ? "Nothing, Limit should not be more than 50" :"Nothing!";
                    Toast.makeText(getBaseContext(),msg , Toast.LENGTH_LONG).show();
                }
            });

            queue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (sDialog.isShowing()) {
                sDialog.dismiss();
            }
        }


    }


}


