package com.example.bikinishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private static final int key = 69;
    RecyclerView recyclerView;
    List<Order> orders;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        if(getIntent().getIntExtra("youknowwhatitis", 0) != key){
            finish();
        }

        recyclerView = findViewById(R.id.recView);
        dao = new DAO();

        dao.update().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                orders = dao.getOrders();
                Log.i("orders SIZE", String.valueOf(orders.size()));

                RecViewAdapter rva = new RecViewAdapter(OrdersActivity.this, orders);
                recyclerView.setAdapter(rva);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
            }
        });
    }
}