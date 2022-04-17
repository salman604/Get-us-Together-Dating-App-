package com.firedating.chat.Main;

import android.accessibilityservice.GestureDescription;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.internal.view.SupportMenu;

import com.firedating.chat.Accounts.AccountsActivity;
import com.firedating.chat.Chats.ChatUserClass;
import com.firedating.chat.Facebook.AppEventsConstants;
import com.firedating.chat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Application extends android.app.Application {
    public static final String CHANNEL_DETAIL_1 = "Check when you have new Match";
    public static final String CHANNEL_DETAIL_2 = "Check new and unread messages";
    public static final String CHANNEL_DETAIL_3 = "Check out who is into you";
    public static final String CHANNEL_DETAIL_4 = "Check who has crush on you";
    public static final String CHANNEL_DETAIL_5 = "Check who is stalking you";
    public static final String CHANNEL_DETAIL_6 = "Check out the recent uploads";
    public static final String CHANNEL_DETAIL_7 = "Check out other notifications";
    public static final String CHANNEL_GROUP_1 = "New Matches";
    public static final String CHANNEL_GROUP_2 = "Messages";
    public static final String CHANNEL_GROUP_3 = "New Likes";
    public static final String CHANNEL_GROUP_4 = "Super Likes";
    public static final String CHANNEL_GROUP_5 = "New Visits";
    public static final String CHANNEL_GROUP_6 = "New Feeds";
    public static final String CHANNEL_GROUP_7 = "Uncategorised";
    public static final String CHANNEL_ID_1 = "New Matches";
    public static final String CHANNEL_ID_2 = "Messages";
    public static final String CHANNEL_ID_3 = "New Likes";
    public static final String CHANNEL_ID_4 = "Super Likes";
    public static final String CHANNEL_ID_5 = "New Visits";
    public static final String CHANNEL_ID_6 = "New Feeds";
    public static final String CHANNEL_ID_7 = "Uncategorised";
    public static final String CHANNEL_NAME_1 = "New Matches";
    public static final String CHANNEL_NAME_2 = "Messages";
    public static final String CHANNEL_NAME_3 = "New Likes";
    public static final String CHANNEL_NAME_4 = "Super Likes";
    public static final String CHANNEL_NAME_5 = "New Visits";
    public static final String CHANNEL_NAME_6 = "New Feeds";
    public static final String CHANNEL_NAME_7 = "Uncategorised";
    public static boolean appRunning = false;
    public static NotificationManagerCompat notificationManagerCompat = null;
    static String user_unread = "0";
    int add;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    int news;

    public void onCreate() {
        super.onCreate();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = this.firebaseAuth.getCurrentUser();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        appRunning = true;
        NotificationChannels();
        StartNotificationMatch();
        StartNotificationChats();
        StartNotificationLikes();
        StartNotificationSuper();
        StartNotificationVisits();
    }

    private void NotificationChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            String str = "New Matches";
            NotificationChannel notificationChannel = new NotificationChannel(str, str, 4);
            notificationChannel.setDescription(CHANNEL_DETAIL_1);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            String str2 = "Messages";
            NotificationChannel notificationChannel2 = new NotificationChannel(str2, str2, 4);
            notificationChannel2.setDescription(CHANNEL_DETAIL_2);
            notificationChannel2.enableLights(true);
            notificationChannel2.enableVibration(true);
            String str3 = "New Likes";
            NotificationChannel notificationChannel3 = new NotificationChannel(str3, str3, 4);
            notificationChannel3.setDescription(CHANNEL_DETAIL_3);
            notificationChannel3.enableLights(true);
            notificationChannel3.enableVibration(true);
            String str4 = "Super Likes";
            NotificationChannel notificationChannel4 = new NotificationChannel(str4, str4, 4);
            notificationChannel4.setDescription(CHANNEL_DETAIL_4);
            notificationChannel4.enableLights(true);
            notificationChannel4.enableVibration(true);
            String str5 = "New Visits";
            NotificationChannel notificationChannel5 = new NotificationChannel(str5, str5, 4);
            notificationChannel5.setDescription(CHANNEL_DETAIL_5);
            notificationChannel5.enableLights(true);
            notificationChannel5.enableVibration(true);
            String str6 = "New Feeds";
            NotificationChannel notificationChannel6 = new NotificationChannel(str6, str6, 4);
            notificationChannel6.setDescription(CHANNEL_DETAIL_6);
            notificationChannel6.enableLights(true);
            notificationChannel6.enableVibration(true);
            String str7 = "Uncategorised";
            NotificationChannel notificationChannel7 = new NotificationChannel(str7, str7, 4);
            notificationChannel7.setDescription(CHANNEL_DETAIL_7);
            notificationChannel7.enableLights(true);
            notificationChannel7.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.createNotificationChannel(notificationChannel2);
            notificationManager.createNotificationChannel(notificationChannel3);
            notificationManager.createNotificationChannel(notificationChannel4);
            notificationManager.createNotificationChannel(notificationChannel5);
            notificationManager.createNotificationChannel(notificationChannel6);
            notificationManager.createNotificationChannel(notificationChannel7);
        }
    }

    private void StartNotificationMatch() {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).collection("matches").addSnapshotListener(new EventListener<QuerySnapshot>() {
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (querySnapshot != null) {
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            String str = "users";
                            if (documentChange.getType() == Type.ADDED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_match").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationMatch();
                                        }
                                    }
                                });
                            }
                            if (documentChange.getType() == Type.MODIFIED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_match").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationMatch();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ShowNotificationMatch() {
        Intent intent = new Intent(this, AccountsActivity.class);
        intent.putExtra("tab_show", "tab_matches");
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        String str = "New Matches";
        notificationManagerCompat.notify(1, new NotificationCompat.Builder(this, str).setSmallIcon(setNotificationIcon()).setLargeIcon(CircleBitmap(decodeResource)).setStyle(new NotificationCompat.BigTextStyle()).setColor(getResources().getColor(R.color.colorPink)).setPriority(1).setContentTitle("You have new match").setContentText("Someone matched you! Tap here to find out who!").setContentIntent(activity).setAutoCancel(true).setGroup(str).build());
    }

    private void StartNotificationLikes() {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).collection("likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (querySnapshot != null) {
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            String str = "users";
                            if (documentChange.getType() == Type.ADDED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_likes").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationLikes();
                                        }
                                    }
                                });
                            }
                            if (documentChange.getType() == Type.MODIFIED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_likes").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationLikes();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ShowNotificationLikes() {
        Intent intent = new Intent(this, AccountsActivity.class);
        intent.putExtra("tab_show", "tab_likes");
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        String str = "New Likes";
        notificationManagerCompat.notify(3, new NotificationCompat.Builder(this, str).setSmallIcon(setNotificationIcon()).setLargeIcon(CircleBitmap(decodeResource)).setStyle(new NotificationCompat.BigTextStyle()).setColor(getResources().getColor(R.color.colorPink)).setPriority(1).setContentTitle("You have new likes").setContentText("Someone liked you! Tap here to find out who!").setContentIntent(activity).setAutoCancel(true).setGroup(str).build());
    }

    private void StartNotificationSuper() {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).collection("likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (querySnapshot != null) {
                        for (final DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            String str = "users";
                            if (documentChange.getType() == Type.ADDED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String str = "yes";
                                            if (((DocumentSnapshot) task.getResult()).getString("alert_super").equals(str)) {
                                                String string = documentChange.getDocument().getString("user_super");
                                                if (string != null && string.equals(str) && !Application.appRunning) {
                                                    Application.this.ShowNotificationSuper();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            if (documentChange.getType() == Type.MODIFIED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String str = "yes";
                                            if (((DocumentSnapshot) task.getResult()).getString("alert_super").equals(str)) {
                                                String string = documentChange.getDocument().getString("user_super");
                                                if (string != null && string.equals(str) && !Application.appRunning) {
                                                    Application.this.ShowNotificationSuper();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ShowNotificationSuper() {
        Intent intent = new Intent(this, AccountsActivity.class);
        intent.putExtra("tab_show", "tab_likes");
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        String str = "Super Likes";
        notificationManagerCompat.notify(4, new NotificationCompat.Builder(this, str).setSmallIcon(setNotificationIcon()).setLargeIcon(CircleBitmap(decodeResource)).setStyle(new NotificationCompat.BigTextStyle()).setColor(getResources().getColor(R.color.colorPink)).setPriority(1).setContentTitle("You have new super like").setContentText("Someone super liked you! Tap here to find out!").setContentIntent(activity).setAutoCancel(true).setGroup(str).build());
    }

    private void StartNotificationVisits() {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).collection("visits").addSnapshotListener(new EventListener<QuerySnapshot>() {
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (querySnapshot != null) {
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            String str = "users";
                            if (documentChange.getType() == Type.ADDED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_visits").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationVisits();
                                        }
                                    }
                                });
                            }
                            if (documentChange.getType() == Type.MODIFIED) {
                                Application.this.firebaseFirestore.collection(str).document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_visits").equals("yes") && !Application.appRunning) {
                                            Application.this.ShowNotificationVisits();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ShowNotificationVisits() {
        Intent intent = new Intent(this, AccountsActivity.class);
        intent.putExtra("tab_show", "tab_visitors");
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        String str = "New Visits";
        notificationManagerCompat.notify(5, new NotificationCompat.Builder(this, str).setSmallIcon(setNotificationIcon()).setLargeIcon(CircleBitmap(decodeResource)).setStyle(new NotificationCompat.BigTextStyle()).setColor(getResources().getColor(R.color.colorPink)).setPriority(1).setContentTitle("You have new visitor").setContentText("Someone visited you! Tap here to find out who!").setContentIntent(activity).setAutoCancel(true).setGroup(str).build());
    }

    private void StartNotificationChats() {
        FirebaseUser firebaseUser2 = this.firebaseUser;
        if (firebaseUser2 != null) {
            final String uid = firebaseUser2.getUid();
            this.firebaseFirestore.collection("users").document(uid).collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                    if (querySnapshot != null) {
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            Type type = documentChange.getType();
                            Type type2 = Type.ADDED;
                            String str = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                            if (type == type2) {
                                ChatUserClass chatUserClass = (ChatUserClass) documentChange.getDocument().toObject(ChatUserClass.class);
                                if (!chatUserClass.getUser_unread().equals(str)) {
                                    String user_message = chatUserClass.getUser_message();
                                    String user_receiver = chatUserClass.getUser_receiver();
                                    chatUserClass.getUser_unread();
                                    Application.this.CheckNotificationsChats(uid, user_message, user_receiver);
                                }
                            }
                            if (documentChange.getType() == Type.MODIFIED) {
                                ChatUserClass chatUserClass2 = (ChatUserClass) documentChange.getDocument().toObject(ChatUserClass.class);
                                if (!chatUserClass2.getUser_unread().equals(str)) {
                                    String user_message2 = chatUserClass2.getUser_message();
                                    String user_receiver2 = chatUserClass2.getUser_receiver();
                                    chatUserClass2.getUser_unread();
                                    Application.this.CheckNotificationsChats(uid, user_message2, user_receiver2);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void CheckNotificationsChats(final String str, final String str2, String str3) {
        this.firebaseFirestore.collection("users").document(str3).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ((DocumentSnapshot) task.getResult()).getString("user_status");
                    String string = ((DocumentSnapshot) task.getResult()).getString("user_name");
                    final String string2 = ((DocumentSnapshot) task.getResult()).getString("user_uid");
                    final String str = string.split(" ")[0];
                    final int intValue = Integer.valueOf(string2.replaceAll("[^0-9]", "")).intValue();
                    if (!Application.appRunning) {
                        Application.this.firebaseFirestore.collection("users").document(str).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful() && ((DocumentSnapshot) task.getResult()).getString("alert_chats").equals("yes")) {
//                                    Application.this.ShowNotificationChats(str, str2, string2, intValue, MessageActivity.class);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void ShowNotificationChats(String str, String str2, String str3, int i, Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("user_uid", str3);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 134217728);
        PendingIntent activity2 = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 134217728);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        String str4 = "Messages";
        Notification build = new NotificationCompat.Builder(this, str4).setSmallIcon(setNotificationIcon()).setLargeIcon(CircleBitmap(decodeResource)).setStyle(new NotificationCompat.BigTextStyle()).setColor(getResources().getColor(R.color.colorPink)).setPriority(1).setContentTitle(str).setContentText(str2).setContentIntent(activity).setAutoCancel(true).setGroup(str4).build();
        String str5 = "New Chat Messages";
        Notification build2 = new NotificationCompat.Builder(this, str4).setSmallIcon(setNotificationIcon()).setStyle(new NotificationCompat.InboxStyle().setBigContentTitle(str5).setSummaryText(str5)).setPriority(1).setColor(getResources().getColor(R.color.colorPink)).setGroup(str4).setGroupSummary(true).setAutoCancel(true).setGroupAlertBehavior(2).setContentIntent(activity2).build();
        notificationManagerCompat.notify(i, build);
        notificationManagerCompat.notify(2, build2);
    }

    private int setNotificationIcon() {
        if (Build.VERSION.SDK_INT >= 21) {
        }
        return R.drawable.notify_icon;
    }

    private Bitmap CircleBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(SupportMenu.CATEGORY_MASK);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return createBitmap;
    }
}
