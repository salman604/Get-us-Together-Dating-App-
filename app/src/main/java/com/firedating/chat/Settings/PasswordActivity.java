package com.firedating.chat.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class PasswordActivity extends AppCompatActivity {
    AuthCredential authCredential;
    Button buttonPasswordUpdate;
    String currentUser;
    ProgressDialog dialog;
    EditText editTextPasswordCheck;
    EditText editTextPasswordNew;
    EditText editTextPasswordOld;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    Toolbar toolbarPasswordToolbar;
    String user_email;
    String user_epass;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = this.firebaseUser.getUid();
        this.editTextPasswordOld = (EditText) findViewById(R.id.editTextPasswordOld);
        this.editTextPasswordNew = (EditText) findViewById(R.id.editTextPasswordNew);
        this.editTextPasswordCheck = (EditText) findViewById(R.id.editTextPasswordCheck);
        this.buttonPasswordUpdate = (Button) findViewById(R.id.buttonPasswordUpdate);
        this.toolbarPasswordToolbar = (Toolbar) findViewById(R.id.toolbarPasswordToolbar);
        setSupportActionBar(this.toolbarPasswordToolbar);
        getSupportActionBar().setTitle((CharSequence) "Password Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbarPasswordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PasswordActivity.this.finish();
            }
        });
        this.user_email = getIntent().getStringExtra("user_email");
        this.user_epass = getIntent().getStringExtra("user_epass");
        this.buttonPasswordUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = PasswordActivity.this.editTextPasswordOld.getText().toString();
                final String obj2 = PasswordActivity.this.editTextPasswordNew.getText().toString();
                String obj3 = PasswordActivity.this.editTextPasswordCheck.getText().toString();
                if (!obj.equals(PasswordActivity.this.user_epass)) {
                    Toast.makeText(PasswordActivity.this, "Current password field does not match with your password. please recheck it!", Toast.LENGTH_SHORT).show();
                } else if (obj2.equals(obj)) {
                    Toast.makeText(PasswordActivity.this, "Your new password and old password are same. please recheck it!", Toast.LENGTH_SHORT).show();
                } else if (obj2.length() < 5) {
                    Toast.makeText(PasswordActivity.this, "Your new password should be atleast 6 characters. please recheck it!", Toast.LENGTH_SHORT).show();
                } else if (obj2.equals(obj3)) {
                    PasswordActivity passwordActivity = PasswordActivity.this;
                    passwordActivity.dialog = new ProgressDialog(passwordActivity);
                    PasswordActivity.this.dialog.setTitle("Please Wait");
                    PasswordActivity.this.dialog.setMessage("Updating Password...");
                    PasswordActivity.this.dialog.setCancelable(false);
                    PasswordActivity.this.dialog.show();
                    PasswordActivity passwordActivity2 = PasswordActivity.this;
                    passwordActivity2.authCredential = EmailAuthProvider.getCredential(passwordActivity2.user_email, PasswordActivity.this.user_epass);
                    PasswordActivity.this.firebaseUser.reauthenticate(PasswordActivity.this.authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                PasswordActivity.this.PasswordUpdate(obj2);
                            }
                        }
                    });
                } else {
                    Toast.makeText(PasswordActivity.this, "New password and Confirm passwords do not match. please recheck it!", 0).show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void PasswordUpdate(final String str) {
        this.firebaseUser.updatePassword(str).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("user_epass", str);
                    PasswordActivity.this.firebaseFirestore.collection("users").document(PasswordActivity.this.currentUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(PasswordActivity.this, AccountActivity.class);
//                                intent.addFlags(67108864);
                                PasswordActivity.this.startActivity(intent);
                                Toast.makeText(PasswordActivity.this, "Password Updated Succesfully!", Toast.LENGTH_SHORT).show();
                                PasswordActivity.this.dialog.dismiss();
                            }
                        }
                    });
                    return;
                }
                Toast.makeText(PasswordActivity.this, "Password update failed! Please try again later!", Toast.LENGTH_SHORT).show();
                PasswordActivity.this.dialog.dismiss();
            }
        });
    }
}
