package com.example.bikinishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final int key = 69;
    EditText username, email, password, password2;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(getIntent().getIntExtra("youknowwhatitis", 0) != key){
            finish();
        }

        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        password2 = findViewById(R.id.editTextPasswordAgain);

        mAuth = FirebaseAuth.getInstance();


    }

    public void onSingUpButtonPushed(View view) {
        String usr = username.getText().toString();
        String eml = email.getText().toString();
        String psw = password.getText().toString();
        String psw2 = password2.getText().toString();

        if (usr.isEmpty()) {
            username.setError("This field can't be empty!");
            username.requestFocus();
            return;
        }

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

        if (psw2.isEmpty()) {
            password2.setError("This field can't be empty!");
            password2.requestFocus();
            return;
        }

        if (psw2.length() < 6) {
            password2.setError("The password has to be at least 6 character!");
            password2.requestFocus();
            return;
        }

        if (!psw2.equals(psw)) {
            password2.setError("The passwords do not match!");
            password2.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(eml, psw).addOnCompleteListener(task -> {
//            Log.i(SignUpActivity.class.toString(), task.getException().toString());

            if (task.isSuccessful()){
                User user = new User(usr, eml, psw);
                FirebaseDatabase.getInstance("https://bikinishop-2022-mobilalk-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "Ügyes vagy Iván!", Toast.LENGTH_LONG).show();
                            Intent profileIntent = new Intent(SignUpActivity.this, ProfileActivity.class);
                            profileIntent.putExtra("youknowwhatitis", key);
                            startActivity(profileIntent);
                        }else{
                            Toast.makeText(SignUpActivity.this, "Kurva nagy balfasz vagy Iván!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(SignUpActivity.this, "Csak kicsit vagy balfasz Iván!", Toast.LENGTH_LONG).show();
            }

//            Log.i(SignUpActivity.class.toString(), task.getException().toString());
        });

    }



    public void onCancel(View view) {
        finish();
    }
}