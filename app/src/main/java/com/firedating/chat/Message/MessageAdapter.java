package com.firedating.chat.Message;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firedating.chat.Chats.ChatsClass;
import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    public static final String FIREBASE_STORAGE_PART_OF_DOWNLOAD_LINK = "https://firebasestorage.googleapis.com";
    ArrayList<ChatsClass> arrayUsersClass;
    Context context;
    FirebaseFirestore  firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        ImageView imageViewChatSeen;
        ImageView imageViewChatSent,image_iv;
        ArrayList<ChatsClass> intentUserClass = new ArrayList<>();
        TextView textViewChatMessage,textViewChatSentDate;
        CircleImageView recieverProfileImage;

        public ViewHolder(@NonNull View view, Context context2, ArrayList<ChatsClass> arrayList) {
            super(view);
            this.intentUserClass = arrayList;
            this.context = context2;
            view.setOnClickListener(this);
            this.textViewChatMessage = (TextView) view.findViewById(R.id.textViewChatMessage);
            this.imageViewChatSent = (ImageView) view.findViewById(R.id.imageViewChatSent);
            this.imageViewChatSeen = (ImageView) view.findViewById(R.id.imageViewChatSeen);
            this.textViewChatSentDate = (TextView)view.findViewById(R.id.textViewChatSentDate);
            this.image_iv = (ImageView) view.findViewById(R.id.image_iv);
            this.recieverProfileImage = (CircleImageView) view.findViewById(R.id.recieverProfileImage);
        }

        public void onClick(View view) {
            String str = "user_message";
            new Intent(this.context, ProfileActivity.class).putExtra(str, ((ChatsClass) this.intentUserClass.get(getAdapterPosition())).getChat_message());
        }
    }

    public MessageAdapter(ArrayList<ChatsClass> arrayList, Context context2) {
        this.arrayUsersClass = arrayList;
        this.context = context2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_right, viewGroup, false), this.context, this.arrayUsersClass);
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item_left, viewGroup, false), this.context, this.arrayUsersClass);
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ChatsClass chatsClass = (ChatsClass) this.arrayUsersClass.get(i);
//        viewHolder.textViewChatMessage.setText(chatsClass.getChat_message());
        if (!chatsClass.getChat_message().contains(FIREBASE_STORAGE_PART_OF_DOWNLOAD_LINK)) {
            viewHolder.textViewChatMessage.setText(chatsClass.getChat_message());
            viewHolder.image_iv.setVisibility(View.GONE);
        } else {
            viewHolder.image_iv.setVisibility(View.VISIBLE);
            Picasso.get().load(chatsClass.getChat_message()).into(viewHolder.image_iv);
            viewHolder.textViewChatMessage.setVisibility(View.GONE);
        }
        long timestamp = chatsClass.getChat_datesent().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm:ss a");
        String string13 = dateFormat.format(timestamp);
        viewHolder.textViewChatSentDate.setText(string13);
        if (chatsClass.getChat_seenchat().equals("no")) {
            if (chatsClass.getChat_sender().equals(this.firebaseUser.getUid())) {
                viewHolder.imageViewChatSent.setVisibility(View.VISIBLE);
                viewHolder.imageViewChatSeen.setVisibility(View.GONE);
            }
        } else if (chatsClass.getChat_sender().equals(this.firebaseUser.getUid())) {
            viewHolder.imageViewChatSeen.setVisibility(View.VISIBLE);
            viewHolder.imageViewChatSent.setVisibility(View.GONE);
        }

        viewHolder.image_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatsClass.getChat_message().contains(FIREBASE_STORAGE_PART_OF_DOWNLOAD_LINK)) {
                    Intent i = new Intent(context, FullScreenImageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Image", chatsClass.getChat_message());
                    context.startActivity(i);
                }
            }
        });


        this.firebaseFirestore.collection("users").document(chatsClass.getChat_sender()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                String recieverPImage = documentSnapshot.getString("user_cover0");

                try{
                    Glide.with(context).load(recieverPImage).placeholder(R.drawable.profile_image).into(viewHolder.recieverProfileImage);
                }
                catch (Exception e)
                {

                }


            }
        });
    }

    public int getItemCount() {
        return this.arrayUsersClass.size();
    }

    public int getItemViewType(int i) {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return ((ChatsClass) this.arrayUsersClass.get(i)).getChat_sender().equals(this.firebaseUser.getUid()) ? 1 : 0;
    }
}
