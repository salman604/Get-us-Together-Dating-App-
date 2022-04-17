package com.firedating.chat.Settings;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.Extra.DateClass;
import com.firedating.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class AccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String currentUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    LinearLayout linearLayoutAccountUserBirthday;
    LinearLayout linearLayoutAccountUserCity;
    LinearLayout linearLayoutAccountUserCountry;
    LinearLayout linearLayoutAccountUserEmail;
    LinearLayout linearLayoutAccountUserGender;
    LinearLayout linearLayoutAccountUserMobile;
    LinearLayout linearLayoutAccountUserPassword;
    LinearLayout linearLayoutAccountUserState;
    LinearLayout linearLayoutAccountUserUsername;
    String[] string_array_user_gender;
    TextView textViewAccountUserBirthday;
    TextView textViewAccountUserCity;
    TextView textViewAccountUserCountry;
    TextView textViewAccountUserEmail;
    TextView textViewAccountUserGender;
    TextView textViewAccountUserMobile;
    TextView textViewAccountUserState;
    TextView textViewAccountUserUsername;
    Toolbar toolbarAccountUserToolbar;
    String user_email;
    String user_epass;

    /* access modifiers changed from: protected */
    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        this.linearLayoutAccountUserUsername = (LinearLayout) findViewById(R.id.linearLayoutAccountUserUsername);
        this.linearLayoutAccountUserBirthday = (LinearLayout) findViewById(R.id.linearLayoutAccountUserBirthday);
        this.linearLayoutAccountUserGender = (LinearLayout) findViewById(R.id.linearLayoutAccountUserGender);
        this.linearLayoutAccountUserCity = (LinearLayout) findViewById(R.id.linearLayoutAccountUserCity);
        this.linearLayoutAccountUserState = (LinearLayout) findViewById(R.id.linearLayoutAccountUserState);
        this.linearLayoutAccountUserCountry = (LinearLayout) findViewById(R.id.linearLayoutAccountUserCountry);
        this.linearLayoutAccountUserEmail = (LinearLayout) findViewById(R.id.linearLayoutAccountUserEmail);
        this.linearLayoutAccountUserMobile = (LinearLayout) findViewById(R.id.linearLayoutAccountUserMobile);
        this.linearLayoutAccountUserPassword = (LinearLayout) findViewById(R.id.linearLayoutAccountUserPassword);
        this.textViewAccountUserUsername = (TextView) findViewById(R.id.textViewAccountUserUsername);
        this.textViewAccountUserBirthday = (TextView) findViewById(R.id.textViewAccountUserBirthday);
        this.textViewAccountUserGender = (TextView) findViewById(R.id.textViewAccountUserGender);
        this.textViewAccountUserCity = (TextView) findViewById(R.id.textViewAccountUserCity);
        this.textViewAccountUserState = (TextView) findViewById(R.id.textViewAccountUserState);
        this.textViewAccountUserCountry = (TextView) findViewById(R.id.textViewAccountUserCountry);
        this.textViewAccountUserEmail = (TextView) findViewById(R.id.textViewAccountUserEmail);
        this.textViewAccountUserMobile = (TextView) findViewById(R.id.textViewAccountUserMobile);
        this.string_array_user_gender = getResources().getStringArray(R.array.string_array_user_gender);
        this.toolbarAccountUserToolbar = (Toolbar) findViewById(R.id.toolbarAccountUserToolbar);
        setSupportActionBar(this.toolbarAccountUserToolbar);
        getSupportActionBar().setTitle((CharSequence) "Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbarAccountUserToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AccountActivity.this.finish();
            }
        });
        this.linearLayoutAccountUserUsername.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AccountActivity accountActivity = AccountActivity.this;
                accountActivity.ProfileDialogInput(accountActivity.textViewAccountUserUsername, "user_name", "First Name", 1);
            }
        });
        this.linearLayoutAccountUserGender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AccountActivity accountActivity = AccountActivity.this;
                accountActivity.ProfileDialogRadio(accountActivity.string_array_user_gender, AccountActivity.this.textViewAccountUserGender, "user_gender", "Select your Gender");
            }
        });
        this.linearLayoutAccountUserBirthday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DateClass().show(AccountActivity.this.getSupportFragmentManager(), "Date Picker");
            }
        });
        this.linearLayoutAccountUserMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AccountActivity accountActivity = AccountActivity.this;
                accountActivity.ProfileDialogInput(accountActivity.textViewAccountUserMobile, "user_mobile", "Mobile Number", 1);
            }
        });
        this.linearLayoutAccountUserPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, PasswordActivity.class);
                intent.putExtra("user_email", AccountActivity.this.user_email);
                intent.putExtra("user_epass", AccountActivity.this.user_epass);
                AccountActivity.this.startActivity(intent);
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
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int checkedItemPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                textView.setText(strArr[checkedItemPosition]);
                AccountActivity.this.ProfileUpdate(str, strArr[checkedItemPosition]);
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
        editText.setText(textView.getText().toString());
        builder.setPositiveButton((CharSequence) "Save", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                if (!obj.equals("")) {
                    textView.setText(obj);
                    AccountActivity.this.ProfileUpdate(str, obj);
                    return;
                }
                Toast.makeText(AccountActivity.this, "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public void ProfileUpdate(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        this.firebaseFirestore.collection("users").document(this.currentUser).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.firebaseFirestore.collection("users").document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    String string = documentSnapshot.getString("user_name");
                    String string2 = documentSnapshot.getString("user_birthday");
                    String string3 = documentSnapshot.getString("user_birthage");
                    String string4 = documentSnapshot.getString("user_gender");
                    String string5 = documentSnapshot.getString("user_city");
                    String string6 = documentSnapshot.getString("user_state");
                    String string7 = documentSnapshot.getString("user_country");
                    AccountActivity.this.user_email = documentSnapshot.getString("user_email");
                    AccountActivity.this.user_epass = documentSnapshot.getString("user_epass");
                    String string8 = documentSnapshot.getString("user_mobile");
                    AccountActivity.this.textViewAccountUserUsername.setText(string);
                    TextView textView = AccountActivity.this.textViewAccountUserBirthday;
                    StringBuilder sb = new StringBuilder();
                    sb.append("(");
                    sb.append(string3);
                    sb.append("yrs) ");
                    sb.append(string2);
                    textView.setText(sb.toString());
                    AccountActivity.this.textViewAccountUserGender.setText(string4);
                    AccountActivity.this.textViewAccountUserCity.setText(string5);
                    AccountActivity.this.textViewAccountUserState.setText(string6);
                    AccountActivity.this.textViewAccountUserCountry.setText(string7);
                    AccountActivity.this.textViewAccountUserEmail.setText(AccountActivity.this.user_email);
                    AccountActivity.this.textViewAccountUserMobile.setText(string8);
                }
            }
        });
    }

    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, i);
        instance.set(Calendar.MONTH, i2);
        instance.set(Calendar.DATE, i3);
        String format = new SimpleDateFormat("dd-MM-YYYY").format(instance.getTime());
        if (i > 2000) {
            Toast.makeText(this, "Sorry! you dont meet our user registration minimum age limits policy now. Please register with us after some time. Thank you for trying our app now!", 1).show();
        } else {
            AgeUser(format);
        }
    }

    private void AgeUser(String str) {
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
        String num = new Integer(i).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(num);
        sb.append("yrs) ");
        sb.append(str);
        this.textViewAccountUserBirthday.setText(sb.toString());
        ProfileUpdate("user_birthday", str);
        ProfileUpdate("user_birthage", num);
    }
}
