package com.firedating.chat.Loves;

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

public class LovesFirestore extends FirestoreRecyclerAdapter<LovesClass, LovesFirestore.LovesHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;

    class LovesHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView RelativeTimeLovesItemLovesDate;
        RoundedImageView roundedImageViewLovesItemLovesImage;
        TextView textViewLovesItemLovesMessage;
        TextView textViewLovesItemLovesName;
        TextView textViewLovesItemLovesUnread;

        public LovesHolder(@NonNull View view) {
            super(view);
            this.textViewLovesItemLovesName = (TextView) view.findViewById(R.id.textViewLovesItemLovesName);
            this.textViewLovesItemLovesMessage = (TextView) view.findViewById(R.id.textViewLovesItemLovesMessage);
            this.textViewLovesItemLovesUnread = (TextView) view.findViewById(R.id.textViewLovesItemLovesUnread);
            this.RelativeTimeLovesItemLovesDate = (RelativeTimeTextView) view.findViewById(R.id.RelativeTimeLovesItemLovesDate);
            this.roundedImageViewLovesItemLovesImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewLovesItemLovesImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = LovesHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && LovesFirestore.this.listener != null) {
                        LovesFirestore.this.listener.onItemClick((DocumentSnapshot) LovesFirestore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public LovesFirestore(@NonNull FirestoreRecyclerOptions<LovesClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final LovesHolder lovesHolder, int i, @NonNull final LovesClass lovesClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(lovesClass.getUser_loves())) {
                            lovesHolder.textViewLovesItemLovesName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                lovesHolder.roundedImageViewLovesItemLovesImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) lovesHolder.roundedImageViewLovesItemLovesImage);
                            }
                        }
                    }
                }
            }
        });
        new SimpleDateFormat("d MMMM yyyy, hh:mm a").format(new Date(lovesClass.getUser_loved().toString()));
        lovesHolder.RelativeTimeLovesItemLovesDate.setReferenceTime(lovesClass.user_loved.getTime());
    }

    @NonNull
    public LovesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LovesHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loves_item, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
