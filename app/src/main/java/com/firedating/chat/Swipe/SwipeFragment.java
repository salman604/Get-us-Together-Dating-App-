package com.firedating.chat.Swipe;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firedating.chat.Extra.EventClass;
import com.firedating.chat.Extra.NopesClass;
import com.firedating.chat.Loves.LovesClass;
import com.firedating.chat.Matches.MatchesClass;
import com.firedating.chat.Message.MessageActivity;
import com.firedating.chat.Notification.MySingleton;
import com.firedating.chat.Notification.Token;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class SwipeFragment extends Fragment implements CardStackListener {

    /* access modifiers changed from: private */
    public ArrayList<ProfileClass> arrayUserClass;
    /* access modifiers changed from: private */
    public List<String> arrayUserRemove;
    CardStackView cardStackView;
    String currentUser;
    private FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    ImageView imageRippleSwipeUser;
    CircleImageView imageViewProfileViewLovesBoost;
    CircleImageView imageViewProfileViewLovesLike;
    CircleImageView imageViewProfileViewLovesNope;
    CircleImageView imageViewProfileViewLovesSuper;
    CircleImageView imageViewProfileViewLovesUndo;
    int intSwipePositionFirst;
    int intSwipePositionLast;
    int intSwipePositionRewind;
    LinearLayout linearLayoutSwipeButtons;
    LinearLayout linearLayoutSwipeEmptyGroup;
    LinearLayout linearLayoutSwipeImageGroup;
    CardStackLayoutManager manager;
    RippleBackground rippleSwipeAnimation;
    String stringCheckAgesMax;
    String stringCheckAgesMin;
    String stringCheckGender;
    String stringCheckLocation;
    String stringCheckMarital;
    String stringCheckSeeking;
    String stringCheckSexual;
    String currentUserId,stringlat,stringlong,username;

    boolean notify = false;
    String server_key = "AAAAQ-eFzEo:APA91bFeZ0AL7c7R_SsZT9xzxReszLFXgViq1X8R3JtvtfE9hScs6YvzMjBxhsBV8cvTcImkAjWFx1Yd6xI_mJWPvyzEeTunyHTOprfeGcn0a5nIrS0KvL9lYxY8raoUv9lq6i27vGGv";
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + server_key;
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String TOPIC;


    public static SwipeFragment swipeFragment;

    public void onCardCanceled() {
    }

    public void onCardDragging(Direction direction, float f) {
    }

    public void onCardRewound() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.swipe_fragment, viewGroup, false);
        System.out.println("debug Swipe fragment onCreate view");

        swipeFragment = this;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            this.currentUser = firebaseUser2.getUid();
        }

        this.arrayUserClass = new ArrayList<>();
        this.arrayUserRemove = new ArrayList();
        this.arrayUserRemove.add("demoUserWhenZero");
        this.imageViewProfileViewLovesUndo = (CircleImageView) inflate.findViewById(R.id.imageViewProfileViewLovesUndo);
        this.imageViewProfileViewLovesNope = (CircleImageView) inflate.findViewById(R.id.imageViewProfileViewLovesNope);
        this.imageViewProfileViewLovesSuper = (CircleImageView) inflate.findViewById(R.id.imageViewProfileViewLovesSuper);
        this.imageViewProfileViewLovesLike = (CircleImageView) inflate.findViewById(R.id.imageViewProfileViewLovesLike);
        this.imageViewProfileViewLovesBoost = (CircleImageView) inflate.findViewById(R.id.imageViewProfileViewLovesBoost);
        this.linearLayoutSwipeButtons = (LinearLayout) inflate.findViewById(R.id.linearLayoutSwipeButtons);
        this.linearLayoutSwipeButtons.setVisibility(View.GONE);
        this.linearLayoutSwipeImageGroup = (LinearLayout) inflate.findViewById(R.id.linearLayoutSwipeImageGroup);
        this.linearLayoutSwipeEmptyGroup = (LinearLayout) inflate.findViewById(R.id.linearLayoutSwipeEmptyGroup);
        this.manager = new CardStackLayoutManager(getContext(), this);
        this.cardStackView = (CardStackView) inflate.findViewById(R.id.cardStackViewNavigationSwipe);
        this.cardStackView.setLayoutManager(this.manager);
        this.manager.setStackFrom(StackFrom.None);
        this.manager.setVisibleCount(2);
        this.manager.setScaleInterval(0.95f);
        this.manager.setSwipeThreshold(0.2f);
        this.manager.setMaxDegree(30.0f);
        this.manager.setDirections(Arrays.asList(new Direction[]{Direction.Top, Direction.Left, Direction.Right}));
        this.manager.setCanScrollHorizontal(true);
        this.manager.setCanScrollVertical(true);
        this.manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        getCurrentUserLocation();
        UserRecyclerView();
        this.rippleSwipeAnimation = (RippleBackground) inflate.findViewById(R.id.rippleSwipeAnimation);
        this.imageRippleSwipeUser = (ImageView) inflate.findViewById(R.id.imageRippleSwipeUser);
        this.rippleSwipeAnimation.startRippleAnimation();
        this.firebaseFirestore.collection("users").document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    Picasso.get().load(documentSnapshot.getString("user_image")).placeholder((int) R.drawable.profile_image).into(SwipeFragment.this.imageRippleSwipeUser);
                }
            }
        });

        this.imageViewProfileViewLovesNope.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SwipeFragment.this.manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Left).setDuration(Duration.Normal.duration).build());
                SwipeFragment.this.cardStackView.swipe();
            }
        });
        this.imageViewProfileViewLovesLike.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SwipeFragment.this.manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Right).setDuration(Duration.Normal.duration).build());
                SwipeFragment.this.cardStackView.swipe();
            }
        });
        this.imageViewProfileViewLovesUndo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SwipeFragment.this.SwipeUserRewind();
            }
        });
        this.imageViewProfileViewLovesSuper.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SwipeFragment.this.SwipeUserSuper();
            }
        });
        this.imageViewProfileViewLovesBoost.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(SwipeFragment.this.getContext(), "Under development!", Toast.LENGTH_SHORT).show();
            }
        });
        return inflate;
    }

    private void UserRecyclerView() {
        String str = "users";
        this.firebaseFirestore.collection(str).document(this.currentUser).collection("loves").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            SwipeFragment.this.arrayUserRemove.add(((LovesClass) documentChange.getDocument().toObject(LovesClass.class)).getUser_loves());
                        }
                    }
                }
            }
        });
        this.firebaseFirestore.collection(str).document(this.currentUser).collection("nopes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            SwipeFragment.this.arrayUserRemove.add(((NopesClass) documentChange.getDocument().toObject(NopesClass.class)).getUser_nopes());
                        }
                    }
                }
            }
        });
        this.firebaseFirestore.collection(str).document(this.currentUser).collection("matches").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            SwipeFragment.this.arrayUserRemove.add(((MatchesClass) documentChange.getDocument().toObject(MatchesClass.class)).getUser_matches());
                        }
                    }
                }
            }
        });

    }

    public void ShowPeopleOnswipeFragment(String stringlat, String stringlong, String username)
    {
        this.firebaseFirestore.collection("users").document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                String string = documentSnapshot.getString("show_marital");
                String str = "Any";
                if (string != null) {
                    SwipeFragment.this.stringCheckMarital = string;
                } else {
                    SwipeFragment.this.stringCheckMarital = str;
                }
                String string2 = documentSnapshot.getString("show_sexual");
                if (string2 != null) {
                    SwipeFragment.this.stringCheckSexual = string2;
                } else {
                    SwipeFragment.this.stringCheckSexual = str;
                }
                String string3 = documentSnapshot.getString("show_seeking");
                if (string3 != null) {
                    SwipeFragment.this.stringCheckSeeking = string3;
                } else {
                    SwipeFragment.this.stringCheckSeeking = str;
                }
                String string4 = documentSnapshot.getString("show_ages");
                if (string4 != null) {
                    String[] split = string4.trim().split("\\s*-\\s*");
                    SwipeFragment swipeFragment = SwipeFragment.this;
                    swipeFragment.stringCheckAgesMin = split[0];
                    swipeFragment.stringCheckAgesMax = split[1];
                } else {
                    SwipeFragment swipeFragment2 = SwipeFragment.this;
                    swipeFragment2.stringCheckAgesMin = "16";
                    swipeFragment2.stringCheckAgesMax = "100000";
                }
                String str2 = "show_location";
                String string5 = documentSnapshot.getString(str2);
                String str3 = "users";
                if (string5 != null) {
                    SwipeFragment.this.stringCheckLocation = string5;
                } else {
                    String string6 = documentSnapshot.getString("user_state");
                    HashMap hashMap = new HashMap();
                    hashMap.put(str2, "Anywhere");
                    SwipeFragment.this.firebaseFirestore.collection(str3).document(SwipeFragment.this.currentUser).update(hashMap);
                    SwipeFragment.this.stringCheckLocation = string6;
                }
                String str4 = "show_gender";
                String string7 = documentSnapshot.getString(str4);
                String str5 = "Woman";
                String str6 = "female";
                String str7 = "Man";
                String str8 = "male";
                if (string7 != null) {
                    if (string7.equals(str7)) {
                        SwipeFragment swipeFragment3 = SwipeFragment.this;
                        swipeFragment3.stringCheckGender = str8;
                        swipeFragment3.UsersDisplay(swipeFragment3.stringCheckGender, SwipeFragment.this.stringCheckAgesMin, SwipeFragment.this.stringCheckAgesMax, SwipeFragment.this.stringCheckLocation, SwipeFragment.this.stringCheckMarital, SwipeFragment.this.stringCheckSexual, SwipeFragment.this.stringCheckSeeking);
                    } else if (string7.equals(str5)) {
                        SwipeFragment swipeFragment4 = SwipeFragment.this;
                        swipeFragment4.stringCheckGender = str6;
                        swipeFragment4.UsersDisplay(swipeFragment4.stringCheckGender, SwipeFragment.this.stringCheckAgesMin, SwipeFragment.this.stringCheckAgesMax, SwipeFragment.this.stringCheckLocation, SwipeFragment.this.stringCheckMarital, SwipeFragment.this.stringCheckSexual, SwipeFragment.this.stringCheckSeeking);
                    } else if (string7.equals(str)) {
                        SwipeFragment swipeFragment5 = SwipeFragment.this;
                        swipeFragment5.stringCheckGender = str;
                        swipeFragment5.UsersDisplay(swipeFragment5.stringCheckGender, SwipeFragment.this.stringCheckAgesMin, SwipeFragment.this.stringCheckAgesMax, SwipeFragment.this.stringCheckLocation, SwipeFragment.this.stringCheckMarital, SwipeFragment.this.stringCheckSexual, SwipeFragment.this.stringCheckSeeking);
                    }
                } else if (documentSnapshot.getString("user_gender").equals(str8)) {
                    SwipeFragment.this.stringCheckGender = str6;
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("show_gender", str5);
                    SwipeFragment.this.firebaseFirestore.collection(str3).document(SwipeFragment.this.currentUser).update(hashMap2);
                    SwipeFragment swipeFragment6 = SwipeFragment.this;
                    swipeFragment6.UsersDisplay(swipeFragment6.stringCheckGender, SwipeFragment.this.stringCheckAgesMin, SwipeFragment.this.stringCheckAgesMax, SwipeFragment.this.stringCheckLocation, SwipeFragment.this.stringCheckMarital, SwipeFragment.this.stringCheckSexual, SwipeFragment.this.stringCheckSeeking);
                } else {
                    SwipeFragment.this.stringCheckGender = str8;
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put("show_gender", str7);
                    SwipeFragment.this.firebaseFirestore.collection(str3).document(SwipeFragment.this.currentUser).update(hashMap3);
                    SwipeFragment swipeFragment7 = SwipeFragment.this;
                    swipeFragment7.UsersDisplay(swipeFragment7.stringCheckGender, SwipeFragment.this.stringCheckAgesMin, SwipeFragment.this.stringCheckAgesMax, SwipeFragment.this.stringCheckLocation, SwipeFragment.this.stringCheckMarital, SwipeFragment.this.stringCheckSexual, SwipeFragment.this.stringCheckSeeking);
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
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                } else if (str11.equals(profileClass.getUser_city()) || str11.equals(profileClass.getUser_state()) || str11.equals(profileClass.getUser_country())) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
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
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                } else if (str11.equals(profileClass.getUser_city()) || str11.equals(profileClass.getUser_state()) || str11.equals(profileClass.getUser_country())) {
                                    if (str12.equals(str)) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    } else if (str12.equals(profileClass.getUser_marital())) {
                                        if (str13.equals(str)) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        } else if (str13.equals(profileClass.getUser_sexual())) {
                                            if (str14.equals(str)) {
                                                if (!SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                    SwipeFragment.this.arrayUserClass.add(profileClass);
                                                }
                                            } else if (str14.equals(profileClass.getUser_seeking()) && !SwipeFragment.this.arrayUserRemove.contains(profileClass.getUser_uid()) && (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals(str3))) {
                                                SwipeFragment.this.arrayUserClass.add(profileClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    SwipeFragment.this.cardStackView.setAdapter(new SwipeAdapter(SwipeFragment.this.arrayUserClass, SwipeFragment.this.getActivity(),stringlat,stringlong,username));
                    if (SwipeFragment.this.arrayUserClass.size() == 0) {
                        SwipeFragment.this.linearLayoutSwipeButtons.setVisibility(View.GONE);
                        SwipeFragment.this.linearLayoutSwipeImageGroup.setVisibility(View.VISIBLE);
                        SwipeFragment.this.linearLayoutSwipeEmptyGroup.setVisibility(View.GONE);
                        SwipeFragment.this.rippleSwipeAnimation.stopRippleAnimation();
                        SwipeFragment.this.rippleSwipeAnimation.setVisibility(View.GONE);
                    } else {
                        SwipeFragment.this.linearLayoutSwipeButtons.setVisibility(View.VISIBLE);
                        SwipeFragment.this.linearLayoutSwipeImageGroup.setVisibility(View.VISIBLE);
                        SwipeFragment.this.linearLayoutSwipeEmptyGroup.setVisibility(View.GONE);
                        SwipeFragment.this.rippleSwipeAnimation.stopRippleAnimation();
                        SwipeFragment.this.rippleSwipeAnimation.setVisibility(View.GONE);
                    }
                }
            }
        };
        task.addOnCompleteListener(r0);
    }

    /* access modifiers changed from: private */
    public void SwipeUserMatch(final String str, final Context context) {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("likes").document(str).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("user_matched", Timestamp.now());
                    hashMap.put("user_matches", str);
                    SwipeFragment.this.firebaseFirestore.collection("users").document(SwipeFragment.this.currentUser).collection("matches").document(str).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("user_matched", Timestamp.now());
                                hashMap.put("user_matches", SwipeFragment.this.currentUser);
                                SwipeFragment.this.firebaseFirestore.collection("users").document(str).collection("matches").document(SwipeFragment.this.currentUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AlertDialogueForItsAMatch(context);
                                            Toast.makeText(SwipeFragment.this.getContext(), "Hoorayy!! Matched!", Toast.LENGTH_SHORT).show();
                                            senNotification(str,"Dine Dating","Hurray ! You got a match");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void SwipeUserLoves() {
        final String user_uid = ((ProfileClass) this.arrayUserClass.get(this.intSwipePositionFirst)).getUser_uid();
        final HashMap hashMap = new HashMap();
        hashMap.put("user_loves", user_uid);
        hashMap.put("user_loved", Timestamp.now());
        hashMap.put("user_super", "no");
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("loves").document(user_uid).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                HashMap hashMap = new HashMap();
                hashMap.put("user_likes", SwipeFragment.this.currentUser);
                hashMap.put("user_liked", Timestamp.now());
                hashMap.put("user_super", "no");
                SwipeFragment.this.firebaseFirestore.collection("users").document(user_uid).collection("likes").document(SwipeFragment.this.currentUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SwipeFragment.this.SwipeUserMatch(user_uid, getContext());
                            try{
                                senNotification(user_uid,"Dine Dating"," Someone likes you ");
                            }
                            catch (Exception e)
                            {

                            }

                        }
                    }
                });
            }
        });
    }

    public void SwipeUserSuper() {
        final String user_uid = ((ProfileClass) this.arrayUserClass.get(this.intSwipePositionFirst)).getUser_uid();
        final HashMap hashMap = new HashMap();
        hashMap.put("user_loves", user_uid);
        hashMap.put("user_loved", Timestamp.now());
        hashMap.put("user_super", "yes");
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("loves").document(user_uid).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                HashMap hashMap = new HashMap();
                hashMap.put("user_likes", SwipeFragment.this.currentUser);
                hashMap.put("user_liked", Timestamp.now());
                hashMap.put("user_super", "yes");
                SwipeFragment.this.firebaseFirestore.collection("users").document(user_uid).collection("likes").document(SwipeFragment.this.currentUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SwipeFragment.this.SwipeUserMatch(user_uid,getContext());
                            try {
                                MessageActivity.messageActivity.senNotification(user_uid,"Dine Dating","You got a Superlike ");
                            }
                            catch (Exception e)
                            {

                            }


                        }
                    }
                });
            }
        });
        this.manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder().setDirection(Direction.Top).setDuration(Duration.Normal.duration).build());
        this.cardStackView.swipe();
    }

    public void SwipeUserNopes() {
        String user_uid = ((ProfileClass) this.arrayUserClass.get(this.intSwipePositionFirst)).getUser_uid();
        HashMap hashMap = new HashMap();
        hashMap.put("user_nopes", user_uid);
        hashMap.put("user_noped", Timestamp.now());
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("nopes").document(user_uid).set((Map<String, Object>) hashMap);
    }

    public void SwipeUserRewind() {
        this.intSwipePositionRewind = this.intSwipePositionFirst - 1;
        int i = this.intSwipePositionRewind;
        if (i >= 0) {
            String user_uid = ((ProfileClass) this.arrayUserClass.get(i)).getUser_uid();
            String str = "users";
            this.firebaseFirestore.collection(str).document(this.currentUser).collection("loves").document(user_uid).delete();
            this.firebaseFirestore.collection(str).document(this.currentUser).collection("nopes").document(user_uid).delete();
            String str2 = "matches";
            this.firebaseFirestore.collection(str).document(this.currentUser).collection(str2).document(user_uid).delete();
            this.firebaseFirestore.collection(str).document(user_uid).collection(str2).document(this.currentUser).delete();
            this.firebaseFirestore.collection(str).document(user_uid).collection("likes").document(this.currentUser).delete();
            this.manager.setRewindAnimationSetting(new RewindAnimationSetting.Builder().setDuration(Duration.Normal.duration).build());
            this.cardStackView.rewind();
            return;
        }
        Toast.makeText(getContext(), "Swipe more cards to rewind!", Toast.LENGTH_SHORT).show();
    }

    private void EventSend() {
        EventBus.getDefault().post(new EventClass("Hello everyone!"));
    }

    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            SwipeUserLoves();
        } else if (direction == Direction.Left) {
            SwipeUserNopes();
        } else if (direction == Direction.Top) {
            SwipeUserSuper();
        }
    }

    public void onCardAppeared(View view, int i) {
        this.intSwipePositionFirst = i;
    }

    public void onCardDisappeared(View view, int i) {
        this.intSwipePositionLast = i;
    }

    @Override
    public void onStart() {
        super.onStart();

//        ShowPeopleOnswipeFragment(stringlat, stringlong);
    }


    public void getCurrentUserLocation()
    {
        currentUserId = firebaseUser.getUid();
        firebaseFirestore.collection("users").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                DocumentSnapshot documentSnapshot1 = documentSnapshot;
                if (documentSnapshot1 != null) {
                    stringlat = documentSnapshot1.getString("user_latitude");
                    stringlong = documentSnapshot1.getString("user_longitude");
                    username = documentSnapshot1.getString("user_name");
                    System.out.println("debug current user lat  1" + stringlat );

                    ShowPeopleOnswipeFragment(stringlat,stringlong,username);
                }
            }
        });
    }

    private void AlertDialogueForItsAMatch(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alert_dialogue_for_match, null);
        builder.setView(dialoglayout);

        final AlertDialog alert = builder.create();
        alert.show();

// Hide after some seconds
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                alert.dismiss();
            }
        }.start();


    }

    public void senNotification(String messageReceiverID, final String myName, final String messageText)
    {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        com.google.firebase.database.Query query = allTokens.orderByKey().equalTo(messageReceiverID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("debug inside sendNotification==");
                    Token token = ds.getValue(Token.class);
                    System.out.println("debug Token " + token);


                    TOPIC = "/topics/userABC"; //topic has to match what the receiver subscribed to


                    JSONObject notification = new JSONObject();
                    JSONObject notifcationBody = new JSONObject();
                    try {
                        notifcationBody.put("title", myName);
                        notifcationBody.put("message", messageText);

                        notification.put("to", token.getToken());
                        notification.put("data", notifcationBody);

                        System.out.println("debug title=="+myName+"+message=="+messageText+"+token"+token.getToken());
                    } catch (JSONException e) {
                        Log.e(TAG, "onCreate: " + e.getMessage() );
                    }
                    sendNotification(notification);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toast.makeText(getContext(), "Notification Sent succesfully", Toast.LENGTH_SHORT).show();
//                        edtTitle.setText("");
//                        edtMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }


}
