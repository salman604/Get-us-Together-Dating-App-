package com.firedating.chat.Start;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firedating.chat.Main.MainActivity;
import com.firedating.chat.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    ArrayList<String> arrayListFacebook;
    Button buttonStartEmail;
    Button buttonStartFacebook, buttonStartGoogle;
    ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    ImageView imageStart;
    private CallbackManager mCallbackManager;
    GoogleSignInClient mGoogleSignInClient;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
        setContentView(R.layout.register_activity);
        this.arrayListFacebook = new ArrayList<>();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        this.buttonStartFacebook = (Button) findViewById(R.id.buttonStartFacebook);
        this.buttonStartEmail = (Button) findViewById(R.id.buttonStartEmail);
        this.buttonStartGoogle = (Button) findViewById(R.id.buttonStartgoogle);
        this.imageStart = (ImageView) findViewById(R.id.imageStart);
//        this.firebaseFirestore.collection("admin").document("settings").addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
//                if (documentSnapshot != null) {
//
//                }
//            }
//        });
        this.buttonStartEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StartActivity.this.startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        this.buttonStartGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                google_sign_in();
                StartActivity startActivity = StartActivity.this;
                startActivity.dialog = new ProgressDialog(startActivity);
                StartActivity.this.dialog.setTitle("Login");
                StartActivity.this.dialog.setMessage("Please wait , while we are Logging in...");
                StartActivity.this.dialog.setCancelable(true);
                StartActivity.this.dialog.setCanceledOnTouchOutside(true);
                StartActivity.this.dialog.show();
            }
        });
        this.mCallbackManager = CallbackManager.Factory.create();
        this.buttonStartFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StartActivity.this.FacebookSignIn();
                StartActivity startActivity = StartActivity.this;
                startActivity.dialog = new ProgressDialog(startActivity);
                StartActivity.this.dialog.setTitle("Login");
                StartActivity.this.dialog.setMessage("Please wait , while we are Logging in...");
                StartActivity.this.dialog.setCancelable(true);
                StartActivity.this.dialog.setCanceledOnTouchOutside(true);
                StartActivity.this.dialog.show();
            }
        });

    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sign in Failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        } else if (mCallbackManager != null) {
            this.mCallbackManager.onActivityResult(i, i2, intent);
        }

    }

    /* access modifiers changed from: private */
    public void FacebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions((Activity) this, (Collection<String>) Arrays.asList(new String[]{"email", "public_profile"}));
        LoginManager.getInstance().registerCallback(this.mCallbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult loginResult) {
                StartActivity.this.handleFacebookAccessToken(loginResult.getAccessToken());
            }

            public void onCancel() {
                StartActivity.this.dialog.dismiss();
                StartActivity.this.WelcomePage();
            }

            public void onError(FacebookException facebookException) {
                StartActivity.this.dialog.dismiss();

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            StartActivity.this.dialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            final String uid = ((AuthResult) task.getResult()).getUser().getUid();
                            final String displayName = ((AuthResult) task.getResult()).getUser().getDisplayName();
                            final String email = ((AuthResult) task.getResult()).getUser().getEmail();
                            final String uri = ((AuthResult) task.getResult()).getUser().getPhotoUrl().toString();
                            Task task2 = StartActivity.this.firebaseFirestore.collection("users").document(uid).get();
                            OnCompleteListener r1 = new OnCompleteListener<DocumentSnapshot>() {
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (!((DocumentSnapshot) task.getResult()).exists()) {
                                        StartActivity.this.FirestoreRegister(uid, displayName, email, uri);
                                    } else {
                                        StartActivity.this.WelcomePage();
                                    }
                                }
                            };
                            task2.addOnCompleteListener(r1);
                            return;
                        } else {
                            // If sign in fails, display a message to the user.
                            StartActivity.this.dialog.dismiss();
                            Toast.makeText(StartActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        StartActivity.this.dialog.dismiss();
                        Toast.makeText(StartActivity.this.getApplicationContext(), "User with Email id already exists. Login with email to link this account", Toast.LENGTH_SHORT).show();

                        // ...
                    }
                });
    }


    /* access modifiers changed from: private */
    public void FirestoreRegister(final String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_uid", str);
        hashMap.put("user_email", str3);
        hashMap.put("user_epass", "");
        hashMap.put("user_name", str2);
        hashMap.put("user_gender", "Male");
        hashMap.put("user_birthday", "01/01/1990");
        hashMap.put("user_birthage", "25");
        hashMap.put("user_city", "Mumbai");
        hashMap.put("user_state", "Maharashtra");
        hashMap.put("user_country", "India");
        hashMap.put("user_location", "Bandra");
        hashMap.put("user_thumb", str4);
        hashMap.put("user_image", str4);
        hashMap.put("user_cover0", str4);
        hashMap.put("user_status", "offline");
        hashMap.put("user_looking", "Man");
        hashMap.put("user_about", "Hi! Everybody I am newbie here.");
        hashMap.put("user_latitude", "19.075983");
        hashMap.put("user_longitude", "72.877655");
        hashMap.put("user_online", Timestamp.now());
        hashMap.put("user_joined", Timestamp.now());
        String str5 = "yes";
        hashMap.put("user_verified", str5);
        hashMap.put("user_facebook", str5);
        hashMap.put("user_dummy", str5);
        this.firebaseFirestore.collection("users").document(str).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    StartActivity.this.RemindPage(str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void WelcomePage() {
        startActivity(new Intent(this, MainActivity.class));
    }

    /* access modifiers changed from: private */
    public void RemindPage(String str) {
        Intent intent = new Intent(this, RemindActivity.class);
        intent.putExtra("user_uid", str);
        startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    private void google_sign_in()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(StartActivity.this);
        if (account != null) {
            firebaseAuthWithGoogle(account.getIdToken());

        }
        else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 123);
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                final String uid = ((AuthResult) task.getResult()).getUser().getUid();
                                final String displayName = ((AuthResult) task.getResult()).getUser().getDisplayName();
                                final String email = ((AuthResult) task.getResult()).getUser().getEmail();
                                final String uri = ((AuthResult) task.getResult()).getUser().getPhotoUrl().toString();
                                Task task2 = StartActivity.this.firebaseFirestore.collection("users").document(uid).get();
                                OnCompleteListener r1 = new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (!((DocumentSnapshot) task.getResult()).exists()) {
                                            StartActivity.this.dialog.dismiss();
                                            StartActivity.this.FirestoreRegister(uid, displayName, email, uri);
                                        } else {
                                            StartActivity.this.dialog.dismiss();
                                            StartActivity.this.WelcomePage();
                                        }
                                    }
                                };
                                task2.addOnCompleteListener(r1);
                                return;
                            } else {
                                // If sign in fails, display a message to the user.
                                StartActivity.this.dialog.dismiss();
                                Toast.makeText(StartActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                            StartActivity.this.dialog.dismiss();
                            Toast.makeText(StartActivity.this.getApplicationContext(), "User with Email id already exists. Login with email to link this account", Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            StartActivity.this.dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sign In failed", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

}
