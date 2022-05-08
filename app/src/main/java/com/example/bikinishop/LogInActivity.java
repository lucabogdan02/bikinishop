package com.example.bikinishop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private static final int key = 69;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if(getIntent().getIntExtra("youknowwhatitis", 0) != key){
            finish();
        }

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onSingUpButtonPushed(View view) {
        String eml = email.getText().toString();
        String psw = password.getText().toString();

        if (eml.isEmpty()) {
            email.setError("This field can't be empty!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(eml).matches()) {
            email.setError("Email address is not valid!");
            email.requestFocus();
            return;
        }

        if (psw.isEmpty()) {
            password.setError("This field can't be empty!");
            password.requestFocus();
            return;
        }

        if (psw.length() < 6) {
            password.setError("The password has to be at least 6 character!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(eml, psw).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LogInActivity.this, "Ügyes vagy Iván!", Toast.LENGTH_LONG).show();

                Intent profileIntent = new Intent(this, ProfileActivity.class);
                profileIntent.putExtra("youknowwhatitis", key);
                startActivity(profileIntent);
            } else {
                Toast.makeText(LogInActivity.this, "Failed to login user :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCancel(View view) {
    }
}