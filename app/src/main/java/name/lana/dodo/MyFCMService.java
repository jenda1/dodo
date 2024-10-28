package name.lana.dodo;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {
    private static final String TAG = MainActivity.class.getSimpleName();


    public void onMessageReceived (RemoteMessage message) {
        Log.v(TAG, "onMessageReceived: " + message);


    }
}