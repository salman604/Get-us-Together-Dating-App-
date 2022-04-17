package com.firedating.chat.Likes;

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

public class LikesFirestore extends FirestoreRecyclerAdapter<LikesClass, LikesFirestore.LikesHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;

    class LikesHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView RelativeTimeLikesItemLikesDate;
        RoundedImageView roundedImageViewLikesItemLikesImage;
        TextView textViewLikesItemLikesMessage;
        TextView textViewLikesItemLikesName;
        TextView textViewLikesItemLikesUnread;

        public LikesHolder(@NonNull View view) {
            super(view);
            this.textViewLikesItemLikesName = (TextView) view.findViewById(R.id.textViewLikesItemLikesName);
            this.textViewLikesItemLikesUnread = (TextView) view.findViewById(R.id.textViewLikesItemLikesUnread);
            this.RelativeTimeLikesItemLikesDate = (RelativeTimeTextView) view.findViewById(R.id.RelativeTimeLikesItemLikesDate);
            this.roundedImageViewLikesItemLikesImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewLikesItemLikesImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = LikesHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && LikesFirestore.this.listener != null) {
                        LikesFirestore.this.listener.onItemClick((DocumentSnapshot) LikesFirestore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public LikesFirestore(@NonNull FirestoreRecyclerOptions<LikesClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final LikesHolder likesHolder, int i, @NonNull final LikesClass likesClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(likesClass.getUser_likes())) {
                            likesHolder.textViewLikesItemLikesName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                likesHolder.roundedImageViewLikesItemLikesImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) likesHolder.roundedImageViewLikesItemLikesImage);
                            }
                        }
                    }
                }
            }
        });
        new SimpleDateFormat("d MMMM yyyy, hh:mm a").format(new Date(likesClass.getUser_liked().toString()));
        likesHolder.RelativeTimeLikesItemLikesDate.setReferenceTime(likesClass.user_liked.getTime());
    }

    @NonNull
    public LikesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LikesHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.likes_item, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
