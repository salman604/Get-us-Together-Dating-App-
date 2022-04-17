package com.firedating.chat.Message;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firedating.chat.Notification.MySingleton;
import com.firedating.chat.Notification.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vistrav.ask.Ask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firedating.chat.Chats.ChatsClass;
import com.firedating.chat.Extra.BlockClass;
import com.firedating.chat.Facebook.AppEventsConstants;
import com.firedating.chat.Main.Application;
import com.firedating.chat.Main.MainActivity;
import com.firedating.chat.Profile.ProfileActivity;
import com.firedating.chat.R;
import com.firedating.chat.Settings.ReportActivity;
import com.firedating.chat.Start.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class MessageActivity extends AppCompatActivity {
    public static final int MESSAGE_IMAGE = 1;
    private static final int PICK_IMAGE = 5;

    ArrayList<BlockClass> arraylistBlockUsers;
    Bitmap bitmapThumb;
    ListenerRegistration blockChatUserListenerRegistration;
    ListenerRegistration blockCurrentListenerRegistration;
    ImageButton buttonMessageImage;
    ImageButton buttonMessageSend;
    ListenerRegistration chatsListenerRegistration;
    String countryCurrent;
    String currentUser;
    ListenerRegistration deleteChatListenerRegistration;
    ProgressDialog dialog;
    String editTextChatHide = "no";
    EditText editTextMessageText;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = this.firebaseAuth.getCurrentUser();
    String genderCurrent;
    Bitmap image;
    String imageCurrent;
    int intUnread;
    private ProgressDialog loadingBar;

    ArrayList<ChatsClass> listChatsClass;
    /* access modifiers changed from: private */
    public Menu menuMessage;
    MessageAdapter messageAdapter;
    String premiumCurrent;
    ListenerRegistration privacyListenerRegistration;
    String profileUser;
    RecyclerView recyclerViewMessageView;
    RelativeLayout relativeLayoutMessageChat;
    ListenerRegistration seenListenerRegistration;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String stringThumb;
    String stringUnread;
    String stringUnreadz;
    RoundedImageView toolbarImageViewUserImage;
    TextView toolbarTextViewUserName;
    TextView toolbarTextViewUserStatus;
    Toolbar toolbarToolbar;
    String verifiedCurrent;

    private ValueEventListener valueEventListener;
    private HashMap<com.google.firebase.database.Query, ValueEventListener> groupCreatorAndKeys = new HashMap<>();

    public static MessageActivity messageActivity;


    boolean notify = false;
    String server_key = "AAAAQ-eFzEo:APA91bFeZ0AL7c7R_SsZT9xzxReszLFXgViq1X8R3JtvtfE9hScs6YvzMjBxhsBV8cvTcImkAjWFx1Yd6xI_mJWPvyzEeTunyHTOprfeGcn0a5nIrS0KvL9lYxY8raoUv9lq6i27vGGv";
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + server_key;
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";


    DatabaseReference allTokens;

    String profileUserName;


    String TOPIC;

    /* access modifiers changed from: protected */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        messageActivity = this;
        this.toolbarTextViewUserName = (TextView) findViewById(R.id.toolbarTextViewUserName);
        this.toolbarTextViewUserStatus = (TextView) findViewById(R.id.toolbarTextViewUserStatus);
        this.toolbarImageViewUserImage = (RoundedImageView) findViewById(R.id.toolbarImageViewUserImage);
        this.editTextMessageText = (EditText) findViewById(R.id.editTextMessageText);
        this.buttonMessageSend = (ImageButton) findViewById(R.id.buttonMessageSend);
        this.buttonMessageImage = (ImageButton)findViewById(R.id.buttonMessageImage);
        loadingBar = new ProgressDialog(this);
        this.listChatsClass = new ArrayList<>();
        this.relativeLayoutMessageChat = (RelativeLayout) findViewById(R.id.relativeLayoutMessageChat);
        this.toolbarToolbar = (Toolbar) findViewById(R.id.toolbarToolbar);
        setSupportActionBar(this.toolbarToolbar);
        getSupportActionBar().setTitle((CharSequence) "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessageActivity.this.finish();
            }
        });

        this.recyclerViewMessageView = (RecyclerView) findViewById(R.id.recyclerViewMessageView);
        this.recyclerViewMessageView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        this.recyclerViewMessageView.setLayoutManager(linearLayoutManager);

        allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        this.currentUser = this.firebaseAuth.getCurrentUser().getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");


             MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (documentSnapshot != null) {
                                profileUserName = documentSnapshot.getString("user_name");

                            }
                         }
                     });


        this.buttonMessageSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = MessageActivity.this.editTextMessageText.getText().toString();
                String str = "";
                if (!obj.equals(str)) {
                    MessageActivity messageActivity = MessageActivity.this;
                    messageActivity.sendMessage(messageActivity.currentUser, MessageActivity.this.profileUser, obj);
                } else {
                    Toast.makeText(MessageActivity.this, "Please type message to send", Toast.LENGTH_SHORT).show();
                }
                MessageActivity.this.editTextMessageText.setText(str);
            }
        });

        editTextMessageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().trim().length()==0)
                {
                   UserTypingStatus("noOne");
                }
                else
                {
                    UserTypingStatus(profileUser);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        readMessage(this.currentUser, this.profileUser);
        this.toolbarTextViewUserName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessageActivity.this.chatProfile();
            }
        });
        this.toolbarTextViewUserStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessageActivity.this.chatProfile();
            }
        });
        this.toolbarImageViewUserImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MessageActivity.this.chatProfile();
            }
        });
        buttonMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageAttachment();
            }
        });

    }

    /* access modifiers changed from: private */
    public void chatProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("user_uid", this.profileUser);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void sendMessage(final String str, final String str2, final String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put("chat_datesent", Timestamp.now());
        hashMap.put("chat_dateseen", Timestamp.now());
        hashMap.put("chat_sender", str);
        hashMap.put("chat_receiver", str2);
        hashMap.put("chat_message", str3);
        hashMap.put("chat_seenchat", "no");
        String str4 = "delete";
        hashMap.put("delete_sender", str4);
        hashMap.put("delete_receiver", str4);
        String str5 = "chats";
        this.firebaseFirestore.collection(str5).add((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    final HashMap hashMap = new HashMap();
//                    String str = "user_datesent";
                    hashMap.put("user_datesent", Timestamp.now());
//                    String str2 = "user_sender";
                    hashMap.put("user_sender", str);
//                    String str3 = "user_receiver";
                    hashMap.put("user_receiver", str2);
//                    String str4 = "user_message";
                    hashMap.put("user_message", str3);
                    String str5 = AppEventsConstants.EVENT_PARAM_VALUE_NO;
//                    String str6 = "user_unread";
                    hashMap.put("user_unread", str5);
                    final HashMap hashMap2 = new HashMap();
                    hashMap2.put("user_datesent", Timestamp.now());
                    hashMap2.put("user_sender", str2);
                    hashMap2.put("user_receiver", str);
                    hashMap2.put("user_message", str3);
                    hashMap2.put("user_unread", str5);
                    final HashMap hashMap3 = new HashMap();
                    hashMap3.put("user_datesent", Timestamp.now());
                    hashMap3.put("user_sender", str2);
                    hashMap3.put("user_receiver", str);
                    hashMap3.put("user_message", str3);
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("chats").document(MessageActivity.this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String str = "chats";
                            final String str2 = "users";
                            if (((DocumentSnapshot) task.getResult()).exists()) {
                                MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.currentUser).collection(str).document(MessageActivity.this.profileUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).update(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        loadingBar.dismiss();
                                                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).set((Map<String, Object>) hashMap2);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.currentUser).collection(str).document(MessageActivity.this.profileUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            loadingBar.dismiss();

                                            MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).set((Map<String, Object>) hashMap2);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        this.firebaseFirestore.collection("users").document(this.profileUser).collection(str5).document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    String str = "user_unread";
                    MessageActivity.this.stringUnread = ((DocumentSnapshot) task.getResult()).getString(str);
                    MessageActivity messageActivity = MessageActivity.this;
                    messageActivity.intUnread = Integer.parseInt(messageActivity.stringUnread) + 1;
                    MessageActivity messageActivity2 = MessageActivity.this;
                    messageActivity2.stringUnreadz = String.valueOf(messageActivity2.intUnread);
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, MessageActivity.this.stringUnreadz);
                    loadingBar.dismiss();
                    senNotification(MessageActivity.this.profileUser,profileUserName,str3);
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).update(hashMap);
                }
            }
        });
    }

    private void readMessage(final String str, final String str2) {
        this.chatsListenerRegistration = this.firebaseFirestore.collection("chats").orderBy("chat_datesent", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            ChatsClass chatsClass = (ChatsClass) documentChange.getDocument().toObject(ChatsClass.class);
                            if (((chatsClass.getChat_receiver().equals(str) && chatsClass.getChat_sender().equals(str2)) || (chatsClass.getChat_receiver().equals(str2) && chatsClass.getChat_sender().equals(str))) && !chatsClass.getDelete_sender().equals(str) && !chatsClass.getDelete_receiver().equals(str)) {
                                MessageActivity.this.listChatsClass.add(chatsClass);
                            }
                        }
                        if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                            ChatsClass chatsClass2 = (ChatsClass) documentChange.getDocument().toObject(ChatsClass.class);
                            for (int i = 0; i < MessageActivity.this.listChatsClass.size(); i++) {
                                if (documentChange.getDocument().getDate("chat_datesent").equals(((ChatsClass) MessageActivity.this.listChatsClass.get(i)).getChat_datesent()) && documentChange.getDocument().getString("chat_message").equals(((ChatsClass) MessageActivity.this.listChatsClass.get(i)).getChat_message())) {
                                    MessageActivity.this.listChatsClass.set(i, chatsClass2);
                                }
                            }
                        }
                    }
                    MessageActivity messageActivity = MessageActivity.this;
                    messageActivity.messageAdapter = new MessageAdapter(messageActivity.listChatsClass, MessageActivity.this);
                    MessageActivity.this.recyclerViewMessageView.setAdapter(MessageActivity.this.messageAdapter);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        this.menuMessage = menu;
        menu.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);
        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuUserBlockUser /*2131296656*/:
                BlockUser();
                return true;
            case R.id.menuUserDeleteChat /*2131296657*/:
                DeleteChat();
                return true;
            case R.id.menuUserFavoriteUser /*2131296658*/:
                FavoriteUser();
                return true;
            case R.id.menuUserReportUser /*2131296659*/:
                ReportUser();
                return true;
            case R.id.menuUserUnmatchUser /*2131296660*/:
                UnmatchUser();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();
        return super.onPrepareOptionsMenu(menu);
    }

    private void LikesUser() {
        this.firebaseFirestore.collection("users").document(this.profileUser).collection("likes").document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "likes";
                String str2 = "users";
                String str3 = "user_liked";
                String str4 = "user_likes";
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(str4, MessageActivity.this.currentUser);
                    hashMap.put(str3, Timestamp.now());
                    MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.profileUser).collection(str).document(MessageActivity.this.currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put(str4, MessageActivity.this.currentUser);
                hashMap2.put(str3, Timestamp.now());
                MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.profileUser).collection(str).document(MessageActivity.this.currentUser).set((Map<String, Object>) hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        task.isSuccessful();
                    }
                });
            }
        });
    }

    private void BlockUser() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        this.firebaseFirestore.collection("users").document(uid).collection("block").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "block";
                String str2 = "users";
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    MessageActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(MessageActivity.this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MessageActivity.this, "You have unblocked this user!", 0).show();
                                MessageActivity.this.BlockCheck();
                            }
                        }
                    });
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put("user_block", MessageActivity.this.profileUser);
                MessageActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(MessageActivity.this.profileUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MessageActivity.this, "You have blocked this user!", 0).show();
                            MessageActivity.this.BlockCheck();
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void BlockCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("block").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    MessageActivity.this.menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Unblock User");
                    return;
                }
                MessageActivity.this.menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Block User");
                MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("block").document(MessageActivity.this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!((DocumentSnapshot) task.getResult()).exists()) {
                            MessageActivity.this.ChatControl();
                        }
                    }
                });
            }
        });
    }

    private void BlockCheckChat() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("block").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!((DocumentSnapshot) task.getResult()).exists()) {
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("block").document(MessageActivity.this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (!((DocumentSnapshot) task.getResult()).exists()) {
                                MessageActivity.this.ChatControl();
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void ChatControl() {
        if (this.editTextChatHide.equals("yes")) {
            this.relativeLayoutMessageChat.setVisibility(View.GONE);
        } else {
            this.relativeLayoutMessageChat.setVisibility(View.VISIBLE);
        }
    }

    private void DeleteChat() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        this.deleteChatListenerRegistration = this.firebaseFirestore.collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (querySnapshot != null) {
                    Iterator it = querySnapshot.getDocumentChanges().iterator();
                    while (true) {
                        String str = "chats";
                        if (it.hasNext()) {
                            DocumentChange documentChange = (DocumentChange) it.next();
                            ChatsClass chatsClass = (ChatsClass) documentChange.getDocument().toObject(ChatsClass.class);
                            if ((chatsClass.getChat_receiver().equals(uid) && chatsClass.getChat_sender().equals(MessageActivity.this.profileUser)) || (chatsClass.getChat_receiver().equals(MessageActivity.this.profileUser) && chatsClass.getChat_sender().equals(uid))) {
                                String str2 = "delete";
                                if (chatsClass.getDelete_sender().equals(str2)) {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("delete_sender", uid);
                                    MessageActivity.this.firebaseFirestore.collection(str).document(documentChange.getDocument().getId()).update(hashMap);
                                } else if (chatsClass.getDelete_receiver().equals(str2)) {
                                    HashMap hashMap2 = new HashMap();
                                    hashMap2.put("delete_receiver", uid);
                                    MessageActivity.this.firebaseFirestore.collection(str).document(documentChange.getDocument().getId()).update(hashMap2);
                                }
                            }
                        } else {
                            MessageActivity.this.firebaseFirestore.collection("users").document(uid).collection(str).document(MessageActivity.this.profileUser).delete();
                            MessageActivity.this.deleteChatListenerRegistration.remove();
                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("tab_show", "tab_chats");
//                            intent.addFlags(67108864);
                            MessageActivity.this.startActivity(intent);
                            return;
                        }
                    }
                }
            }
        });
    }

    private void FavoriteUser() {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        final HashMap hashMap = new HashMap();
        hashMap.put("user_favorite", this.profileUser);
        hashMap.put("user_favorited", Timestamp.now());
        this.firebaseFirestore.collection("users").document(uid).collection("favors").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String str = "favors";
                String str2 = "users";
                if (!((DocumentSnapshot) task.getResult()).exists()) {
                    MessageActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(MessageActivity.this.profileUser).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MessageActivity.this, "User added in favorite", Toast.LENGTH_SHORT).show();
                                MessageActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);
                            }
                        }
                    });
                } else {
                    MessageActivity.this.firebaseFirestore.collection(str2).document(uid).collection(str).document(MessageActivity.this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MessageActivity.this, "User removed from favorite", Toast.LENGTH_SHORT).show();
                                MessageActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);
                            }
                        }
                    });
                }
            }
        });
    }

    private void FavoriteCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("favors").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    MessageActivity.this.menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);
                }
            }
        });
    }

    private void UnmatchCheck() {
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    MessageActivity.this.menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(true);
                } else {
                    MessageActivity.this.menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);
                }
            }
        });
    }

    private void UnmatchUser() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = this.firebaseUser.getUid();
        this.firebaseFirestore.collection("users").document(this.currentUser).collection("matches").document(this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("loves").document(MessageActivity.this.profileUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("matches").document(MessageActivity.this.currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("loves").document(MessageActivity.this.currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("likes").document(MessageActivity.this.currentUser).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(MessageActivity.this, "User removed from your matches!", 0).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void ReportUser() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("user_uid", this.profileUser);
        startActivity(intent);
    }

    private void SeenMessage(final String str) {
        this.currentUser = this.firebaseAuth.getCurrentUser().getUid();
        this.profileUser = getIntent().getStringExtra("user_uid");
        String str2 = "chats";
        this.firebaseFirestore.collection(str2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Iterator it = ((QuerySnapshot) task.getResult()).iterator();
                while (it.hasNext()) {
                    QueryDocumentSnapshot queryDocumentSnapshot = (QueryDocumentSnapshot) it.next();
                    ChatsClass chatsClass = (ChatsClass) queryDocumentSnapshot.toObject(ChatsClass.class);
                    if (chatsClass.getChat_receiver().equals(MessageActivity.this.firebaseUser.getUid()) && chatsClass.getChat_sender().equals(str)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("chat_seenchat", "yes");
                        hashMap.put("chat_dateseen", Timestamp.now());
                        if (chatsClass.getChat_seenchat().equals("no")) {
                            queryDocumentSnapshot.getReference().update(hashMap);
                        }
                    }
                }
            }
        });
        this.firebaseFirestore.collection("users").document(this.currentUser).collection(str2).document(this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("user_unread", AppEventsConstants.EVENT_PARAM_VALUE_NO);
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("chats").document(MessageActivity.this.profileUser).update(hashMap);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        BlockCheckChat();
        if (Application.notificationManagerCompat != null) {
            Application.notificationManagerCompat.cancelAll();
        }
        this.profileUser = getIntent().getStringExtra("user_uid");
        UserStatus("online");
        UserCurrent();
        UserProfile();
    }

    private void UserCurrent() {
        String str = "users";
        this.firebaseFirestore.collection(str).document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    MessageActivity.this.genderCurrent = documentSnapshot.getString("user_gender");
                    MessageActivity.this.imageCurrent = documentSnapshot.getString("user_image");
                    MessageActivity.this.verifiedCurrent = documentSnapshot.getString("user_verified");
                    MessageActivity.this.premiumCurrent = documentSnapshot.getString("user_premium");
                    MessageActivity.this.countryCurrent = documentSnapshot.getString("user_country");
                    String string = documentSnapshot.getString("show_match");
                    if (string != null && string.equals("yes")) {
                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("matches").document(MessageActivity.this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                                if (documentSnapshot != null && !documentSnapshot.exists()) {
                                    MessageActivity.this.editTextChatHide = "yes";
                                    MessageActivity.this.ChatControl();
                                }
                            }
                        });
                    }
                }
            }
        });
        this.firebaseFirestore.collection(str).document(this.profileUser).collection("chats").document(this.currentUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot == null || !documentSnapshot.exists()) {
                    MessageActivity.this.UserChats("no");
                } else {
                    MessageActivity.this.UserChats("yes");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void UserChats(final String str) {
        this.firebaseFirestore.collection("users").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    String string = documentSnapshot.getString("block_stranger");
                    if (string != null) {
                        String str = "yes";
                        if (string.equals(str) && str.equals("no")) {
                            MessageActivity messageActivity = MessageActivity.this;
                            messageActivity.editTextChatHide = str;
                            messageActivity.ChatControl();
                        }
                    }
                }
            }
        });
    }

    private void UserProfile() {
        this.firebaseFirestore.collection("users").document(this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                if (documentSnapshot != null) {
                    String string = documentSnapshot.getString("user_name");
                    String string2 = documentSnapshot.getString("user_status");
                    String string3 = documentSnapshot.getString("user_gender");
                    documentSnapshot.getString("user_birthage");
                    String string4 = documentSnapshot.getString("user_thumb");
                    String string5 = documentSnapshot.getString("user_country");
                    documentSnapshot.getString("share_birthage");
                    String string6 = documentSnapshot.getString("show_match");
                    String string7 = documentSnapshot.getString("show_status");
                    String string8 = documentSnapshot.getString("block_genders");
                    String string9 = documentSnapshot.getString("block_photos");
                    String string10 = documentSnapshot.getString("allow_verified");
                    String string11 = documentSnapshot.getString("allow_premium");
                    String string12 = documentSnapshot.getString("allow_country");
                    String typingStatus = documentSnapshot.getString("TypingTo");
                    Date timestamp13 = documentSnapshot.getTimestamp("user_online").toDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm a");
                    String string13 = dateFormat.format(timestamp13);
                    System.out.println("debug timestamp as string"+string13);
                    String str = "yes";
                    if (string6 != null && string6.equals(str)) {
                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("matches").document(MessageActivity.this.profileUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                                if (documentSnapshot != null && !documentSnapshot.exists()) {
                                    MessageActivity.this.editTextChatHide = "yes";
                                    MessageActivity.this.ChatControl();
                                }
                            }
                        });
                    }
                    if (string8 != null && string8.equals(str) && string3.equals(MessageActivity.this.genderCurrent)) {
                        MessageActivity messageActivity = MessageActivity.this;
                        messageActivity.editTextChatHide = str;
                        messageActivity.ChatControl();
                    }
                    if (string9 != null && string9.equals(str) && MessageActivity.this.imageCurrent.equals(RegisterActivity.MEDIA_IMAGE)) {
                        MessageActivity messageActivity2 = MessageActivity.this;
                        messageActivity2.editTextChatHide = str;
                        messageActivity2.ChatControl();
                    }
                    String str2 = "no";
                    if (string10 != null && string10.equals(str) && MessageActivity.this.verifiedCurrent.equals(str2)) {
                        MessageActivity messageActivity3 = MessageActivity.this;
                        messageActivity3.editTextChatHide = str;
                        messageActivity3.ChatControl();
                    }
                    if (string11 != null && string11.equals(str) && MessageActivity.this.premiumCurrent.equals(str2)) {
                        MessageActivity messageActivity4 = MessageActivity.this;
                        messageActivity4.editTextChatHide = str;
                        messageActivity4.ChatControl();
                    }
                    if (string12 != null && string12.equals(str) && !MessageActivity.this.countryCurrent.equals(string5)) {
                        MessageActivity messageActivity5 = MessageActivity.this;
                        messageActivity5.editTextChatHide = str;
                        messageActivity5.ChatControl();
                    }
                    MessageActivity.this.toolbarTextViewUserName.setText(string.split(" ")[0]);
                    if (string7 == null || !string7.equals(str)) {
                        MessageActivity.this.toolbarTextViewUserStatus.setText(string2);
                    }
                    else {
                        if(typingStatus.equals(currentUser))
                        {
                            MessageActivity.this.toolbarTextViewUserStatus.setText("Typing .....");
                        }
                        else
                        {
                            if(string2.equals("online"))
                            {
                                MessageActivity.this.toolbarTextViewUserStatus.setText("online");
                            }
                            else
                            {
                                MessageActivity.this.toolbarTextViewUserStatus.setText(string13);
                            }
                        }


                    }
                    if (string4.equals("thumb")) {
                        Picasso.get().load((int) R.drawable.profile_image).into((ImageView) MessageActivity.this.toolbarImageViewUserImage);
                    } else {
                        Picasso.get().load(string4).into((ImageView) MessageActivity.this.toolbarImageViewUserImage);
                    }
                }
            }
        });
    }

    private void UserStatus(String str) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("user_status", str);
        FirebaseFirestore.getInstance().collection("users").document(uid).update(hashMap);
    }

    private void UserTypingStatus(String str) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap hashMap = new HashMap();
        hashMap.put("TypingTo", str);
        FirebaseFirestore.getInstance().collection("users").document(uid).update(hashMap);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.profileUser = getIntent().getStringExtra("user_uid");
        SeenMessage(this.profileUser);
        this.currentUser = this.firebaseAuth.getCurrentUser().getUid();
        String str = "users";
        String str2 = "block";
        this.blockChatUserListenerRegistration = this.firebaseFirestore.collection(str).document(this.profileUser).collection(str2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED && documentChange.getDocument().getId().equals(MessageActivity.this.currentUser)) {
                        Toast.makeText(MessageActivity.this, "You have been blocked by this user!", Toast.LENGTH_SHORT).show();
                        MessageActivity.this.editTextMessageText.setVisibility(View.GONE);
                        MessageActivity.this.buttonMessageSend.setVisibility(View.GONE);
                    }
                    if (documentChange.getType() == DocumentChange.Type.REMOVED && documentChange.getDocument().getId().equals(MessageActivity.this.currentUser)) {
                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("block").document(MessageActivity.this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!((DocumentSnapshot) task.getResult()).exists()) {
                                    MessageActivity.this.editTextMessageText.setVisibility(View.VISIBLE);
                                    MessageActivity.this.buttonMessageSend.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            }
        });
        this.blockCurrentListenerRegistration = this.firebaseFirestore.collection(str).document(this.currentUser).collection(str2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException firebaseFirestoreException) {
                for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED && documentChange.getDocument().getId().equals(MessageActivity.this.profileUser)) {
                        Toast.makeText(MessageActivity.this, "Please unblock user to send message!", Toast.LENGTH_SHORT).show();
                        MessageActivity.this.editTextMessageText.setVisibility(View.GONE);
                        MessageActivity.this.buttonMessageSend.setVisibility(View.GONE);
                    }
                    if (documentChange.getType() == DocumentChange.Type.REMOVED && documentChange.getDocument().getId().equals(MessageActivity.this.profileUser)) {
                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("block").document(MessageActivity.this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!((DocumentSnapshot) task.getResult()).exists()) {
                                    MessageActivity.this.editTextMessageText.setVisibility(View.VISIBLE);
                                    MessageActivity.this.buttonMessageSend.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        UserStatus("offline");
        Application.appRunning = false;
        this.blockCurrentListenerRegistration.remove();
        this.blockChatUserListenerRegistration.remove();


    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1) {
            CropImage.activity(intent.getData()).setAspectRatio(1, 1).start(this);
        }
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1) {
                Bitmap decodeFile = BitmapFactory.decodeFile(new File(activityResult.getUri().getPath()).getPath());
                this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                this.firebaseUser.getUid();
                ThumbUpload(decodeFile);
                this.dialog = new ProgressDialog(this);
                this.dialog.setTitle("Please Wait");
                this.dialog.setMessage("Sending Photo...");
                this.dialog.setCancelable(false);
                this.dialog.show();
            } else if (i2 == 204) {
                activityResult.getError();
            }
        }
        if (i == PICK_IMAGE) {
            if (i2 == Activity.RESULT_OK) {
//
                loadingBar.setTitle("Sending File");
                loadingBar.setMessage("Please wait, We are sending your file...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri selectedImageURI = intent.getData();
                File compressedImageFile = null;
                File imageFile = new File(getPath(selectedImageURI));
                try {
                    compressedImageFile = new Compressor(this).compressToFile(imageFile);
                    Log.d("dsds", "onActivityResult: " + Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath());
                    System.out.println("debug ==" + String.valueOf(Uri.fromFile(compressedImageFile)));
                    sendImageasMessage(Uri.fromFile(compressedImageFile));

                } catch (IOException e) {
                    e.printStackTrace();
                    Ask.on(this)
                            .id(1) // in case you are invoking multiple time Ask from same activity or fragment
                            .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withRationales("In order to Share and save Media Files you will need to grant storage permission") //optional
                            .go();
                }
            }
        }
    }

    private void sendImageasMessage(Uri uri)
    {
        String valueOf = String.valueOf(System.currentTimeMillis());
        final StorageReference child = this.storageReference.child(this.currentUser).child("images").child("chats").child("chat_"+valueOf+".jpg");
        child.putFile(uri).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    child.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = (Uri) task.getResult();
                            String imageUrl = uri.toString();
                            sendMessage(MessageActivity.this.currentUser,MessageActivity.this.profileUser,imageUrl);

                        }
                    });
                }
            }

        });
    }

    private void ThumbUpload(Bitmap bitmap) {
        this.bitmapThumb = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.bitmapThumb.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String valueOf = String.valueOf(System.currentTimeMillis());
        StorageReference child = this.storageReference.child(this.currentUser).child("images").child("chats");
        StringBuilder sb = new StringBuilder();
        sb.append("chat_");
        sb.append(valueOf);
        sb.append(".jpg");
        final StorageReference child2 = child.child(sb.toString());
        child2.putBytes(byteArray).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    child2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri uri = (Uri) task.getResult();
                            MessageActivity.this.stringThumb = uri.toString();
                            new HashMap().put("chat_image", MessageActivity.this.stringThumb);
                            MessageActivity.this.sendImage(MessageActivity.this.stringThumb);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendImage(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("chat_datesent", Timestamp.now());
        hashMap.put("chat_dateseen", Timestamp.now());
        hashMap.put("chat_sender", this.currentUser);
        hashMap.put("chat_receiver", this.profileUser);
        final String str2 = "picture message!";
        hashMap.put("chat_message", str2);
        hashMap.put("chat_image", str);
        hashMap.put("chat_media", "yes");
        hashMap.put("chat_seenchat", "no");
        String str3 = "delete";
        hashMap.put("delete_sender", str3);
        hashMap.put("delete_receiver", str3);
        String str4 = "chats";
        this.firebaseFirestore.collection(str4).add((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    final HashMap hashMap = new HashMap();
                    String str = "user_datesent";
                    hashMap.put(str, Timestamp.now());
                    String str2 = "user_sender";
                    hashMap.put(str2, MessageActivity.this.currentUser);
                    String str3 = "user_receiver";
                    hashMap.put(str3, MessageActivity.this.profileUser);
                    String str4 = "user_message";
                    hashMap.put(str4, str2);
                    String str5 = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    String str6 = "user_unread";
                    hashMap.put(str6, str5);
                    final HashMap hashMap2 = new HashMap();
                    hashMap2.put(str, Timestamp.now());
                    hashMap2.put(str2, MessageActivity.this.profileUser);
                    hashMap2.put(str3, MessageActivity.this.currentUser);
                    hashMap2.put(str4, str2);
                    hashMap2.put(str6, str5);
                    final HashMap hashMap3 = new HashMap();
                    hashMap3.put(str, Timestamp.now());
                    hashMap3.put(str2, MessageActivity.this.profileUser);
                    hashMap3.put(str3, MessageActivity.this.currentUser);
                    hashMap3.put(str4, str2);
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.currentUser).collection("chats").document(MessageActivity.this.profileUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String str = "chats";
                            String str2 = "users";
                            if (((DocumentSnapshot) task.getResult()).exists()) {
                                MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.currentUser).collection(str).document(MessageActivity.this.profileUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).update(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (!task.isSuccessful()) {
                                                        MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).set((Map<String, Object>) hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                MessageActivity.this.dialog.dismiss();
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                MessageActivity.this.firebaseFirestore.collection(str2).document(MessageActivity.this.currentUser).collection(str).document(MessageActivity.this.profileUser).set((Map<String, Object>) hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).set((Map<String, Object>) hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    MessageActivity.this.dialog.dismiss();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        this.firebaseFirestore.collection("users").document(this.profileUser).collection(str4).document(this.currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (((DocumentSnapshot) task.getResult()).exists()) {
                    String str = "user_unread";
                    MessageActivity.this.stringUnread = ((DocumentSnapshot) task.getResult()).getString(str);
                    MessageActivity messageActivity = MessageActivity.this;
                    messageActivity.intUnread = Integer.parseInt(messageActivity.stringUnread) + 1;
                    MessageActivity messageActivity2 = MessageActivity.this;
                    messageActivity2.stringUnreadz = String.valueOf(messageActivity2.intUnread);
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, MessageActivity.this.stringUnreadz);
                    MessageActivity.this.firebaseFirestore.collection("users").document(MessageActivity.this.profileUser).collection("chats").document(MessageActivity.this.currentUser).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            MessageActivity.this.dialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    private void selectImageAttachment() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_IMAGE);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void senNotification(String messageReceiverID, final String myName, final String messageText)
    {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        com.google.firebase.database.Query query = allTokens.orderByKey().equalTo(messageReceiverID);
        query.addListenerForSingleValueEvent(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("debug inside sendNotification==");
                    Token token = ds.getValue(Token.class);
                    System.out.println("debug Token " + token);


                    TOPIC = "/topics/userABC"; //topic has to match what the receiver subscribed to


                    JSONObject notification = new JSONObject();
                    JSONObject notifcationBody = new JSONObject();
                    try {
                        notifcationBody.put("title", myName);
                        notifcationBody.put("message", messageText);

                        notification.put("to", token.getToken());
                        notification.put("data", notifcationBody);

                        System.out.println("debug title=="+myName+"+message=="+messageText+"+token"+token.getToken());
                    } catch (JSONException e) {
                        Log.e(TAG, "onCreate: " + e.getMessage() );
                    }
                    sendNotification(notification);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toast.makeText(MessageActivity.this, "Notification Sent succesfully", Toast.LENGTH_SHORT).show();
//                        edtTitle.setText("");
//                        edtMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }



    @Override
    protected void onStop() {
        super.onStop();
        UserTypingStatus("noOne");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
