package com.welcome.android.utils;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.welcome.android.objects.User;

/**
 * Created by Kraji on 1/13/2017.
 */

public class FirebaseAuthUtils {
    public static Task login(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static Task signUp(final User newUser, final String password) {
        Task t1 = newUser.pushToDB();
        Task t2 = FirebaseAuth.getInstance().createUserWithEmailAndPassword(newUser.getEmail(), password);
        Task[] tasks = {t1, t2};
        return Tasks.whenAll(tasks);
    }

    public static Task updateEmail(User currentUserObject, String email) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("can't update email when no user is logged in");
        currentUserObject.setEmail(email);
        Task t1 = currentUserObject.pushToDB();
        Task t2 = currentUser.updateEmail(email);
        Task[] tasks = {t1, t2};
        return Tasks.whenAll(tasks);
    }

    public static Task updatePassword(String password) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("can't update password when no user is logged in");
        return currentUser.updatePassword(password);
    }

    public static Task updateProfile(User currentUserObject, String displayName, Uri profilePicUri) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null)
            throw new RuntimeException("can't update profile when no user is logged in");
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(displayName);
        builder.setPhotoUri(profilePicUri);
        Task t1 = currentUser.updateProfile(builder.build());
        currentUserObject.setProfPicUrl(profilePicUri.toString());
        Task t2 = currentUserObject.pushToDB();
        Task[] tasks = {t1, t2};
        return Tasks.whenAll(tasks);
    }
}
