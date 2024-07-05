package com.example.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
	EditText editTextSignUpEmail, editTextSignUpPassword;
	Button buttonRegister;

	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
		editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
		buttonRegister = findViewById(R.id.buttonRegister);

		buttonRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String email = editTextSignUpEmail.getText().toString();
				String pass = editTextSignUpPassword.getText().toString();
				//
				signUpFirebase(email, pass);
			}
		});
	}

	//
	public void signUpFirebase(String email, String pass) {
		firebaseAuth.createUserWithEmailAndPassword(email, pass)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if (task.isSuccessful()) {
						String message = "Akun kamu berhasil dibuat";
						Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
						//
						Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						String message = "Gagal membuat akun, ada kendala";
						Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
}