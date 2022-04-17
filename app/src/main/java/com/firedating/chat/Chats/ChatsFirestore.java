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
import com.firedating.chat.Facebook.AppEventsConstants;
import com.firedating.chat.Message.MessageClass;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.firedating.chat.Start.RegisterActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsFirestore extends FirestoreRecyclerAdapter<MessageClass, ChatsFirestore.ChatHolder> {
    /* access modifiers changed from: private */
    public OnItemClickListener listener;

    public class ChatHolder extends RecyclerView.ViewHolder {
        RelativeTimeTextView RelativeTimeChatsItemChatsDate;
        CircleImageView circleImageViewChatsItemChatsOffline;
        CircleImageView circleImageViewChatsItemChatsOnline;
        RoundedImageView roundedImageViewChatsItemChatsImage;
        TextView textViewChatsItemChatsMessage;
        TextView textViewChatsItemChatsName;
        TextView textViewChatsItemChatsUnread;

        public ChatHolder(@NonNull View view) {
            super(view);
            this.textViewChatsItemChatsName = (TextView) view.findViewById(R.id.textViewChatsItemChatsName);
            this.textViewChatsItemChatsMessage = (TextView) view.findViewById(R.id.textViewChatsItemChatsMessage);
            this.textViewChatsItemChatsUnread = (TextView) view.findViewById(R.id.textViewChatsItemChatsUnread);
            this.RelativeTimeChatsItemChatsDate = (RelativeTimeTextView) view.findViewById(R.id.RelativeTimeChatsItemChatsDate);
            this.circleImageViewChatsItemChatsOnline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            this.roundedImageViewChatsItemChatsImage = (RoundedImageView) view.findViewById(R.id.roundedImageViewChatsItemChatsImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int adapterPosition = ChatHolder.this.getAdapterPosition();
                    if (adapterPosition != -1 && ChatsFirestore.this.listener != null) {
                       ChatsFirestore.this.listener.onItemClick((DocumentSnapshot) getSnapshots().getSnapshot(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int i);
    }

    public ChatsFirestore(@NonNull FirestoreRecyclerOptions<MessageClass> firestoreRecyclerOptions) {
        super(firestoreRecyclerOptions);
    }

    /* access modifiers changed from: protected */
    public void onBindViewHolder(@NonNull final ChatHolder chatHolder, int i, @NonNull final MessageClass messageClass) {
        FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                        ProfileClass profileClass = (ProfileClass) document.getDocument().toObject(ProfileClass.class);
                        if (profileClass.getUser_uid().equals(messageClass.getUser_receiver())) {
                            chatHolder.textViewChatsItemChatsName.setText(profileClass.getUser_name());
                            if (profileClass.getUser_image().equals(RegisterActivity.MEDIA_IMAGE)) {
                                chatHolder.roundedImageViewChatsItemChatsImage.setImageResource(R.drawable.profile_image);
                            } else {
                                Picasso.get().load(profileClass.getUser_image()).into((ImageView) chatHolder.roundedImageViewChatsItemChatsImage);
                            }
                            if (profileClass.getUser_status().equals("online")) {
                                chatHolder.circleImageViewChatsItemChatsOnline.setVisibility(View.VISIBLE);
                            } else {
                                chatHolder.circleImageViewChatsItemChatsOnline.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });
        chatHolder.textViewChatsItemChatsMessage.setText(messageClass.getUser_message());
        if (!messageClass.getUser_unread().equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            chatHolder.textViewChatsItemChatsUnread.setVisibility(View.VISIBLE);
        } else {
            chatHolder.textViewChatsItemChatsUnread.setVisibility(View.GONE);
        }
        chatHolder.textViewChatsItemChatsUnread.setText(messageClass.getUser_unread());
        chatHolder.RelativeTimeChatsItemChatsDate.setReferenceTime(messageClass.getUser_datesent().getTime());
    }

    @NonNull
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChatHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chats_item, viewGroup, false));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
