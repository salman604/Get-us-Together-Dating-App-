package com.firedating.chat.Settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firedating.chat.R;

public class EmailActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "email", Toast.LENGTH_SHORT).show();

    }
}
