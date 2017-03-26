package arbetsprov.knowit.com.spotifyfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;

import arbetsprov.knowit.com.spotifyfinder.model.Track;
import arbetsprov.knowit.com.spotifyfinder.utils.CustomVolleyRequest;


/**
 * Created by Hichem Memmi on 2017-03-26.
 */

public class ListTrackAdapter extends BaseAdapter {

    private ImageLoader imageLoader;
    private Activity listTrack;
    private HashMap<Integer,Track> trackHashMap;
    private static LayoutInflater inflater=null;

    public ListTrackAdapter(Activity listTrack, HashMap<Integer,Track> trackHashMap) {
        this.listTrack=listTrack;
        this.trackHashMap=trackHashMap;
        inflater = (LayoutInflater)listTrack.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return trackHashMap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.track_item, null);

        TextView songName = (TextView)view.findViewById(R.id.songName); // title
        TextView artistName = (TextView)view.findViewById(R.id.artistName); // artist name
        NetworkImageView thumb_image=(NetworkImageView)view.findViewById(R.id.songAlbumImage); // thumb image


        imageLoader = CustomVolleyRequest.getInstance(view.getContext())
                .getImageLoader();
        imageLoader.get(this.trackHashMap.get(position).getImage().getUrlSmallImage(), ImageLoader.getImageListener(thumb_image,
                R.drawable.image, android.R.drawable
                        .ic_dialog_alert));
        thumb_image.setImageUrl(this.trackHashMap.get(position).getImage().getUrlSmallImage(), imageLoader);


        songName.setText(this.trackHashMap.get(position).getName());
        artistName.setText(this.trackHashMap.get(position).getArtist().getName());
        return view;
    }
}
