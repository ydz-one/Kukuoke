package pennapps.com.kukuoke;

import android.app.Application;
import com.firebase.client.Firebase;

/*
This class is a helper for logging in and connecting to Firebase
 */

public class LoginHelper extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
