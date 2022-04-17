package com.firedating.chat.Feeds;

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

import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    ArrayList<FeedsClass> arrayFeedsClasses;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String user_keyz;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        CircleImageView imageViewFeedsItemFeedsCover,imageViewFeedsCurrentUser;
        ImageView imageViewFeedsItemFeedsLikes;
        CircleImageView imageViewFeedsItemFeedsThumb;
        ArrayList<FeedsClass> intentFeedsClasses = new ArrayList<>();
        RelativeTimeTextView relativeTimeFeedsItemFeedsDate;
        TextView textViewFeedsItemFeedsLikes;
        TextView textViewFeedsItemFeedsUser,viewMore;

        public ViewHolder(@NonNull View view, Context context2, ArrayList<FeedsClass> arrayList) {
            super(view);
            this.intentFeedsClasses = arrayList;
            this.context = context2;
            view.setOnClickListener(this);
            this.textViewFeedsItemFeedsUser = (TextView) view.findViewById(R.id.textViewFeedsItemFeedsUser);
            this.textViewFeedsItemFeedsLikes = (TextView) view.findViewById(R.id.textViewFeedsItemFeedsLikes);
            this.imageViewFeedsItemFeedsThumb = (CircleImageView) view.findViewById(R.id.imageViewFeedsItemFeedsThumb);
            this.imageViewFeedsItemFeedsCover = (CircleImageView) view.findViewById(R.id.imageViewFeedsItemFeedsCover);
            this.imageViewFeedsItemFeedsLikes = (ImageView) view.findViewById(R.id.imageViewFeedsItemFeedsLikes);
            this.relativeTimeFeedsItemFeedsDate = (RelativeTimeTextView) view.findViewById(R.id.relativeTimeFeedsItemFeedsDate);
            this.imageViewFeedsCurrentUser = (CircleImageView)view.findViewById(R.id.imageViewFeedsCurrentUser);
            this.viewMore = (TextView) view.findViewById(R.id.view_more);
        }

        public void onClick(View view) {
            FeedsClass feedsClass = (FeedsClass) this.intentFeedsClasses.get(getAdapterPosition());
            Intent intent = new Intent(this.context, ProfileActivity.class);
            intent.putExtra("user_uid", feedsClass.getFeed_user());
            this.context.startActivity(intent);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public FeedsAdapter(ArrayList<FeedsClass> arrayList, Context context2) {
        this.arrayFeedsClasses = arrayList;
        this.context = context2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feeds_item, viewGroup, false);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(inflate, this.context, this.arrayFeedsClasses);
    }

    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final FeedsClass feedsClass = (FeedsClass) this.arrayFeedsClasses.get(i);
        String uid = this.firebaseUser.getUid();
        this.firebaseFirestore.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    Picasso.get().load(documentSnapshot.getString("user_thumb")).into((ImageView) viewHolder.imageViewFeedsCurrentUser);
                }
            }
        });
        this.firebaseFirestore.collection("users").document(feedsClass.getFeed_user()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    Picasso.get().load(documentSnapshot.getString("user_thumb")).into((ImageView) viewHolder.imageViewFeedsItemFeedsThumb);
                    viewHolder.textViewFeedsItemFeedsUser.setText(documentSnapshot.getString("user_name"));
                }
            }
        });
        Picasso.get().load(feedsClass.getFeed_cover()).into(viewHolder.imageViewFeedsItemFeedsCover);
        viewHolder.textViewFeedsItemFeedsLikes.setText(String.valueOf(feedsClass.getFeed_like()));
        viewHolder.relativeTimeFeedsItemFeedsDate.setReferenceTime(feedsClass.feed_date.getTime());
        this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).collection("likes").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    Picasso.get().load((int) R.drawable.tab_feed_like_on).into(viewHolder.imageViewFeedsItemFeedsLikes);
                } else {
                    Picasso.get().load((int) R.drawable.tab_feed_like_off).into(viewHolder.imageViewFeedsItemFeedsLikes);
                }
            }
        });
        viewHolder.imageViewFeedsItemFeedsLikes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FeedsAdapter feedsAdapter = FeedsAdapter.this;
//                ViewHolder viewHolder = viewHolder;
                feedsAdapter.FeedLike(viewHolder, viewHolder.imageViewFeedsItemFeedsLikes, viewHolder.textViewFeedsItemFeedsLikes, feedsClass);
            }
        });
        viewHolder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FeedDetailsActivity.class);
                intent.putExtra("user_uid", feedsClass.getFeed_user());
                context.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: private */
    public void FeedLike(final ViewHolder viewHolder, ImageView imageView, TextView textView, final FeedsClass feedsClass) {
        final String uid = this.firebaseUser.getUid();
        this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).collection("likes").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "feeds";
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    FeedsAdapter.this.firebaseFirestore.collection(str).document(feedsClass.getFeed_uid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                String str = "feed_like";
                                final String valueOf = String.valueOf(Long.valueOf(((DocumentSnapshot) task.getResult()).getString(str)).longValue() - 1);
                                HashMap hashMap = new HashMap();
                                hashMap.put(str, valueOf);
                                FeedsAdapter.this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FeedsAdapter.this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).collection("likes").document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        viewHolder.textViewFeedsItemFeedsLikes.setText(valueOf);
                                                        Picasso.get().load((int) R.drawable.tab_feed_like_off).into(viewHolder.imageViewFeedsItemFeedsLikes);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    FeedsAdapter.this.firebaseFirestore.collection(str).document(feedsClass.getFeed_uid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                String str = "feed_like";
                                final String valueOf = String.valueOf(Long.valueOf(((DocumentSnapshot) task.getResult()).getString(str)).longValue() + 1);
                                HashMap hashMap = new HashMap();
                                hashMap.put(str, valueOf);
                                FeedsAdapter.this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            HashMap hashMap = new HashMap();
                                            hashMap.put("feed_like_user", uid);
                                            hashMap.put("feed_like_date", Timestamp.now());
                                            FeedsAdapter.this.firebaseFirestore.collection("feeds").document(feedsClass.getFeed_uid()).collection("likes").document(uid).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        viewHolder.textViewFeedsItemFeedsLikes.setText(valueOf);
                                                        Picasso.get().load((int) R.drawable.tab_feed_like_on).into(viewHolder.imageViewFeedsItemFeedsLikes);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public int getItemCount() {
        return this.arrayFeedsClasses.size();
    }
}
