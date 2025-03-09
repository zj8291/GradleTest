package com.z.my_gradle_test_project01.model;

import java.util.ArrayList;
import java.util.List;

/**
 * MyTestService的数据包装，用于通知外部数据更新
 */
public class MyServiceLiveData<T> {

    public T data;

    private final List<OnDataChangedListener<T>> _listeners = new ArrayList<>();

    public MyServiceLiveData(T data) {
        this.data = data;
    }

    public void changeData(T newData) {
        data = newData;
        notifyListener(data);
    }

    public MyServiceLiveData<T> changeDataWithResult(T newData) {
        data = newData;
        notifyListener(data);
        return this;
    }


    public interface OnDataChangedListener<T> {
        void onDataChanged(T data);
    }

    private void notifyListener(T data) {
        for (OnDataChangedListener<T> listener : _listeners) {
            listener.onDataChanged(data);
        }
    }

    public void addListener(OnDataChangedListener<T> listener) {
        _listeners.add(listener);
    }
}