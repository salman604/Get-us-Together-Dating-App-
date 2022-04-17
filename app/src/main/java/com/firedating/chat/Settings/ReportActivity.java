package com.firedating.chat.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {
    Button buttonProfileReportButton;
    CheckBox checkBoxProfileReportAbusive;
    CheckBox checkBoxProfileReportAdult;
    CheckBox checkBoxProfileReportAdverts;
    CheckBox checkBoxProfileReportFake;
    CheckBox checkBoxProfileReportOffence;
    CheckBox checkBoxProfileReportPhotos;
    CheckBox checkBoxProfileReportReligious;
    CheckBox checkBoxProfileReportSpam;
    CheckBox checkBoxProfileReportUnderage;
    String currentUser;
    EditText editTextProfileReportOther;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String profileUser;
    TextView textViewProfileReportContent;
    TextView textViewProfileReportTitle;
    Toolbar toolbarReport;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.toolbarReport = (Toolbar) findViewById(R.id.toolbarReport);
        setSupportActionBar(this.toolbarReport);
        getSupportActionBar().setTitle((CharSequence) "Report User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarReport.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReportActivity.this.finish();
            }
        });
        this.currentUser = this.firebaseUser.getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        this.textViewProfileReportContent = (TextView) findViewById(R.id.textViewProfileReportContent);
        this.checkBoxProfileReportFake = (CheckBox) findViewById(R.id.checkBoxProfileReportFake);
        this.checkBoxProfileReportPhotos = (CheckBox) findViewById(R.id.checkBoxProfileReportPhotos);
        this.checkBoxProfileReportSpam = (CheckBox) findViewById(R.id.checkBoxProfileReportSpam);
        this.checkBoxProfileReportAdverts = (CheckBox) findViewById(R.id.checkBoxProfileReportAdverts);
        this.checkBoxProfileReportAdult = (CheckBox) findViewById(R.id.checkBoxProfileReportAdult);
        this.checkBoxProfileReportOffence = (CheckBox) findViewById(R.id.checkBoxProfileReportOffence);
        this.checkBoxProfileReportAbusive = (CheckBox) findViewById(R.id.checkBoxProfileReportAbusive);
        this.checkBoxProfileReportReligious = (CheckBox) findViewById(R.id.checkBoxProfileReportReligious);
        this.checkBoxProfileReportUnderage = (CheckBox) findViewById(R.id.checkBoxProfileReportUnderage);
        this.editTextProfileReportOther = (EditText) findViewById(R.id.editTextProfileReportOther);
        this.buttonProfileReportButton = (Button) findViewById(R.id.buttonProfileReportButton);
        this.buttonProfileReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                hashMap.put("report_userby", ReportActivity.this.currentUser);
                hashMap.put("report_userto", ReportActivity.this.profileUser);
                hashMap.put("report_date", Timestamp.now());
                if (ReportActivity.this.checkBoxProfileReportFake.isChecked()) {
                    hashMap.put("report_fake", "This user is fake, fraud and duplicate");
                }
                if (ReportActivity.this.checkBoxProfileReportPhotos.isChecked()) {
                    hashMap.put("report_photos", "This user is using inappropriate photos");
                }
                if (ReportActivity.this.checkBoxProfileReportSpam.isChecked()) {
                    hashMap.put("report_spam", "This user is sending spam messages");
                }
                if (ReportActivity.this.checkBoxProfileReportAdverts.isChecked()) {
                    hashMap.put("report_adverts", "This user is sending advertisements");
                }
                if (ReportActivity.this.checkBoxProfileReportAdult.isChecked()) {
                    hashMap.put("report_adult", "This user is sending adult contents");
                }
                if (ReportActivity.this.checkBoxProfileReportOffence.isChecked()) {
                    hashMap.put("report_offence", "This user is offending me personally");
                }
                if (ReportActivity.this.checkBoxProfileReportAbusive.isChecked()) {
                    hashMap.put("report_abusive", "This user is using abusive languages");
                }
                if (ReportActivity.this.checkBoxProfileReportReligious.isChecked()) {
                    hashMap.put("report_religious", "This user is commenting on religions");
                }
                if (ReportActivity.this.checkBoxProfileReportUnderage.isChecked()) {
                    hashMap.put("report_underage", "This user is child and underage");
                }
                String obj = ReportActivity.this.editTextProfileReportOther.getText().toString();
                if (!obj.equals("")) {
                    hashMap.put("report_other", obj);
                }
                if (hashMap.size() > 3) {
                    ReportActivity.this.firebaseFirestore.collection("report").document(ReportActivity.this.profileUser).collection(ReportActivity.this.currentUser).add((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ReportActivity.this, "User Reported!", Toast.LENGTH_SHORT).show();
                                ReportActivity.this.finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ReportActivity.this, "Please select report types to send", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
