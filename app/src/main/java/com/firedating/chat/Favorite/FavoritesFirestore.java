package com.firedating.chat.Favorite;

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

public class FavoritesFirestore extends FirestoreRecyclerAdapter<FavoritesClass, FavoritesFirestore.FavoritesHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;
    long milliseconds;

    class FavoritesHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView RelativeTimeFavoritesItemFavoritesDate;
        RoundedImageView roundedImageViewFavoritesItemFavoritesImage;
        TextView textViewFavoritesItemFavoritesName;
        TextView textViewFavoritesItemFavoritesUnread;

        public FavoritesHolder(@NonNull View view) {
            super(view);
            this.textViewFavoritesItemFavoritesName = (TextView) view.findViewById(R.id.textViewFavoritesItemFavoritesName);
            this.textViewFavoritesItemFavoritesUnread = (TextView) view.findViewById(R.id.textViewFavoritesItemFavoritesUnread);
            this.RelativeTimeFavoritesItemFavoritesDate = (RelativeTimeTextView) view.findViewById(R.id.RelativeTimeFavoritesItemFavoritesDate);
            this.roundedImageViewFavoritesItemFavoritesImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewFavoritesItemFavoritesImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = FavoritesHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && FavoritesFirestore.this.listener != null) {
                        FavoritesFirestore.this.listener.onItemClick((DocumentSnapshot) FavoritesFirestore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public FavoritesFirestore(@NonNull FirestoreRecyclerOptions<FavoritesClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final FavoritesHolder favoritesHolder, int i, @NonNull final FavoritesClass favoritesClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(favoritesClass.getUser_favorite())) {
                            favoritesHolder.textViewFavoritesItemFavoritesName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                favoritesHolder.roundedImageViewFavoritesItemFavoritesImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) favoritesHolder.roundedImageViewFavoritesItemFavoritesImage);
                            }
                        }
                    }
                }
            }
        });
        favoritesHolder.RelativeTimeFavoritesItemFavoritesDate.setReferenceTime(favoritesClass.getUser_favorited().getTime());
    }

    @NonNull
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FavoritesHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorites_item, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
