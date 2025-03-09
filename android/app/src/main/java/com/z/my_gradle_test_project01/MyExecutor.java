package com.z.my_gradle_test_project01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor {
    private static MyExecutor _instance;

    public static MyExecutor getInstance() {
        if (_instance == null) {
            synchronized (MyExecutor.class) {
                if (_instance == null) {
                    _instance = new MyExecutor();
                }
            }
        }
        return _instance;
    }

    private ExecutorService executorService = null;

    private MyExecutor() {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public <T> T execute(Callable<T> callable) throws ExecutionException, InterruptedException {
        return executorService.submit(callable).get();
    }
}
