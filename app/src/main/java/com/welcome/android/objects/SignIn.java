package com.welcome.android.objects;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;
import com.welcome.android.utils.FirebaseDBUtils;

import java.util.List;

/**
 * Created by Kraji on 1/12/2017.
 */

public class SignIn extends FirebaseObject {
    private String userId;
    private String time;
    private String eventId;

    public SignIn() {

    }

    @Exclude
    public static Task<SignIn> getById(String id) {
        return new FirebaseDBUtils<SignIn>(SignIn.class).getById(id);
    }

    @Exclude
    public static Task<List<SignIn>> getAll() {
        return new FirebaseDBUtils<SignIn>(SignIn.class).getAll();
    }

    @Exclude
    public static Task<List<SignIn>> filterAllByParamEqualTo(String param, Object value) {
        return new FirebaseDBUtils<SignIn>(SignIn.class).filterAllByParamEqualTo(param, value);
    }

    @Exclude
    protected void copy(FirebaseObject o) {
        SignIn other = null;
        try {
            other = (SignIn) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("can't cast object to SignIn");
        }

        userId = other.userId;
        time = other.time;
        eventId = other.eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
