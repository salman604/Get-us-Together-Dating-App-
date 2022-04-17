package com.firedating.chat.Favorite;

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

public class FavoritesFragment extends Fragment {

    private FavoritesFirestore favoritesFirestore;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutFavoritesContent;
    LinearLayout linearLayoutFavoritesEmpty;
    private RecyclerView recyclerViewFavoritesView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.favorites_fragment, viewGroup, false);
        this.recyclerViewFavoritesView = (RecyclerView) inflate.findViewById(R.id.recyclerViewFavoritesView);
        FavoritesRecyclerView();
        this.linearLayoutFavoritesContent = (LinearLayout) inflate.findViewById(R.id.linearLayoutFavoritesContent);
        this.linearLayoutFavoritesContent.setVisibility(View.VISIBLE);
        this.linearLayoutFavoritesEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutFavoritesEmpty);
        this.linearLayoutFavoritesEmpty.setVisibility(View.GONE);

        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("favors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot == null) {
                    return;
                }
                if (querySnapshot.size() == 0) {
                    FavoritesFragment.this.linearLayoutFavoritesContent.setVisibility(View.GONE);
                    FavoritesFragment.this.linearLayoutFavoritesEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                FavoritesFragment.this.linearLayoutFavoritesContent.setVisibility(View.VISIBLE);
                FavoritesFragment.this.linearLayoutFavoritesEmpty.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void FavoritesRecyclerView() {
        this.favoritesFirestore = new FavoritesFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("favors").orderBy("user_favorited", Query.Direction.DESCENDING), FavoritesClass.class).build());
        this.recyclerViewFavoritesView.setHasFixedSize(true);
        this.recyclerViewFavoritesView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewFavoritesView.setAdapter(this.favoritesFirestore);
        this.favoritesFirestore.setOnItemClickListener(new FavoritesFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                FavoritesClass favoritesClass = (FavoritesClass) documentSnapshot.toObject(FavoritesClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(FavoritesFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", favoritesClass.getUser_favorite());
                FavoritesFragment.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.favoritesFirestore.startListening();
    }
}
