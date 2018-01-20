package pennapps.com.kukuoke;

import android.support.annotation.NonNull;

/**
 * Created by zyud on 1/20/2018.
 */

public class Song implements Comparable<Song> {
    private String songName;
    private String artistName;

    public Song(String songName, String artistName){
        this.songName = songName;
        this.artistName = artistName;
    }

    @Override
    public int compareTo(@NonNull Song otherSong) {
        if (songName.equals(otherSong.getSongName()) && artistName.equals(otherSong.getArtistName())){
            return 0;
        } else {
            return -1;
        }
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }
}
