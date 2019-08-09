package com.jess.arms.http.test;

import android.support.annotation.NonNull;

import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @author DrChen
 * @Date 2019/8/7 0007.
 * qq:1414355045
 */
public class TestLife implements ActivityLifecycleable {
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }
}
