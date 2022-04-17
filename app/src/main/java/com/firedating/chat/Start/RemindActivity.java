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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.HttpHeaders;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.auth.AuthCredential;
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

public class RemindActivity extends AppCompatActivity implements OnDateSetListener {
    AuthCredential authCredential;
    private Button btnRemind;
    private String currentUser;
    ProgressDialog dialog;
    private EditText editTextRemindPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    private LocationRequest locationRequest;
    /* access modifiers changed from: private */
    public RadioButton radioButtonRemindGender;
    private RadioGroup radioGroupRemindGender;
    private String stringEmail;
    private String stringEpass;
    String stringLatitude;
    String stringLongitude;
    String stringLooking;
    String string_city;
    String string_country;
    String string_location;
    String string_state;
    /* access modifiers changed from: private */
    public TextView textViewRemindBirthday;

    /* access modifiers changed from: protected */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind_activity);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = firebaseAuth.getCurrentUser().getUid();
        this.btnRemind = (Button) findViewById(R.id.btnRemind);
        this.editTextRemindPassword = (EditText) findViewById(R.id.editTextRemindPassword);
        this.textViewRemindBirthday = (TextView) findViewById(R.id.textViewRemindBirthday);
        this.radioGroupRemindGender = (RadioGroup) findViewById(R.id.radioGroupRemindGender);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationPremissionCheck();
        GooglePlayServiceCheck();
        GPSLocationServiceCheck();
        this.textViewRemindBirthday.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new DateClass().show(RemindActivity.this.getSupportFragmentManager(), "Date Picker");
            }
        });
        this.btnRemind.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "Please Fill in all the details to proceed!";
                if (RemindActivity.this.radioButtonRemindGender == null) {
                    Toast.makeText(RemindActivity.this, str, Toast.LENGTH_SHORT).show();
                } else if (RemindActivity.this.string_city == null || RemindActivity.this.string_city.equals("city") || RemindActivity.this.string_state == null || RemindActivity.this.string_state.equals("state") || RemindActivity.this.string_country == null || RemindActivity.this.string_country.equals("country")) {
                    Toast.makeText(RemindActivity.this, "Please turn on Location service to continue.", Toast.LENGTH_SHORT).show();
                } else {
                    String charSequence = RemindActivity.this.radioButtonRemindGender.getText().toString();
                    String charSequence2 = RemindActivity.this.textViewRemindBirthday.getText().toString();
                    if (charSequence.equals("Male")) {
                        RemindActivity.this.stringLooking = "Woman";
                    } else {
                        RemindActivity.this.stringLooking = "Man";
                    }
                    if (TextUtils.isEmpty(charSequence) && TextUtils.isEmpty(charSequence2)) {
                        Toast.makeText(RemindActivity.this, str, Toast.LENGTH_SHORT).show();
                    } else if (!charSequence2.equals("")) {
                        RemindActivity remindActivity = RemindActivity.this;
                        remindActivity.dialog = new ProgressDialog(remindActivity);
                        RemindActivity.this.dialog.setTitle("Please Wait");
                        RemindActivity.this.dialog.setMessage("Setting Profile...");
                        RemindActivity.this.dialog.setCancelable(false);
                        RemindActivity.this.dialog.show();
                        RemindActivity.this.ProfileUpdate(charSequence, charSequence2);
                    } else {
                        Toast.makeText(RemindActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void radioButtonRemindGender(View view) {
        this.radioButtonRemindGender = (RadioButton) findViewById(this.radioGroupRemindGender.getCheckedRadioButtonId());
    }

    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, i);
        instance.set(Calendar.MONTH, i2);
        instance.set(Calendar.DATE, i3);
        String format = new SimpleDateFormat("dd-MM-YYYY").format(instance.getTime());
        if (i > 2000) {
            Toast.makeText(this, "Sorry! you dont meet our user registration minimum age limits policy now. Please register with us after some time. Thank you for trying our app now!", Toast.LENGTH_SHORT).show();
            this.textViewRemindBirthday.setText("");
            return;
        }
        this.textViewRemindBirthday.setText(format);
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

    /* access modifiers changed from: private */
    public void ProfileUpdate(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_gender", str);
        hashMap.put("user_birthday", str2);
        hashMap.put("user_birthage", AgeUser(str2));
        hashMap.put("user_city", this.string_city);
        hashMap.put("user_state", this.string_state);
        hashMap.put("user_country", this.string_country);
        hashMap.put("user_location", this.string_location);
        hashMap.put("user_looking", this.stringLooking);
        hashMap.put("user_latitude", this.stringLatitude);
        hashMap.put("user_longitude", this.stringLongitude);
        hashMap.put("user_dummy", "no");
        this.firebaseFirestore.collection("users").document(this.currentUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    RemindActivity.this.startActivity(new Intent(RemindActivity.this, MainActivity.class));
                    RemindActivity.this.finish();
                    RemindActivity.this.dialog.dismiss();
                    return;
                }
                RemindActivity.this.dialog.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void LocationPremissionCheck() {
        String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
        new Options().setRationaleDialogTitle("Location Permission").setSettingsDialogTitle(HttpHeaders.WARNING);
        Permissions.check((Context) this, strArr, (String) null, (Options) null, (PermissionHandler) new PermissionHandler() {
            public void onGranted() {
                RemindActivity.this.LocationRequest();
            }

            public void onDenied(Context context, ArrayList<String> arrayList) {
                super.onDenied(context, arrayList);
                RemindActivity.this.LocationPremissionCheck();
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
                        RemindActivity.this.stringLatitude = valueOf.toString();
                        RemindActivity.this.stringLongitude = valueOf2.toString();
                        String str2 = "0.0";
                        if (RemindActivity.this.stringLatitude.equals(str2) || RemindActivity.this.stringLongitude.equals(str2)) {
                            Toast.makeText(RemindActivity.this, str, Toast.LENGTH_SHORT).show();
                        } else {
                            RemindActivity.this.LocationRetreive(valueOf, valueOf2);
                        }
                    } else {
                        Toast.makeText(RemindActivity.this, str, Toast.LENGTH_SHORT).show();
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
                    RemindActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                }
            }).setNegativeButton((CharSequence) str, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(RemindActivity.this, StartActivity.class);
//                    intent.setFlags(67108864);
                    RemindActivity.this.startActivity(intent);
                    RemindActivity.this.finish();
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

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
