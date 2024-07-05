package com.example.firebase_example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

	EditText editTextTextEmailForgot;
	Button buttonReset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);

		editTextTextEmailForgot = findViewById(R.id.editTextTextEmailForgot);
		buttonReset = findViewById(R.id.buttonReset);

		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

		buttonReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String email = editTextTextEmailForgot.getText().toString();

				// kirim alamat email ke firebase br dikirim link ke email tersebut
				firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
						@Override
						public void onComplete(@NonNull Task<Void> task) {
							// jika email sudah berhasil masuk ke alamat emailnya
							if (task.isSuccessful()) {
								String message = "We sent an email to reset your password";
								Toast.makeText(ForgetActivity.this, message, Toast.LENGTH_SHORT).show();
							}
						}
				});

				finish();

			}
		});
	}
}