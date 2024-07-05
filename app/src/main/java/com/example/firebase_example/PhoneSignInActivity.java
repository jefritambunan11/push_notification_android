package com.example.firebase_example;

import android.content.Intent;
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
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneSignInActivity extends AppCompatActivity {

	EditText editTextPhoneNumber, editTextCode;
	Button buttonRequestCode, buttonConfirmTheCode;

	FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
	String codeSent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_sign_in);

		editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
		editTextCode = findViewById(R.id.editTextCode);
		buttonRequestCode = findViewById(R.id.buttonRequestCode);
		buttonConfirmTheCode = findViewById(R.id.buttonConfirmTheCode);

		//
		buttonRequestCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String phoneNumber = editTextPhoneNumber.getText().toString();

				// Memilih konfig opsi-opsi
				Long timeOut60 = 60L;
				PhoneAuthOptions options = PhoneAuthOptions
																		.newBuilder(firebaseAuth)
																		.setPhoneNumber(phoneNumber)
																		.setTimeout(timeOut60, TimeUnit.SECONDS) // 60 detik, jika 60 menit -> TimeUnit.MINUTES
																		.setActivity(PhoneSignInActivity.this)
																		.setCallbacks(mCallbacks)
																		.build();

				// menggunakan konfig sebagai verifikasi no telepon
				PhoneAuthProvider.verifyPhoneNumber(options);

			}
		});

		//
		buttonConfirmTheCode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// panggil method ini
				signInWithPhoneCode();
			}
		});
	}

	public void signInWithPhoneCode() {
		String confirmationCode = editTextCode.getText().toString();

		// proses ini membanding 2 kode, satu kode dari firebase,
		// satu kode dari inputan user
		PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(codeSent, confirmationCode);

		//
		signInWithPhoneAuthCredential(phoneAuthCredential);
	}

	public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
		firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				System.out.println("task.isSuccessful()");
				System.out.println(task.isSuccessful());

				// cek jika proses ontentikasi sudah benar atau belum
				if (task.isSuccessful()) {
					Intent intent = new Intent(PhoneSignInActivity.this, MainMenu.class);
					startActivity(intent);
					finish();
				}else{
					String message = "The code you entered is incorrect";
					Toast.makeText(PhoneSignInActivity.this, message, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	// fungsi mCallbacks ini digunkana untuk handler jika
	// verifikasi no telpon nya berhasil atau gagal
	PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
		@Override
		public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

		}

		@Override
		public void onVerificationFailed(@NonNull FirebaseException e) {

		}

		// saat kode verifikasi dikirim
		@Override
		public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
			super.onCodeSent(s, forceResendingToken);

			// set kode nya di global variable
			codeSent = s;
		}
	};
}