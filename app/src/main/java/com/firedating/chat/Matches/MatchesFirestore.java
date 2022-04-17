package com.firedating.chat.Matches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesFirestore extends FirestoreRecyclerAdapter<MatchesClass, MatchesFirestore.MatchesHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;

    class MatchesHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView textViewMatchesItemLastSeen;
        CircleImageView circleImageViewUsersItemUsersOnline;
        ImageView roundedImageViewMatchesItemMatchesImage;
        TextView textViewMatchesItemMatchesName,textViewMatchesItemMatchesage,textViewMatchesItemCity,RelativeTimeMatchesItemMatchesDate;

        public MatchesHolder(@NonNull View view) {
            super(view);
            this.textViewMatchesItemMatchesName = (TextView) view.findViewById(R.id.textViewMatchesItemMatchesName);
            this.roundedImageViewMatchesItemMatchesImage = (ImageView) view.findViewById(R.id.roundedImageViewMatchesItemMatchesImage);
            this.circleImageViewUsersItemUsersOnline = (CircleImageView) view.findViewById(R.id.circleImageViewMatchesItemMatchesOnline);
            this.RelativeTimeMatchesItemMatchesDate = (TextView) view.findViewById(R.id.RelativeTimeMatchesItemMatchesDate);
            this.textViewMatchesItemMatchesage = (TextView)view.findViewById(R.id.textViewUsersItemUsersGender);
            this.textViewMatchesItemCity = (TextView)view.findViewById(R.id.textViewUsersItemUsersCity);
            this.textViewMatchesItemLastSeen = (RelativeTimeTextView)view.findViewById(R.id.textViewMatchesItemMatchesLastSeen);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = MatchesHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && MatchesFirestore.this.listener != null) {
                        MatchesFirestore.this.listener.onItemClick((DocumentSnapshot) MatchesFirestore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public MatchesFirestore(@NonNull FirestoreRecyclerOptions<MatchesClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final MatchesHolder matchesHolder, int i, @NonNull final MatchesClass matchesClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(matchesClass.getUser_matches())) {
                            matchesHolder.textViewMatchesItemMatchesName.setText(profileClass.getUser_name());
                            matchesHolder.textViewMatchesItemMatchesage.setText(profileClass.getUser_birthage());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                matchesHolder.roundedImageViewMatchesItemMatchesImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) matchesHolder.roundedImageViewMatchesItemMatchesImage);
                            }
                            if(profileClass.getUser_status().equals("online"))
                            {
                                matchesHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.VISIBLE);
                                matchesHolder.textViewMatchesItemLastSeen.setText("Online");
                            }
                            else
                            {
                                matchesHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.GONE);
                                matchesHolder.textViewMatchesItemLastSeen.setReferenceTime( profileClass.getUser_online().getTime());
                            }

                        }
                    }
                }
            }
        });
        long timestamp = matchesClass.getUser_matched().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm a");
        String string13 = dateFormat.format(timestamp);
        matchesHolder.RelativeTimeMatchesItemMatchesDate.setText(string13);

    }

    @NonNull
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MatchesHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.matches_item_2, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
