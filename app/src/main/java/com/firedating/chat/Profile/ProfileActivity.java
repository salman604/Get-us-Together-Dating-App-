package com.firedating.chat.Profile;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.Extra.EventClass;
import com.firedating.chat.Extra.ImageClass;
import com.firedating.chat.Message.MessageActivity;
import com.firedating.chat.R;
import com.firedating.chat.Settings.ReportActivity;
import com.firedating.chat.Start.RegisterActivity;
import com.firedating.chat.Swipe.SwipeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.net.HttpHeaders;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ss.com.bannerslider.Slider;

public class ProfileActivity extends AppCompatActivity {

    int caraoselTotalNumber = 1;
    CarouselView carouselView;
    String countryCurrent;
    String currentUser;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FloatingActionButton floatingActionButtonChat,floatingActionButtonLikeBack;
    String genderCurrent;
    String imageCurrent;
    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int i, ImageView imageView) {
            FirebaseStorage.getInstance();
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 0) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 1) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 2) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 3) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 4) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 5) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 6) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 7) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
            if (i == 8) {
                ProfileActivity.this.StartSlider(i, imageView);
            }
        }
    };
    ImageView imageViewProfileImage;
    Double latitudeChatUser;
    Double latitudeCurrent;
    LinearLayout linearLayoutProfileViewAbout;
    LinearLayout linearLayoutProfileViewActivities;
    LinearLayout linearLayoutProfileViewAppearance;
    LinearLayout linearLayoutProfileViewBodyart;
    LinearLayout linearLayoutProfileViewBodytype;
    LinearLayout linearLayoutProfileViewBooks;
    LinearLayout linearLayoutProfileViewCompany;
    LinearLayout linearLayoutProfileViewDrinking;
    LinearLayout linearLayoutProfileViewEducation;
    LinearLayout linearLayoutProfileViewEthnicity;
    LinearLayout linearLayoutProfileViewEyecolor;
    LinearLayout linearLayoutProfileViewEyewear;
    LinearLayout linearLayoutProfileViewFeature;
    LinearLayout linearLayoutProfileViewHaircolor;
    LinearLayout linearLayoutProfileViewHeight;
    LinearLayout linearLayoutProfileViewIncome;
    LinearLayout linearLayoutProfileViewInterests;
    LinearLayout linearLayoutProfileViewJobtitle;
    LinearLayout linearLayoutProfileViewLanguage;
    LinearLayout linearLayoutProfileViewLiving;
    LinearLayout linearLayoutProfileViewLooking;
    LinearLayout linearLayoutProfileViewMarital;
    LinearLayout linearLayoutProfileViewMovies;
    LinearLayout linearLayoutProfileViewMusics;
    LinearLayout linearLayoutProfileViewReligion;
    LinearLayout linearLayoutProfileViewRelocation;
    LinearLayout linearLayoutProfileViewSeeking;
    LinearLayout linearLayoutProfileViewSexual;
    LinearLayout linearLayoutProfileViewSmoking;
    LinearLayout linearLayoutProfileViewSports;
    LinearLayout linearLayoutProfileViewStarsign;
    LinearLayout linearLayoutProfileViewTvshows;
    LinearLayout linearLayoutProfileViewWeight;
    Double longitudeChatUser;
    Double longitudeCurrent;
    /* access modifiers changed from: private */
    public Menu menuMessage;
    String premiumCurrent;
    String profileUser;
    /* access modifiers changed from: private */
    public ArrayList<String> stringArrayCovers;
    String stringDistance;
    String[] string_array_user_report;
    TextView textViewProfileViewAbout;
    TextView textViewProfileViewActivities;
    TextView textViewProfileViewAppearance;
    TextView textViewProfileViewBirthage;
    TextView textViewProfileViewBodyart;
    TextView textViewProfileViewBodytype;
    TextView textViewProfileViewBooks;
    TextView textViewProfileViewCompany;
    TextView textViewProfileViewDistance;
    TextView textViewProfileViewDrinking;
    TextView textViewProfileViewEducation;
    TextView textViewProfileViewEthnicity;
    TextView textViewProfileViewEyecolor;
    TextView textViewProfileViewEyewear;
    TextView textViewProfileViewFeature;
    TextView textViewProfileViewHaircolor;
    TextView textViewProfileViewHeight;
    TextView textViewProfileViewIncome;
    TextView textViewProfileViewInterests;
    TextView textViewProfileViewJobtitle;
    TextView textViewProfileViewLanguage;
    TextView textViewProfileViewLiving;
    TextView textViewProfileViewLocation;
    TextView textViewProfileViewLooking;
    TextView textViewProfileViewMarital;
    TextView textViewProfileViewMovies;
    TextView textViewProfileViewMusics;
    TextView textViewProfileViewReligion;
    TextView textViewProfileViewRelocation;
    TextView textViewProfileViewSeeking;
    TextView textViewProfileViewSexual;
    TextView textViewProfileViewSmoking;
    TextView textViewProfileViewSports;
    TextView textViewProfileViewStarsign;
    TextView textViewProfileViewTvshows;
    TextView textViewProfileViewUsername;
    TextView textViewProfileViewWeight;
    Toolbar toolbarProfile;
    String user_cover_eight;
    String user_cover_five;
    String user_cover_four;
    String user_cover_one;
    String user_cover_seven;
    String user_cover_six;
    String user_cover_three;
    String user_cover_two;
    String user_cover_zero;
    String verifiedCurrent;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        this.linearLayoutProfileViewAbout = (LinearLayout) findViewById(R.id.linearLayoutProfileViewAbout);
        this.linearLayoutProfileViewLooking = (LinearLayout) findViewById(R.id.linearLayoutProfileViewLooking);
        this.linearLayoutProfileViewSeeking = (LinearLayout) findViewById(R.id.linearLayoutProfileViewSeeking);
        this.linearLayoutProfileViewMarital = (LinearLayout) findViewById(R.id.linearLayoutProfileViewMarital);
        this.linearLayoutProfileViewSexual = (LinearLayout) findViewById(R.id.linearLayoutProfileViewSexual);
        this.linearLayoutProfileViewHeight = (LinearLayout) findViewById(R.id.linearLayoutProfileViewHeight);
        this.linearLayoutProfileViewWeight = (LinearLayout) findViewById(R.id.linearLayoutProfileViewWeight);
        this.linearLayoutProfileViewAppearance = (LinearLayout) findViewById(R.id.linearLayoutProfileViewAppearance);
        this.linearLayoutProfileViewFeature = (LinearLayout) findViewById(R.id.linearLayoutProfileViewFeature);
        this.linearLayoutProfileViewEthnicity = (LinearLayout) findViewById(R.id.linearLayoutProfileViewEthnicity);
        this.linearLayoutProfileViewBodytype = (LinearLayout) findViewById(R.id.linearLayoutProfileViewBodytype);
        this.linearLayoutProfileViewBodyart = (LinearLayout) findViewById(R.id.linearLayoutProfileViewBodyart);
        this.linearLayoutProfileViewEyecolor = (LinearLayout) findViewById(R.id.linearLayoutProfileViewEyecolor);
        this.linearLayoutProfileViewEyewear = (LinearLayout) findViewById(R.id.linearLayoutProfileViewEyewear);
        this.linearLayoutProfileViewHaircolor = (LinearLayout) findViewById(R.id.linearLayoutProfileViewHaircolor);
        this.linearLayoutProfileViewStarsign = (LinearLayout) findViewById(R.id.linearLayoutProfileViewStarsign);
        this.linearLayoutProfileViewJobtitle = (LinearLayout) findViewById(R.id.linearLayoutProfileViewJobtitle);
        this.linearLayoutProfileViewCompany = (LinearLayout) findViewById(R.id.linearLayoutProfileViewCompany);
        this.linearLayoutProfileViewIncome = (LinearLayout) findViewById(R.id.linearLayoutProfileViewIncome);
        this.linearLayoutProfileViewEducation = (LinearLayout) findViewById(R.id.linearLayoutProfileViewEducation);
        this.linearLayoutProfileViewLanguage = (LinearLayout) findViewById(R.id.linearLayoutProfileViewLanguage);
        this.linearLayoutProfileViewReligion = (LinearLayout) findViewById(R.id.linearLayoutProfileViewReligion);
        this.linearLayoutProfileViewDrinking = (LinearLayout) findViewById(R.id.linearLayoutProfileViewDrinking);
        this.linearLayoutProfileViewSmoking = (LinearLayout) findViewById(R.id.linearLayoutProfileViewSmoking);
        this.linearLayoutProfileViewLiving = (LinearLayout) findViewById(R.id.linearLayoutProfileViewLiving);
        this.linearLayoutProfileViewRelocation = (LinearLayout) findViewById(R.id.linearLayoutProfileViewRelocation);
        this.linearLayoutProfileViewInterests = (LinearLayout) findViewById(R.id.linearLayoutProfileViewInterests);
        this.linearLayoutProfileViewActivities = (LinearLayout) findViewById(R.id.linearLayoutProfileViewActivities);
        this.linearLayoutProfileViewMovies = (LinearLayout) findViewById(R.id.linearLayoutProfileViewMovies);
        this.linearLayoutProfileViewMusics = (LinearLayout) findViewById(R.id.linearLayoutProfileViewMusics);
        this.linearLayoutProfileViewTvshows = (LinearLayout) findViewById(R.id.linearLayoutProfileViewTvshows);
        this.linearLayoutProfileViewSports = (LinearLayout) findViewById(R.id.linearLayoutProfileViewSports);
        this.linearLayoutProfileViewBooks = (LinearLayout) findViewById(R.id.linearLayoutProfileViewBooks);
        this.textViewProfileViewUsername = (TextView) findViewById(R.id.textViewProfileViewUsername);
        this.textViewProfileViewBirthage = (TextView) findViewById(R.id.textViewProfileViewBirthage);
        this.textViewProfileViewLocation = (TextView) findViewById(R.id.textViewProfileViewLocation);
        this.textViewProfileViewDistance = (TextView) findViewById(R.id.textViewProfileViewDistance);
        this.textViewProfileViewAbout = (TextView) findViewById(R.id.textViewProfileViewAbout);
        this.textViewProfileViewLooking = (TextView) findViewById(R.id.textViewProfileViewLooking);
        this.textViewProfileViewSeeking = (TextView) findViewById(R.id.textViewProfileViewSeeking);
        this.textViewProfileViewMarital = (TextView) findViewById(R.id.textViewProfileViewMarital);
        this.textViewProfileViewSexual = (TextView) findViewById(R.id.textViewProfileViewSexual);
        this.textViewProfileViewHeight = (TextView) findViewById(R.id.textViewProfileViewHeight);
        this.textViewProfileViewWeight = (TextView) findViewById(R.id.textViewProfileViewWeight);
        this.textViewProfileViewAppearance = (TextView) findViewById(R.id.textViewProfileViewAppearance);
        this.textViewProfileViewFeature = (TextView) findViewById(R.id.textViewProfileViewFeature);
        this.textViewProfileViewEthnicity = (TextView) findViewById(R.id.textViewProfileViewEthnicity);
        this.textViewProfileViewBodytype = (TextView) findViewById(R.id.textViewProfileViewBodytype);
        this.textViewProfileViewBodyart = (TextView) findViewById(R.id.textViewProfileViewBodyart);
        this.textViewProfileViewEyecolor = (TextView) findViewById(R.id.textViewProfileViewEyecolor);
        this.textViewProfileViewEyewear = (TextView) findViewById(R.id.textViewProfileViewEyewear);
        this.textViewProfileViewHaircolor = (TextView) findViewById(R.id.textViewProfileViewHaircolor);
        this.textViewProfileViewStarsign = (TextView) findViewById(R.id.textViewProfileViewStarsign);
        this.textViewProfileViewJobtitle = (TextView) findViewById(R.id.textViewProfileViewJobtitle);
        this.textViewProfileViewCompany = (TextView) findViewById(R.id.textViewProfileViewCompany);
        this.textViewProfileViewIncome = (TextView) findViewById(R.id.textViewProfileViewIncome);
        this.textViewProfileViewEducation = (TextView) findViewById(R.id.textViewProfileViewEducation);
        this.textViewProfileViewLanguage = (TextView) findViewById(R.id.textViewProfileViewLanguage);
        this.textViewProfileViewReligion = (TextView) findViewById(R.id.textViewProfileViewReligion);
        this.textViewProfileViewDrinking = (TextView) findViewById(R.id.textViewProfileViewDrinking);
        this.textViewProfileViewSmoking = (TextView) findViewById(R.id.textViewProfileViewSmoking);
        this.textViewProfileViewLiving = (TextView) findViewById(R.id.textViewProfileViewLiving);
        this.textViewProfileViewRelocation = (TextView) findViewById(R.id.textViewProfileViewRelocation);
        this.textViewProfileViewInterests = (TextView) findViewById(R.id.textViewProfileViewInterests);
        this.textViewProfileViewActivities = (TextView) findViewById(R.id.textViewProfileViewActivities);
        this.textViewProfileViewMovies = (TextView) findViewById(R.id.textViewProfileViewMovies);
        this.textViewProfileViewMusics = (TextView) findViewById(R.id.textViewProfileViewMusics);
        this.textViewProfileViewTvshows = (TextView) findViewById(R.id.textViewProfileViewTvshows);
        this.textViewProfileViewSports = (TextView) findViewById(R.id.textViewProfileViewSports);
        this.textViewProfileViewBooks = (TextView) findViewById(R.id.textViewProfileViewBooks);
        this.floatingActionButtonChat =  findViewById(R.id.floatingActionButtonChat);
        this.floatingActionButtonLikeBack =  findViewById(R.id.floatingActionButtonLikeBack);
        this.imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
        this.stringArrayCovers = new ArrayList<>();
        this.string_array_user_report = getResources().getStringArray(R.array.string_array_user_report);
        this.toolbarProfile = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(this.toolbarProfile);
        getSupportActionBar().setTitle((CharSequence) "Profile ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });
        this.carouselView = (CarouselView) findViewById(R.id.carouselView);
        this.carouselView.setImageListener(this.imageListener);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");

        int like_back = getIntent().getIntExtra("like_back",0);
        if(like_back == 1)
        {
            this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e)
                {
                    if(documentSnapshot.exists())
                    {
                        floatingActionButtonLikeBack.setVisibility(View.GONE);
                    }
                    else
                    {
                        floatingActionButtonLikeBack.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else
        {
            floatingActionButtonLikeBack.setVisibility(View.GONE);
        }
        floatingActionButtonLikeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeFragment.swipeFragment.SwipeUserMatch(profileUser, ProfileActivity.this);
                floatingActionButtonLikeBack.setVisibility(View.GONE);
            }
        });


        Slider.init((new ImageClass(this)));
        this.floatingActionButtonChat.bringToFront();
        this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                intent.putExtra("user_uid", ProfileActivity.this.profileUser);
//                intent.addFlags(67108864);
                ProfileActivity.this.startActivity(intent);
            }
        });
        this.firebaseFirestore.collection("users").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                ProfileActivity.this.user_cover_zero = documentSnapshot.getString("user_cover0");
                ProfileActivity.this.user_cover_one = documentSnapshot.getString("user_cover1");
                ProfileActivity.this.user_cover_two = documentSnapshot.getString("user_cover2");
                ProfileActivity.this.user_cover_three = documentSnapshot.getString("user_cover3");
                ProfileActivity.this.user_cover_four = documentSnapshot.getString("user_cover4");
                ProfileActivity.this.user_cover_five = documentSnapshot.getString("user_cover5");
                ProfileActivity.this.user_cover_six = documentSnapshot.getString("user_cover6");
                ProfileActivity.this.user_cover_seven = documentSnapshot.getString("user_cover7");
                ProfileActivity.this.user_cover_eight = documentSnapshot.getString("user_cover8");
                if (ProfileActivity.this.user_cover_one != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_two != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_three != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_four != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_five != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_six != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_seven != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                if (ProfileActivity.this.user_cover_eight != null) {
                    ProfileActivity.this.caraoselTotalNumber++;
                }
                ProfileActivity.this.carouselView.setPageCount(ProfileActivity.this.caraoselTotalNumber);
            }
        });
    }

    private void BlockUser() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.firebaseFirestore.collection("users").document(uid).collection("block").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "block";
                String str2 = "users";
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    ProfileActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(ProfileActivity.this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "You have unblocked this user!", Toast.LENGTH_SHORT).show();
                                ProfileActivity.this.BlockCheck();
                            }
                        }
                    });
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put("user_block", ProfileActivity.this.profileUser);
                ProfileActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(ProfileActivity.this.profileUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "You have blocked this user!", Toast.LENGTH_SHORT).show();
                            ProfileActivity.this.BlockCheck();
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void BlockCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("block").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    ProfileActivity.this.menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Unblock User");
                } else {
                    ProfileActivity.this.menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Block User");
                }
            }
        });
    }

    private void UnmatchUser() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        String str = "users";
        this.firebaseFirestore.collection(str).document(this.currentUser).collection("loves").document(this.profileUser).delete();
        this.firebaseFirestore.collection(str).document(this.currentUser).collection("nopes").document(this.profileUser).delete();
        String str2 = "matches";
        this.firebaseFirestore.collection(str).document(this.currentUser).collection(str2).document(this.profileUser).delete();
        this.firebaseFirestore.collection(str).document(this.profileUser).collection(str2).document(this.currentUser).delete();
        this.firebaseFirestore.collection(str).document(this.profileUser).collection("likes").document(this.currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "User Unmatched!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FavoriteUser() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final HashMap hashMap = new HashMap();
        hashMap.put("user_favorite", this.profileUser);
        hashMap.put("user_favorited", Timestamp.now());
        this.firebaseFirestore.collection("users").document(uid).collection("favors").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "favors";
                String str2 = "users";
                if (!((DocumentSnapshot) task.getResult()).exists()) {
                    ProfileActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(ProfileActivity.this.profileUser).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "User added in favorite", Toast.LENGTH_SHORT).show();
                                ProfileActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);
                            }
                        }
                    });
                } else {
                    ProfileActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(ProfileActivity.this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "User removed from favorite", Toast.LENGTH_SHORT).show();
                                ProfileActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);
                            }
                        }
                    });
                }
            }
        });
    }

    private void FavoriteCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("favors").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    ProfileActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);
                }
            }
        });
    }

    private void UnmatchCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    ProfileActivity.this.menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(true);
                } else {
                    ProfileActivity.this.menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);
                }
            }
        });
    }

    private void EventSend() {
        EventBus.getDefault().post(new EventClass(HttpHeaders.REFRESH));
    }

    /* access modifiers changed from: private */
    public void StartSlider(final int i, final ImageView imageView) {
        this.firebaseFirestore.collection("users").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    String str = "user_cover0";
                    String string = documentSnapshot.getString(str);
                    String string2 = documentSnapshot.getString("user_cover1");
                    String string3 = documentSnapshot.getString("user_cover2");
                    String string4 = documentSnapshot.getString("user_cover3");
                    String string5 = documentSnapshot.getString("user_cover4");
                    String string6 = documentSnapshot.getString("user_cover5");
                    String string7 = documentSnapshot.getString("user_cover6");
                    String string8 = documentSnapshot.getString("user_cover7");
                    String string9 = documentSnapshot.getString("user_cover8");
                    if (string != null) {
                        ProfileActivity.this.stringArrayCovers.add(string);
                    }
                    if (string2 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string2);
                    }
                    if (string3 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string3);
                    }
                    if (string4 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string4);
                    }
                    if (string5 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string5);
                    }
                    if (string6 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string6);
                    }
                    if (string7 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string7);
                    }
                    if (string8 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string8);
                    }
                    if (string9 != null) {
                        ProfileActivity.this.stringArrayCovers.add(string9);
                    }
                    if (i == 0) {
                        ProfileActivity.this.user_cover_zero = documentSnapshot.getString(str);
                        if (!ProfileActivity.this.user_cover_zero.equals("cover")) {
                            Picasso.get().load(ProfileActivity.this.user_cover_zero).into(imageView);
                        } else {
                            Picasso.get().load((int) R.drawable.profile_image).into(imageView);
                        }
                    } else {
                        ProfileActivity profileActivity = ProfileActivity.this;
                        profileActivity.user_cover_zero = (String) profileActivity.stringArrayCovers.get(i);
                        Picasso.get().load(ProfileActivity.this.user_cover_zero).into(imageView);
                    }
                }
            }
        });
    }

    private void VisitsUser() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        this.firebaseFirestore.collection("users").document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String string = ((DocumentSnapshot) task.getResult()).getString("share_visits");
                    if (string == null || !string.equals("no")) {
                        ProfileActivity.this.firebaseFirestore.collection("users").document(ProfileActivity.this.profileUser).collection("visits").document(ProfileActivity.this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String str = "visits";
                                String str2 = "users";
                                String str3 = "user_visited";
                                String str4 = "user_visitor";
                                if (((DocumentSnapshot) task.getResult()).exists()) {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put(str4, ProfileActivity.this.currentUser);
                                    hashMap.put(str3, Timestamp.now());
                                    ProfileActivity.this.firebaseFirestore.collection(str2).document(ProfileActivity.this.profileUser).collection(str).document(ProfileActivity.this.currentUser).update(hashMap);
                                    return;
                                }
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put(str4, ProfileActivity.this.currentUser);
                                hashMap2.put(str3, Timestamp.now());
                                ProfileActivity.this.firebaseFirestore.collection(str2).document(ProfileActivity.this.profileUser).collection(str).document(ProfileActivity.this.currentUser).set((Map<String, Object>) hashMap2);
                            }
                        });
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.profileUser = getIntent().getStringExtra("user_uid");
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        if (this.currentUser.equals(this.profileUser)) {
            this.floatingActionButtonChat.setVisibility(View.GONE);
        } else {
            this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (((DocumentSnapshot) task.getResult()).exists()) {
                        ProfileActivity.this.BlockCheck();
                    }
                }
            });
            VisitsUser();
        }
        UserCurrent();
        UserProfile();
    }

    private void UserCurrent() {
        String str = "users";
        this.firebaseFirestore.collection(str).document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    ProfileActivity.this.latitudeCurrent = Double.valueOf(documentSnapshot.getString("user_latitude"));
                    ProfileActivity.this.longitudeCurrent = Double.valueOf(documentSnapshot.getString("user_longitude"));
                    ProfileActivity.this.genderCurrent = documentSnapshot.getString("user_gender");
                    ProfileActivity.this.imageCurrent = documentSnapshot.getString("user_image");
                    ProfileActivity.this.verifiedCurrent = documentSnapshot.getString("user_verified");
                    ProfileActivity.this.premiumCurrent = documentSnapshot.getString("user_premium");
                    ProfileActivity.this.countryCurrent = documentSnapshot.getString("user_country");
                    String string = documentSnapshot.getString("show_match");
                    if (string != null && string.equals("yes")) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                ProfileActivity.this.firebaseFirestore.collection("users").document(ProfileActivity.this.currentUser).collection("matches").document(ProfileActivity.this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                                        if (documentSnapshot == null || !documentSnapshot.exists()) {
                                            Toast.makeText(ProfileActivity.this, "Match mode on! Swipe cards to connect with this user!", 0).show();
                                            return;
                                        }
                                        Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                                        intent.putExtra("user_uid", ProfileActivity.this.profileUser);
//                                        intent.addFlags(67108864);
                                        ProfileActivity.this.startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
        this.firebaseFirestore.collection(str).document(this.profileUser).collection("chats").document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot == null || !documentSnapshot.exists()) {
                    ProfileActivity.this.UserChats("no");
                } else {
                    ProfileActivity.this.UserChats("yes");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void UserChats(final String str) {
        this.firebaseFirestore.collection("users").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    String string = documentSnapshot.getString("block_stranger");
                    if (string != null && string.equals("yes") && str.equals("no")) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", 0).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void UserProfile() {
        this.firebaseFirestore.collection("users").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                DocumentSnapshot documentSnapshot2 = documentSnapshot;
                if (documentSnapshot2 != null) {
                    String string = documentSnapshot2.getString("user_name");
                    String string2 = documentSnapshot2.getString("user_gender");
                    documentSnapshot2.getString("user_birthday");
                    String string3 = documentSnapshot2.getString("user_birthage");
                    documentSnapshot2.getString("user_image");
                    String string4 = documentSnapshot2.getString("user_city");
                    String string5 = documentSnapshot2.getString("user_state");
                    String string6 = documentSnapshot2.getString("user_country");
                    String string7 = documentSnapshot2.getString("user_about");
                    String string8 = documentSnapshot2.getString("user_looking");
                    String string9 = documentSnapshot2.getString("user_seeking");
                    String string10 = documentSnapshot2.getString("user_marital");
                    String string11 = documentSnapshot2.getString("user_sexual");
                    String string12 = documentSnapshot2.getString("user_height");
                    String string13 = documentSnapshot2.getString("user_weight");
                    String string14 = documentSnapshot2.getString("user_appearance");
                    String string15 = documentSnapshot2.getString("user_feature");
                    String string16 = documentSnapshot2.getString("user_ethnicity");
                    String string17 = documentSnapshot2.getString("user_bodytype");
                    String string18 = documentSnapshot2.getString("user_bodyart");
                    String string19 = documentSnapshot2.getString("user_eyecolor");
                    String string20 = documentSnapshot2.getString("user_eyewear");
                    String string21 = documentSnapshot2.getString("user_haircolor");
                    String string22 = documentSnapshot2.getString("user_starsign");
                    String string23 = documentSnapshot2.getString("user_jobtitle");
                    String string24 = documentSnapshot2.getString("user_company");
                    String string25 = documentSnapshot2.getString("user_income");
                    String string26 = documentSnapshot2.getString("user_education");
                    String string27 = documentSnapshot2.getString("user_language");
                    String string28 = documentSnapshot2.getString("user_religion");
                    String string29 = documentSnapshot2.getString("user_drinking");
                    String string30 = documentSnapshot2.getString("user_smoking");
                    String string31 = documentSnapshot2.getString("user_living");
                    String string32 = documentSnapshot2.getString("user_relocation");
                    String string33 = documentSnapshot2.getString("user_interests");
                    String string34 = documentSnapshot2.getString("user_activities");
                    String string35 = documentSnapshot2.getString("user_movies");
                    String string36 = documentSnapshot2.getString("user_musics");
                    String string37 = documentSnapshot2.getString("user_tvshows");
                    String string38 = documentSnapshot2.getString("user_sports");
                    String string39 = documentSnapshot2.getString("user_books");
                    String string40 = documentSnapshot2.getString("show_match");
                    String str = string13;
                    String string41 = documentSnapshot2.getString("share_location");
                    String string42 = documentSnapshot2.getString("share_birthage");
                    String str2 = string12;
                    String string43 = documentSnapshot2.getString("block_genders");
                    String str3 = string11;
                    String string44 = documentSnapshot2.getString("block_photos");
                    String str4 = string10;
                    String string45 = documentSnapshot2.getString("allow_verified");
                    String str5 = string9;
                    String string46 = documentSnapshot2.getString("allow_premium");
                    String str6 = string8;
                    String string47 = documentSnapshot2.getString("allow_country");
                    String str7 = string7;
                    String str8 = "yes";
                    if (string40 != null && string40.equals(str8)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                ProfileActivity.this.firebaseFirestore.collection("users").document(ProfileActivity.this.currentUser).collection("matches").document(ProfileActivity.this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                                        if (documentSnapshot == null || !documentSnapshot.exists()) {
                                            Toast.makeText(ProfileActivity.this, "Match mode on! Swipe cards to connect with this user!", 0).show();
                                            return;
                                        }
                                        Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                                        intent.putExtra("user_uid", ProfileActivity.this.profileUser);
//                                        intent.addFlags(67108864);
                                        ProfileActivity.this.startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                    if (string43 != null && string43.equals(str8) && string2.equals(ProfileActivity.this.genderCurrent)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (string44 != null && string44.equals(str8) && ProfileActivity.this.imageCurrent.equals(RegisterActivity.MEDIA_IMAGE)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    String str9 = "no";
                    if (string45 != null && string45.equals(str8) && ProfileActivity.this.verifiedCurrent.equals(str9)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (string46 != null && string46.equals(str8) && ProfileActivity.this.premiumCurrent.equals(str9)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", 0).show();
                            }
                        });
                    }
                    if (string47 != null && string47.equals(str8) && !ProfileActivity.this.countryCurrent.equals(string6)) {
                        ProfileActivity.this.floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(ProfileActivity.this, "User has privacy restrictions enabled so you cant contact this user now!", 0).show();
                            }
                        });
                    }
                    String[] split = string.split(" ");
                    String str10 = ", ";
                    if (string42 == null || !string42.equals(str9)) {
                        TextView textView = ProfileActivity.this.textViewProfileViewUsername;
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[0]);
                        sb.append(str10);
                        textView.setText(sb.toString());
                    } else {
                        ProfileActivity.this.textViewProfileViewBirthage.setVisibility(View.GONE);
                        ProfileActivity.this.textViewProfileViewUsername.setText(split[0]);
                    }
                    ProfileActivity.this.textViewProfileViewBirthage.setText(string3);
                    ProfileActivity.this.textViewProfileViewDistance.setText("20 km away");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(string4);
                    sb2.append(str10);
                    sb2.append(string5);
                    sb2.append(str10);
                    sb2.append(string6);
                    ProfileActivity.this.textViewProfileViewLocation.setText(sb2.toString());
                    DocumentSnapshot documentSnapshot3 = documentSnapshot;
                    ProfileActivity.this.latitudeChatUser = Double.valueOf(documentSnapshot3.getString("user_latitude"));
                    ProfileActivity.this.longitudeChatUser = Double.valueOf(documentSnapshot3.getString("user_longitude"));
                    float[] fArr = new float[10];
                    Location.distanceBetween(ProfileActivity.this.latitudeCurrent.doubleValue(), ProfileActivity.this.longitudeCurrent.doubleValue(), ProfileActivity.this.latitudeChatUser.doubleValue(), ProfileActivity.this.longitudeChatUser.doubleValue(), fArr);
                    int round = Math.round(fArr[0]);
                    if (round >= 1000) {
                        ProfileActivity profileActivity = ProfileActivity.this;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(String.valueOf(round / 1000));
                        sb3.append(" km away");
                        profileActivity.stringDistance = sb3.toString();
                    } else {
                        ProfileActivity.this.stringDistance = "Less than 1 km";
                    }
                    ProfileActivity.this.textViewProfileViewDistance.setText(ProfileActivity.this.stringDistance);
                    if (str7 != null) {
                        ProfileActivity.this.textViewProfileViewAbout.setText(str7);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewAbout.setVisibility(View.GONE);
                    }
                    if (str6 != null) {
                        ProfileActivity.this.textViewProfileViewLooking.setText(str6);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewLooking.setVisibility(View.GONE);
                    }
                    if (str5 != null) {
                        ProfileActivity.this.textViewProfileViewSeeking.setText(str5);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewSeeking.setVisibility(View.GONE);
                    }
                    if (str4 != null) {
                        ProfileActivity.this.textViewProfileViewMarital.setText(str4);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewMarital.setVisibility(View.GONE);
                    }
                    if (str3 != null) {
                        ProfileActivity.this.textViewProfileViewSexual.setText(str3);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewSexual.setVisibility(View.GONE);
                    }
                    if (str2 != null) {
                        ProfileActivity.this.textViewProfileViewHeight.setText(str2);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewHeight.setVisibility(View.GONE);
                    }
                    if (str != null) {
                        ProfileActivity.this.textViewProfileViewWeight.setText(str);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewWeight.setVisibility(View.GONE);
                    }
                    if (string14 != null) {
                        ProfileActivity.this.textViewProfileViewAppearance.setText(string14);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewAppearance.setVisibility(View.GONE);
                    }
                    if (string15 != null) {
                        ProfileActivity.this.textViewProfileViewFeature.setText(string15);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewFeature.setVisibility(View.GONE);
                    }
                    if (string16 != null) {
                        ProfileActivity.this.textViewProfileViewEthnicity.setText(string16);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewEthnicity.setVisibility(View.GONE);
                    }
                    if (string17 != null) {
                        ProfileActivity.this.textViewProfileViewBodytype.setText(string17);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewBodytype.setVisibility(View.GONE);
                    }
                    if (string18 != null) {
                        ProfileActivity.this.textViewProfileViewBodyart.setText(string18);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewBodyart.setVisibility(View.GONE);
                    }
                    if (string19 != null) {
                        ProfileActivity.this.textViewProfileViewEyecolor.setText(string19);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewEyecolor.setVisibility(View.GONE);
                    }
                    if (string20 != null) {
                        ProfileActivity.this.textViewProfileViewEyewear.setText(string20);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewEyewear.setVisibility(View.GONE);
                    }
                    if (string21 != null) {
                        ProfileActivity.this.textViewProfileViewHaircolor.setText(string21);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewHaircolor.setVisibility(View.GONE);
                    }
                    if (string22 != null) {
                        ProfileActivity.this.textViewProfileViewStarsign.setText(string22);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewStarsign.setVisibility(View.GONE);
                    }
                    if (string23 != null) {
                        ProfileActivity.this.textViewProfileViewJobtitle.setText(string23);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewJobtitle.setVisibility(View.GONE);
                    }
                    if (string24 != null) {
                        ProfileActivity.this.textViewProfileViewCompany.setText(string24);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewCompany.setVisibility(View.GONE);
                    }
                    if (string25 != null) {
                        ProfileActivity.this.textViewProfileViewIncome.setText(string25);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewIncome.setVisibility(View.GONE);
                    }
                    if (string26 != null) {
                        ProfileActivity.this.textViewProfileViewEducation.setText(string26);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewEducation.setVisibility(View.GONE);
                    }
                    if (string27 != null) {
                        ProfileActivity.this.textViewProfileViewLanguage.setText(string27);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewLanguage.setVisibility(View.GONE);
                    }
                    if (string28 != null) {
                        ProfileActivity.this.textViewProfileViewReligion.setText(string28);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewReligion.setVisibility(View.GONE);
                    }
                    if (string29 != null) {
                        ProfileActivity.this.textViewProfileViewDrinking.setText(string29);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewDrinking.setVisibility(View.GONE);
                    }
                    if (string30 != null) {
                        ProfileActivity.this.textViewProfileViewSmoking.setText(string30);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewSmoking.setVisibility(View.GONE);
                    }
                    if (string31 != null) {
                        ProfileActivity.this.textViewProfileViewLiving.setText(string31);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewLiving.setVisibility(View.GONE);
                    }
                    if (string32 != null) {
                        ProfileActivity.this.textViewProfileViewRelocation.setText(string32);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewRelocation.setVisibility(View.GONE);
                    }
                    if (string33 != null) {
                        ProfileActivity.this.textViewProfileViewInterests.setText(string33);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewInterests.setVisibility(View.GONE);
                    }
                    if (string34 != null) {
                        ProfileActivity.this.textViewProfileViewActivities.setText(string34);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewActivities.setVisibility(View.GONE);
                    }
                    if (string35 != null) {
                        ProfileActivity.this.textViewProfileViewMovies.setText(string35);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewMovies.setVisibility(View.GONE);
                    }
                    if (string36 != null) {
                        ProfileActivity.this.textViewProfileViewMusics.setText(string36);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewMusics.setVisibility(View.GONE);
                    }
                    if (string37 != null) {
                        ProfileActivity.this.textViewProfileViewTvshows.setText(string37);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewTvshows.setVisibility(View.GONE);
                    }
                    if (string38 != null) {
                        ProfileActivity.this.textViewProfileViewSports.setText(string38);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewSports.setVisibility(View.GONE);
                    }
                    if (string39 != null) {
                        ProfileActivity.this.textViewProfileViewBooks.setText(string39);
                    } else {
                        ProfileActivity.this.linearLayoutProfileViewBooks.setVisibility(View.GONE);
                    }
                    if (string41 != null && string41.equals(str9)) {
                        ProfileActivity.this.textViewProfileViewLocation.setVisibility(View.GONE);
                        ProfileActivity.this.textViewProfileViewDistance.setVisibility(View.GONE);
                    }
                    if (string42 != null && string42.equals(str9)) {
                        ProfileActivity.this.textViewProfileViewBirthage.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void UserStatus(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_status", str);
        FirebaseFirestore.getInstance().collection("users").document(this.profileUser).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        UserStatus("online");
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.firebaseUser != null) {
            UserStatus("offline");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        this.menuMessage = menu;
        if (this.currentUser.equals(this.profileUser)) {
            this.menuMessage.findItem(R.id.menuUserBlockUser).setVisible(false);
            this.menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);
            this.menuMessage.findItem(R.id.menuUserReportUser).setVisible(false);
            this.menuMessage.findItem(R.id.menuUserFavoriteUser).setVisible(false);
        } else {
            BlockCheck();
            FavoriteCheck();
            UnmatchCheck();
        }
        this.menuMessage.findItem(R.id.menuUserDeleteChat).setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuUserBlockUser /*2131296656*/:
                BlockUser();
                return true;
            case R.id.menuUserFavoriteUser /*2131296658*/:
                FavoriteUser();
                return true;
            case R.id.menuUserReportUser /*2131296659*/:
                ReportUser();
                return true;
            case R.id.menuUserUnmatchUser /*2131296660*/:
                UnmatchUser();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();
        return super.onPrepareOptionsMenu(menu);
    }

    private void ReportUser() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("user_uid", this.profileUser);
        startActivity(intent);
    }

    private void AgeUpdate(String str, String str2) {
        int intValue = Integer.valueOf(AgeUser(str)).intValue();
        if (intValue > Integer.valueOf(str2).intValue()) {
            HashMap hashMap = new HashMap();
            hashMap.put("user_birthage", String.valueOf(intValue));
            this.firebaseFirestore.collection("users").document(this.profileUser).update(hashMap);
        }
    }

    private String AgeUser(String str) {
        String[] split = str.split("-");
        int intValue = Integer.valueOf(split[0]).intValue();
        int intValue2 = Integer.valueOf(split[1]).intValue();
        int intValue3 = Integer.valueOf(split[2]).intValue();
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance.set(intValue3, intValue2, intValue);
        int i = instance2.get(Calendar.YEAR) - instance.get(Calendar.YEAR);
        if (instance2.get(Calendar.DAY_OF_YEAR) < instance.get(Calendar.DAY_OF_YEAR)) {
            i--;
        }
        return new Integer(i).toString();
    }
}
