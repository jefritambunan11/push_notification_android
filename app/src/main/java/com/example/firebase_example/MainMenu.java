package com.example.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {

	Button buttonSignOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		buttonSignOut = findViewById(R.id.buttonSignOut);
		buttonSignOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// fungsi firebase untuk hapus session loggedIn
				FirebaseAuth.getInstance().signOut();
				// redirect
				Intent intent = new Intent(MainMenu.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});

	}
}