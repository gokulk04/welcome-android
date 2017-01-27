package com.welcome.android.utils;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.welcome.android.objects.Event;
import com.welcome.android.objects.FirebaseObject;
import com.welcome.android.objects.Organization;
import com.welcome.android.objects.SignIn;
import com.welcome.android.objects.User;
import com.welcome.android.objects.iBeacon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishnan on 1/15/17.
 */

public class FirebaseDBUtils<T extends FirebaseObject> {
    private final String wrongFetchStr = "wrong fetch method";
    private final String cantCastStr = "cant cast value to double, String, or boolean";
    private final Class[] types = {Event.class, Organization.class, SignIn.class, User.class, iBeacon.class};
    private final String[] containingRefStrs = {"Events", "Organizations", "SignIns", "Users", "iBeacons"};
    private int i;

    public FirebaseDBUtils(Class<T> type) {
        i = 0;
        boolean found = false;
        for (Class t : types) {
            if (t.equals(type)) {
                found = true;
                break;
            }
            i++;
        }
        if (!found) throw new RuntimeException("type is not valid");
    }

    public DatabaseReference getNewChildRef(String childKey) {
        return getContainingRef().child(childKey);
    }

    public DatabaseReference getNewChildRef() {
        return getContainingRef().push();
    }

    private boolean has1SubContainingRefs() {
        return getType().equals(Event.class);
    }

    private DatabaseReference getContainingRef() {
        return FirebaseDatabase.getInstance().getReference().child(containingRefStrs[i]);
    }

    private Class<T> getType() {
        return types[i];
    }

    private Task<T> fetchSingleByRef(DatabaseReference ref) {
        final TaskCompletionSource<T> source = new TaskCompletionSource<T>();
        Task<T> task = source.getTask();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T object = dataSnapshot.getValue(getType());
                object.setRef(dataSnapshot.getRef());
                source.setResult(object);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                source.setException(databaseError.toException());
            }
        });
        return task;
    }

    private Task<List<T>> fetchMultipleByRef(DatabaseReference ref) {
        final TaskCompletionSource<List<T>> source = new TaskCompletionSource<List<T>>();
        Task<List<T>> task = source.getTask();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> objects = new ArrayList<T>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    T object = childSnapshot.getValue(getType());
                    object.setRef(childSnapshot.getRef());
                    objects.add(object);
                }
                source.setResult(objects);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                source.setException(databaseError.toException());
            }
        });
        return task;
    }

    private Task<List<T>> fetchMultipleByRef(DatabaseReference ref, final List<String> keys) {
        final TaskCompletionSource<List<T>> source = new TaskCompletionSource<List<T>>();
        Task<List<T>> task = source.getTask();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> objects = new ArrayList<T>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (keys.contains(childSnapshot.getKey())) {
                        T object = childSnapshot.getValue(getType());
                        object.setRef(childSnapshot.getRef());
                        objects.add(object);
                    }
                }
                source.setResult(objects);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                source.setException(databaseError.toException());
            }
        });
        return task;
    }

    public Task<List<T>> getByIds(List<String> ids) {
        if (has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef();
        return fetchMultipleByRef(ref, ids);
    }

    public Task<List<T>> getByIds(String parentId, List<String> ids) {
        if (!has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef().child(parentId);
        return fetchMultipleByRef(ref, ids);
    }

    public Task<T> getById(String id) {
        if (has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef().child(id);
        return fetchSingleByRef(ref);
    }

    public Task<T> getById(String parentId, String id) {
        if (!has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef().child(parentId).child(id);
        return fetchSingleByRef(ref);
    }

    public Task<List<T>> getAll() {
        if (has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef();
        return fetchMultipleByRef(ref);
    }

    public Task<List<T>> getAll(String parentId) {
        if (!has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef().child(parentId);
        return fetchMultipleByRef(ref);
    }

    public Task<List<T>> filterAllByParamEqualTo(String param, Object value) {
        if (has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef();
        Query q = ref.orderByChild(param);
        try {
            double v = (Double) value;
            q = q.equalTo(v);
        } catch (ClassCastException e) {
            try {
                String v = (String) value;
                q = q.equalTo(v);
            } catch (ClassCastException ee) {
                try {
                    boolean v = (Boolean) value;
                    q = q.equalTo(v);
                } catch (ClassCastException eee) {
                    throw new RuntimeException(cantCastStr);
                }
            }
        }
        return fetchMultipleByRef((DatabaseReference) q);
    }

    public Task<List<T>> filterAllByParamEqualTo(String parentId, String param, Object value) {
        if (!has1SubContainingRefs()) throw new RuntimeException(wrongFetchStr);
        DatabaseReference ref = getContainingRef().child(parentId);
        Query q = ref.orderByChild(param);
        try {
            double v = (Double) value;
            q = q.equalTo(v);
        } catch (ClassCastException e) {
            try {
                String v = (String) value;
                q = q.equalTo(v);
            } catch (ClassCastException ee) {
                try {
                    boolean v = (Boolean) value;
                    q = q.equalTo(v);
                } catch (ClassCastException eee) {
                    throw new RuntimeException(cantCastStr);
                }
            }
        }
        return fetchMultipleByRef((DatabaseReference) q);
    }
}
