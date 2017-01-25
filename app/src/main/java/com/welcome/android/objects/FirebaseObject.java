package com.welcome.android.objects;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kraji on 1/12/2017.
 */

public abstract class FirebaseObject {
    @Exclude
    private DatabaseReference ref;

    public FirebaseObject() {
    }

    @Exclude
    protected abstract void copy(FirebaseObject o);

    @Exclude
    public <T extends FirebaseObject> Task pullFromDB(final GenericTypeIndicator<T> type) {
        final TaskCompletionSource<Boolean> source = new TaskCompletionSource();
        Task<Boolean> task = source.getTask();
        getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                T temp = dataSnapshot.getValue(type);
                copy(temp);
                source.setResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                source.setException(databaseError.toException());
            }
        });
        return task;
    }

    @Exclude
    public DatabaseReference getRef() {
        return ref;
    }

    @Exclude
    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    @Exclude
    public Task pushToDB() {
        return ref.setValue(this);
    }
}
