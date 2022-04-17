package com.firedating.chat.Chats;

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
import com.firedating.chat.Matches.MatchesClass;
import com.firedating.chat.Matches.MatchesFirestore;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewMatchesFireStore extends FirestoreRecyclerAdapter<MatchesClass, NewMatchesFireStore.MatchesHolder> {
    /* access modifiers changed from: private */
    public NewMatchesFireStore.OnItemClickListener listener;

    class MatchesHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageViewMatchesItemMatchesImage;
        TextView textViewMatchesItemMatchesName;
        CircleImageView circleImageViewUsersItemUsersOnline;

        public MatchesHolder(@NonNull View view) {
            super(view);
            this.textViewMatchesItemMatchesName = (TextView) view.findViewById(R.id.textViewChatsItemChatsName);
            this.roundedImageViewMatchesItemMatchesImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewChatsItemChatsImage);
            this.circleImageViewUsersItemUsersOnline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = NewMatchesFireStore.MatchesHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && NewMatchesFireStore.this.listener != null) {
                        NewMatchesFireStore.this.listener.onItemClick((DocumentSnapshot) NewMatchesFireStore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public NewMatchesFireStore(@NonNull FirestoreRecyclerOptions<MatchesClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final NewMatchesFireStore.MatchesHolder matchesHolder, int i, @NonNull final MatchesClass matchesClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(matchesClass.getUser_matches())) {
                            matchesHolder.textViewMatchesItemMatchesName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                matchesHolder.roundedImageViewMatchesItemMatchesImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) matchesHolder.roundedImageViewMatchesItemMatchesImage);
                            }
                            if (profileClass.getUser_status().equals("online")) {
                                matchesHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.VISIBLE);
                            } else
                            {
                                matchesHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });
    }

    @NonNull
    public NewMatchesFireStore.MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewMatchesFireStore.MatchesHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_new_matches_item, viewGroup, false));
    }

    public void setOnItemClickListener(NewMatchesFireStore.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
