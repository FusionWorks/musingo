package iis.production.musingo.objects;

import android.support.v7.appcompat.R;

/**
 * Created by dima on 1/28/14.
 */
public class Song {
    private String songName;
    private String time;
    private String artistName;
    private Integer imageId;

    public Song(Integer imageId, String songName, String artistName, String time){
        this.imageId = imageId;
        this.songName = songName;
        this.artistName = artistName;
        this.time = time;
    }

    public void setSongName(String songName){
        this.songName = songName;
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

    public void setArtistName(String artistName){
        this.artistName = artistName;
    }
    public String getArtistName(){
        return artistName;
    }

    public void setImageId (Integer imageId){
        this.imageId = imageId;
    }
    public Integer getImageId(){
        return imageId;
    }
}
