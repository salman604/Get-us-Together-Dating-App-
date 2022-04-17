package com.firedating.chat.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class MatchesFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutMatchContent;
    LinearLayout linearLayoutMatchEmpty;
    private MatchesFirestore matchesFirestore;
    private RecyclerView recyclerViewMatchesView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.matches_fragment, viewGroup, false);
        this.recyclerViewMatchesView = (RecyclerView) inflate.findViewById(R.id.recyclerViewMatchesView);
        MatchesRecyclerView();
        this.linearLayoutMatchContent = (LinearLayout) inflate.findViewById(R.id.linearLayoutMatchContent);
        this.linearLayoutMatchContent.setVisibility(View.VISIBLE);
        this.linearLayoutMatchEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutMatchEmpty);
        this.linearLayoutMatchEmpty.setVisibility(View.GONE);
        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("matches").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot == null) {
                    return;
                }
                if (querySnapshot.size() == 0) {
                    MatchesFragment.this.linearLayoutMatchContent.setVisibility(View.GONE);
                    MatchesFragment.this.linearLayoutMatchEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                MatchesFragment.this.linearLayoutMatchContent.setVisibility(View.VISIBLE);
                MatchesFragment.this.linearLayoutMatchEmpty.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void MatchesRecyclerView() {
        this.matchesFirestore = new MatchesFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("matches").orderBy("user_matched", Query.Direction.DESCENDING), MatchesClass.class).build());
        this.recyclerViewMatchesView.setHasFixedSize(true);
        this.recyclerViewMatchesView.setLayoutManager(new GridLayoutManager(getContext(),2));
        this.recyclerViewMatchesView.setAdapter(this.matchesFirestore);
        this.matchesFirestore.setOnItemClickListener(new MatchesFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                MatchesClass matchesClass = (MatchesClass) documentSnapshot.toObject(MatchesClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(MatchesFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", matchesClass.getUser_matches());
                MatchesFragment.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.matchesFirestore.startListening();
    }
}
