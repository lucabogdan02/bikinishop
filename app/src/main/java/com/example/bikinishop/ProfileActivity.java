package com.example.bikinishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private static final int key = 69;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DAO dao;
    TextView emailText, usernameText;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(getIntent().getIntExtra("youknowwhatitis", 0) != key){
            finish();
        }

        dao = new DAO();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        emailText = findViewById(R.id.emailText);
        usernameText = findViewById(R.id.usernameText);

        if (user != null) {
            dao.update().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    currentUser = dao.getCurrentUser();
                    usernameText.setText(currentUser.getUsername());
                    emailText.setText(currentUser.getEmail());
                }
            });
        }
    }

    public void onPlaceNewOrder(View view) {
        Intent newOrderIntent = new Intent(this, NewOrderActivity.class);
        newOrderIntent.putExtra("youknowwhatitis", key);
        startActivity(newOrderIntent);
    }

    public void onShowMyOrders(View view) {
        Intent showOrdersIntent = new Intent(this, OrdersActivity.class);
        showOrdersIntent.putExtra("youknowwhatitis", key);
        startActivity(showOrdersIntent);
    }
}