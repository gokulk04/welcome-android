package com.welcome.android.objects;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.Exclude;
import com.welcome.android.utils.FirebaseDBUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kraji on 1/12/2017.
 */

public class User extends FirebaseObject{
    private String name;
    private String email;
    private String profPicUrl;
    private List<String> memberOrganizationIds;
    private List<String> adminOrganizationIds;
    private String birthday;
    private String phoneNumber;
    private String race;
    private String gender;
    private String occupation;
    private String school;
    private String major;
    private String year;
    private String company;
    private String jobTitle;

    public User() {

    }

    @Exclude
    public static Task<User> getById(String id) {
        return new FirebaseDBUtils<User>(User.class).getById(id);
    }

    @Exclude
    public static Task<List<User>> getByIds(List<String> ids) {
        return new FirebaseDBUtils<User>(User.class).getByIds(ids);
    }

    @Exclude
    public static Task<List<User>> getAll() {
        return new FirebaseDBUtils<User>(User.class).getAll();
    }

    @Exclude
    public static Task<List<User>> filterAllByParamEqualTo(String param, Object value) {
        return new FirebaseDBUtils<User>(User.class).filterAllByParamEqualTo(param, value);
    }

    @Exclude
    public Task<List<Organization>> getOrganizations() {
        final List<Organization> result = new ArrayList<>();
        if (memberOrganizationIds != null)
            memberOrganizationIds = new ArrayList<>();
        if (adminOrganizationIds != null)
            memberOrganizationIds = new ArrayList<>();
        return Organization.getByIds(memberOrganizationIds).continueWithTask(new Continuation<List<Organization>, Task<List<Organization>>>() {
            @Override
            public Task<List<Organization>> then(@NonNull Task<List<Organization>> task) throws Exception {
                result.addAll(task.getResult());
                return Organization.getByIds(adminOrganizationIds);
            }
        }).continueWith(new Continuation<List<Organization>, List<Organization>>() {
            @Override
            public List<Organization> then(@NonNull Task<List<Organization>> task) throws Exception {
                result.addAll(task.getResult());
                return result;
            }
        });
    }

    @Exclude
    public Task<List<Event>> getEventsAttended() {
        final List<Event> result = new ArrayList<>();
        return SignIn.filterAllByParamEqualTo("userId", getRef().getKey()).continueWithTask(new Continuation<List<SignIn>, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<List<SignIn>> task) throws Exception {
                List<SignIn> signInList = task.getResult();
                List<Task<Event>> tasks = new ArrayList<Task<Event>>();
                for (SignIn signIn : signInList) {
                    String eventId = signIn.getEventId();
                    String orgId = signIn.getOrganizationId();
                    Task<Event> t = Event.getById(orgId, eventId).addOnSuccessListener(new OnSuccessListener<Event>() {
                        @Override
                        public void onSuccess(Event event) {
                            result.add(event);
                        }
                    });
                    tasks.add(t);
                }
                Task[] taskArray = (Task[]) tasks.toArray();
                return Tasks.whenAll(taskArray);
            }
        }).continueWith(new Continuation<Void, List<Event>>() {
            @Override
            public List<Event> then(@NonNull Task<Void> task) throws Exception {
                return result;
            }
        });
    }

    @Exclude
    private boolean canSignInSync(Event event) {
        Date start = new Date(event.getStartTime());
        Date end = new Date(event.getEndTime());
        Date now = new Date();
        return now.getTime() >= start.getTime() && now.getTime() <= end.getTime();
    }

    @Exclude
    public Task<Boolean> canSignIn(final Event event) {
        return getEventsAttended().continueWith(new Continuation<List<Event>, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<List<Event>> task) throws Exception {
                List<Event> events = task.getResult();
                return canSignInSync(event) && !events.contains(event);
            }
        });
    }

    @Exclude
    public Task<Void> signIn(Event event) {
        FirebaseDBUtils<SignIn> fdbu = new FirebaseDBUtils<SignIn>(SignIn.class);
        SignIn signIn = new SignIn();
        signIn.setRef(fdbu.getNewChildRef());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date today = new Date();
        String time = df.format(today);

        signIn.setEventId(event.getRef().getKey());
        signIn.setOrganizationId(event.getRef().getParent().getKey());
        signIn.setUserId(getRef().getKey());
        signIn.setTime(time);

        return signIn.pushToDB();
    }

    @Exclude
    protected void copy(FirebaseObject o) {
        User other = null;
        try {
            other = (User) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("can't cast object to SignIn");
        }

        name = other.name;
        email = other.email;
        profPicUrl = other.profPicUrl;
        memberOrganizationIds = other.memberOrganizationIds;
        adminOrganizationIds = other.adminOrganizationIds;
        birthday = other.birthday;
        phoneNumber = other.phoneNumber;
        race = other.race;
        gender = other.gender;
        occupation = other.occupation;
        school = other.school;
        major = other.major;
        year = other.year;
        company = other.company;
        jobTitle = other.jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfPicUrl() {
        return profPicUrl;
    }

    public void setProfPicUrl(String profPicUrl) {
        this.profPicUrl = profPicUrl;
    }

    public List<String> getMemberOrganizationIds() {
        return memberOrganizationIds;
    }

    public void setMemberOrganizationIds(List<String> memberOrganizationIds) {
        this.memberOrganizationIds = memberOrganizationIds;
    }

    public List<String> getAdminOrganizationIds() {
        return adminOrganizationIds;
    }

    public void setAdminOrganizationIds(List<String> adminOrganizationIds) {
        this.adminOrganizationIds = adminOrganizationIds;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


}
