package com.firedating.chat.Feeds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firedating.chat.Matches.MatchesClass;
import com.firedating.chat.R;
import com.firedating.chat.Start.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedsFragment extends Fragment {

    /* access modifiers changed from: private */
    public ArrayList<FeedsClass> arrayFeedsClasses;
    private ArrayList<FeedsClass> arrayUserClass;
    /* access modifiers changed from: private */
    public List<String> arrayUserFeedsCheck;
    /* access modifiers changed from: private */
    public List<String> arrayUserFollows;
    String currentUser;
    /* access modifiers changed from: private */
    public FeedsAdapter feedsAdapter;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    LinearLayout linearLayoutFeedsEmpty;
    SharedPreferences prefs;
    ProgressBar progressBarFeedsView;
    /* access modifiers changed from: private */
    public RecyclerView recyclerViewUserView;
    RelativeLayout relativeLayoutFeedsContent;
    String stringLookageMax;
    String stringLookageMin;
    String stringShowLooking;
    String stringShowLookingIn;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.feeds_fragment, viewGroup, false);
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            this.currentUser = firebaseUser2.getUid();
        }
        else
        {
            startActivity(new Intent(getContext(), StartActivity.class));
            getActivity().finish();
        }
        this.prefs = getActivity().getSharedPreferences("pref", 0);
        this.arrayUserClass = new ArrayList<>();
        this.arrayFeedsClasses = new ArrayList<>();
        this.progressBarFeedsView = (ProgressBar) inflate.findViewById(R.id.progressBarFeedsView);
        this.recyclerViewUserView = (RecyclerView) inflate.findViewById(R.id.recyclerViewFeedsView);
        this.relativeLayoutFeedsContent = (RelativeLayout) inflate.findViewById(R.id.linearLayoutFeedsContent);
        this.relativeLayoutFeedsContent.setVisibility(View.VISIBLE);
        this.linearLayoutFeedsEmpty = (LinearLayout) inflate.findViewById(R.id.linearLayoutFeedsEmpty);
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.feedswipeRefreshLayout);
        this.linearLayoutFeedsEmpty.setVisibility(View.GONE);
        this.recyclerViewUserView.setHasFixedSize(true);
        this.recyclerViewUserView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.arrayUserFeedsCheck = new ArrayList();
        this.arrayUserFollows = new ArrayList();
        this.arrayUserFollows.add("demoUserWhenZero");

        UserRecyclerViewNew();
        RefreshLayout();


        return inflate;
    }

    private void RefreshLayout() {
        int parseColor = Color.parseColor("#880e4f");
        this.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#ffffff"));
        this.swipeRefreshLayout.setColorSchemeColors(parseColor);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                FeedsFragment.this.swipeRefreshLayout.post(new Runnable() {
                    public void run() {
                        FeedsFragment.this.swipeRefreshLayout.setRefreshing(true);
                        FeedsFragment.this.arrayFeedsClasses.clear();
                        FeedsFragment.this.arrayUserFollows.clear();
                        FeedsFragment.this.arrayUserFeedsCheck.clear();
                        FeedsFragment.this.UserRecyclerViewNew();
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void UserRecyclerViewNew() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentChange document : ((QuerySnapshot) task.getResult()).getDocumentChanges()) {
                        FeedsFragment.this.arrayUserFollows.add(((MatchesClass) document.getDocument().toObject(MatchesClass.class)).getUser_matches());
                    }
                }
            }
        });
        this.firebaseFirestore.collection("feeds").orderBy("feed_date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentChange document : ((QuerySnapshot) task.getResult()).getDocumentChanges()) {
                    System.out.println("debug ==" +arrayUserFollows.size());
                    FeedsClass feedsClass = (FeedsClass) document.getDocument().toObject(FeedsClass.class);
                   if (FeedsFragment.this.arrayUserFollows.contains(feedsClass.getFeed_user()) && feedsClass.getShow_feeds() != null && feedsClass.getShow_feeds().equals("yes")) {
                               FeedsFragment.this.arrayFeedsClasses.add(feedsClass);
                   }
                    FeedsFragment feedsFragment = FeedsFragment.this;
                    feedsFragment.feedsAdapter = new FeedsAdapter(feedsFragment.arrayFeedsClasses, FeedsFragment.this.getActivity());
                    FeedsFragment.this.recyclerViewUserView.setAdapter(FeedsFragment.this.feedsAdapter);
                    FeedsFragment.this.swipeRefreshLayout.setRefreshing(false);
                    FeedsFragment.this.progressBarFeedsView.setVisibility(View.GONE);
                }
                if (FeedsFragment.this.arrayFeedsClasses.size() == 0) {
                    FeedsFragment.this.progressBarFeedsView.setVisibility(View.GONE);
                    FeedsFragment.this.relativeLayoutFeedsContent.setVisibility(View.GONE);
                    FeedsFragment.this.linearLayoutFeedsEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                FeedsFragment.this.progressBarFeedsView.setVisibility(View.VISIBLE);
                FeedsFragment.this.relativeLayoutFeedsContent.setVisibility(View.VISIBLE);
                FeedsFragment.this.linearLayoutFeedsEmpty.setVisibility(View.GONE);
            }
        });
    }

    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }
}
