package com.example.bikinishop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class NewOrderActivity extends AppCompatActivity {

    private static final int key = 69;
    private static final String CHANNEL_ID = "420";
    EditText editTextBust, editTextUnderbust, editTextWaist, editTextBottomLength, editTextSytle, editTextOther;
    FirebaseAuth mAuth;
    User currentUser;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        if(getIntent().getIntExtra("youknowwhatitis", 0) != key){
            finish();
        }

        editTextBust = findViewById(R.id.editTextBust);
        editTextUnderbust = findViewById(R.id.editTextUnderbust);
        editTextWaist = findViewById(R.id.editTextWaist);
        editTextBottomLength = findViewById(R.id.editTextBottomLength);
        editTextSytle = findViewById(R.id.editTextSytle);
        editTextOther = findViewById(R.id.editTextOther);

        mAuth = FirebaseAuth.getInstance();
        dao = new DAO();
        currentUser = dao.getCurrentUser();
    }

    public void onPlaceNewOrderButtonPushed(View view) {
        Bikini newBikini = new Bikini(
            Integer.parseInt(editTextBust.getText().toString()),
            Integer.parseInt(editTextUnderbust.getText().toString()),
            Integer.parseInt(editTextWaist.getText().toString()),
            Integer.parseInt(editTextBottomLength.getText().toString()),
            editTextSytle.getText().toString(),
            editTextOther.getText().toString()
        );

        Order.setOrdernumber(Order.getOrdernumber() + 1);

        Order newOrder = new Order(
                Order.getOrdernumber(),
                currentUser,
                System.currentTimeMillis(),
                newBikini
        );


        dao.addNewOrder(newOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(NewOrderActivity.this, "Sikeres rendelés!", Toast.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = getString(R.string.notifications);
                    String description = getString(R.string.channel_description);
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(description);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(NewOrderActivity.this, OrdersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(NewOrderActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewOrderActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.bra)
                        .setContentTitle("Sikeres rendelés!")
                        .setContentText("Nézd meg a rendeléseid!")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NewOrderActivity.this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(new Random().nextInt(), builder.build());

                Intent profileIntent = new Intent(NewOrderActivity.this, ProfileActivity.class);
                profileIntent.putExtra("youknowwhatitis", key);
                startActivity(profileIntent);
            }
        });
    }

    public void onCancel(View view) {
        finish();
    }
}