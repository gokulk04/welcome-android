package com.welcome.android.objects;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Exclude;
import com.welcome.android.utils.FirebaseDBUtils;

import java.util.List;

/**
 * Created by Kraji on 1/12/2017.
 */

public class Event extends FirebaseObject {
    private String name;
    private String startTime;
    private String endTime;
    private String organizationId;
    private String eventPicUrl;
    private String description;
    private Boolean isPublic;
    private String location;

    public Event() {

    }

    @Exclude
    public static Task<Event> getById(String organizationId, String eventId) {
        return new FirebaseDBUtils<Event>(Event.class).getById(organizationId, eventId);
    }

    @Exclude
    public static Task<List<Event>> getAll(String organizationId) {
        return new FirebaseDBUtils<Event>(Event.class).getAll(organizationId);
    }

    @Exclude
    public static Task<List<Event>> filterAllByParamEqualTo(String organizationId, String param, Object value) {
        return new FirebaseDBUtils<Event>(Event.class).filterAllByParamEqualTo(organizationId, param, value);
    }

    @Exclude
    protected void copy(FirebaseObject o) {
        Event other = null;
        try {
            other = (Event) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("can't cast object to Event");
        }

        name = other.name;
        startTime = other.startTime;
        endTime = other.endTime;
        organizationId = other.organizationId;
        eventPicUrl = other.eventPicUrl;
        description = other.description;
        isPublic = other.isPublic;
        location = other.location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getEventPicUrl() {
        return eventPicUrl;
    }

    public void setEventPicUrl(String eventPicUrl) {
        this.eventPicUrl = eventPicUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
