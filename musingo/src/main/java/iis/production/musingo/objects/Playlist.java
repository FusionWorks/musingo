package iis.production.musingo.objects;

import android.graphics.Bitmap;

/**
 * Created by AGalkin on 2/10/14.
 */
public class Playlist {
    private String name;
    private String genre;
    int listNumber;
    int scoreToBeat;
    int order;
    private Bitmap image;

    public Playlist(String name, String genre, int listNumber, int scoreToBeat, int order, Bitmap image){
        this.name = name;
        this.genre = genre;
        this.listNumber = listNumber;
        this.scoreToBeat = scoreToBeat;
        this.order = order;
        this.image = image;
    }

    public int getListNumber(){
        return listNumber;
    }

    public int getScoreToBeat(){
        return scoreToBeat;
    }

    public int getOrder(){
        return order;
    }

    public String getName(){
        return name;
    }

    public Bitmap getImage(){
        return image;
    }

    public String getGenre(){
        return genre;
    }
}
