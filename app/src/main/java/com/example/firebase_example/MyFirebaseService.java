package com.example.firebase_example;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(@NonNull RemoteMessage message) {
		super.onMessageReceived(message);
		Toast.makeText(MyFirebaseService.this, "Notification Received", Toast.LENGTH_SHORT).show();
	}
}
