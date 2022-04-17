package com.firedating.chat.Users;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.Profile.ProfileClass;
import com.firedating.chat.R;
import com.firedating.chat.Start.RegisterActivity;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    ArrayList<ProfileClass> arrayProfileClasses;
    Context context;
    String currentUser;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageViewUsersItemUsersOffline;
        CircleImageView circleImageViewUsersItemUsersOnline;
        Context context;
        ImageView imageViewUsersItemUsersImage;
        ArrayList<ProfileClass> intentUserClass = new ArrayList<>();
        RoundedImageView roundedImageViewUsersItemUserImage;
        TextView textViewUsersItemUserName;
        RelativeTimeTextView textViewUsersItemUsersAbout;
        TextView textViewUsersItemUsersAge;
        TextView textViewUsersItemUsersCity;
        TextView textViewUsersItemUsersDistance;
        TextView textViewUsersItemUsersGender;

        public ViewHolder(@NonNull View view, Context context2, ArrayList<ProfileClass> arrayList) {
            super(view);
            this.intentUserClass = arrayList;
            this.context = context2;
            view.setOnClickListener(this);
            this.textViewUsersItemUserName = (TextView) view.findViewById(R.id.textViewUsersItemUsersName);
            this.textViewUsersItemUsersCity = (TextView) view.findViewById(R.id.textViewUsersItemUsersCity);
            this.textViewUsersItemUsersDistance = (TextView) view.findViewById(R.id.textViewUsersItemUsersDistance);
            this.textViewUsersItemUsersGender = (TextView) view.findViewById(R.id.textViewUsersItemUsersGender);
            this.textViewUsersItemUsersAge = (TextView) view.findViewById(R.id.textViewUsersItemUsersAge);
            this.textViewUsersItemUsersAbout = (RelativeTimeTextView) view.findViewById(R.id.textViewUsersItemUsersAbout);
            this.imageViewUsersItemUsersImage = (ImageView) view.findViewById(R.id.imageViewUsersItemUsersImage);
            this.circleImageViewUsersItemUsersOnline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            this.circleImageViewUsersItemUsersOffline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOffline);
        }

        public void onClick(View view) {
            ProfileClass profileClass = (ProfileClass) this.intentUserClass.get(getAdapterPosition());
            Intent intent = new Intent(this.context, ProfileActivity.class);
            intent.putExtra("user_uid", profileClass.getUser_uid());
            this.context.startActivity(intent);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public UsersAdapter(ArrayList<ProfileClass> arrayList, Context context2) {
        this.arrayProfileClasses = arrayList;
        this.context = context2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_item, viewGroup, false);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.currentUser = this.firebaseUser.getUid();
        return new ViewHolder(inflate, this.context, this.arrayProfileClasses);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProfileClass profileClass = (ProfileClass) this.arrayProfileClasses.get(i);
        viewHolder.textViewUsersItemUserName.setText(profileClass.getUser_name().split(" ")[0]);
        viewHolder.textViewUsersItemUsersCity.setText(profileClass.getUser_city());
        String str = "no";
        if (profileClass.getShare_location() == null || !profileClass.getShare_location().equals(str)) {
            viewHolder.textViewUsersItemUsersCity.setText(profileClass.getUser_city());
        } else {
            viewHolder.textViewUsersItemUsersCity.setVisibility(View.VISIBLE);
            viewHolder.textViewUsersItemUsersDistance.setVisibility(View.GONE);
        }
        if (profileClass.getShare_birthage() == null || !profileClass.getShare_birthage().equals(str)) {
            viewHolder.textViewUsersItemUsersAge.setText(profileClass.getUser_birthage());
        } else {
            viewHolder.textViewUsersItemUsersAge.setVisibility(View.GONE);
        }
        String user_state = profileClass.getUser_status();
        if(user_state.equals("offline"))
        {
            viewHolder.textViewUsersItemUsersDistance.setVisibility(View.VISIBLE);
            viewHolder.textViewUsersItemUsersCity.setText(profileClass.getUser_city());
            viewHolder.textViewUsersItemUsersAbout.setReferenceTime(profileClass.getUser_online().getTime());
        }
        else
        {
            viewHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.VISIBLE);
            viewHolder.circleImageViewUsersItemUsersOffline.setVisibility(View.GONE);
            viewHolder.textViewUsersItemUsersAbout.setText("Online");
        }
        if (profileClass.getUser_gender().equals("male")) {
            viewHolder.textViewUsersItemUsersGender.setText("M");
        } else {
            viewHolder.textViewUsersItemUsersGender.setText("F");
        }
        if (profileClass.getUser_image().equals(RegisterActivity.MEDIA_IMAGE)) {
            viewHolder.imageViewUsersItemUsersImage.setImageResource(R.drawable.profile_image);
        } else {
            Picasso.get().load(profileClass.getUser_image()).into(viewHolder.imageViewUsersItemUsersImage);
        }
    }

    public int getItemCount() {
        return this.arrayProfileClasses.size();
    }
}
