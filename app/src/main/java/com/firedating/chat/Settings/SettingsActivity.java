package com.firedating.chat.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.firedating.chat.Feeds.FeedsClass;
import com.firedating.chat.R;
import com.firedating.chat.Start.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.HttpHeaders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SettingsActivity extends AppCompatActivity {
    Button buttonSettingsAccountDelete;
    Button buttonSettingsAccountLogout;
    String currentUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    LinearLayout linearLayoutAgeRange;
    LinearLayout linearLayoutGender;
    LinearLayout linearLayoutLocation;
    LinearLayout linearLayoutRelationship;
    LinearLayout linearLayoutSeeking;
    LinearLayout linearLayoutSettingsAccount;
    LinearLayout linearLayoutSettingsNotify;
    LinearLayout linearLayoutSettingsPrivacy;
    LinearLayout linearLayoutSettingsSupport;
    LinearLayout linearLayoutSexual;
    CrystalRangeSeekbar seekbarSettingsAgeRange;
    String stringSeekbarMaximum;
    String stringSeekbarMinimum;
    String[] string_array_show_lookingfor;
    String[] string_array_show_lookingin;
    String[] string_array_show_relationship;
    String[] string_array_show_seekingfor;
    String[] string_array_show_sexual;
    Switch switchSettingsFeeds;
    Switch switchSettingsMatch;
    Switch switchSettingsProfile;
    Switch switchSettingsStatus;
    TextView textViewSettingsAgeRangeMax;
    TextView textViewSettingsAgeRangeMin;
    TextView textViewSettingsGender;
    TextView textViewSettingsLocation;
    TextView textViewSettingsRelationship;
    TextView textViewSettingsSeeking;
    TextView textViewSettingsSexual;
    Toolbar toolbarSettingsToolbar;
    String user_city;
    String user_country;
    String user_state;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = this.firebaseUser.getUid();
        this.toolbarSettingsToolbar = (Toolbar) findViewById(R.id.toolbarSettingsToolbar);
        setSupportActionBar(this.toolbarSettingsToolbar);
        getSupportActionBar().setTitle((CharSequence) "Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarSettingsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.finish();
            }
        });
        this.linearLayoutAgeRange = (LinearLayout) findViewById(R.id.linearLayoutAgeRange);
        this.linearLayoutGender = (LinearLayout) findViewById(R.id.linearLayoutGender);
        this.linearLayoutLocation = (LinearLayout) findViewById(R.id.linearLayoutLocation);
        this.linearLayoutRelationship = (LinearLayout) findViewById(R.id.linearLayoutRelationship);
        this.linearLayoutSexual = (LinearLayout) findViewById(R.id.linearLayoutSexual);
        this.linearLayoutSeeking = (LinearLayout) findViewById(R.id.linearLayoutSeeking);
        this.linearLayoutSettingsPrivacy = (LinearLayout) findViewById(R.id.linearLayoutSettingsPrivacy);
        this.linearLayoutSettingsAccount = (LinearLayout) findViewById(R.id.linearLayoutSettingsAccount);
        this.linearLayoutSettingsSupport = (LinearLayout) findViewById(R.id.linearLayoutSettingsSupport);
        this.linearLayoutSettingsNotify = (LinearLayout) findViewById(R.id.linearLayoutSettingsNotify);
        this.buttonSettingsAccountLogout = (Button) findViewById(R.id.buttonSettingsAccountLogout);
        this.buttonSettingsAccountDelete = (Button) findViewById(R.id.buttonSettingsAccountDelete);
        this.seekbarSettingsAgeRange = (CrystalRangeSeekbar) findViewById(R.id.seekbarSettingsAgeRange);
        this.textViewSettingsGender = (TextView) findViewById(R.id.textViewSettingsGender);
        this.textViewSettingsLocation = (TextView) findViewById(R.id.textViewSettingsLocation);
        this.textViewSettingsRelationship = (TextView) findViewById(R.id.textViewSettingsRelationship);
        this.textViewSettingsSexual = (TextView) findViewById(R.id.textViewSettingsSexual);
        this.textViewSettingsSeeking = (TextView) findViewById(R.id.textViewSettingsSeeking);
        this.textViewSettingsAgeRangeMin = (TextView) findViewById(R.id.textViewSettingsAgeRangeMin);
        this.textViewSettingsAgeRangeMax = (TextView) findViewById(R.id.textViewSettingsAgeRangeMax);
        this.string_array_show_sexual = getResources().getStringArray(R.array.string_array_show_sexual);
        this.string_array_show_lookingfor = getResources().getStringArray(R.array.string_array_show_gender);
        this.string_array_show_lookingin = new String[4];
        this.string_array_show_seekingfor = getResources().getStringArray(R.array.string_array_show_seeking);
        this.string_array_show_relationship = getResources().getStringArray(R.array.string_array_show_marital);
        this.switchSettingsFeeds = (Switch) findViewById(R.id.switchSettingsFeeds);
        this.switchSettingsProfile = (Switch) findViewById(R.id.switchSettingsProfile);
        this.switchSettingsStatus = (Switch) findViewById(R.id.switchSettingsStatus);
        this.switchSettingsMatch = (Switch) findViewById(R.id.switchSettingsMatch);
        this.switchSettingsFeeds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = "feeds";
                String str2 = "users";
                String str3 = "show_feeds";
                if (SettingsActivity.this.switchSettingsFeeds.isChecked()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(str3, "yes");
                    SettingsActivity.this.firebaseFirestore.collection(str2).document(SettingsActivity.this.currentUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "You are sharing your feeds now!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    SettingsActivity.this.firebaseFirestore.collection(str).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Iterator it = ((QuerySnapshot) task.getResult()).iterator();
                            while (it.hasNext()) {
                                QueryDocumentSnapshot queryDocumentSnapshot = (QueryDocumentSnapshot) it.next();
                                if (((FeedsClass) queryDocumentSnapshot.toObject(FeedsClass.class)).getFeed_user().equals(SettingsActivity.this.currentUser)) {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("feed_show", "yes");
                                    SettingsActivity.this.firebaseFirestore.collection("feeds").document(queryDocumentSnapshot.getId()).update(hashMap);
                                }
                            }
                        }
                    });
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(str3, "no");
                SettingsActivity.this.firebaseFirestore.collection(str2).document(SettingsActivity.this.currentUser).update(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Feeds sharing stopped!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                SettingsActivity.this.firebaseFirestore.collection(str).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Iterator it = ((QuerySnapshot) task.getResult()).iterator();
                        while (it.hasNext()) {
                            QueryDocumentSnapshot queryDocumentSnapshot = (QueryDocumentSnapshot) it.next();
                            if (((FeedsClass) queryDocumentSnapshot.toObject(FeedsClass.class)).getFeed_user().equals(SettingsActivity.this.currentUser)) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("feed_show", "no");
                                SettingsActivity.this.firebaseFirestore.collection("feeds").document(queryDocumentSnapshot.getId()).update(hashMap);
                            }
                        }
                    }
                });
            }
        });
        this.switchSettingsProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.PrivacyProfile(settingsActivity.switchSettingsProfile, "show_profile", "You are public now!", "You are on stealth mode now!");
            }
        });
        this.switchSettingsStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.PrivacyProfile(settingsActivity.switchSettingsStatus, "show_status", "You are online!", "You are now ninja mode!");
            }
        });
        this.switchSettingsMatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.PrivacyProfile(settingsActivity.switchSettingsMatch, "show_match", "Match Mode on! Swipe to connect!", "Match mode deactive!");
            }
        });
        this.linearLayoutSettingsAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, AccountActivity.class));
            }
        });
        this.linearLayoutSettingsNotify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, NotificationActivity.class));
            }
        });
        this.linearLayoutSettingsSupport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, SupportActivity.class));
            }
        });
        this.linearLayoutSettingsPrivacy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, PrivacyActivity.class));
            }
        });
        this.buttonSettingsAccountLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(SettingsActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
//                intent.addFlags(335544320);
                SettingsActivity.this.startActivity(intent);
                SettingsActivity.this.finish();
            }
        });
        this.buttonSettingsAccountDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Under development!", Toast.LENGTH_SHORT).show();
            }
        });
        this.seekbarSettingsAgeRange.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            public void valueChanged(Number number, Number number2) {
                SettingsActivity.this.stringSeekbarMinimum = String.valueOf(number);
                SettingsActivity.this.stringSeekbarMaximum = String.valueOf(number2);
                StringBuilder sb = new StringBuilder();
                sb.append(SettingsActivity.this.stringSeekbarMinimum);
                sb.append(" - ");
                sb.append(SettingsActivity.this.stringSeekbarMaximum);
                String sb2 = sb.toString();
                if (SettingsActivity.this.stringSeekbarMaximum.equals("60")) {
                    SettingsActivity.this.textViewSettingsAgeRangeMin.setText(SettingsActivity.this.stringSeekbarMinimum);
                    TextView textView = SettingsActivity.this.textViewSettingsAgeRangeMax;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(SettingsActivity.this.stringSeekbarMaximum);
                    sb3.append("+");
                    textView.setText(sb3.toString());
                } else {
                    SettingsActivity.this.textViewSettingsAgeRangeMin.setText(SettingsActivity.this.stringSeekbarMinimum);
                    SettingsActivity.this.textViewSettingsAgeRangeMax.setText(SettingsActivity.this.stringSeekbarMaximum);
                }
                SettingsActivity.this.ProfileUpdate("show_ages", sb2);
            }
        });
        this.linearLayoutGender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.ProfileDialogRadio(settingsActivity.string_array_show_lookingfor, SettingsActivity.this.textViewSettingsGender, "show_gender", "Looking For");
            }
        });
        this.linearLayoutLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity.this.string_array_show_lookingin[0] = "Anywhere";
                SettingsActivity.this.string_array_show_lookingin[1] = SettingsActivity.this.user_city;
                SettingsActivity.this.string_array_show_lookingin[2] = SettingsActivity.this.user_state;
                SettingsActivity.this.string_array_show_lookingin[3] = SettingsActivity.this.user_country;
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.ProfileDialogRadio(settingsActivity.string_array_show_lookingin, SettingsActivity.this.textViewSettingsLocation, "show_location", HttpHeaders.LOCATION);
            }
        });
        this.linearLayoutRelationship.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.ProfileDialogRadio(settingsActivity.string_array_show_relationship, SettingsActivity.this.textViewSettingsRelationship, "show_marital", "Marital Status");
            }
        });
        this.linearLayoutSexual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.ProfileDialogRadio(settingsActivity.string_array_show_sexual, SettingsActivity.this.textViewSettingsSexual, "show_sexual", "Sexual Orientation");
            }
        });
        this.linearLayoutSeeking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingsActivity settingsActivity = SettingsActivity.this;
                settingsActivity.ProfileDialogRadio(settingsActivity.string_array_show_seekingfor, SettingsActivity.this.textViewSettingsSeeking, "show_seeking", "I am Seeking for");
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
                        Toast.makeText(SettingsActivity.this, str2, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SettingsActivity.this, str3, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void ProfileDialogRadio(final String[] strArr, final TextView textView, final String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str2);
        builder.setSingleChoiceItems((CharSequence[]) strArr, new ArrayList(Arrays.asList(strArr)).indexOf(textView.getText().toString()), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int checkedItemPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                textView.setText(strArr[checkedItemPosition]);
                SettingsActivity.this.ProfileUpdate(str, strArr[checkedItemPosition]);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void ProfileUpdate(String str, String str2) {
        final HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        this.firebaseFirestore.collection("users").document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "users";
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    SettingsActivity.this.firebaseFirestore.collection(str).document(SettingsActivity.this.currentUser).update(hashMap);
                } else {
                    SettingsActivity.this.firebaseFirestore.collection(str).document(SettingsActivity.this.currentUser).set(hashMap);
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
                    SettingsActivity.this.user_city = documentSnapshot.getString("user_city");
                    SettingsActivity.this.user_state = documentSnapshot.getString("user_state");
                    SettingsActivity.this.user_country = documentSnapshot.getString("user_country");
                    String string = documentSnapshot.getString("show_seeking");
                    String string2 = documentSnapshot.getString("show_sexual");
                    String string3 = documentSnapshot.getString("show_gender");
                    String string4 = documentSnapshot.getString("show_location");
                    String string5 = documentSnapshot.getString("show_ages");
                    String string6 = documentSnapshot.getString("show_marital");
                    String string7 = documentSnapshot.getString("show_feeds");
                    String string8 = documentSnapshot.getString("show_profile");
                    String string9 = documentSnapshot.getString("show_status");
                    String string10 = documentSnapshot.getString("show_match");
                    if (string2 != null) {
                        SettingsActivity.this.textViewSettingsSexual.setText(string2);
                    }
                    if (string != null) {
                        SettingsActivity.this.textViewSettingsSeeking.setText(string);
                    }
                    if (string3 != null) {
                        SettingsActivity.this.textViewSettingsGender.setText(string3);
                    }
                    if (string4 != null) {
                        SettingsActivity.this.textViewSettingsLocation.setText(string4);
                    }
                    if (string6 != null) {
                        SettingsActivity.this.textViewSettingsRelationship.setText(string6);
                    }
                    if (string5 != null) {
                        String[] split = string5.split(" - ");
                        SettingsActivity.this.textViewSettingsAgeRangeMin.setText(split[0]);
                        SettingsActivity.this.textViewSettingsAgeRangeMax.setText(split[1]);
                        SettingsActivity.this.seekbarSettingsAgeRange.setMinStartValue(Float.valueOf(split[0]).floatValue());
                        SettingsActivity.this.seekbarSettingsAgeRange.setMaxStartValue(Float.valueOf(split[1]).floatValue());
                        SettingsActivity.this.seekbarSettingsAgeRange.apply();
                    }
                    String str = "yes";
                    if (string7 == null || !string7.equals(str)) {
                        SettingsActivity.this.switchSettingsFeeds.setChecked(false);
                    } else {
                        SettingsActivity.this.switchSettingsFeeds.setChecked(true);
                    }
                    if (string8 == null || !string8.equals(str)) {
                        SettingsActivity.this.switchSettingsProfile.setChecked(false);
                    } else {
                        SettingsActivity.this.switchSettingsProfile.setChecked(true);
                    }
                    if (string9 == null || !string9.equals(str)) {
                        SettingsActivity.this.switchSettingsStatus.setChecked(false);
                    } else {
                        SettingsActivity.this.switchSettingsStatus.setChecked(true);
                    }
                    if (string10 == null || !string10.equals(str)) {
                        SettingsActivity.this.switchSettingsMatch.setChecked(false);
                    } else {
                        SettingsActivity.this.switchSettingsMatch.setChecked(true);
                    }
                }
            }
        });
    }
}
