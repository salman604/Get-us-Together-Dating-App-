package com.firedating.chat.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firedating.chat.Accounts.AccountsActivity;
import com.firedating.chat.R;
import com.firedating.chat.Settings.SettingsActivity;
import com.firedating.chat.Start.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    String currentUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    CircleImageView imageViewProfileTabAccountImage;
    CircleImageView imageViewProfileTabPrivacyImage;
    CircleImageView imageViewProfileTabSettingsImage;
    CircleImageView imageViewProfileTabUsernameImage;
    TextView textViewProfileTabAccountText;
    TextView textViewProfileTabLocationText;
    TextView textViewProfileTabPrivacyText;
    TextView textViewProfileTabSettingsText;
    TextView textViewProfileTabUsernameText;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.profile_fragment, viewGroup, false);
        this.imageViewProfileTabUsernameImage = (CircleImageView) inflate.findViewById(R.id.imageViewProfileTabUsernameImage);
        this.imageViewProfileTabSettingsImage = (CircleImageView) inflate.findViewById(R.id.imageViewProfileTabSettingsImage);
        this.imageViewProfileTabAccountImage = (CircleImageView) inflate.findViewById(R.id.imageViewProfileTabAccountImage);
        this.imageViewProfileTabPrivacyImage = (CircleImageView) inflate.findViewById(R.id.imageViewProfileTabPrivacyImage);
        this.textViewProfileTabUsernameText = (TextView) inflate.findViewById(R.id.textViewProfileTabUsernameText);
        this.textViewProfileTabLocationText = (TextView) inflate.findViewById(R.id.textViewProfileTabLocationText);
        this.textViewProfileTabSettingsText = (TextView) inflate.findViewById(R.id.textViewProfileTabSettingsText);
        this.textViewProfileTabAccountText = (TextView) inflate.findViewById(R.id.textViewProfileTabAccountText);
        this.textViewProfileTabPrivacyText = (TextView) inflate.findViewById(R.id.textViewProfileTabPrivacyText);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            this.currentUser = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String string = documentSnapshot.getString("user_image");
                        String[] split = documentSnapshot.getString("user_name").split(" ");
                        String string2 = documentSnapshot.getString("user_birthage");
                        TextView textView = ProfileFragment.this.textViewProfileTabUsernameText;
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[0]);
                        String str = ", ";
                        sb.append(str);
                        sb.append(string2);
                        textView.setText(sb.toString());
                        if (string.equals(RegisterActivity.MEDIA_IMAGE)) {
                            ProfileFragment.this.imageViewProfileTabUsernameImage.setImageResource(R.drawable.profile_image);
                        } else {
                            Picasso.get().load(string).into((ImageView) ProfileFragment.this.imageViewProfileTabUsernameImage);
                        }
                        String string3 = documentSnapshot.getString("user_city");
                        String string4 = documentSnapshot.getString("user_state");
                        String string5 = documentSnapshot.getString("user_country");
                        TextView textView2 = ProfileFragment.this.textViewProfileTabLocationText;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(string3);
                        sb2.append(str);
                        sb2.append(string4);
                        sb2.append(str);
                        sb2.append(string5);
                        textView2.setText(sb2.toString());
                    }
                }
            });
        }
        this.imageViewProfileTabUsernameImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ProfileFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", ProfileFragment.this.currentUser);
                ProfileFragment.this.startActivity(intent);
            }
        });
        this.imageViewProfileTabSettingsImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getContext(), SettingsActivity.class));
            }
        });
        this.imageViewProfileTabAccountImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getContext(), AccountsActivity.class));
            }
        });
        this.imageViewProfileTabPrivacyImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getContext(), ProfileEditActivity.class));
            }
        });
        return inflate;
    }
}
