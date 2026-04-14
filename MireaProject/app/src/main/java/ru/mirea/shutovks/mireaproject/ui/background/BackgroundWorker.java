package ru.mirea.shutovks.mireaproject.ui.background;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {
    private static final String TAG = "BackgroundWorker";

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Work started");

        try {
            Thread.sleep(5_000); // эмуляция 5-секундной задачи
        } catch (InterruptedException e) {
            return Result.failure();
        }

        Log.d(TAG, "Work finished");

        return Result.success();
    }
}
