package com.firedating.chat.Likes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LikesFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private LikesFirestore likesFirestore;
    LinearLayout linearLayoutLikesContent;
    LinearLayout linearLayoutLikesEmpty;
    private RecyclerView recyclerViewLikesView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.likes_fragment, viewGroup, false);
        this.recyclerViewLikesView = (RecyclerView) inflate.findViewById(R.id.recyclerViewLikesView);
        LikesRecyclerView();
        this.linearLayoutLikesContent = (LinearLayout) inflate.findViewById(R.id.linearLayoutLikesContent);
        this.linearLayoutLikesContent.setVisibility(View.VISIBLE);
        this.linearLayoutLikesEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutLikesEmpty);
        this.linearLayoutLikesEmpty.setVisibility(View.GONE);

        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot == null) {
                    return;
                }
                if (querySnapshot.size() == 0) {
                    LikesFragment.this.linearLayoutLikesContent.setVisibility(View.GONE);
                    LikesFragment.this.linearLayoutLikesEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                LikesFragment.this.linearLayoutLikesContent.setVisibility(View.VISIBLE);
                LikesFragment.this.linearLayoutLikesEmpty.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void LikesRecyclerView() {
        this.likesFirestore = new LikesFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("likes").orderBy("user_liked", Query.Direction.DESCENDING), LikesClass.class).build());
        this.recyclerViewLikesView.setHasFixedSize(true);
        this.recyclerViewLikesView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewLikesView.setAdapter(this.likesFirestore);
        this.likesFirestore.setOnItemClickListener(new LikesFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                LikesClass likesClass = (LikesClass) documentSnapshot.toObject(LikesClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(LikesFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", likesClass.getUser_likes());
                intent.putExtra("like_back", 1);
                LikesFragment.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.likesFirestore.startListening();
    }
}
