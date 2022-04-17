package com.firedating.chat.Chats;


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
import com.firedating.chat.Matches.MatchesClass;
import com.firedating.chat.Matches.MatchesFirestore;
import com.firedating.chat.Matches.MatchesFragment;
import com.firedating.chat.Message.MessageActivity;
import com.firedating.chat.Message.MessageClass;
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

public class ChatsFragment extends Fragment {

    private ChatsFirestore chatsFirestore;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutChatsContent;
    LinearLayout linearLayoutChatsEmpty;
    private NewMatchesFireStore newmatchesFirestore;
    private RecyclerView recyclerViewChatsView,recyclerViewChatsNewMatches;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.chats_fragment, viewGroup, false);
        this.recyclerViewChatsView = (RecyclerView) inflate.findViewById(R.id.recyclerViewChatsView);
        this.recyclerViewChatsNewMatches = (RecyclerView)inflate.findViewById(R.id.recyclerViewChatsNewMatches);
        ChatRecyclerView();
        MatchesRecyclerView();

        this.linearLayoutChatsContent = (LinearLayout) inflate.findViewById(R.id.linearLayoutChatsContent);
        this.linearLayoutChatsContent.setVisibility(View.VISIBLE);
        this.linearLayoutChatsEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutChatsEmpty);
        this.linearLayoutChatsEmpty.setVisibility(View.GONE);
        this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshot, @javax.annotation.Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot == null) {
                    return;
                }
                if (querySnapshot.size() == 0) {
                    ChatsFragment.this.linearLayoutChatsContent.setVisibility(View.GONE);
                    ChatsFragment.this.linearLayoutChatsEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                ChatsFragment.this.linearLayoutChatsContent.setVisibility(View.VISIBLE);
                ChatsFragment.this.linearLayoutChatsEmpty.setVisibility(View.GONE);
            }
        });
        return inflate;
    }

    private void ChatRecyclerView() {
        this.chatsFirestore = new ChatsFirestore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("chats").orderBy("user_datesent", Query.Direction.DESCENDING), MessageClass.class).build());
        this.recyclerViewChatsView.setHasFixedSize(true);
        this.recyclerViewChatsView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewChatsView.setAdapter(this.chatsFirestore);
        this.chatsFirestore.setOnItemClickListener(new ChatsFirestore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                MessageClass messageClass = (MessageClass) documentSnapshot.toObject(MessageClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(ChatsFragment.this.getContext(), MessageActivity.class);
                intent.putExtra("user_uid", messageClass.getUser_receiver());
                ChatsFragment.this.startActivity(intent);
            }
        });
    }

    private void MatchesRecyclerView() {
        this.newmatchesFirestore = new NewMatchesFireStore(new FirestoreRecyclerOptions.Builder().setQuery(this.firebaseFirestore.collection("users").document(this.firebaseUser.getUid()).collection("matches").orderBy("user_matched", Query.Direction.DESCENDING), MatchesClass.class).build());
        this.recyclerViewChatsNewMatches.setHasFixedSize(true);
        this.recyclerViewChatsNewMatches.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        this.recyclerViewChatsNewMatches.setAdapter(this.newmatchesFirestore);
        this.newmatchesFirestore.setOnItemClickListener(new NewMatchesFireStore.OnItemClickListener() {
            public void onItemClick(DocumentSnapshot documentSnapshot, int i) {
                MatchesClass matchesClass = (MatchesClass) documentSnapshot.toObject(MatchesClass.class);
                documentSnapshot.getId();
                documentSnapshot.getReference().getPath();
                Intent intent = new Intent(ChatsFragment.this.getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", matchesClass.getUser_matches());
                ChatsFragment.this.startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.chatsFirestore.startListening();
        this.newmatchesFirestore.startListening();
    }

    public void onResume() {
        super.onResume();
    }
}
