package com.firedating.chat.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.firedating.chat.Main.MainActivity;
import com.firedating.chat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountsActivity extends AppCompatActivity {
    AccountsAdapter adapter;
    CircleImageView circleImageViewProfileAvatar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    TabLayout tabLayoutAccount;
    ViewPager viewPagerAccount;
    private FloatingActionButton viewPagerBack;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarToolbar));
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.viewPagerAccount = (ViewPager) findViewById(R.id.viewPagerAccount);
        this.viewPagerAccount.setOffscreenPageLimit(7);
        this.viewPagerBack = (FloatingActionButton) findViewById(R.id.viewPagerBack);
        this.tabLayoutAccount = (TabLayout) findViewById(R.id.tabLayoutAccount);
        TabLayout tabLayout = this.tabLayoutAccount;
        tabLayout.addTab(tabLayout.newTab().setText((CharSequence) "Matches"));
        TabLayout tabLayout2 = this.tabLayoutAccount;
        tabLayout2.addTab(tabLayout2.newTab().setText((CharSequence) "Likes"));
        TabLayout tabLayout3 = this.tabLayoutAccount;
        tabLayout3.addTab(tabLayout3.newTab().setText((CharSequence) "Visits"));
        TabLayout tabLayout4 = this.tabLayoutAccount;
        tabLayout4.addTab(tabLayout4.newTab().setText((CharSequence) "Favorite"));
        this.tabLayoutAccount.setTabGravity(0);
        this.adapter = new AccountsAdapter(getSupportFragmentManager(), this.tabLayoutAccount.getTabCount());
        this.viewPagerAccount.setAdapter(this.adapter);
        this.viewPagerAccount.setCurrentItem(0);
        String stringExtra = getIntent().getStringExtra("tab_show");
        if (stringExtra != null) {
            char c = 65535;
            switch (stringExtra.hashCode()) {
                case -2007659895:
                    if (stringExtra.equals("tab_matches")) {
                        c = 0;
                        break;
                    }
                    break;
                case -820149457:
                    if (stringExtra.equals("tab_visitors")) {
                        c = 2;
                        break;
                    }
                    break;
                case 974351917:
                    if (stringExtra.equals("tab_favorites")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1941349010:
                    if (stringExtra.equals("tab_likes")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                this.viewPagerAccount.setCurrentItem(0);
            } else if (c == 1) {
                this.viewPagerAccount.setCurrentItem(1);
            } else if (c == 2) {
                this.viewPagerAccount.setCurrentItem(2);
            } else if (c == 3) {
                this.viewPagerAccount.setCurrentItem(3);
            }
        }
        this.viewPagerBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AccountsActivity.this, MainActivity.class);
                intent.putExtra("tab_show", "tab_profile");
              //  intent.addFlags(67108864);
                AccountsActivity.this.startActivity(intent);
            }
        });
        this.viewPagerAccount.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayoutAccount));
        this.tabLayoutAccount.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                AccountsActivity.this.viewPagerAccount.setCurrentItem(tab.getPosition());
            }
        });
    }

    private void UserStatus(String str) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("user_status", str);
        FirebaseFirestore.getInstance().collection("users").document(uid).update(hashMap);
    }

    private void OnlineUser() {
        String uid = this.firebaseUser.getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("user_online", Timestamp.now());
        this.firebaseFirestore.collection("users").document(uid).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        UserStatus("online");
        OnlineUser();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        UserStatus("offline");
    }
}
