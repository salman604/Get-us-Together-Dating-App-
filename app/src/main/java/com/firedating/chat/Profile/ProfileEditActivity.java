package com.firedating.chat.Profile;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.exifinterface.media.ExifInterface;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.firedating.chat.Facebook.AppEventsConstants;
import com.firedating.chat.R;
import com.firedating.chat.Start.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    public static final int GALLERY_IMAGE_COVER3 = 3;
    public static final int GALLERY_IMAGE_IMAGE = 2;
    String ImageUploadNumber;
    Bitmap bitmapCover;
    Bitmap bitmapImage;
    Bitmap bitmapThumb;
    CarouselView carouselView;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = this.firebaseUser.getUid();
    ProgressDialog dialog;
    ArrayList<Uri> firebaseCovers = new ArrayList<>();
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    RoundedImageView imageViewProfileEditCoverEight;
    CircleImageView imageViewProfileEditCoverEightAdd;
    CircleImageView imageViewProfileEditCoverEightRemove;
    RoundedImageView imageViewProfileEditCoverFive;
    CircleImageView imageViewProfileEditCoverFiveAdd;
    CircleImageView imageViewProfileEditCoverFiveRemove;
    RoundedImageView imageViewProfileEditCoverFour;
    CircleImageView imageViewProfileEditCoverFourAdd;
    CircleImageView imageViewProfileEditCoverFourRemove;
    RoundedImageView imageViewProfileEditCoverOne;
    CircleImageView imageViewProfileEditCoverOneAdd;
    CircleImageView imageViewProfileEditCoverOneRemove;
    RoundedImageView imageViewProfileEditCoverSeven;
    CircleImageView imageViewProfileEditCoverSevenAdd;
    CircleImageView imageViewProfileEditCoverSevenRemove;
    RoundedImageView imageViewProfileEditCoverSix;
    CircleImageView imageViewProfileEditCoverSixAdd;
    CircleImageView imageViewProfileEditCoverSixRemove;
    RoundedImageView imageViewProfileEditCoverThree;
    CircleImageView imageViewProfileEditCoverThreeAdd;
    CircleImageView imageViewProfileEditCoverThreeRemove;
    RoundedImageView imageViewProfileEditCoverTwo;
    CircleImageView imageViewProfileEditCoverTwoAdd;
    CircleImageView imageViewProfileEditCoverTwoRemove;
    RoundedImageView imageViewProfileEditCoverZero;
    CircleImageView imageViewProfileEditCoverZeroAdd;
    CircleImageView imageViewProfileEditCoverZeroRemove;
    ImageView imageViewProfileImage;
    LinearLayout linearLayoutProfileEditActivities;
    LinearLayout linearLayoutProfileEditAppearance;
    LinearLayout linearLayoutProfileEditBodyart;
    LinearLayout linearLayoutProfileEditBodytype;
    LinearLayout linearLayoutProfileEditBooks;
    LinearLayout linearLayoutProfileEditCompany;
    LinearLayout linearLayoutProfileEditDrinking;
    LinearLayout linearLayoutProfileEditEducation;
    LinearLayout linearLayoutProfileEditEthnicity;
    LinearLayout linearLayoutProfileEditEyecolor;
    LinearLayout linearLayoutProfileEditEyewear;
    LinearLayout linearLayoutProfileEditFeature;
    LinearLayout linearLayoutProfileEditHaircolor;
    LinearLayout linearLayoutProfileEditHeight;
    LinearLayout linearLayoutProfileEditIncome;
    LinearLayout linearLayoutProfileEditInterests;
    LinearLayout linearLayoutProfileEditJobtitle;
    LinearLayout linearLayoutProfileEditLanguage;
    LinearLayout linearLayoutProfileEditLiving;
    LinearLayout linearLayoutProfileEditLooking;
    LinearLayout linearLayoutProfileEditMarital;
    LinearLayout linearLayoutProfileEditMovies;
    LinearLayout linearLayoutProfileEditMusics;
    LinearLayout linearLayoutProfileEditReligion;
    LinearLayout linearLayoutProfileEditRelocation;
    LinearLayout linearLayoutProfileEditSeeking;
    LinearLayout linearLayoutProfileEditSexual;
    LinearLayout linearLayoutProfileEditSmoking;
    LinearLayout linearLayoutProfileEditSports;
    LinearLayout linearLayoutProfileEditStarsign;
    LinearLayout linearLayoutProfileEditTvshows;
    LinearLayout linearLayoutProfileEditWeight;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private ArrayList<String> stringArrayCovers = new ArrayList<>();
    String stringCover;
    String stringImage;
    String stringSeekbarMaximum;
    String stringSeekbarMinimum;
    String stringThumb;
    String[] string_array_user_appearance;
    String[] string_array_user_bodyart;
    String[] string_array_user_bodytype;
    String[] string_array_user_drinking;
    String[] string_array_user_education;
    String[] string_array_user_ethnicity;
    String[] string_array_user_eyecolor;
    String[] string_array_user_eyewear;
    String[] string_array_user_feature;
    String[] string_array_user_haircolor;
    String[] string_array_user_income;
    String[] string_array_user_language;
    String[] string_array_user_living;
    String[] string_array_user_looking;
    String[] string_array_user_marital;
    String[] string_array_user_religion;
    String[] string_array_user_relocation;
    String[] string_array_user_seeking;
    String[] string_array_user_sexual;
    String[] string_array_user_smoking;
    String[] string_array_user_starsign;
    TextView textViewProfileEditAbout;
    TextView textViewProfileEditActivities;
    private TextView textViewProfileEditAge;
    TextView textViewProfileEditAppearance;
    TextView textViewProfileEditBodyart;
    TextView textViewProfileEditBodytype;
    TextView textViewProfileEditBooks;
    private TextView textViewProfileEditCity;
    TextView textViewProfileEditCompany;
    private TextView textViewProfileEditCountry;
    TextView textViewProfileEditDrinking;
    TextView textViewProfileEditEducation;
    TextView textViewProfileEditEthnicity;
    TextView textViewProfileEditEyecolor;
    TextView textViewProfileEditEyewear;
    TextView textViewProfileEditFeature;
    private TextView textViewProfileEditGender;
    TextView textViewProfileEditHaircolor;
    TextView textViewProfileEditHeight;
    TextView textViewProfileEditIncome;
    TextView textViewProfileEditInterests;
    TextView textViewProfileEditJobtitle;
    TextView textViewProfileEditLanguage;
    TextView textViewProfileEditLiving;
    TextView textViewProfileEditLooking;
    TextView textViewProfileEditMarital;
    TextView textViewProfileEditMovies;
    TextView textViewProfileEditMusics;
    TextView textViewProfileEditReligion;
    TextView textViewProfileEditRelocation;
    TextView textViewProfileEditSeeking;
    TextView textViewProfileEditSexual;
    TextView textViewProfileEditSmoking;
    TextView textViewProfileEditSports;
    TextView textViewProfileEditStarsign;
    private TextView textViewProfileEditState;
    TextView textViewProfileEditTvshows;
    TextView textViewProfileEditWeight;
    Toolbar toolbarProfileEditToolbar;
    TextView toolbarTextViewUserNames;
    TextView toolbarTextViewUserStatus;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_activity);
        this.stringArrayCovers = new ArrayList<>();
        this.toolbarProfileEditToolbar = (Toolbar) findViewById(R.id.toolbarProfileEditToolbar);
        setSupportActionBar(this.toolbarProfileEditToolbar);
        getSupportActionBar().setTitle((CharSequence) "Edit Profile");
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbarProfileEditToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.finish();
            }
        });
        this.imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
        this.linearLayoutProfileEditLooking = (LinearLayout) findViewById(R.id.linearLayoutProfileEditLooking);
        this.linearLayoutProfileEditSeeking = (LinearLayout) findViewById(R.id.linearLayoutProfileEditSeeking);
        this.linearLayoutProfileEditMarital = (LinearLayout) findViewById(R.id.linearLayoutProfileEditMarital);
        this.linearLayoutProfileEditSexual = (LinearLayout) findViewById(R.id.linearLayoutProfileEditSexual);
        this.linearLayoutProfileEditHeight = (LinearLayout) findViewById(R.id.linearLayoutProfileEditHeight);
        this.linearLayoutProfileEditWeight = (LinearLayout) findViewById(R.id.linearLayoutProfileEditWeight);
        this.linearLayoutProfileEditAppearance = (LinearLayout) findViewById(R.id.linearLayoutProfileEditAppearance);
        this.linearLayoutProfileEditFeature = (LinearLayout) findViewById(R.id.linearLayoutProfileEditFeature);
        this.linearLayoutProfileEditEthnicity = (LinearLayout) findViewById(R.id.linearLayoutProfileEditEthnicity);
        this.linearLayoutProfileEditBodytype = (LinearLayout) findViewById(R.id.linearLayoutProfileEditBodytype);
        this.linearLayoutProfileEditBodyart = (LinearLayout) findViewById(R.id.linearLayoutProfileEditBodyart);
        this.linearLayoutProfileEditEyecolor = (LinearLayout) findViewById(R.id.linearLayoutProfileEditEyecolor);
        this.linearLayoutProfileEditEyewear = (LinearLayout) findViewById(R.id.linearLayoutProfileEditEyewear);
        this.linearLayoutProfileEditHaircolor = (LinearLayout) findViewById(R.id.linearLayoutProfileEditHaircolor);
        this.linearLayoutProfileEditStarsign = (LinearLayout) findViewById(R.id.linearLayoutProfileEditStarsign);
        this.linearLayoutProfileEditJobtitle = (LinearLayout) findViewById(R.id.linearLayoutProfileEditJobtitle);
        this.linearLayoutProfileEditCompany = (LinearLayout) findViewById(R.id.linearLayoutProfileEditCompany);
        this.linearLayoutProfileEditIncome = (LinearLayout) findViewById(R.id.linearLayoutProfileEditIncome);
        this.linearLayoutProfileEditEducation = (LinearLayout) findViewById(R.id.linearLayoutProfileEditEducation);
        this.linearLayoutProfileEditLanguage = (LinearLayout) findViewById(R.id.linearLayoutProfileEditLanguage);
        this.linearLayoutProfileEditReligion = (LinearLayout) findViewById(R.id.linearLayoutProfileEditReligion);
        this.linearLayoutProfileEditDrinking = (LinearLayout) findViewById(R.id.linearLayoutProfileEditDrinking);
        this.linearLayoutProfileEditSmoking = (LinearLayout) findViewById(R.id.linearLayoutProfileEditSmoking);
        this.linearLayoutProfileEditLiving = (LinearLayout) findViewById(R.id.linearLayoutProfileEditLiving);
        this.linearLayoutProfileEditRelocation = (LinearLayout) findViewById(R.id.linearLayoutProfileEditRelocation);
        this.linearLayoutProfileEditInterests = (LinearLayout) findViewById(R.id.linearLayoutProfileEditInterests);
        this.linearLayoutProfileEditActivities = (LinearLayout) findViewById(R.id.linearLayoutProfileEditActivities);
        this.linearLayoutProfileEditMovies = (LinearLayout) findViewById(R.id.linearLayoutProfileEditMovies);
        this.linearLayoutProfileEditMusics = (LinearLayout) findViewById(R.id.linearLayoutProfileEditMusics);
        this.linearLayoutProfileEditTvshows = (LinearLayout) findViewById(R.id.linearLayoutProfileEditTvshows);
        this.linearLayoutProfileEditSports = (LinearLayout) findViewById(R.id.linearLayoutProfileEditSports);
        this.linearLayoutProfileEditBooks = (LinearLayout) findViewById(R.id.linearLayoutProfileEditBooks);
        this.textViewProfileEditAbout = (TextView) findViewById(R.id.textViewProfileEditAbout);
        this.textViewProfileEditLooking = (TextView) findViewById(R.id.textViewProfileEditLooking);
        this.textViewProfileEditSeeking = (TextView) findViewById(R.id.textViewProfileEditSeeking);
        this.textViewProfileEditMarital = (TextView) findViewById(R.id.textViewProfileEditMarital);
        this.textViewProfileEditSexual = (TextView) findViewById(R.id.textViewProfileEditSexual);
        this.textViewProfileEditHeight = (TextView) findViewById(R.id.textViewProfileEditHeight);
        this.textViewProfileEditWeight = (TextView) findViewById(R.id.textViewProfileEditWeight);
        this.textViewProfileEditAppearance = (TextView) findViewById(R.id.textViewProfileEditAppearance);
        this.textViewProfileEditFeature = (TextView) findViewById(R.id.textViewProfileEditFeature);
        this.textViewProfileEditEthnicity = (TextView) findViewById(R.id.textViewProfileEditEthnicity);
        this.textViewProfileEditBodytype = (TextView) findViewById(R.id.textViewProfileEditBodytype);
        this.textViewProfileEditBodyart = (TextView) findViewById(R.id.textViewProfileEditBodyart);
        this.textViewProfileEditEyecolor = (TextView) findViewById(R.id.textViewProfileEditEyecolor);
        this.textViewProfileEditEyewear = (TextView) findViewById(R.id.textViewProfileEditEyewear);
        this.textViewProfileEditHaircolor = (TextView) findViewById(R.id.textViewProfileEditHaircolor);
        this.textViewProfileEditStarsign = (TextView) findViewById(R.id.textViewProfileEditStarsign);
        this.textViewProfileEditJobtitle = (TextView) findViewById(R.id.textViewProfileEditJobtitle);
        this.textViewProfileEditCompany = (TextView) findViewById(R.id.textViewProfileEditCompany);
        this.textViewProfileEditIncome = (TextView) findViewById(R.id.textViewProfileEditIncome);
        this.textViewProfileEditEducation = (TextView) findViewById(R.id.textViewProfileEditEducation);
        this.textViewProfileEditLanguage = (TextView) findViewById(R.id.textViewProfileEditLanguage);
        this.textViewProfileEditReligion = (TextView) findViewById(R.id.textViewProfileEditReligion);
        this.textViewProfileEditDrinking = (TextView) findViewById(R.id.textViewProfileEditDrinking);
        this.textViewProfileEditSmoking = (TextView) findViewById(R.id.textViewProfileEditSmoking);
        this.textViewProfileEditLiving = (TextView) findViewById(R.id.textViewProfileEditLiving);
        this.textViewProfileEditRelocation = (TextView) findViewById(R.id.textViewProfileEditRelocation);
        this.textViewProfileEditInterests = (TextView) findViewById(R.id.textViewProfileEditInterests);
        this.textViewProfileEditActivities = (TextView) findViewById(R.id.textViewProfileEditActivities);
        this.textViewProfileEditMovies = (TextView) findViewById(R.id.textViewProfileEditMovies);
        this.textViewProfileEditMusics = (TextView) findViewById(R.id.textViewProfileEditMusics);
        this.textViewProfileEditTvshows = (TextView) findViewById(R.id.textViewProfileEditTvshows);
        this.textViewProfileEditSports = (TextView) findViewById(R.id.textViewProfileEditSports);
        this.textViewProfileEditBooks = (TextView) findViewById(R.id.textViewProfileEditBooks);
        this.string_array_user_looking = getResources().getStringArray(R.array.string_array_user_looking);
        this.string_array_user_seeking = getResources().getStringArray(R.array.string_array_user_seeking);
        this.string_array_user_marital = getResources().getStringArray(R.array.string_array_user_marital);
        this.string_array_user_sexual = getResources().getStringArray(R.array.string_array_user_sexual);
        this.string_array_user_appearance = getResources().getStringArray(R.array.string_array_user_appearance);
        this.string_array_user_feature = getResources().getStringArray(R.array.string_array_user_feature);
        this.string_array_user_ethnicity = getResources().getStringArray(R.array.string_array_user_ethnicity);
        this.string_array_user_bodytype = getResources().getStringArray(R.array.string_array_user_bodytype);
        this.string_array_user_bodyart = getResources().getStringArray(R.array.string_array_user_bodyart);
        this.string_array_user_eyecolor = getResources().getStringArray(R.array.string_array_user_eyecolor);
        this.string_array_user_eyewear = getResources().getStringArray(R.array.string_array_user_eyewear);
        this.string_array_user_haircolor = getResources().getStringArray(R.array.string_array_user_haircolor);
        this.string_array_user_starsign = getResources().getStringArray(R.array.string_array_user_starsign);
        this.string_array_user_income = getResources().getStringArray(R.array.string_array_user_income);
        this.string_array_user_education = getResources().getStringArray(R.array.string_array_user_education);
        this.string_array_user_language = getResources().getStringArray(R.array.string_array_user_language);
        this.string_array_user_religion = getResources().getStringArray(R.array.string_array_user_religion);
        this.string_array_user_drinking = getResources().getStringArray(R.array.string_array_user_drinking);
        this.string_array_user_smoking = getResources().getStringArray(R.array.string_array_user_smoking);
        this.string_array_user_living = getResources().getStringArray(R.array.string_array_user_living);
        this.string_array_user_relocation = getResources().getStringArray(R.array.string_array_user_relocation);
        this.imageViewProfileEditCoverZero = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverZero);
        this.imageViewProfileEditCoverZeroAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverZeroAdd);
        this.imageViewProfileEditCoverZeroRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverZeroRemove);
        this.imageViewProfileEditCoverOne = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverOne);
        this.imageViewProfileEditCoverOneAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverOneAdd);
        this.imageViewProfileEditCoverOneRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverOneRemove);
        this.imageViewProfileEditCoverTwo = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverTwo);
        this.imageViewProfileEditCoverTwoAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverTwoAdd);
        this.imageViewProfileEditCoverTwoRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverTwoRemove);
        this.imageViewProfileEditCoverThree = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverThree);
        this.imageViewProfileEditCoverThreeAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverThreeAdd);
        this.imageViewProfileEditCoverThreeRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverThreeRemove);
        this.imageViewProfileEditCoverFour = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverFour);
        this.imageViewProfileEditCoverFourAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverFourAdd);
        this.imageViewProfileEditCoverFourRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverFourRemove);
        this.imageViewProfileEditCoverFive = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverFive);
        this.imageViewProfileEditCoverFiveAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverFiveAdd);
        this.imageViewProfileEditCoverFiveRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverFiveRemove);
        this.imageViewProfileEditCoverSix = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverSix);
        this.imageViewProfileEditCoverSixAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverSixAdd);
        this.imageViewProfileEditCoverSixRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverSixRemove);
        this.imageViewProfileEditCoverSeven = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverSeven);
        this.imageViewProfileEditCoverSevenAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverSevenAdd);
        this.imageViewProfileEditCoverSevenRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverSevenRemove);
        this.imageViewProfileEditCoverEight = (RoundedImageView) findViewById(R.id.imageViewProfileEditCoverEight);
        this.imageViewProfileEditCoverEightAdd = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverEightAdd);
        this.imageViewProfileEditCoverEightRemove = (CircleImageView) findViewById(R.id.imageViewProfileEditCoverEightRemove);
        this.imageViewProfileEditCoverZero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("0");
            }
        });
        this.imageViewProfileEditCoverZeroAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("0");
            }
        });
        this.imageViewProfileEditCoverZeroRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("0", profileEditActivity.imageViewProfileEditCoverZero, ProfileEditActivity.this.imageViewProfileEditCoverZeroAdd, ProfileEditActivity.this.imageViewProfileEditCoverZeroRemove);
            }
        });
        this.imageViewProfileEditCoverOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
        });
        this.imageViewProfileEditCoverOneAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(AppEventsConstants.EVENT_PARAM_VALUE_YES);
            }
        });
        this.imageViewProfileEditCoverOneRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover(AppEventsConstants.EVENT_PARAM_VALUE_YES, profileEditActivity.imageViewProfileEditCoverOne, ProfileEditActivity.this.imageViewProfileEditCoverOneAdd, ProfileEditActivity.this.imageViewProfileEditCoverOneRemove);
            }
        });
        this.imageViewProfileEditCoverTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(ExifInterface.GPS_MEASUREMENT_2D);
            }
        });
        this.imageViewProfileEditCoverTwoAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(ExifInterface.GPS_MEASUREMENT_2D);
            }
        });
        this.imageViewProfileEditCoverTwoRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover(ExifInterface.GPS_MEASUREMENT_2D, profileEditActivity.imageViewProfileEditCoverTwo, ProfileEditActivity.this.imageViewProfileEditCoverTwoAdd, ProfileEditActivity.this.imageViewProfileEditCoverTwoRemove);
            }
        });
        this.imageViewProfileEditCoverThree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(ExifInterface.GPS_MEASUREMENT_3D);
            }
        });
        this.imageViewProfileEditCoverThreeAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover(ExifInterface.GPS_MEASUREMENT_3D);
            }
        });
        this.imageViewProfileEditCoverThreeRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover(ExifInterface.GPS_MEASUREMENT_3D, profileEditActivity.imageViewProfileEditCoverThree, ProfileEditActivity.this.imageViewProfileEditCoverThreeAdd, ProfileEditActivity.this.imageViewProfileEditCoverThreeRemove);
            }
        });
        this.imageViewProfileEditCoverFour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("4");
            }
        });
        this.imageViewProfileEditCoverFourAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("4");
            }
        });
        this.imageViewProfileEditCoverFourRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("4", profileEditActivity.imageViewProfileEditCoverFour, ProfileEditActivity.this.imageViewProfileEditCoverFourAdd, ProfileEditActivity.this.imageViewProfileEditCoverFourRemove);
            }
        });
        this.imageViewProfileEditCoverFive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("5");
            }
        });
        this.imageViewProfileEditCoverFiveAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("5");
            }
        });
        this.imageViewProfileEditCoverFiveRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("5", profileEditActivity.imageViewProfileEditCoverFive, ProfileEditActivity.this.imageViewProfileEditCoverFiveAdd, ProfileEditActivity.this.imageViewProfileEditCoverFiveRemove);
            }
        });
        this.imageViewProfileEditCoverSix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("6");
            }
        });
        this.imageViewProfileEditCoverSixAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("6");
            }
        });
        this.imageViewProfileEditCoverSixRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("6", profileEditActivity.imageViewProfileEditCoverSix, ProfileEditActivity.this.imageViewProfileEditCoverSixAdd, ProfileEditActivity.this.imageViewProfileEditCoverSixRemove);
            }
        });
        this.imageViewProfileEditCoverSeven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("7");
            }
        });
        this.imageViewProfileEditCoverSevenAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("7");
            }
        });
        this.imageViewProfileEditCoverSevenRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("7", profileEditActivity.imageViewProfileEditCoverSeven, ProfileEditActivity.this.imageViewProfileEditCoverSevenAdd, ProfileEditActivity.this.imageViewProfileEditCoverSevenRemove);
            }
        });
        this.imageViewProfileEditCoverEight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("8");
            }
        });
        this.imageViewProfileEditCoverEightAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity.this.ImageClickUploadCover("8");
            }
        });
        this.imageViewProfileEditCoverEightRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ImageClickRemoveCover("8", profileEditActivity.imageViewProfileEditCoverEight, ProfileEditActivity.this.imageViewProfileEditCoverEightAdd, ProfileEditActivity.this.imageViewProfileEditCoverEightRemove);
            }
        });
        this.textViewProfileEditAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditAbout, "user_about", "About Me", 6);
            }
        });
        this.linearLayoutProfileEditLooking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_looking, ProfileEditActivity.this.textViewProfileEditLooking, "user_looking", "I am looking for");
            }
        });
        this.linearLayoutProfileEditSeeking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_seeking, ProfileEditActivity.this.textViewProfileEditSeeking, "user_seeking", "I am seeking for");
            }
        });
        this.linearLayoutProfileEditMarital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_marital, ProfileEditActivity.this.textViewProfileEditMarital, "user_marital", "Select your Relationship");
            }
        });
        this.linearLayoutProfileEditSexual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_sexual, ProfileEditActivity.this.textViewProfileEditSexual, "user_sexual", "Select your Sexual");
            }
        });
        this.linearLayoutProfileEditHeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogSeekbar(profileEditActivity.textViewProfileEditHeight, "user_height", "Select your Height", 140, 240);
            }
        });
        this.linearLayoutProfileEditWeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogSeekbar(profileEditActivity.textViewProfileEditWeight, "user_weight", "Select your Weight", 40, 160);
            }
        });
        this.linearLayoutProfileEditAppearance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_appearance, ProfileEditActivity.this.textViewProfileEditAppearance, "user_appearance", "Select your Appearance");
            }
        });
        this.linearLayoutProfileEditFeature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_feature, ProfileEditActivity.this.textViewProfileEditFeature, "user_feature", "Select your Feature");
            }
        });
        this.linearLayoutProfileEditEthnicity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_ethnicity, ProfileEditActivity.this.textViewProfileEditEthnicity, "user_ethnicity", "Select your Ethnicity");
            }
        });
        this.linearLayoutProfileEditBodytype.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_bodytype, ProfileEditActivity.this.textViewProfileEditBodytype, "user_bodytype", "Select your Bodytype");
            }
        });
        this.linearLayoutProfileEditBodyart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_bodyart, ProfileEditActivity.this.textViewProfileEditBodyart, "user_bodyart", "Select your Bodyart");
            }
        });
        this.linearLayoutProfileEditEyecolor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_eyecolor, ProfileEditActivity.this.textViewProfileEditEyecolor, "user_eyecolor", "Select your Eyecolor");
            }
        });
        this.linearLayoutProfileEditEyewear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_eyewear, ProfileEditActivity.this.textViewProfileEditEyewear, "user_eyewear", "Select your Eyewear");
            }
        });
        this.linearLayoutProfileEditHaircolor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_haircolor, ProfileEditActivity.this.textViewProfileEditHaircolor, "user_haircolor", "Select your Haircolor");
            }
        });
        this.linearLayoutProfileEditStarsign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_starsign, ProfileEditActivity.this.textViewProfileEditStarsign, "user_starsign", "Select your Starsign");
            }
        });
        this.linearLayoutProfileEditJobtitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditJobtitle, "user_jobtitle", "Select your Jobtitle", 1);
            }
        });
        this.linearLayoutProfileEditCompany.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditCompany, "user_company", "Select your Company", 1);
            }
        });
        this.linearLayoutProfileEditIncome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_income, ProfileEditActivity.this.textViewProfileEditIncome, "user_income", "Select your Income");
            }
        });
        this.linearLayoutProfileEditEducation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_education, ProfileEditActivity.this.textViewProfileEditEducation, "user_education", "Select your Education");
            }
        });
        this.linearLayoutProfileEditLanguage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogCheck(profileEditActivity.string_array_user_language, ProfileEditActivity.this.textViewProfileEditLanguage, "user_language", "Select your Language");
            }
        });
        this.linearLayoutProfileEditReligion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_religion, ProfileEditActivity.this.textViewProfileEditReligion, "user_religion", "Select your Religion");
            }
        });
        this.linearLayoutProfileEditDrinking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_drinking, ProfileEditActivity.this.textViewProfileEditDrinking, "user_drinking", "Select your Drinking");
            }
        });
        this.linearLayoutProfileEditSmoking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_smoking, ProfileEditActivity.this.textViewProfileEditSmoking, "user_smoking", "Select your Smoking");
            }
        });
        this.linearLayoutProfileEditLiving.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_living, ProfileEditActivity.this.textViewProfileEditLiving, "user_living", "Select your Living");
            }
        });
        this.linearLayoutProfileEditRelocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogRadio(profileEditActivity.string_array_user_relocation, ProfileEditActivity.this.textViewProfileEditRelocation, "user_relocation", "Select your Relocation");
            }
        });
        this.textViewProfileEditInterests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditInterests, "user_interests", "I am interests for", 6);
            }
        });
        this.textViewProfileEditActivities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditActivities, "user_activities", "I am activities for", 6);
            }
        });
        this.textViewProfileEditMovies.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditMovies, "user_movies", "I am movies for", 6);
            }
        });
        this.textViewProfileEditMusics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditMusics, "user_musics", "I am musics for", 6);
            }
        });
        this.textViewProfileEditTvshows.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditTvshows, "user_tvshows", "I am tvshows for", 6);
            }
        });
        this.textViewProfileEditSports.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditSports, "user_sports", "I am sports for", 6);
            }
        });
        this.textViewProfileEditBooks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileEditActivity profileEditActivity = ProfileEditActivity.this;
                profileEditActivity.ProfileDialogInput(profileEditActivity.textViewProfileEditBooks, "user_books", "I am books for", 6);
            }
        });
    }

    /* access modifiers changed from: private */
    public void ImageClickActiveCovers(String str, String str2) {
        if (str.equals("0")) {
            this.imageViewProfileEditCoverZero.setClickable(false);
            this.imageViewProfileEditCoverZeroAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverZeroRemove.setVisibility(View.VISIBLE);
            System.out.println("debug cover bitmap set " + str);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverZero);
        } else if (str.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
            this.imageViewProfileEditCoverOne.setClickable(false);
            this.imageViewProfileEditCoverOneAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverOneRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverOne);
        } else if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
            this.imageViewProfileEditCoverTwo.setClickable(false);
            this.imageViewProfileEditCoverTwoAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverTwoRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverTwo);
        } else if (str.equals("3")) {
            this.imageViewProfileEditCoverThree.setClickable(false);
            this.imageViewProfileEditCoverThreeAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverThreeRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverThree);
        } else if (str.equals("4")) {
            this.imageViewProfileEditCoverFour.setClickable(false);
            this.imageViewProfileEditCoverFourAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverFourRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverFour);
        } else if (str.equals("5")) {
            this.imageViewProfileEditCoverFive.setClickable(false);
            this.imageViewProfileEditCoverFiveAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverFiveRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverFive);
        } else if (str.equals("6")) {
            this.imageViewProfileEditCoverSix.setClickable(false);
            this.imageViewProfileEditCoverSixAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverSixRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverSix);
        } else if (str.equals("7")) {
            this.imageViewProfileEditCoverSeven.setClickable(false);
            this.imageViewProfileEditCoverSevenAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverSevenRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverSeven);
        } else if (str.equals("8")) {
            this.imageViewProfileEditCoverEight.setClickable(false);
            this.imageViewProfileEditCoverEightAdd.setVisibility(View.GONE);
            this.imageViewProfileEditCoverEightRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(str2).into((ImageView) this.imageViewProfileEditCoverEight);
        }
    }

    /* access modifiers changed from: private */
    public void ImageClickRemoveCover(String str, RoundedImageView roundedImageView, CircleImageView circleImageView, CircleImageView circleImageView2) {
        String str2 = "users";
        String str3 = "user_cover";
        if (str.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.currentUser);
            sb.append(str3);
            final String sb2 = sb.toString();
            HashMap hashMap = new HashMap();
            hashMap.put(str3, "cover");
            hashMap.put("user_image", RegisterActivity.MEDIA_IMAGE);
            hashMap.put("user_thumb", "thumb");
            Task update = this.firebaseFirestore.collection(str2).document(this.currentUser).update(hashMap);
            final RoundedImageView roundedImageView2 = roundedImageView;
            final CircleImageView circleImageView3 = circleImageView;
            final CircleImageView circleImageView4 = circleImageView2;
            OnCompleteListener r3 = new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ProfileEditActivity.this.firebaseFirestore.collection("feeds").document(sb2).delete();
                        roundedImageView2.setClickable(true);
                        circleImageView3.setVisibility(View.VISIBLE);
                        circleImageView4.setVisibility(View.GONE);
                        Picasso.get().load((int) R.drawable.profile_image).into((ImageView) roundedImageView2);
                    }
                }
            };
            update.addOnCompleteListener(r3);
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.currentUser);
        sb3.append(str3);
        sb3.append(str);
        final String sb4 = sb3.toString();
        HashMap hashMap2 = new HashMap();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(str3);
        sb5.append(str);
        hashMap2.put(sb5.toString(), FieldValue.delete());
        Task update2 = this.firebaseFirestore.collection(str2).document(this.currentUser).update(hashMap2);
        final RoundedImageView roundedImageView3 = roundedImageView;
        final CircleImageView circleImageView5 = circleImageView;
        final CircleImageView circleImageView6 = circleImageView2;
        OnCompleteListener r4 = new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ProfileEditActivity.this.firebaseFirestore.collection("feeds").document(sb4).delete();
                    roundedImageView3.setClickable(true);
                    circleImageView5.setVisibility(View.VISIBLE);
                    circleImageView6.setVisibility(View.GONE);
                    Picasso.get().load((int) R.drawable.profile_image).into((ImageView) roundedImageView3);
                }
            }
        };
        update2.addOnCompleteListener(r4);
    }

    public void ImageClickUploadCover(String str) {
        this.ImageUploadNumber = str;
        System.out.println("debug Image Click" + str);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2 && i2 == RESULT_OK) {
            CropImage.activity(intent.getData()).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);
        }
        if (i == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == RESULT_OK) {
                Bitmap decodeFile = BitmapFactory.decodeFile(new File(activityResult.getUri().getPath()).getPath());
                this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = this.firebaseUser.getUid();
                if (this.ImageUploadNumber.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                    System.out.println("debug Thumb Upload " + AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    ThumbUpload(uid, decodeFile, "cover_", this.ImageUploadNumber);
                }
                    StringBuilder sb = new StringBuilder();
                    sb.append("cover");
                    sb.append(this.ImageUploadNumber);
                    sb.append("_");

                    CoverUpload(uid, decodeFile, sb.toString(), this.ImageUploadNumber);

                this.dialog = new ProgressDialog(this);
                this.dialog.setTitle("Please Wait");
                this.dialog.setMessage("Uploading Photo...");
                this.dialog.setCancelable(false);
                this.dialog.show();
            } else if (i2 == 204) {
                activityResult.getError();
            }
        }
    }

    private void ThumbUpload(String str, Bitmap bitmap, String str2, String str3) {
        this.bitmapThumb = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmapThumb.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        StorageReference child = this.storageReference.child(str).child("images").child("user");
        StringBuilder sb = new StringBuilder();
        sb.append("thumb_");
        sb.append(str);
        sb.append(".jpg");
        final StorageReference child2 = child.child(sb.toString());
        UploadTask putBytes = child2.putBytes(byteArray);
        final String str4 = str;
        final Bitmap bitmap2 = bitmap;
        final String str5 = str2;
        final String str6 = str3;
        OnCompleteListener r3 = new OnCompleteListener<UploadTask.TaskSnapshot>() {
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    child2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = (Uri) task.getResult();
                            ProfileEditActivity.this.stringThumb = uri.toString();
                            System.out.println("url__"+uri.toString());
                            HashMap hashMap = new HashMap();
                            hashMap.put("user_thumb", ProfileEditActivity.this.stringThumb);
                            ProfileEditActivity.this.firebaseFirestore.collection("users").document(str4).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        ProfileEditActivity.this.ImageUpload(str4, bitmap2, str5, str6);
                                    } else {
                                        Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        };
        putBytes.addOnCompleteListener((OnCompleteListener) r3);
    }

    /* access modifiers changed from: private */
    public void ImageUpload(String str, Bitmap bitmap, String str2, String str3) {
        this.bitmapImage = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmapImage.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        StorageReference child = this.storageReference.child(str).child("images").child("user");
        StringBuilder sb = new StringBuilder();
        sb.append("image_");
        sb.append(str);
        sb.append(".jpg");
        final StorageReference child2 = child.child(sb.toString());
        UploadTask putBytes = child2.putBytes(byteArray);
        final String str4 = str;
        final Bitmap bitmap2 = bitmap;
        final String str5 = str2;
        final String str6 = str3;
        OnCompleteListener r3 = new OnCompleteListener<UploadTask.TaskSnapshot>() {
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    child2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = (Uri) task.getResult();
                            ProfileEditActivity.this.stringImage = uri.toString();
                            HashMap hashMap = new HashMap();
                            hashMap.put("user_image", ProfileEditActivity.this.stringImage);
                            ProfileEditActivity.this.firebaseFirestore.collection("users").document(str4).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        ProfileEditActivity.this.CoverUpload(str4, bitmap2, str5, str6);
                                    } else {
                                        Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        };
        putBytes.addOnCompleteListener((OnCompleteListener) r3);
    }

    /* access modifiers changed from: private */
    public void CoverUpload(final String str, Bitmap bitmap, String str2, final String str3) {
        System.out.println("debug cover upload start" + str3);
        this.bitmapCover = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmapCover.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        StorageReference child = this.storageReference.child(str).child("images").child("user");
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        sb.append(".jpg");
        final StorageReference child2 = child.child(sb.toString());
        child2.putBytes(byteArray).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    child2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = (Uri) task.getResult();
                            ProfileEditActivity.this.stringCover = uri.toString();
                            String str1 = "user_cover";
//                            if (!str3.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str1);
                                sb.append(str3);
//                                str = sb.toString();
//                            }
                            HashMap hashMap = new HashMap();
                            hashMap.put(sb.toString(), ProfileEditActivity.this.stringCover);
//                            StringBuilder sb2 = new StringBuilder();
//                            sb2.append(str);
//                            sb2.append(str);
//                            final String sb3 = sb2.toString();
//                            final String finalStr = str;
                            System.out.println("debug cover upload process" + str);
                            ProfileEditActivity.this.firebaseFirestore.collection("users").document(str).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final HashMap hashMap = new HashMap();
                                        hashMap.put("feed_date", Timestamp.now());
                                        hashMap.put("feed_user", str);
                                        hashMap.put("feed_cover", ProfileEditActivity.this.stringCover);
                                        hashMap.put("feed_like", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                                        hashMap.put("feed_uid", str);
                                        ProfileEditActivity.this.firebaseFirestore.collection("users").document(str).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    String str6 = "yes";
                                                    String str2 = "feeds";
//                                                    final String str3 = "feed_show";
                                                    final String str7 = "show_feeds";
                                                    if (((DocumentSnapshot) task.getResult()).getString("show_feeds").equals(str6)) {
                                                        hashMap.put(str7, str6);
                                                        ProfileEditActivity.this.firebaseFirestore.collection(str2).document(str).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    System.out.println("debug cover upload yes" + str3);
                                                                    Toast.makeText(ProfileEditActivity.this, "Photo uploaded successfully!!", Toast.LENGTH_SHORT).show();
                                                                    ProfileEditActivity.this.ImageClickActiveCovers(str3, ProfileEditActivity.this.stringCover);
                                                                    System.out.println("debug after cover set ===" + str3);
                                                                    ProfileEditActivity.this.dialog.dismiss();
                                                                    return;
                                                                }
                                                                System.out.println("debug after cover upload " + str3);
                                                                ProfileEditActivity.this.dialog.dismiss();
                                                                Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        return;
                                                    }
                                                    hashMap.put(str7, "no");
//                                                    StringBuilder sb = new StringBuilder();
//                                                    sb.append(str);
//                                                    sb.append(str);
                                                    ProfileEditActivity.this.firebaseFirestore.collection(str2).document(str).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                System.out.println("debug cover upload no " + str3);
                                                                Toast.makeText(ProfileEditActivity.this, "Photo uploaded successfully!!", Toast.LENGTH_SHORT).show();
                                                                ProfileEditActivity.this.ImageClickActiveCovers(str3, ProfileEditActivity.this.stringCover);
                                                                ProfileEditActivity.this.dialog.dismiss();
                                                                return;
                                                            }
                                                            System.out.println("debug cover upload " + str3);
                                                            ProfileEditActivity.this.dialog.dismiss();
                                                            Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                        return;
                                    }
                                    ProfileEditActivity.this.dialog.dismiss();
                                    Toast.makeText(ProfileEditActivity.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                DocumentSnapshot documentSnapshot2 = documentSnapshot;
                if (documentSnapshot2 != null) {
                    String string = documentSnapshot2.getString("user_about");
                    String string2 = documentSnapshot2.getString("user_looking");
                    String string3 = documentSnapshot2.getString("user_seeking");
                    String string4 = documentSnapshot2.getString("user_marital");
                    String string5 = documentSnapshot2.getString("user_sexual");
                    String string6 = documentSnapshot2.getString("user_height");
                    String string7 = documentSnapshot2.getString("user_weight");
                    String string8 = documentSnapshot2.getString("user_appearance");
                    String string9 = documentSnapshot2.getString("user_feature");
                    String string10 = documentSnapshot2.getString("user_ethnicity");
                    String string11 = documentSnapshot2.getString("user_bodytype");
                    String string12 = documentSnapshot2.getString("user_bodyart");
                    String string13 = documentSnapshot2.getString("user_eyecolor");
                    String string14 = documentSnapshot2.getString("user_eyewear");
                    String string15 = documentSnapshot2.getString("user_haircolor");
                    String string16 = documentSnapshot2.getString("user_starsign");
                    String string17 = documentSnapshot2.getString("user_jobtitle");
                    String string18 = documentSnapshot2.getString("user_company");
                    String string19 = documentSnapshot2.getString("user_income");
                    String string20 = documentSnapshot2.getString("user_education");
                    String string21 = documentSnapshot2.getString("user_language");
                    String string22 = documentSnapshot2.getString("user_religion");
                    String string23 = documentSnapshot2.getString("user_drinking");
                    String string24 = documentSnapshot2.getString("user_smoking");
                    String string25 = documentSnapshot2.getString("user_living");
                    String string26 = documentSnapshot2.getString("user_relocation");
                    String string27 = documentSnapshot2.getString("user_interests");
                    String string28 = documentSnapshot2.getString("user_activities");
                    String string29 = documentSnapshot2.getString("user_movies");
                    String string30 = documentSnapshot2.getString("user_musics");
                    String string31 = documentSnapshot2.getString("user_tvshows");
                    String string32 = documentSnapshot2.getString("user_sports");
                    String string33 = documentSnapshot2.getString("user_books");
                    String string34 = documentSnapshot2.getString("user_cover0");
                    String str = string13;
                    String string35 = documentSnapshot2.getString("user_cover1");
                    String str2 = string12;
                    String string36 = documentSnapshot2.getString("user_cover2");
                    String str3 = string11;
                    String string37 = documentSnapshot2.getString("user_cover3");
                    String str4 = string10;
                    String string38 = documentSnapshot2.getString("user_cover4");
                    String str5 = string9;
                    String string39 = documentSnapshot2.getString("user_cover5");
                    String str6 = string8;
                    String string40 = documentSnapshot2.getString("user_cover6");
                    String str7 = string7;
                    String string41 = documentSnapshot2.getString("user_cover7");
                    String str8 = string6;
                    String string42 = documentSnapshot2.getString("user_cover8");
                    String str9 = string5;
                    if (string34.equals("cover")) {
                        ProfileEditActivity.this.imageViewProfileEditCoverZero.setImageResource(R.drawable.profile_image);
                    } else {
                        Picasso.get().load(string34).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverZero);
                        ProfileEditActivity.this.imageViewProfileEditCoverZero.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverZeroAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverZeroRemove.setVisibility(View.VISIBLE);
                    }
                    if (string35 != null) {
                        Picasso.get().load(string35).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverOne);
                        ProfileEditActivity.this.imageViewProfileEditCoverOne.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverOneAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverOneRemove.setVisibility(View.VISIBLE);
                    }
                    if (string36 != null) {
                        Picasso.get().load(string36).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverTwo);
                        ProfileEditActivity.this.imageViewProfileEditCoverTwo.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverTwoAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverTwoRemove.setVisibility(View.VISIBLE);
                    }
                    if (string37 != null) {
                        Picasso.get().load(string37).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverThree);
                        ProfileEditActivity.this.imageViewProfileEditCoverThree.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverThreeAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverThreeRemove.setVisibility(View.VISIBLE);
                    }
                    if (string38 != null) {
                        Picasso.get().load(string38).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverFour);
                        ProfileEditActivity.this.imageViewProfileEditCoverFour.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverFourAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverFourRemove.setVisibility(View.VISIBLE);
                    }
                    if (string39 != null) {
                        Picasso.get().load(string39).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverFive);
                        ProfileEditActivity.this.imageViewProfileEditCoverFive.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverFiveAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverFiveRemove.setVisibility(View.VISIBLE);
                    }
                    if (string40 != null) {
                        Picasso.get().load(string40).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverSix);
                        ProfileEditActivity.this.imageViewProfileEditCoverSix.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverSixAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverSixRemove.setVisibility(View.VISIBLE);
                    }
                    if (string41 != null) {
                        Picasso.get().load(string41).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverSeven);
                        ProfileEditActivity.this.imageViewProfileEditCoverSeven.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverSevenAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverSevenRemove.setVisibility(View.VISIBLE);
                    }
                    if (string42 != null) {
                        Picasso.get().load(string42).into((ImageView) ProfileEditActivity.this.imageViewProfileEditCoverEight);
                        ProfileEditActivity.this.imageViewProfileEditCoverEight.setClickable(false);
                        ProfileEditActivity.this.imageViewProfileEditCoverEightAdd.setVisibility(View.GONE);
                        ProfileEditActivity.this.imageViewProfileEditCoverEightRemove.setVisibility(View.VISIBLE);
                    }
                    if (string != null) {
                        ProfileEditActivity.this.textViewProfileEditAbout.setText(string);
                    }
                    if (string2 != null) {
                        ProfileEditActivity.this.textViewProfileEditLooking.setText(string2);
                    }
                    if (string3 != null) {
                        ProfileEditActivity.this.textViewProfileEditSeeking.setText(string3);
                    }
                    if (string4 != null) {
                        ProfileEditActivity.this.textViewProfileEditMarital.setText(string4);
                    }
                    if (str9 != null) {
                        ProfileEditActivity.this.textViewProfileEditSexual.setText(str9);
                    }
                    if (str8 != null) {
                        ProfileEditActivity.this.textViewProfileEditHeight.setText(str8);
                    }
                    if (str7 != null) {
                        ProfileEditActivity.this.textViewProfileEditWeight.setText(str7);
                    }
                    if (str6 != null) {
                        ProfileEditActivity.this.textViewProfileEditAppearance.setText(str6);
                    }
                    if (str5 != null) {
                        ProfileEditActivity.this.textViewProfileEditFeature.setText(str5);
                    }
                    if (str4 != null) {
                        ProfileEditActivity.this.textViewProfileEditEthnicity.setText(str4);
                    }
                    if (str3 != null) {
                        ProfileEditActivity.this.textViewProfileEditBodytype.setText(str3);
                    }
                    if (str2 != null) {
                        ProfileEditActivity.this.textViewProfileEditBodyart.setText(str2);
                    }
                    if (str != null) {
                        ProfileEditActivity.this.textViewProfileEditEyecolor.setText(str);
                    }
                    if (string14 != null) {
                        ProfileEditActivity.this.textViewProfileEditEyewear.setText(string14);
                    }
                    if (string15 != null) {
                        ProfileEditActivity.this.textViewProfileEditHaircolor.setText(string15);
                    }
                    if (string16 != null) {
                        ProfileEditActivity.this.textViewProfileEditStarsign.setText(string16);
                    }
                    if (string17 != null) {
                        ProfileEditActivity.this.textViewProfileEditJobtitle.setText(string17);
                    }
                    if (string18 != null) {
                        ProfileEditActivity.this.textViewProfileEditCompany.setText(string18);
                    }
                    if (string19 != null) {
                        ProfileEditActivity.this.textViewProfileEditIncome.setText(string19);
                    }
                    if (string20 != null) {
                        ProfileEditActivity.this.textViewProfileEditEducation.setText(string20);
                    }
                    if (string21 != null) {
                        ProfileEditActivity.this.textViewProfileEditLanguage.setText(string21);
                    }
                    if (string22 != null) {
                        ProfileEditActivity.this.textViewProfileEditReligion.setText(string22);
                    }
                    if (string23 != null) {
                        ProfileEditActivity.this.textViewProfileEditDrinking.setText(string23);
                    }
                    if (string24 != null) {
                        ProfileEditActivity.this.textViewProfileEditSmoking.setText(string24);
                    }
                    if (string25 != null) {
                        ProfileEditActivity.this.textViewProfileEditLiving.setText(string25);
                    }
                    if (string26 != null) {
                        ProfileEditActivity.this.textViewProfileEditRelocation.setText(string26);
                    }
                    if (string27 != null) {
                        ProfileEditActivity.this.textViewProfileEditInterests.setText(string27);
                    }
                    if (string28 != null) {
                        ProfileEditActivity.this.textViewProfileEditActivities.setText(string28);
                    }
                    if (string29 != null) {
                        ProfileEditActivity.this.textViewProfileEditMovies.setText(string29);
                    }
                    if (string30 != null) {
                        ProfileEditActivity.this.textViewProfileEditMusics.setText(string30);
                    }
                    if (string31 != null) {
                        ProfileEditActivity.this.textViewProfileEditTvshows.setText(string31);
                    }
                    if (string32 != null) {
                        ProfileEditActivity.this.textViewProfileEditSports.setText(string32);
                    }
                    if (string33 != null) {
                        ProfileEditActivity.this.textViewProfileEditBooks.setText(string33);
                    }
                }
            }
        });
    }

    private void UserStatus(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_status", str);
        FirebaseFirestore.getInstance().collection("users").document(this.currentUser).update(hashMap);
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

    /* access modifiers changed from: private */
    public void ProfileDialogCheck(String[] strArr, TextView textView, String str, String str2) {
        final ArrayList arrayList = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str2);
        String charSequence = textView.getText().toString();
        boolean[] zArr = new boolean[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            if (charSequence.contains(strArr[i])) {
                zArr[i] = true;
                arrayList.add(Integer.valueOf(i));
            } else {
                zArr[i] = false;
            }
        }
        builder.setMultiChoiceItems((CharSequence[]) strArr, zArr, (DialogInterface.OnMultiChoiceClickListener) new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                if (z) {
                    arrayList.add(Integer.valueOf(i));
                } else {
                    arrayList.remove(Integer.valueOf(i));
                }
            }
        });
        final ArrayList arrayList2 = arrayList;
        final String[] strArr2 = strArr;
        final TextView textView2 = textView;
        final String str3 = str;
        DialogInterface.OnClickListener r0 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String str = "";
                String str2 = str;
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(strArr2[((Integer) arrayList2.get(i2)).intValue()]);
                    str2 = sb.toString();
                    if (i2 != arrayList2.size() - 1) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(", ");
                        str2 = sb2.toString();
                    }
                }
                if (str2 == str) {
                    textView2.setText("Add");
                    Toast.makeText(ProfileEditActivity.this, "Sorry! Could not save empty fields", 0).show();
                    return;
                }
                textView2.setText(str2);
                ProfileEditActivity.this.ProfileUpdate(str3, str2);
            }
        };
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) r0);
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final boolean[] zArr2 = zArr;
        final TextView textView3 = textView;
        final ArrayList arrayList3 = arrayList;
        DialogInterface.OnClickListener r02 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int i2 = 0; i2 < zArr2.length; i2++) {
                    textView3.setText("Add");
                    zArr2[i2] = false;
                    arrayList3.clear();
                    ProfileEditActivity.this.ProfileDelete(str3);
                }
            }
        };
        builder.setNeutralButton((CharSequence) "Clear", (DialogInterface.OnClickListener) r02);
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
                    ProfileEditActivity.this.firebaseFirestore.collection(str).document(ProfileEditActivity.this.currentUser).update(hashMap);
                } else {
                    ProfileEditActivity.this.firebaseFirestore.collection(str).document(ProfileEditActivity.this.currentUser).set(hashMap);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void ProfileDelete(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, FieldValue.delete());
        this.firebaseFirestore.collection("users").document(this.currentUser).update(hashMap);
    }

    /* access modifiers changed from: private */
    public void ProfileDialogRadio(final String[] strArr, final TextView textView, final String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str2);
        builder.setSingleChoiceItems((CharSequence[]) strArr, new ArrayList(Arrays.asList(strArr)).indexOf(textView.getText().toString()), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int checkedItemPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                if (checkedItemPosition != -1) {
                    textView.setText(strArr[checkedItemPosition]);
                    ProfileEditActivity.this.ProfileUpdate(str, strArr[checkedItemPosition]);
                    return;
                }
                Toast.makeText(ProfileEditActivity.this, "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton((CharSequence) "Clear", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText("Add");
                ProfileEditActivity.this.ProfileDelete(str);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void ProfileDialogInput(final TextView textView, final String str, String str2, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str2);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_input, null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.editTextProfileEditInput);
        editText.setMinLines(i);
        String charSequence = textView.getText().toString();
        if (!charSequence.equals("Add")) {
            editText.setText(charSequence);
        }
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                if (!obj.equals("")) {
                    textView.setText(obj);
                    ProfileEditActivity.this.ProfileUpdate(str, obj);
                    return;
                }
                Toast.makeText(ProfileEditActivity.this, "Sorry! Could not save empty fields", 0).show();
            }
        });
        builder.setNeutralButton((CharSequence) "Clear", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText("Add");
                ProfileEditActivity.this.ProfileDelete(str);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void ProfileDialogSeekbar(final TextView textView, final String str, String str2, int i, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) str2);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_seekbar, null);
        builder.setView(inflate);
        final TextView textView2 = (TextView) inflate.findViewById(R.id.textViewProfileEditSeekbarLeft);
        final TextView textView3 = (TextView) inflate.findViewById(R.id.textViewProfileEditSeekbarRight);
        CrystalSeekbar crystalSeekbar = (CrystalSeekbar) inflate.findViewById(R.id.seekbarProfileEditSeekbarSlider);
        crystalSeekbar.setMinValue((float) i);
        crystalSeekbar.setMaxValue((float) i2);
        String charSequence = textView.getText().toString();
        if (!charSequence.equals("Add")) {
            String[] strArr = new String[10];
            if (str.equals("user_height")) {
                strArr = charSequence.split("cm");
            }
            if (str.equals("user_weight")) {
                strArr = charSequence.split("kg");
            }
            crystalSeekbar.setMinStartValue((float) Integer.valueOf(strArr[0]).intValue());
            crystalSeekbar.apply();
        }
        crystalSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            public void valueChanged(Number number) {
                if (str.equals("user_height")) {
                    String valueOf = String.valueOf(number);
                    TextView textView = textView2;
                    StringBuilder sb = new StringBuilder();
                    sb.append(valueOf);
                    sb.append("cm");
                    textView.setText(sb.toString());
                    String[] split = String.format("%.2f", new Object[]{Double.valueOf((Double.valueOf(valueOf).doubleValue() * 0.3937d) / 12.0d)}).split("\\.");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(split[0]);
                    sb2.append("ft ");
                    String sb3 = sb2.toString();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(".");
                    sb4.append(split[1]);
                    int doubleValue = (int) (Double.valueOf(sb4.toString()).doubleValue() * 12.0d);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(String.valueOf(doubleValue));
                    sb5.append("in");
                    String sb6 = sb5.toString();
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(sb3);
                    sb7.append(sb6);
                    textView3.setText(sb7.toString());
                }
                if (str.equals("user_weight")) {
                    String valueOf2 = String.valueOf(number);
                    //TextView textView2 = textView2;
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(valueOf2);
                    sb8.append("kg");
                    textView2.setText(sb8.toString());
                    int doubleValue2 = (int) (Double.valueOf(valueOf2).doubleValue() * 2.2046d);
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(String.valueOf(doubleValue2));
                    sb9.append("lb");
                    textView3.setText(sb9.toString());
                }
            }
        });
        final TextView textView4 = textView;
        final String str3 = str;
        DialogInterface.OnClickListener r3 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String charSequence = textView2.getText().toString();
                String charSequence2 = textView3.getText().toString();
                StringBuilder sb = new StringBuilder();
                sb.append(charSequence);
                sb.append(" (");
                sb.append(charSequence2);
                sb.append(")");
                String sb2 = sb.toString();
                textView4.setText(sb2);
                ProfileEditActivity.this.ProfileUpdate(str3, sb2);
            }
        };
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) r3);
        builder.setNeutralButton((CharSequence) "Clear", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText("Add");
                ProfileEditActivity.this.ProfileDelete(str);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    private void AgeUpdate(String str, String str2) {
        int intValue = Integer.valueOf(AgeUser(str)).intValue();
        if (intValue > Integer.valueOf(str2).intValue()) {
            HashMap hashMap = new HashMap();
            hashMap.put("user_birthage", String.valueOf(intValue));
            this.firebaseFirestore.collection("users").document(this.currentUser).update(hashMap);
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
