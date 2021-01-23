package name.lana.dodo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class IncomingSms extends BroadcastReceiver {

    private static final String TAG = IncomingSms.class.getSimpleName();

    private String lastRoomId;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (SmsMessage message : smsMessages) {
                String msg = message.getDisplayMessageBody();
                if (lastRoomId != null) {
                    Log.v(TAG, "kill " + msg);
                    context.sendBroadcast(new Intent("killroom-" + msg));
                    lastRoomId = null;
                } else {
                    Intent vcall = new Intent(context, VideoCallActivity.class);
                    vcall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    vcall.putExtra("roomID", msg);

                    context.startActivity(vcall);
                    lastRoomId = msg;
                }
            }
        }
    }
}
