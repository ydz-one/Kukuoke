package pennapps.com.kukuoke;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds the songs of one user.
 * It used to be a Singleton class but had to be repurposed for pennapps demo purposes.
 * Will be replaced by Firebase data.
 * Created by zyud on 1/20/2018.
 */

public class UserSongs {
    private static UserSongs userSongsInstance = new UserSongs();

    private List<List<JSONObject>> allUserSongs;

    /**
     * private constructor for Singleton class.
     */
    public UserSongs(){
        List<JSONObject> tab1Songs = new ArrayList<>();
        List<JSONObject> tab2Songs = new ArrayList<>();
        List<JSONObject> tab3Songs = new ArrayList<>();

        allUserSongs = new ArrayList<>();
        allUserSongs.add(tab1Songs);
        allUserSongs.add(tab2Songs);
        allUserSongs.add(tab3Songs);
    }

    public static UserSongs getUserSongsInstance(){
        return userSongsInstance;
    }

    public void addSong(Context context, String songName, String artistName, int tabNum){
        try {
            JSONObject newSong = new JSONObject();
            newSong.put("name", songName);
            newSong.put("artist", artistName);

            int tabIndex = tabNum - 1;
            allUserSongs.get(tabIndex).add(newSong);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void addSong(String songName, String artistName, int tabNum){
        try {
            JSONObject newSong = new JSONObject();
            newSong.put("name", songName);
            newSong.put("artist", artistName);

            int tabIndex = tabNum - 1;
            allUserSongs.get(tabIndex).add(newSong);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void deleteSong(int songIndex, int tabNum){
        int tabIndex = tabNum - 1;
        allUserSongs.get(tabIndex).remove(songIndex);
    }

    public void moveSong(Context context, int songIndex, int tabNumSrc, int tabNumDest){
        try {
            JSONObject song = getSong(songIndex, tabNumSrc);
            addSong(context, song.get("name").toString(), song.get("artist").toString(), tabNumDest);
            deleteSong(songIndex, tabNumSrc);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Method to search thru list of songs to find a specific song.
     * @param list list to search thru
     * @param song song to find
     * @return index of song if found, -1 if not found
     */
    private int listContainsSong(List<JSONObject> list, JSONObject song){
        try {
            String songName = song.get("name").toString();
            String artistName = song.get("artist").toString();

            for (int i = 0; i < list.size(); i++){
                String thisSongName = list.get(i).get("name").toString();
                String thisArtistName = list.get(i).get("artist").toString();
                if (songName.equals(thisSongName) && artistName.equals(thisArtistName)){
                    return i;
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return -1;
    }

    public List<JSONObject> getList(int tabNum){
        if (tabNum < 1 || tabNum > 3){
            return null;
        }

        return allUserSongs.get(tabNum - 1);
    }

    public JSONObject getSong(int songIndex, int tabNum){
        return getList(tabNum).get(songIndex);
    }
}