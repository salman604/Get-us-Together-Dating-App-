package com.firedating.chat.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SupportActivity extends AppCompatActivity {
    Button buttonSupportButton;
    CheckBox checkBoxSupportDifficult;
    CheckBox checkBoxSupportEnjoy;
    CheckBox checkBoxSupportFreezes;
    CheckBox checkBoxSupportFriends;
    CheckBox checkBoxSupportNotify;
    String currentUser;
    EditText editTextSupportOther;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String profileUser;
    Toolbar toolbarSupport;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.toolbarSupport = (Toolbar) findViewById(R.id.toolbarSupport);
        setSupportActionBar(this.toolbarSupport);
        getSupportActionBar().setTitle((CharSequence) "Neep Support");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarSupport.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SupportActivity.this.finish();
            }
        });
        this.currentUser = this.firebaseUser.getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        this.checkBoxSupportDifficult = (CheckBox) findViewById(R.id.checkBoxSupportDifficult);
        this.checkBoxSupportFriends = (CheckBox) findViewById(R.id.checkBoxSupportFriends);
        this.checkBoxSupportFreezes = (CheckBox) findViewById(R.id.checkBoxSupportFreezes);
        this.checkBoxSupportNotify = (CheckBox) findViewById(R.id.checkBoxSupportNotify);
        this.checkBoxSupportEnjoy = (CheckBox) findViewById(R.id.checkBoxSupportEnjoy);
        this.editTextSupportOther = (EditText) findViewById(R.id.editTextSupportOther);
        this.buttonSupportButton = (Button) findViewById(R.id.buttonSupportButton);
        this.buttonSupportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                hashMap.put("support_user", SupportActivity.this.currentUser);
                hashMap.put("support_date", Timestamp.now());
                if (SupportActivity.this.checkBoxSupportDifficult.isChecked()) {
                    hashMap.put("support_difficult", "This com.firedating.chat.Main.Application is Difficult");
                }
                if (SupportActivity.this.checkBoxSupportFriends.isChecked()) {
                    hashMap.put("support_friends", "I can't find any friends");
                }
                if (SupportActivity.this.checkBoxSupportFreezes.isChecked()) {
                    hashMap.put("support_freezes", "This app is slow and freezes");
                }
                if (SupportActivity.this.checkBoxSupportNotify.isChecked()) {
                    hashMap.put("support_notify", "I dont like notifications");
                }
                if (SupportActivity.this.checkBoxSupportEnjoy.isChecked()) {
                    hashMap.put("support_enjoy", "I love and enjoy this app");
                }
                String obj = SupportActivity.this.editTextSupportOther.getText().toString();
                if (!obj.equals("")) {
                    hashMap.put("support_other", obj);
                }
                if (hashMap.size() > 2) {
                    SupportActivity.this.firebaseFirestore.collection("support").add((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SupportActivity.this, "Your feedback is sent!", Toast.LENGTH_SHORT).show();
                                SupportActivity.this.finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SupportActivity.this, "Please select support types to send", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
