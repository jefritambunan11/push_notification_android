package com.example.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

	Button buttonSignIn, buttonSignUp, buttonForgot, buttonSignInPhoneNumber;
	EditText editTextEmail, editTextPassword;

	// ##### Firebase Auth
	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

	// ### Firebase Database
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	// konek ke tabel Users
	DatabaseReference reference = database.getReference().child("Users");
	// konek ke database
	DatabaseReference reference2 = database.getReference();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonSignIn = findViewById(R.id.buttonSignIn);
		buttonSignUp = findViewById(R.id.buttonSignUp);
		buttonForgot = findViewById(R.id.buttonForgot);
		buttonSignInPhoneNumber = findViewById(R.id.buttonSignInPhoneNumber);
		editTextEmail = findViewById(R.id.editTextEmail);
		editTextPassword = findViewById(R.id.editTextPassword);

		buttonSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String mail = editTextEmail.getText().toString();
				String pass = editTextPassword.getText().toString();
				// panggil  fungsi konek ke firebase
				signInFirebase(mail, pass);
			}
		});

		buttonSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});

		buttonForgot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, ForgetActivity.class);
				startActivity(intent);
			}
		});

		buttonSignInPhoneNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, PhoneSignInActivity.class);
				startActivity(intent);
			}
		});


	}

	public void signInFirebase(String email, String pass) {
		firebaseAuth.signInWithEmailAndPassword(email, pass)
			.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					// jika return dari firebase email & pass bener
					System.out.println("task.isSuccessful()");
					System.out.println(task.isSuccessful());
					if (task.isSuccessful()) {
						Intent intent = new Intent(MainActivity.this, MainMenu.class);
						startActivity(intent);
						finish();
					} else {
						String message = "Mail or Password is not correct";
						Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
					}
				}
			});
	}

	@Override
	protected void onStart() {
		super.onStart();

		// ambil session user yg sudah login
		// dan cek session nya masih berlaku tidak di firebase
		FirebaseUser user = firebaseAuth.getCurrentUser();

		// jika masih, redirect ke MainMenu
		if (user != null) {
			Intent intent = new Intent(MainActivity.this, MainMenu.class);
			startActivity(intent);
			finish();
		}
	}
}