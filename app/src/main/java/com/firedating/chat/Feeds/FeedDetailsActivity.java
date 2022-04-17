package com.firedating.chat.Feeds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.firedating.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedDetailsActivity extends AppCompatActivity {

    CircleImageView imageViewFeedsItemFeedsCover,imageViewFeedsCurrentUser;
    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView matchTv,recipeName ;
    ImageView recipeOffers;
    ArrayList<String> recipename;
    ArrayList<String> recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        String recieverImage = getIntent().getStringExtra("user_uid");

        this.imageViewFeedsCurrentUser = (CircleImageView) findViewById(R.id.imageViewFeedsCurrentUser);
        this.imageViewFeedsItemFeedsCover = (CircleImageView) findViewById(R.id.imageViewFeedsItemFeedsCover);
        matchTv = (TextView) findViewById(R.id.matchedtv);
        recipeOffers = (ImageView) findViewById(R.id.recipe_offers);
        recipeName = (TextView)findViewById(R.id.recipe_name);

        recipename = new ArrayList<>();
        recipeImage = new ArrayList<>();
        recipeImage.add("https://www.riverlink.net.au/wp-content/uploads/2019/02/50584726_10157138173395039_4916896260021026816_o.jpg");
        recipeImage.add("https://www.indianhealthyrecipes.com/wp-content/uploads/2019/02/chicken-biryani-recipe-500x500.jpg");
        recipeImage.add("https://4.bp.blogspot.com/-7C3B7VPDVKc/XBKGp3-XlcI/AAAAAAAA4VY/wKe7qrJ-ntQlNN5e1fGksH62duvg6f8-gCLcBGAs/s1600/Instant-Suji-idli.jpg");
        recipeImage.add("https://c.ndtvimg.com/2019-08/7m027um_kanda-poha_625x300_14_August_19.jpg");

        recipename.add("Enjoy ! Ice Cream");
        recipename.add("Winner winner chicken dinner");
        recipename.add("Thala ! Enjoy Idli");
        recipename.add("Enjoy Poha");
        setSuggestions();


        setBothImage(recieverImage);
    }

    private void setSuggestions()
    {
        Random random = new Random();
        int randomNumber = random.nextInt(3 - 0) + 0;


        Picasso.get().load(recipeImage.get(randomNumber)).into((ImageView) recipeOffers);
        recipeName.setText(recipename.get(randomNumber));
    }

    private void setBothImage(String recieverImage)
    {
        String uid = this.fUser.getUid();
        this.firebaseFirestore.collection("users").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    Picasso.get().load(documentSnapshot.getString("user_thumb")).into((ImageView) imageViewFeedsCurrentUser);
                }
            }
        });
        this.firebaseFirestore.collection("users").document(recieverImage).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    Picasso.get().load(documentSnapshot.getString("user_thumb")).into((ImageView) imageViewFeedsItemFeedsCover);
                    String recieverName = documentSnapshot.getString("user_name");
                    matchTv.setText("Congratulation ! you and "+ recieverName + " liked each other.");
                }
            }
        });
    }
}
