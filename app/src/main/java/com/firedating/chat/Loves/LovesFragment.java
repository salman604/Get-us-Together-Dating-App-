package com.firedating.chat.Loves;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LovesFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private LovesFirestore lovesFirestore;
    private RecyclerView recyclerViewLovesView;
    String user_keyz;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.loves_fragment, viewGroup, false);
        this.recyclerViewLovesView = (RecyclerView) inflate.findViewById(R.id.recyclerViewLovesView);
        DateRecyclerView();
        return inflate;
    }

    private void DateRecyclerView() {
        this.lovesFirestore = new LovesFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("loves").orderBy("user_loved", Query.Direction.DESCENDING), LovesClass.class).build());
        this.recyclerViewLovesView.setHasFixedSize(true);
        this.recyclerViewLovesView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewLovesView.setAdapter(this.lovesFirestore);
        this.lovesFirestore.setOnItemClickListener(new LovesFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                LovesClass lovesClass = (LovesClass) documentSnapshot.toObject(LovesClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(LovesFragment.this.getContext(), ProfileActivity.class);
                lovesClass.getUser_loves();
                intent.putExtra("user_uid", lovesClass.getUser_loves());
                LovesFragment.this.startActivity(intent);
            }
        });
    }

    private String UserProfile(String str, final String str2) {
        this.firebaseFirestore.collection("users").document(str).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = (DocumentSnapshot) task.getResult();
                    LovesFragment.this.user_keyz = documentSnapshot.getString(str2);
                }
            }
        });
        return this.user_keyz;
    }

    public void onStart() {
        super.onStart();
        this.lovesFirestore.startListening();
    }
}
