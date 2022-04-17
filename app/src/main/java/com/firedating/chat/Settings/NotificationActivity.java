package com.firedating.chat.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {
    String currentUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    Switch switchNotifyChats;
    Switch switchNotifyFollow;
    Switch switchNotifyLikes;
    Switch switchNotifyMatch;
    Switch switchNotifySuper;
    Switch switchNotifyVisits;
    Toolbar toolbarNotifyToolbar;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = this.firebaseUser.getUid();
        this.toolbarNotifyToolbar = (Toolbar) findViewById(R.id.toolbarNotifyToolbar);
        setSupportActionBar(this.toolbarNotifyToolbar);
        getSupportActionBar().setTitle((CharSequence) "Notification Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarNotifyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity.this.finish();
            }
        });
        this.switchNotifyMatch = (Switch) findViewById(R.id.switchNotifyMatch);
        this.switchNotifyChats = (Switch) findViewById(R.id.switchNotifyChats);
        this.switchNotifyLikes = (Switch) findViewById(R.id.switchNotifyLikes);
        this.switchNotifySuper = (Switch) findViewById(R.id.switchNotifySuper);
        this.switchNotifyVisits = (Switch) findViewById(R.id.switchNotifyVisits);
        this.switchNotifyFollow = (Switch) findViewById(R.id.switchNotifyFollow);
        this.switchNotifyMatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifyMatch, "alert_match", "Match notification enabled", "Match notification disabled");
            }
        });
        this.switchNotifyChats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifyChats, "alert_chats", "Chats notification enabled", "Chats notification disabled");
            }
        });
        this.switchNotifyLikes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifyLikes, "alert_likes", "Likes notification enabled", "Likes notification disabled");
            }
        });
        this.switchNotifySuper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifySuper, "alert_super", "Super Likes notification enabled", "Super Likes notification disabled");
            }
        });
        this.switchNotifyVisits.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifyVisits, "alert_visits", "Visitor notification enabled", "Visitor notification disabled");
            }
        });
        this.switchNotifyFollow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationActivity notificationActivity = NotificationActivity.this;
                notificationActivity.PrivacyProfile(notificationActivity.switchNotifyFollow, "alert_follows", "Followers notification enabled", "Followers notification disabled");
            }
        });
    }

    /* access modifiers changed from: private */
    public void PrivacyProfile(Switch switchR, String str, final String str2, final String str3) {
        String str4 = "users";
        if (switchR.isChecked()) {
            HashMap hashMap = new HashMap();
            hashMap.put(str, "yes");
            this.firebaseFirestore.collection(str4).document(this.currentUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(NotificationActivity.this, str2, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put(str, "no");
        this.firebaseFirestore.collection(str4).document(this.currentUser).update(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(NotificationActivity.this, str3, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                    String string = documentSnapshot.getString("alert_match");
                    String string2 = documentSnapshot.getString("alert_chats");
                    String string3 = documentSnapshot.getString("alert_likes");
                    String string4 = documentSnapshot.getString("alert_super");
                    String string5 = documentSnapshot.getString("alert_visits");
                    String string6 = documentSnapshot.getString("alert_follows");
                    String str = "yes";
                    if (string != null) {
                        if (string.equals(str)) {
                            NotificationActivity.this.switchNotifyMatch.setChecked(true);
                        } else {
                            NotificationActivity.this.switchNotifyMatch.setChecked(false);
                        }
                    }
                    if (string2 != null) {
                        if (string2.equals(str)) {
                            NotificationActivity.this.switchNotifyChats.setChecked(true);
                        } else {
                            NotificationActivity.this.switchNotifyChats.setChecked(false);
                        }
                    }
                    if (string3 != null) {
                        if (string3.equals(str)) {
                            NotificationActivity.this.switchNotifyLikes.setChecked(true);
                        } else {
                            NotificationActivity.this.switchNotifyLikes.setChecked(false);
                        }
                    }
                    if (string4 != null) {
                        if (string4.equals(str)) {
                            NotificationActivity.this.switchNotifySuper.setChecked(true);
                        } else {
                            NotificationActivity.this.switchNotifySuper.setChecked(false);
                        }
                    }
                    if (string5 != null) {
                        if (string5.equals(str)) {
                            NotificationActivity.this.switchNotifyVisits.setChecked(true);
                        } else {
                            NotificationActivity.this.switchNotifyVisits.setChecked(false);
                        }
                    }
                    if (string6 == null) {
                        return;
                    }
                    if (string6.equals(str)) {
                        NotificationActivity.this.switchNotifyFollow.setChecked(true);
                    } else {
                        NotificationActivity.this.switchNotifyFollow.setChecked(false);
                    }
                }
            }
        });
    }
}
