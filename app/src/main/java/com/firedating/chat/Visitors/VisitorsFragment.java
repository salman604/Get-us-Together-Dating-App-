package com.firedating.chat.Visitors;

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
import com.google.firebase.firestore.core.OrderBy;

public class VisitorsFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutVisitorsContent;
    LinearLayout linearLayoutVisitorsEmpty;
    private RecyclerView recyclerViewVisitsView;
    private VisitorsFirestore visitorsFirestore;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.visitors_fragment, viewGroup, false);
        this.recyclerViewVisitsView = (RecyclerView) inflate.findViewById(R.id.recyclerViewVisitsView);
        VisitsRecyclerView();
        this.linearLayoutVisitorsContent = (LinearLayout) inflate.findViewById(R.id.linearLayoutVisitorsContent);
        this.linearLayoutVisitorsContent.setVisibility(View.VISIBLE);
        this.linearLayoutVisitorsEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutVisitorsEmpty);
        this.linearLayoutVisitorsEmpty.setVisibility(View.GONE);
        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("visits").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot == null) {
                    return;
                }
                if (querySnapshot.size() == 0) {
                    VisitorsFragment.this.linearLayoutVisitorsContent.setVisibility(View.GONE);
                    VisitorsFragment.this.linearLayoutVisitorsEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                VisitorsFragment.this.linearLayoutVisitorsContent.setVisibility(View.VISIBLE);
                VisitorsFragment.this.linearLayoutVisitorsEmpty.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void VisitsRecyclerView() {
        this.visitorsFirestore = new VisitorsFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("visits").orderBy("user_visited", Query.Direction.DESCENDING), VisitorsClass.class).build());
        this.recyclerViewVisitsView.setHasFixedSize(true);
        this.recyclerViewVisitsView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewVisitsView.setAdapter(this.visitorsFirestore);
        this.visitorsFirestore.setOnItemClickListener(new VisitorsFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                VisitorsClass visitorsClass = (VisitorsClass) documentSnapshot.toObject(VisitorsClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(VisitorsFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", visitorsClass.getUser_visitor());
                VisitorsFragment.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.visitorsFirestore.startListening();
    }
}
