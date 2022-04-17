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

public class PrivacyActivity extends AppCompatActivity {
    String currentUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    Switch switchPrivacyBirthage;
    Switch switchPrivacyChats;
    Switch switchPrivacyCountry;
    Switch switchPrivacyGender;
    Switch switchPrivacyLocation;
    Switch switchPrivacyPhoto;
    Switch switchPrivacyPremium;
    Switch switchPrivacyVerify;
    Switch switchPrivacyVisits;
    Toolbar toolbarPrivacyToolbar;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_activity);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = this.firebaseUser.getUid();
        this.toolbarPrivacyToolbar = (Toolbar) findViewById(R.id.toolbarPrivacyToolbar);
        setSupportActionBar(this.toolbarPrivacyToolbar);
        getSupportActionBar().setTitle((CharSequence) "Privacy Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarPrivacyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity.this.finish();
            }
        });
        this.switchPrivacyLocation = (Switch) findViewById(R.id.switchPrivacyLocation);
        this.switchPrivacyBirthage = (Switch) findViewById(R.id.switchPrivacyBirthage);
        this.switchPrivacyVisits = (Switch) findViewById(R.id.switchPrivacyVisits);
        this.switchPrivacyChats = (Switch) findViewById(R.id.switchPrivacyChats);
        this.switchPrivacyGender = (Switch) findViewById(R.id.switchPrivacyGender);
        this.switchPrivacyPhoto = (Switch) findViewById(R.id.switchPrivacyPhoto);
        this.switchPrivacyVerify = (Switch) findViewById(R.id.switchPrivacyVerify);
        this.switchPrivacyPremium = (Switch) findViewById(R.id.switchPrivacyPremium);
        this.switchPrivacyCountry = (Switch) findViewById(R.id.switchPrivacyCountry);
        this.switchPrivacyLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyLocation, "share_location", "Location sharing enabled", "Location sharing disabled");
            }
        });
        this.switchPrivacyBirthage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyBirthage, "share_birthage", "you are showing your age", "your age is hidden now");
            }
        });
        this.switchPrivacyVisits.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyVisits, "share_visits", "Your visits will be now logged", "Stalker face on!");
            }
        });
        this.switchPrivacyChats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyChats, "block_stranger", "This will keep strangers away!", "Everyone can send you chats now");
            }
        });
        this.switchPrivacyGender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyGender, "block_genders", "Same gender wont disturb you now", "All genders can contact you now");
            }
        });
        this.switchPrivacyPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyPhoto, "block_photos", "People without photos blocked", "People without photos unblocked");
            }
        });
        this.switchPrivacyVerify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyVerify, "allow_verified", "Non verified members blocked", "Non verified members unblocked");
            }
        });
        this.switchPrivacyPremium.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyPremium, "allow_premium", "Only Vips can contact you now", "Every membership levels can contact you now");
            }
        });
        this.switchPrivacyCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyActivity privacyActivity = PrivacyActivity.this;
                privacyActivity.PrivacyProfile(privacyActivity.switchPrivacyCountry, "allow_country", "Only people from your country can contact you now", "People from every country can contact you now");
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
                        Toast.makeText(PrivacyActivity.this, str2, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PrivacyActivity.this, str3, Toast.LENGTH_SHORT).show();
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
                    String string = documentSnapshot.getString("share_location");
                    String string2 = documentSnapshot.getString("share_birthage");
                    String string3 = documentSnapshot.getString("share_visits");
                    String string4 = documentSnapshot.getString("block_stranger");
                    String string5 = documentSnapshot.getString("block_genders");
                    String string6 = documentSnapshot.getString("block_photos");
                    String string7 = documentSnapshot.getString("allow_verified");
                    String string8 = documentSnapshot.getString("allow_premium");
                    String string9 = documentSnapshot.getString("allow_country");
                    String str = "yes";
                    if (string != null) {
                        if (string.equals(str)) {
                            PrivacyActivity.this.switchPrivacyLocation.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyLocation.setChecked(false);
                        }
                    }
                    if (string2 != null) {
                        if (string2.equals(str)) {
                            PrivacyActivity.this.switchPrivacyBirthage.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyBirthage.setChecked(false);
                        }
                    }
                    if (string3 != null) {
                        if (string3.equals(str)) {
                            PrivacyActivity.this.switchPrivacyVisits.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyVisits.setChecked(false);
                        }
                    }
                    if (string4 != null) {
                        if (string4.equals(str)) {
                            PrivacyActivity.this.switchPrivacyChats.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyChats.setChecked(false);
                        }
                    }
                    if (string5 != null) {
                        if (string5.equals(str)) {
                            PrivacyActivity.this.switchPrivacyGender.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyGender.setChecked(false);
                        }
                    }
                    if (string6 != null) {
                        if (string6.equals(str)) {
                            PrivacyActivity.this.switchPrivacyPhoto.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyPhoto.setChecked(false);
                        }
                    }
                    if (string7 != null) {
                        if (string7.equals(str)) {
                            PrivacyActivity.this.switchPrivacyVerify.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyVerify.setChecked(false);
                        }
                    }
                    if (string8 != null) {
                        if (string8.equals(str)) {
                            PrivacyActivity.this.switchPrivacyPremium.setChecked(true);
                        } else {
                            PrivacyActivity.this.switchPrivacyPremium.setChecked(false);
                        }
                    }
                    if (string9 == null) {
                        return;
                    }
                    if (string9.equals(str)) {
                        PrivacyActivity.this.switchPrivacyCountry.setChecked(true);
                    } else {
                        PrivacyActivity.this.switchPrivacyCountry.setChecked(false);
                    }
                }
            }
        });
    }
}
