package com.firedating.chat.Main;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.firedating.chat.Notification.Token;
import com.firedating.chat.R;
import com.firedating.chat.Start.RemindActivity;
import com.firedating.chat.Start.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    MainAdapter adapter;
    ArrayList<Integer> arrayInteger = new ArrayList<>();
    CircleImageView circleImageViewProfileAvatar;
    private FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    HashMap<Integer, String> map = new HashMap<>();
    NotificationManager notificationManagers;
    ViewPager viewPager;
    private String currentUserId;;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbarToolbar));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            currentUserId = firebaseUser.getUid(); //Do what you need to do with the id
        }
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPager.setOffscreenPageLimit(6);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        View inflate = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.tab_icon);
        Picasso.get().load((int) R.drawable.tab_profile_off).into(imageView);
        tabLayout.addTab(tabLayout.newTab().setCustomView(inflate));
        View inflate2 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        final ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.tab_icon);
        Picasso.get().load((int) R.drawable.tab_users_off).into(imageView2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(inflate2));
        View inflate3 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        final ImageView imageView3 = (ImageView) inflate3.findViewById(R.id.tab_icon);
        Picasso.get().load((int) R.drawable.tab_swipe_off).into(imageView3);
        tabLayout.addTab(tabLayout.newTab().setCustomView(inflate3));
        View inflate4 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        final ImageView imageView4 = (ImageView) inflate4.findViewById(R.id.tab_icon);
        Picasso.get().load((int) R.drawable.tab_chat_off).into(imageView4);
        tabLayout.addTab(tabLayout.newTab().setCustomView(inflate4));
        View inflate5 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        final ImageView imageView5 = (ImageView) inflate5.findViewById(R.id.tab_icon);
        Picasso.get().load((int) R.drawable.tab_extra_off).into(imageView5);
        tabLayout.addTab(tabLayout.newTab().setCustomView(inflate5));
        tabLayout.setTabGravity(0);
        this.adapter = new MainAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        this.viewPager.setAdapter(this.adapter);
        this.arrayInteger.add(Integer.valueOf(11111));
        this.map.put(Integer.valueOf(0), "hello");
        String str = "yes";
        UserSettings("show_feeds", str);
        UserSettings("show_profile", str);
        UserSettings("show_status", str);
        UserSettings("share_location", str);
        UserSettings("share_birthage", str);
        UserSettings("share_visits", str);
        String str2 = "no";
        UserSettings("user_verified", str2);
        UserSettings("user_premium", str2);
        UserSettings("user_mobile", "+910000000000");
        UserSettings("alert_match", str);
        UserSettings("alert_chats", str);
        UserSettings("alert_likes", str);
        UserSettings("alert_super", str);
        UserSettings("alert_visits", str);
        UserSettings("alert_follows", str);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        TabLayout.OnTabSelectedListener r5 = new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.this.viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    Picasso.get().load((int) R.drawable.tab_profile_on).placeholder((int) R.drawable.tab_profile_off).into(imageView);
                }
                if (tab.getPosition() == 1) {
                    Picasso.get().load((int) R.drawable.tab_users_on).placeholder((int) R.drawable.tab_users_off).into(imageView2);
                }
                if (tab.getPosition() == 2) {
                    Picasso.get().load((int) R.drawable.tab_swipe_on).placeholder((int) R.drawable.tab_swipe_off).into(imageView3);
                }
                if (tab.getPosition() == 3) {
                    Picasso.get().load((int) R.drawable.tab_chat_on).placeholder((int) R.drawable.tab_chat_off).into(imageView4);
                }
                if (tab.getPosition() == 4) {
                    Picasso.get().load((int) R.drawable.tab_extra_on).placeholder((int) R.drawable.tab_extra_off).into(imageView5);
                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    Picasso.get().load((int) R.drawable.tab_profile_off).placeholder((int) R.drawable.tab_profile_on).into(imageView);
                }
                if (tab.getPosition() == 1) {
                    Picasso.get().load((int) R.drawable.tab_users_off).placeholder((int) R.drawable.tab_users_on).into(imageView2);
                }
                if (tab.getPosition() == 2) {
                    Picasso.get().load((int) R.drawable.tab_swipe_off).placeholder((int) R.drawable.tab_swipe_on).into(imageView3);
                }
                if (tab.getPosition() == 3) {
                    Picasso.get().load((int) R.drawable.tab_chat_off).placeholder((int) R.drawable.tab_chat_on).into(imageView4);
                }
                if (tab.getPosition() == 4) {
                    Picasso.get().load((int) R.drawable.tab_extra_off).placeholder((int) R.drawable.tab_extra_on).into(imageView5);
                }
            }
        };
        tabLayout.setOnTabSelectedListener(r5);
        tabLayout.getTabAt(2).select();
        String stringExtra = getIntent().getStringExtra("tab_show");
        if (stringExtra != null) {
            char c = 65535;
            switch (stringExtra.hashCode()) {
                case 1137019647:
                    if (stringExtra.equals("tab_profile")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1932998385:
                    if (stringExtra.equals("tab_chats")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1935682923:
                    if (stringExtra.equals("tab_feeds")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1948229136:
                    if (stringExtra.equals("tab_swipe")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1949953246:
                    if (stringExtra.equals("tab_users")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                tabLayout.getTabAt(0).select();
            } else if (c == 1) {
                tabLayout.getTabAt(1).select();
            } else if (c == 2) {
                tabLayout.getTabAt(2).select();
            } else if (c == 3) {
                tabLayout.getTabAt(3).select();
            } else if (c == 4) {
                tabLayout.getTabAt(4).select();
            }
        }
        if(currentUserId != null)
        {
            UpdateToken(FirebaseInstanceId.getInstance().getToken(),currentUserId);
        }
        SharedPreferences sp = getSharedPreferences("SP_USER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Current_USERID",currentUserId);
        editor.apply();
    }

    private void UserSettings(final String str, final String str2) {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (((DocumentSnapshot) task.getResult()).getString(str) == null) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(str, str2);
                        MainActivity.this.firebaseFirestore.collection("users").document(uid).update(hashMap);
                    }
                }
            });
        }
    }

    private void OnlineUser() {
        String uid = this.firebaseUser.getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("user_online", Timestamp.now());
        this.firebaseFirestore.collection("users").document(uid).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        Application.appRunning = true;
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 == null) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
            return;
        }
        final String uid = firebaseUser2.getUid();
        this.firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String string = ((DocumentSnapshot) task.getResult()).getString("user_dummy");
                    if (string != null && string.equals("yes")) {
                        Intent intent = new Intent(MainActivity.this, RemindActivity.class);
                        intent.putExtra("user_uid", uid);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                    }
                }
            }
        });

        UserTypingStatus("noOne");
    }

    private void UserStatus(String str) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            HashMap hashMap = new HashMap();
            hashMap.put("user_status", str);
            FirebaseFirestore.getInstance().collection("users").document(uid).update(hashMap);
        }
    }

    private void UserTypingStatus(String str) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("TypingTo", str);
        FirebaseFirestore.getInstance().collection("users").document(uid).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        UserStatus("online");
        OnlineUser();
        if (Application.notificationManagerCompat != null) {
            Application.notificationManagerCompat.cancelAll();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        UserStatus("offline");
        Application.appRunning = false;
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void UpdateToken(String token, String currentUserId)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(currentUserId).setValue(mToken);
    }
}
