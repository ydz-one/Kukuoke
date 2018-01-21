package pennapps.com.kukuoke;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    public String uid;
    public String name;
    public String email;

    public List<String> goodSongs;
    public List<String> maybeSongs;
    public List<String> nopeSongs;
    public List<String> friends;

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;

        goodSongs = new ArrayList<>();
        maybeSongs = new ArrayList<>();
        nopeSongs = new ArrayList<>();
        friends = new ArrayList<>();

        try {
            JSONObject newSong1 = new JSONObject();
            newSong1.put("name", "my love");
            newSong1.put("artist", "westlife");

            JSONObject newSong2 = new JSONObject();
            newSong2.put("name", "season in the sun");
            newSong2.put("artist", "westlife");
            goodSongs.add(newSong1.toString());
            goodSongs.add(newSong2.toString());
        } catch (Exception e) {

        }
    }

    //getters
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public List<String> getGoodSongs() { return goodSongs; }
    public List<String> getMaybeSongs() { return maybeSongs; }
    public List<String> getNopeSongs() { return nopeSongs; }
    public List<String> getFriends() { return friends; }

}
