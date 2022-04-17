package com.firedating.chat.Start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.firedating.chat.Extra.DateClass;
import com.firedating.chat.Main.MainActivity;
import com.firedating.chat.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.HttpHeaders;
import com.google.firebase.Timestamp;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.nabinbhandari.android.permissions.Permissions.Options;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements OnDateSetListener {
    private TextView btnRegisterPageLogin;
    private Button btnRegisterPageRegister;
    ProgressDialog dialog;
    /* access modifiers changed from: private */
    public EditText editTextRegisterEmail;
    /* access modifiers changed from: private */
    public EditText editTextRegisterName;
    /* access modifiers changed from: private */
    public EditText editTextRegisterPassword;
    /* access modifiers changed from: private */
    public FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public FirebaseUser firebaseUser;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    private LocationRequest locationRequest;
    /* access modifiers changed from: private */
    public RadioButton radioButtonRegisterGender;
    private RadioGroup radioGroupRegisterGender;
    String stringLatitude;
    String stringLongitude;
    String stringLooking;
    String string_city;
    String string_country;
    String string_location;
    String string_state;
    /* access modifiers changed from: private */
    public TextView textViewRegisterBirthday;

    public static final String MEDIA_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.btnRegisterPageRegister = (Button) findViewById(R.id.btnRegisterPageRegister);
        this.btnRegisterPageLogin = (TextView) findViewById(R.id.btnRegisterPageLogin);
        this.editTextRegisterName = (EditText) findViewById(R.id.editTextRegisterName);
        this.editTextRegisterEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        this.editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        this.textViewRegisterBirthday = (TextView) findViewById(R.id.textViewRegisterBirthday);
        this.radioGroupRegisterGender = (RadioGroup) findViewById(R.id.radioGroupRegisterGender);
        this.dialog = new ProgressDialog(this);
        this.dialog.setTitle("Register");
        this.dialog.setMessage("Please wait , while we are Registering your account...");
        this.dialog.setCancelable(false);
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationPremissionCheck();
        GooglePlayServiceCheck();
        GPSLocationServiceCheck();
        this.btnRegisterPageLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.addFlags(67108864);
                RegisterActivity.this.startActivity(intent);
            }
        });
        this.textViewRegisterBirthday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new DateClass().show(RegisterActivity.this.getSupportFragmentManager(), "Date Picker");
            }
        });

        this.btnRegisterPageRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "Please Fill in all the details to proceed!";
                if (RegisterActivity.this.radioButtonRegisterGender == null) {
                    Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
                } else if (RegisterActivity.this.string_city == null || RegisterActivity.this.string_city.equals("city") || RegisterActivity.this.string_state == null || RegisterActivity.this.string_state.equals("state") || RegisterActivity.this.string_country == null || RegisterActivity.this.string_country.equals("country")) {
                    Toast.makeText(RegisterActivity.this, "Please turn on Location service to continue.", Toast.LENGTH_SHORT).show();
                } else {
                    final String obj = RegisterActivity.this.editTextRegisterName.getText().toString();
                    final String obj2 = RegisterActivity.this.editTextRegisterEmail.getText().toString();
                    final String obj3 = RegisterActivity.this.editTextRegisterPassword.getText().toString();
                    final String charSequence = RegisterActivity.this.radioButtonRegisterGender.getText().toString();
                    final String charSequence2 = RegisterActivity.this.textViewRegisterBirthday.getText().toString();
                    if (charSequence.equals("Male")) {
                        RegisterActivity.this.stringLooking = "Woman";
                    } else {
                        RegisterActivity.this.stringLooking = "Man";
                    }
                    if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj3) || TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(charSequence2)) {
                        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    RegisterActivity.this.dialog.show();
                    Task createUserWithEmailAndPassword = RegisterActivity.this.firebaseAuth.createUserWithEmailAndPassword(obj2, obj3);
                    OnCompleteListener r2 = new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                RegisterActivity.this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = RegisterActivity.this.firebaseUser.getUid();
                                HashMap hashMap = new HashMap();
                                hashMap.put("user_uid", uid);
                                hashMap.put("user_email", obj2);
                                hashMap.put("user_epass", obj3);
                                hashMap.put("user_name", obj);
                                hashMap.put("user_gender", charSequence);
                                hashMap.put("user_birthday", charSequence2);
                                hashMap.put("user_birthage", RegisterActivity.this.AgeUser(charSequence2));
                                hashMap.put("user_city", RegisterActivity.this.string_city);
                                hashMap.put("user_state", RegisterActivity.this.string_state);
                                hashMap.put("user_country", RegisterActivity.this.string_country);
                                hashMap.put("user_location", RegisterActivity.this.string_location);
                                hashMap.put("user_thumb", "thumb");
                                hashMap.put("user_image", MEDIA_IMAGE);
                                hashMap.put("user_cover0", "cover");
                                hashMap.put("user_status", "offline");
                                hashMap.put("user_looking", RegisterActivity.this.stringLooking);
                                hashMap.put("user_about", "Hi! Everybody I am newbie here.");
                                hashMap.put("user_latitude", RegisterActivity.this.stringLatitude);
                                hashMap.put("user_longitude", RegisterActivity.this.stringLongitude);
                                hashMap.put("user_online", Timestamp.now());
                                hashMap.put("user_joined", Timestamp.now());
                                RegisterActivity.this.firebaseFirestore.collection("users").document(uid).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            RegisterActivity.this.finish();
                                            RegisterActivity.this.dialog.dismiss();
                                            return;
                                        }
                                        Toast.makeText(RegisterActivity.this, "Something went wrong! Please try again!", Toast.LENGTH_SHORT).show();
                                        RegisterActivity.this.dialog.dismiss();
                                    }
                                });
                                return;
                            }
                            Toast.makeText(RegisterActivity.this, "Please check errors to proceed!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    createUserWithEmailAndPassword.addOnCompleteListener(r2);
                }
            }
        });
    }

    public void radioButtonRegisterGender(View view) {
        this.radioButtonRegisterGender = (RadioButton) findViewById(this.radioGroupRegisterGender.getCheckedRadioButtonId());
    }

    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, i);
        instance.set(Calendar.MONTH, i2);
        instance.set(Calendar.DATE, i3);
        String format = new SimpleDateFormat("dd-MM-YYYY").format(instance.getTime());
        if (i > 2000) {
            Toast.makeText(this, "Sorry! you dont meet our user registration minimum age limits policy now. Please register with us after some time. Thank you for trying our app now!", Toast.LENGTH_SHORT).show();
            this.textViewRegisterBirthday.setText("");
            return;
        }
        this.textViewRegisterBirthday.setText(format);
    }

    /* access modifiers changed from: private */
    public String AgeUser(String str) {
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

    /* access modifiers changed from: private */
    public void LocationPremissionCheck() {
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
        new Options().setRationaleDialogTitle("Location Permission").setSettingsDialogTitle(HttpHeaders.WARNING);
        Permissions.check((Context) this, strArr, (String) null, (Options) null, (PermissionHandler) new PermissionHandler() {
            public void onGranted() {
                RegisterActivity.this.LocationRequest();
            }

            public void onDenied(Context context, ArrayList<String> arrayList) {
                super.onDenied(context, arrayList);
                RegisterActivity.this.LocationPremissionCheck();
            }
        });
    }

    /* access modifiers changed from: private */
    public void LocationRetreive(Double d, Double d2) {
        try {
            List fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(d.doubleValue(), d2.doubleValue(), 1);
            if (fromLocation != null && fromLocation.size() > 0) {
                this.string_city = ((Address) fromLocation.get(0)).getLocality();
                this.string_state = ((Address) fromLocation.get(0)).getAdminArea();
                this.string_country = ((Address) fromLocation.get(0)).getCountryName();
                this.string_location = ((Address) fromLocation.get(0)).getAddressLine(0);
                if (this.string_country == null) {
                    if (this.string_state != null) {
                        this.string_country = this.string_state;
                    } else if (this.string_city != null) {
                        this.string_country = this.string_city;
                    } else {
                        this.string_country = "null";
                    }
                }
                if (this.string_city == null) {
                    if (this.string_state != null) {
                        this.string_city = this.string_state;
                    } else {
                        this.string_city = this.string_country;
                    }
                }
                if (this.string_state == null) {
                    if (this.string_city != null) {
                        this.string_state = this.string_city;
                    } else {
                        this.string_state = this.string_country;
                    }
                }
                if (this.string_location == null) {
                    this.string_location = "Null";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /* access modifiers changed from: private */
    public void LocationRequest() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.fusedLocationProviderClient = new FusedLocationProviderClient((Activity) this);
            this.fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                public void onSuccess(Location location) {
                    String str = "Please turn on any GPS or location service and restart to use the app";
                    if (location != null) {
                        Double valueOf = Double.valueOf(location.getLatitude());
                        Double valueOf2 = Double.valueOf(location.getLongitude());
                        RegisterActivity.this.stringLatitude = valueOf.toString();
                        RegisterActivity.this.stringLongitude = valueOf2.toString();
                        String str2 = "0.0";
                        if (RegisterActivity.this.stringLatitude.equals(str2) || RegisterActivity.this.stringLongitude.equals(str2)) {
                            Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
                        } else {
                            RegisterActivity.this.LocationRetreive(valueOf, valueOf2);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return;
        }
        LocationPremissionCheck();
    }

    public boolean GooglePlayServiceCheck() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
            instance.getErrorDialog(this, isGooglePlayServicesAvailable, 2404).show();
        }
        return false;
    }

    private void GPSLocationServiceCheck() {
        if (!this.locationManager.isProviderEnabled("gps")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String str = "No";
            builder.setMessage((CharSequence) "Your GPS seems to be disabled, enable it to use this app?").setCancelable(false).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    RegisterActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                }
            }).setNegativeButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
//                    intent.setFlags(67108864);
                    RegisterActivity.this.startActivity(intent);
                    RegisterActivity.this.finish();
                }
            });
            builder.create().show();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        GPSLocationServiceCheck();
    }
}
