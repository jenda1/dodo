package name.lana.dodo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoCallActivity extends AppCompatActivity {
    private static final String TAG = VideoCallActivity.class.getSimpleName();

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String roomID = getIntent().getExtras().getString("roomID");
        Log.v(TAG, "onCreate '" + roomID + "'");

        registerReceiver(mMessageReceiver, new IntentFilter("killroom-" + roomID));

        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }

        JitsiMeet.setDefaultConferenceOptions(
                new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(serverURL)
                        .setWelcomePageEnabled(false)
                        .build());

        JitsiMeetConferenceOptions defaultOptions =
                new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(serverURL)
                        .setWelcomePageEnabled(false)
                        .setFeatureFlag("pip.enabled", true)
                        .setFeatureFlag("chat.enabled", false)
                        .setFeatureFlag("invite.enabled", false)
                        .setFeatureFlag("meeting-name.enabled", false)
                        .setFeatureFlag("call-integration.enabled", false)
                        .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        JitsiMeetConferenceOptions options =
                new JitsiMeetConferenceOptions.Builder().setRoom(roomID).build();

        JitsiMeetActivity.launch(this, options);

        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }
}
