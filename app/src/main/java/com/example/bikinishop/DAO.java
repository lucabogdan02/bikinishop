package com.example.bikinishop;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class DAO {
    private DatabaseReference userRef;
    private DatabaseReference orderRef;
    private FirebaseDatabase db;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private User currentUser;
    private List<Order> orders;

    public DAO() {
        db = FirebaseDatabase.getInstance("https://bikinishop-2022-mobilalk-default-rtdb.europe-west1.firebasedatabase.app/");
        userRef = db.getReference("Users");
        orderRef = db.getReference("Orders");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser = snapshot.getValue(User.class);
                    Log.i("firebase", "User loaded: " + currentUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("firebase", "loadPost:onCancelled", error.toException());
                }
            });

            Log.i("firebase", "nem null " + user.getEmail() + ", " + currentUser);

        } else {
            Log.i("firebase", "null");
        }

        GenericTypeIndicator<List<Order>> genericTypeIndicator = new GenericTypeIndicator<List<Order>>() {};

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders = snapshot.getValue(genericTypeIndicator);
                orders.remove(0);

                Log.i("firebase", "Orders loaded: " + snapshot);
                Log.i("firebase", "Orders map loaded: " + orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "loadPost:onCancelled", error.toException());
            }
        });
    }

    public Task<Void> update() {
        return userRef.child("0").setValue(new User()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                remove("0");
                orderRef.child("0").setValue(new Order()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        removeOrder("0");
                    }
                });
            }
        });
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            update().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("firebase", "updated " + user.getEmail() + ", " + currentUser);
                }
            });
        }

        return currentUser;
    }

    public Task<Void> remove(String key) {
        return userRef.child(key).removeValue();
    }

    public Task<Void> removeOrder(String key) {
        return orderRef.child(key).removeValue();
    }

    public Task<Void> addNewOrder(Order newOrder) {
        return orderRef.child(String.valueOf(newOrder.getOrderNumber())).setValue(newOrder);
    }

    public List<Order> getOrders() {
        if (orders == null) {
            update().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("firebase", "updated " + user.getEmail() + ", " + currentUser);
                }
            });
        }

        return orders;
    }
}
