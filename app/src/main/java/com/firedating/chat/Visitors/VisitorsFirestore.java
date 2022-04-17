package com.firedating.chat.Visitors;

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

public class VisitorsFirestore extends FirestoreRecyclerAdapter<VisitorsClass, VisitorsFirestore.VisitsHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;
    long milliseconds;

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    class VisitsHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView RelativeTimeVisitsItemVisitsDate;
        RoundedImageView roundedImageViewVisitsItemVisitsImage;
        TextView textViewVisitsItemVisitsName;
        TextView textViewVisitsItemVisitsUnread;

        public VisitsHolder(@NonNull View view) {
            super(view);
            this.textViewVisitsItemVisitsName = (TextView) view.findViewById(R.id.textViewVisitsItemVisitsName);
            this.textViewVisitsItemVisitsUnread = (TextView) view.findViewById(R.id.textViewVisitsItemVisitsUnread);
            this.RelativeTimeVisitsItemVisitsDate = (RelativeTimeTextView) view.findViewById(R.id.RelativeTimeVisitsItemVisitsDate);
            this.roundedImageViewVisitsItemVisitsImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewVisitsItemVisitsImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = VisitsHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && VisitorsFirestore.this.listener != null) {
                        VisitorsFirestore.this.listener.onItemClick((DocumentSnapshot) VisitorsFirestore.this.getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public VisitorsFirestore(@NonNull FirestoreRecyclerOptions<VisitorsClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final VisitsHolder visitsHolder, int i, @NonNull final VisitorsClass visitorsClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(visitorsClass.getUser_visitor())) {
                            visitsHolder.textViewVisitsItemVisitsName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_thumb().equals("thumb")) {
                                visitsHolder.roundedImageViewVisitsItemVisitsImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_thumb()).into((ImageView) visitsHolder.roundedImageViewVisitsItemVisitsImage);
                            }
                        }
                    }
                }
            }
        });
        visitsHolder.RelativeTimeVisitsItemVisitsDate.setReferenceTime(visitorsClass.user_visited.getTime());
    }

    @NonNull
    public VisitsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VisitsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.visitors_item, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
