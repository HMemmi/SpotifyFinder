package arbetsprov.knowit.com.spotifyfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;

import arbetsprov.knowit.com.spotifyfinder.model.Track;

/**
 * Created by Hichem Memmi on 2017-03-25.
 */
public class ListTrack extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListTrackAdapter listTrackAdapter;
    private ProgressDialog pDialog;
    private HashMap<Integer, Track> trackHashMap;
    private ListView list;
    private Track track;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_track);

        list = (ListView) findViewById(R.id.listView);
        Intent it = getIntent();
        trackHashMap = (HashMap<Integer, Track>) it.getSerializableExtra(SearchActivity.TRACK_LIST);

        if (trackHashMap.size() > 0) {
            // new Auth().execute();
            listTrackAdapter = new ListTrackAdapter(ListTrack.this, trackHashMap);
            list.setAdapter(listTrackAdapter);
        }

        intent = new Intent(this, TrackDescActivity.class);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        track = trackHashMap.get(position);
        new Auth().execute();
    }


    private class Auth extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListTrack.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            //
        }

        @Override
        protected Void doInBackground(Void... params) {
            intent.putExtra("track", track);


            return null;
        }

        protected void onPostExecute(Void result) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            startActivity(intent);
        }


    }
}
