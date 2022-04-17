package com.firedating.chat.Swipe;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.ViewHolder> {
    ArrayList<ProfileClass> arrayProfileClasses;
    Context context;
    String currentLat,currentLong,currentuserName;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageViewUsersItemUsersOffline;
        CircleImageView circleImageViewUsersItemUsersOnline;
        Context context;
        ImageView imageViewUsersItemUsersImage;
        ArrayList<ProfileClass> intentUserClass = new ArrayList<>();
        TextView textViewUsersItemUserName;
        TextView textViewUsersItemUsersAge;
        TextView textViewUsersItemUsersCity;
        TextView textViewUsersItemUsersDistance;
        TextView textViewUserLastSeen,compat_percentage;

        public ViewHolder(@NonNull View view, Context context2, ArrayList<ProfileClass> arrayList) {
            super(view);
            this.intentUserClass = arrayList;
            this.context = context2;
            view.setOnClickListener(this);
            this.textViewUsersItemUserName = (TextView) view.findViewById(R.id.textViewUsersItemUsersName);
            this.textViewUsersItemUsersCity = (TextView) view.findViewById(R.id.textViewUsersItemUsersCity);
            this.textViewUsersItemUsersDistance = (TextView) view.findViewById(R.id.textViewUsersItemUsersDistance);
            this.textViewUsersItemUsersAge = (TextView) view.findViewById(R.id.textViewUsersItemUsersAge);
            this.imageViewUsersItemUsersImage = (ImageView) view.findViewById(R.id.imageViewUsersItemUsersImage);
            this.circleImageViewUsersItemUsersOnline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            this.circleImageViewUsersItemUsersOffline = (CircleImageView) view.findViewById(R.id.circleImageViewUsersItemUsersOffline);
            this.textViewUserLastSeen = (TextView)view.findViewById(R.id.textViewLastSeen);
            this.compat_percentage = (TextView) view.findViewById(R.id.compatibility_percentage);
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

    public SwipeAdapter(ArrayList<ProfileClass> arrayList, Context context2, String stringlat, String stringlong, String username) {
        this.arrayProfileClasses = arrayList;
        this.context = context2;
        this.currentLat = stringlat;
        this.currentLong = stringlong;
        this.currentuserName = username;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.swipe_item, viewGroup, false);
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        return new ViewHolder(inflate, this.context, this.arrayProfileClasses);
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProfileClass profileClass = (ProfileClass) this.arrayProfileClasses.get(i);
        viewHolder.textViewUsersItemUserName.setText(profileClass.getUser_name().split(" ")[0]);
        viewHolder.textViewUsersItemUsersCity.setText(" ,  " + profileClass.getUser_city());
        viewHolder.textViewUsersItemUsersDistance.setVisibility(View.VISIBLE);
        String user_state = profileClass.getUser_status();
        if(user_state.equals("offline"))
        {
            long timestamp = profileClass.getUser_online().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm a");
            String string13 = dateFormat.format(timestamp);
            viewHolder.textViewUserLastSeen.setText("Last Seen : " + string13);
        }
        else
        {
            viewHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.VISIBLE);
            viewHolder.circleImageViewUsersItemUsersOffline.setVisibility(View.GONE);
            viewHolder.textViewUserLastSeen.setText("Online");
        }

        int compat_percntge = calculate(currentuserName,profileClass.getUser_name());

       viewHolder.compat_percentage.setText(""+compat_percntge + "%");

        float[] fArr = new float[10];
        System.out.println("debug all lat long"+" ==1=="+profileClass.getUser_latitude().trim()+"==2=="+currentLat.trim());
        Location.distanceBetween(Double.parseDouble(profileClass.getUser_latitude().trim()), Double.parseDouble(profileClass.getUser_longitude().trim()),Double.parseDouble(currentLat.trim()),Double.parseDouble(currentLong.trim()),fArr);
        System.out.println("debug all lat long"+" ==1=="+profileClass.getUser_latitude().trim()+"==2=="+currentLong.trim());
        int round = Math.round(fArr[0]);
        if (round >= 1000) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(String.valueOf(round / 1000));
            sb3.append(" km away");
            String stringDistance = sb3.toString();
            viewHolder.textViewUsersItemUsersDistance.setText(stringDistance);
        } else {
            String stringDistance = "Less than 1 km";
            viewHolder.textViewUsersItemUsersDistance.setText(stringDistance);
        }


        TextView textView = viewHolder.textViewUsersItemUsersAge;
        StringBuilder sb = new StringBuilder();
        sb.append(", ");
        sb.append(profileClass.getUser_birthage());
        textView.setText(sb.toString());
        if (profileClass.getUser_cover0().equals("cover")) {
            viewHolder.imageViewUsersItemUsersImage.setImageResource(R.drawable.profile_image);
        } else {
            Picasso.get().load(profileClass.getUser_cover0()).into(viewHolder.imageViewUsersItemUsersImage);
        }
    }

    private int calculate(String currentuserName, String user_name) {

        final String LOVE = "love";

        String firstName;
        String secondName;

        int firstSum;
        int secondSum;
        int loveSum;
        int totalSum;

        while (true) {
            firstSum = 0;
            secondSum = 0;
            loveSum = 0;

            firstName = currentuserName.toLowerCase();
            secondName = user_name.toLowerCase();

            for (int i = 0; i < firstName.length(); i++) {
                firstSum += firstName.charAt(i);
            }

            for (int i = 0; i < secondName.length(); i++) {
                secondSum += secondName.charAt(i);
            }

            for (int i = 0; i < LOVE.length(); i++) {
                loveSum += LOVE.charAt(i);
            }
            totalSum = findSum(firstSum + secondSum);
            loveSum = findSum(loveSum);
            if (totalSum > loveSum) {
                totalSum = loveSum - (totalSum - loveSum);
            }
           return (totalSum*100)/loveSum;

        }

    }

    private static int findSum(int no) {
        int sum = 0;
        while (no > 0) {
            sum += no % 10;
            no /= 10;
        }
        return sum;
    }


    public int getItemCount() {
        return this.arrayProfileClasses.size();
    }
}
