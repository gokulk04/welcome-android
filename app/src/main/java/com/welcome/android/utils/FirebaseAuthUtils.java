package com.welcome.android.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.welcome.android.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraji on 1/13/2017.
 */

public class FirebaseAuthUtils {
    public static User currentUser;
    public static FirebaseUser currentFirebaseUser;

    public static Task<Void> login(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).continueWithTask(new Continuation<AuthResult, Task<User>>() {
            @Override
            public Task<User> then(@NonNull Task<AuthResult> task) throws Exception {
                FirebaseUser user = task.getResult().getUser();
                FirebaseAuthUtils.currentFirebaseUser = user;
                return User.getById(user.getUid());
            }
        }).continueWith(new Continuation<User, Void>() {
            @Override
            public Void then(@NonNull Task<User> task) throws Exception {
                FirebaseAuthUtils.currentUser = task.getResult();
                return null;
            }
        });
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuthUtils.currentUser = null;
        FirebaseAuthUtils.currentFirebaseUser = null;
    }

    public static Task<Void> signUp(final String email, final String name, final String password) {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).continueWithTask(new Continuation<AuthResult, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<AuthResult> task) throws Exception {
                FirebaseAuthUtils.currentFirebaseUser = task.getResult().getUser();
                final User newUser = new User();
                FirebaseDBUtils<User> fdbu = new FirebaseDBUtils<User>(User.class);
                newUser.setRef(fdbu.getNewChildRef(FirebaseAuthUtils.currentFirebaseUser.getUid()));
                newUser.setName(name);
                newUser.setEmail(email);
                return newUser.pushToDB();
            }
        }).continueWithTask(new Continuation<Void, Task<User>>() {
            @Override
            public Task<User> then(@NonNull Task<Void> task) throws Exception {
                return User.getById(FirebaseAuthUtils.currentFirebaseUser.getUid());
            }
        }).continueWith(new Continuation<User, Void>() {
            @Override
            public Void then(@NonNull Task<User> task) throws Exception {
                FirebaseAuthUtils.currentUser = task.getResult();
                return null;
            }
        });
    }

    public static Task<Void> updateEmail(final String email) {
        final FirebaseUser currentUser = FirebaseAuthUtils.currentFirebaseUser;
        if (currentUser == null)
            throw new RuntimeException("can't update email when no user is logged in");
        FirebaseAuthUtils.currentUser.setEmail(email);
        return FirebaseAuthUtils.currentUser.pushToDB().continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                return currentUser.updateEmail(email);
            }
        });
    }

    public static Task<Void> updatePassword(String password) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("can't update password when no user is logged in");
        return currentUser.updatePassword(password);
    }

    public static Task<Void> updateProfile(final String newName, Bitmap newProfileImage) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("can't update profile when no user is logged in");
        final List<Uri> profilePicUriContainer = new ArrayList<>();
        return FirebaseStorageUtils.uploadImage(newProfileImage).continueWithTask(new Continuation<Uri, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<Uri> task) throws Exception {
                Uri uri = task.getResult();
                profilePicUriContainer.add(uri);
                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                builder.setDisplayName(newName);
                builder.setPhotoUri(uri);
                return FirebaseAuthUtils.currentFirebaseUser.updateProfile(builder.build());
            }
        }).continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                FirebaseAuthUtils.currentUser.setProfPicUrl(profilePicUriContainer.get(0).toString());
                return FirebaseAuthUtils.currentUser.pushToDB();
            }
        });
    }
}
