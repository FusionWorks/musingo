package iis.production.musingo.objects;

import android.graphics.Bitmap;

/**
 * Created by dima on 1/28/14.
 */
public class Song {
    private String songName;
    private String time;
    private String artistName;
    private Bitmap image;
    private String id;
    private String uuid;
    private String mp3URL;

    public Song(String id, String uuid, Bitmap image, String songName, String artistName, String time, String mp3URL){
        this.id = id;
        this.image = image;
        this.songName = songName;
        this.artistName = artistName;
        this.time = time;
        this.uuid = uuid;
        this.mp3URL = mp3URL;
    }

    public String getSongName(){
        return songName;
    }

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return time;
    }

    public String getArtistName(){
        return artistName;
    }

    public Bitmap getImage(){
        return image;
    }

    public String getId (){
        return id;
    }

    public String getmp3Url(){
        return mp3URL;
    }

    public String getUuid(){
        return uuid;
    }

}
