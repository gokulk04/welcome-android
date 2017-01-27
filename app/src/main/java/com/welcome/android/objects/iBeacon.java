package com.welcome.android.objects;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;
import com.welcome.android.utils.FirebaseDBUtils;

import java.util.List;

/**
 * Created by Kraji on 1/26/2017.
 */

public class iBeacon extends FirebaseObject {
    private String eventId;
    private String organizationId;
    private Integer major;
    private Integer minor;

    public iBeacon() {

    }

    @Exclude
    public static Task<iBeacon> getById(String id) {
        return new FirebaseDBUtils<iBeacon>(iBeacon.class).getById(id);
    }

    @Exclude
    public static Task<List<iBeacon>> getByIds(List<String> ids) {
        return new FirebaseDBUtils<iBeacon>(iBeacon.class).getByIds(ids);
    }

    @Exclude
    public static Task<List<iBeacon>> getAll() {
        return new FirebaseDBUtils<iBeacon>(iBeacon.class).getAll();
    }

    @Exclude
    public static Task<List<iBeacon>> filterAllByParamEqualTo(String param, Object value) {
        return new FirebaseDBUtils<iBeacon>(iBeacon.class).filterAllByParamEqualTo(param, value);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    @Exclude
    protected void copy(FirebaseObject o) {
        iBeacon other = null;
        try {
            other = (iBeacon) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("can't cast object to iBeacon");
        }
        organizationId = other.organizationId;
        eventId = other.eventId;
        major = other.major;
        minor = other.minor;
    }
}
