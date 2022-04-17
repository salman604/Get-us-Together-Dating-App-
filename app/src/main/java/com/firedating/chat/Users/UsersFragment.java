package com.firedating.chat.Users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firedating.chat.Extra.EventClass;
import com.firedating.chat.Loves.LovesClass;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.firedating.chat.Start.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UsersFragment extends Fragment {
    /* access modifiers changed from: private */
    public ArrayList<ProfileClass> arrayUserClass;
    String currentUser;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutUsersEmpty;
    ArrayList<LovesClass> listLovesClass;
    SharedPreferences prefs;
    ProgressBar progressBarUsersView;
    /* access modifiers changed from: private */
    public RecyclerView recyclerViewUserView;
    RelativeLayout relativeLayoutUsersContent;
    String stringCheckAgesMax;
    String stringCheckAgesMin;
    String stringCheckGender;
    String stringCheckLocation;
    String stringCheckMarital;
    String stringCheckSeeking;
    String stringCheckSexual;
    SwipeRefreshLayout swipeRefreshLayout;
    /* access modifiers changed from: private */
    public UsersAdapter usersAdapter;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.users_fragment, viewGroup, false);
        this.listLovesClass = new ArrayList<>();
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            this.currentUser = firebaseUser2.getUid();
        }
        else
        {
            startActivity(new Intent(getContext(), StartActivity.class));
            getActivity().finish();
        }
        this.prefs = getActivity().getSharedPreferences("pref", 0);
        this.arrayUserClass = new ArrayList<>();
        this.progressBarUsersView = (ProgressBar) inflate.findViewById(R.id.progressBarUsersView);
        this.recyclerViewUserView = (RecyclerView) inflate.findViewById(R.id.recyclerViewUsersView);
        this.relativeLayoutUsersContent = (RelativeLayout) inflate.findViewById(R.id.relativeLayoutUsersContent);
        this.relativeLayoutUsersContent.setVisibility(View.VISIBLE);
        this.linearLayoutUsersEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutUsersEmpty);
        this.linearLayoutUsersEmpty.setVisibility(View.GONE);
        this.recyclerViewUserView.setHasFixedSize(true);
        this.recyclerViewUserView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.userSwipeRefreshLayout);

        UserRecyclerView();
        SwipeRefresh();
        return inflate;
    }

    private void SwipeRefresh() {
        int parseColor = Color.parseColor("#880e4f");
        this.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#ffffff"));
        this.swipeRefreshLayout.setColorSchemeColors(parseColor);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                UsersFragment.this.swipeRefreshLayout.post(new Runnable() {
                    public void run() {
                        UsersFragment.this.swipeRefreshLayout.setRefreshing(true);
                        UsersFragment.this.arrayUserClass.clear();
                        UsersFragment.this.UserRecyclerView();
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void UserRecyclerView() {
        this.firebaseFirestore.collection("users").document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                String string = documentSnapshot.getString("show_marital");
                String str = "Any";
                if (string != null) {
                    UsersFragment.this.stringCheckMarital = string;
                } else {
                    UsersFragment.this.stringCheckMarital = str;
                }
                String string2 = documentSnapshot.getString("show_sexual");
                if (string2 != null) {
                    UsersFragment.this.stringCheckSexual = string2;
                } else {
                    UsersFragment.this.stringCheckSexual = str;
                }
                String string3 = documentSnapshot.getString("show_seeking");
                if (string3 != null) {
                    UsersFragment.this.stringCheckSeeking = string3;
                } else {
                    UsersFragment.this.stringCheckSeeking = str;
                }
                String string4 = documentSnapshot.getString("show_ages");
                if (string4 != null) {
                    String[] split = string4.trim().split("\\s*-\\s*");
                    UsersFragment usersFragment = UsersFragment.this;
                    usersFragment.stringCheckAgesMin = split[0];
                    usersFragment.stringCheckAgesMax = split[1];
                } else {
                    UsersFragment usersFragment2 = UsersFragment.this;
                    usersFragment2.stringCheckAgesMin = "16";
                    usersFragment2.stringCheckAgesMax = "100000";
                }
                String str2 = "show_location";
                String string5 = documentSnapshot.getString(str2);
                String str3 = "users";
                if (string5 != null) {
                    UsersFragment.this.stringCheckLocation = string5;
                } else {
                    String string6 = documentSnapshot.getString("user_state");
                    HashMap hashMap = new HashMap();
                    hashMap.put(str2, "Anywhere");
                    UsersFragment.this.firebaseFirestore.collection(str3).document(UsersFragment.this.currentUser).update(hashMap);
                    UsersFragment.this.stringCheckLocation = string6;
                }
                String str4 = "show_gender";
                String string7 = documentSnapshot.getString(str4);
                String str5 = "Woman";
                String str6 = "female";
                String str7 = "Man";
                String str8 = "male";
                if (string7 != null) {
                    if (string7.equals(str7)) {
                        UsersFragment usersFragment3 = UsersFragment.this;
                        usersFragment3.stringCheckGender = str8;
                        usersFragment3.UsersDisplay(usersFragment3.stringCheckGender, UsersFragment.this.stringCheckAgesMin, UsersFragment.this.stringCheckAgesMax, UsersFragment.this.stringCheckLocation, UsersFragment.this.stringCheckMarital, UsersFragment.this.stringCheckSexual, UsersFragment.this.stringCheckSeeking);
                    } else if (string7.equals(str5)) {
                        UsersFragment usersFragment4 = UsersFragment.this;
                        usersFragment4.stringCheckGender = str6;
                        usersFragment4.UsersDisplay(usersFragment4.stringCheckGender, UsersFragment.this.stringCheckAgesMin, UsersFragment.this.stringCheckAgesMax, UsersFragment.this.stringCheckLocation, UsersFragment.this.stringCheckMarital, UsersFragment.this.stringCheckSexual, UsersFragment.this.stringCheckSeeking);
                    } else if (string7.equals(str)) {
                        UsersFragment usersFragment5 = UsersFragment.this;
                        usersFragment5.stringCheckGender = str;
                        usersFragment5.UsersDisplay(usersFragment5.stringCheckGender, UsersFragment.this.stringCheckAgesMin, UsersFragment.this.stringCheckAgesMax, UsersFragment.this.stringCheckLocation, UsersFragment.this.stringCheckMarital, UsersFragment.this.stringCheckSexual, UsersFragment.this.stringCheckSeeking);
                    }
                } else if (documentSnapshot.getString("user_gender").equals(str8)) {
                    UsersFragment.this.stringCheckGender = str6;
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(str4, str5);
                    UsersFragment.this.firebaseFirestore.collection(str3).document(UsersFragment.this.currentUser).update(hashMap2);
                    UsersFragment usersFragment6 = UsersFragment.this;
                    usersFragment6.UsersDisplay(usersFragment6.stringCheckGender, UsersFragment.this.stringCheckAgesMin, UsersFragment.this.stringCheckAgesMax, UsersFragment.this.stringCheckLocation, UsersFragment.this.stringCheckMarital, UsersFragment.this.stringCheckSexual, UsersFragment.this.stringCheckSeeking);
                } else {
                    UsersFragment.this.stringCheckGender = str8;
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put(str4, str7);
                    UsersFragment.this.firebaseFirestore.collection(str3).document(UsersFragment.this.currentUser).update(hashMap3);
                    UsersFragment usersFragment7 = UsersFragment.this;
                    usersFragment7.UsersDisplay(usersFragment7.stringCheckGender, UsersFragment.this.stringCheckAgesMin, UsersFragment.this.stringCheckAgesMax, UsersFragment.this.stringCheckLocation, UsersFragment.this.stringCheckMarital, UsersFragment.this.stringCheckSexual, UsersFragment.this.stringCheckSeeking);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void UsersDisplay(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        final String uid = this.firebaseUser.getUid();
        Task task = this.firebaseFirestore.collection("users").orderBy("user_online", Query.Direction.DESCENDING).get();
        final String str8 = str;
        final String str9 = str2;
        final String str10 = str3;
        final String str11 = str4;
        final String str12 = str5;
        final String str13 = str6;
        final String str14 = str7;
        OnCompleteListener r0 = new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Iterator it = ((QuerySnapshot) task.getResult()).iterator();
                while (it.hasNext()) {
                    ProfileClass profileClass = (ProfileClass) ((QueryDocumentSnapshot) it.next()).toObject(ProfileClass.class);
                    if (!profileClass.getUser_uid().equals(uid)) {
                        String str = "Any";
                        String str2 = "Anywhere";
                        String str3 = "yes";
                        if (str8.equals(str)) {
                            int intValue = Integer.valueOf(profileClass.getUser_birthage()).intValue();
                            int intValue2 = Integer.valueOf(str9).intValue();
                            int intValue3 = Integer.valueOf(str10).intValue();
                            if (intValue >= intValue2 && intValue <= intValue3) {
                                if (str11.equals(str2)) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                } else if (str11.equals(profileClass.getUser_city()) || str11.equals(profileClass.getUser_state()) || str11.equals(profileClass.getUser_country())) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (profileClass.getUser_gender().equals(str8)) {
                            int intValue4 = Integer.valueOf(profileClass.getUser_birthage()).intValue();
                            int intValue5 = Integer.valueOf(str9).intValue();
                            int intValue6 = Integer.valueOf(str10).intValue();
                            if (intValue4 >= intValue5 && intValue4 <= intValue6) {
                                if (str11.equals(str2)) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                } else if (str11.equals(profileClass.getUser_city()) || str11.equals(profileClass.getUser_state()) || str11.equals(profileClass.getUser_country())) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3)) {
                                                    UsersFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                UsersFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    UsersFragment usersFragment = UsersFragment.this;
                    usersFragment.usersAdapter = new UsersAdapter(usersFragment.arrayUserClass, UsersFragment.this.getActivity());
                    UsersFragment.this.recyclerViewUserView.setAdapter(UsersFragment.this.usersAdapter);
                    UsersFragment.this.swipeRefreshLayout.setRefreshing(false);
                    UsersFragment.this.progressBarUsersView.setVisibility(View.GONE);
                    if (UsersFragment.this.arrayUserClass.size() == 0) {
                        UsersFragment.this.relativeLayoutUsersContent.setVisibility(View.GONE);
                        UsersFragment.this.linearLayoutUsersEmpty.setVisibility(View.VISIBLE);
                    } else {
                        UsersFragment.this.relativeLayoutUsersContent.setVisibility(View.VISIBLE);
                        UsersFragment.this.linearLayoutUsersEmpty.setVisibility(View.GONE);
                    }
                }
            }
        };
        task.addOnCompleteListener(r0);
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventClass eventClass) {
        this.arrayUserClass.clear();
        UserRecyclerView();
        Toast.makeText(getActivity(), eventClass.message, Toast.LENGTH_SHORT).show();
    }
}
