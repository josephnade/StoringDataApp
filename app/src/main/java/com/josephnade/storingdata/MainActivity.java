package com.josephnade.storingdata;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editAge;
    TextView ageTextView;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.josephnade.storingdata", Context.MODE_PRIVATE);
        editAge = findViewById(R.id.editAge);
        ageTextView = findViewById(R.id.ageTextView);
        builder = new AlertDialog.Builder(this);

        int storedAge = sharedPreferences.getInt("storedAge", 0);
        if (storedAge == 0) {
            ageTextView.setText("Your age: ");
        } else {
            ageTextView.setText("Your age: " + storedAge);
        }
    }

    public void save(View view) {
        if (editAge.getText().toString().matches("")) {
            ageTextView.setText("Enter a valid age");
        } else {
            int userAge = Integer.parseInt(editAge.getText().toString());
            ageTextView.setText("Your age: " + userAge);
            sharedPreferences.edit().putInt("storedAge", userAge).apply();
        }
    }

    public void delete(View view) {
        int storedAge = sharedPreferences.getInt("storedAge", 0);
        if (storedAge != 0) {
            // Delete düğmesine tıklandığında onay uyarısı göster
            builder.setMessage("Are you sure you want to delete your age?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                sharedPreferences.edit().remove("storedAge").apply();
                ageTextView.setText("Your age: ");
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_LONG).show();
            });
            builder.create().show();
        }
    }
}
