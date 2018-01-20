package pennapps.com.kukuoke;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Felix on 1/20/18.
 */

public class AlbumSearchJob extends AsyncTask<String, String, JSONArray> {
    @Override
    protected JSONArray doInBackground(String ... strings) {
        Log.d("ALBUM1", strings[0]);
        String root = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + strings[0] + "&autocorrect=1&api_key=f77bf3071f17ab92415497d1cb231590&format=json";
        Log.d("ALBUM2", root);

        try {
            URL url = new URL(root);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();

            InputStreamReader in;
            try {
                in = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(in);

                String line = "";

                while (true){						// second while loop reads in all the robots txt content
                    try {
                        if (line != null){// && line.length() != 0){
                            line = br.readLine();
                            sb.append(line + "\n");
                        } else {
                            break;
                        }
                    } catch (IOException e) {
                        break;
                    }
                }


                br.close();
                Log.d("/////////////", sb.toString());

                try {
                    JSONObject jo = new JSONObject(sb.toString());
                    JSONObject albummatches = (JSONObject) ((JSONObject) jo.get("results")).get("albummatches");
                    JSONArray ja = (JSONArray) albummatches.get("album");

                    return ja;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                Log.d("File Not Found", e.getMessage());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("Malformed", e.getMessage());
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.d("IOException", e1.getMessage());
        }
        return null;
    }
}
