package com.firedating.chat.Start;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firedating.chat.Main.MainActivity;
import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnLoginPageLogin;
    private TextView btnLoginPageRegister;
    ProgressDialog dialog;
    /* access modifiers changed from: private */
    public EditText loginEmail;
    /* access modifiers changed from: private */
    public EditText loginPass;
    /* access modifiers changed from: private */
    public FirebaseAuth userAuth;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_1);
        this.userAuth = FirebaseAuth.getInstance();
        this.loginEmail = (EditText) findViewById(R.id.loginEmailText);
        this.loginPass = (EditText) findViewById(R.id.loginPassText);
        this.btnLoginPageLogin = (Button) findViewById(R.id.btnLoginPageLogin);
        this.btnLoginPageRegister = (TextView) findViewById(R.id.btnLoginPageRegister);
        this.dialog = new ProgressDialog(this);
        this.dialog.setTitle("Login");
        this.dialog.setMessage("Please wait , while we are Logging in...");
        this.dialog.setCancelable(false);
        this.btnLoginPageLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = LoginActivity.this.loginEmail.getText().toString();
                String obj2 = LoginActivity.this.loginPass.getText().toString();
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2)) {
                    Toast.makeText(LoginActivity.this, "Please enter your email and password!", 0).show();
                    return;
                }
                LoginActivity.this.dialog.show();
                LoginActivity.this.userAuth.signInWithEmailAndPassword(obj, obj2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            LoginActivity.this.sendToMain();
                            LoginActivity.this.dialog.dismiss();
                            return;
                        }
                        String message = task.getException().getMessage();
                        LoginActivity loginActivity = LoginActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error : ");
                        sb.append(message);
                        Toast.makeText(loginActivity, sb.toString(), Toast.LENGTH_LONG).show();
                        LoginActivity.this.dialog.dismiss();
                    }
                });
            }
        });
        this.btnLoginPageRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                intent.addFlags(67108864);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
