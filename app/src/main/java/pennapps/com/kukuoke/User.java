package pennapps.com.kukuoke;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String uid;
    private String name;
    private String email;

    private List<JSONObject> goodSongs;
    private List<JSONObject> maybeSongs;
    private List<JSONObject> nopeSongs;
    private List<JSONObject> friends;

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;

        goodSongs = new ArrayList<>();
        maybeSongs = new ArrayList<>();
        nopeSongs = new ArrayList<>();
        friends = new ArrayList<>();
    }

    //getters
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public List<JSONObject> getGoodSongs() { return goodSongs; }
    public List<JSONObject> getMaybeSongs() { return maybeSongs; }
    public List<JSONObject> getNopeSongs() { return nopeSongs; }
    public List<JSONObject> getFriends() { return friends; }

}
