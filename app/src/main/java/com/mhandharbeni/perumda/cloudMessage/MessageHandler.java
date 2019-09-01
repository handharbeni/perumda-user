package com.mhandharbeni.perumda.cloudMessage;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mhandharbeni.perumda.preferences.AppPreferences;
import com.mhandharbeni.perumda.utils.Constant;

public class MessageHandler extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    @Override
    public void onNewToken(String token) {
        AppPreferences.getInstance(getApplicationContext()).setPref(Constant.TOKEN_FCM, token);
    }
}
