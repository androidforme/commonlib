package com.wangduoyu.lib.commonlib.base.repository;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppExecutors {
    private static AppExecutors sInstance;
    private final Executor mDiskIO;
    private final Scheduler mDiskIOScheduler = Schedulers.io();
    private final Executor mNetWorkIO;
    private final Scheduler mNetWorkIOScheduler;
    private final AppExecutors.HandleExecutor mMainExecutor;
    private final Scheduler mMainScheduler;
    private final Executor mComputationExecutor;
    private final Scheduler mComputationScheduler;
    private final HandlerThread mBackgroundThread;
    private final AppExecutors.HandleExecutor mBackgroundExecutor;
    private final Scheduler mBackgroundScheduler;


    private static AppExecutors getInstance() {
        if (sInstance == null) {
            Class var0 = AppExecutors.class;
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new AppExecutors();
                }
            }
        }
        return sInstance;
    }

    private AppExecutors() {
        this.mDiskIO = new AppExecutors.SchedulerExecutor(this.mDiskIOScheduler);
        this.mNetWorkIOScheduler = Schedulers.io();
        this.mNetWorkIO = new AppExecutors.SchedulerExecutor(this.mNetWorkIOScheduler);
        this.mMainExecutor = new AppExecutors.HandleExecutor(new Handler(Looper.getMainLooper()));
        this.mMainScheduler = Schedulers.from(mMainExecutor);
        this.mComputationScheduler = Schedulers.computation();
        this.mComputationExecutor = new AppExecutors.SchedulerExecutor(this.mComputationScheduler);
        this.mBackgroundThread = new HandlerThread("AppExecutors_BackgroundThread");
        this.mBackgroundThread.start();
        this.mBackgroundExecutor = new AppExecutors.HandleExecutor(new Handler(this.mBackgroundThread.getLooper()));
        this.mBackgroundScheduler = Schedulers.from(this.mBackgroundExecutor);
    }

    public static Executor diskIO() {
        return getInstance().mDiskIO;
    }

    public static Scheduler DISK_IO() {
        return getInstance().mDiskIOScheduler;
    }

    public static void runDiskIO(NameRunnable runnable) {
        getInstance().mDiskIO.execute(runnable);
    }

    public static Executor networkIO() {
        return getInstance().mNetWorkIO;
    }

    public static Scheduler NETWORK_IO() {
        return getInstance().mNetWorkIOScheduler;
    }

    public static void runNetworkIO(NameRunnable runnable) {
        getInstance().mNetWorkIO.execute(runnable);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Executor mainThread() {
        return getInstance().mMainExecutor;
    }

    public static Scheduler MAIN_THREAD() {
        return getInstance().mMainScheduler;
    }

    public static void runOnMainThread(NameRunnable runnable) {
        getInstance().mMainExecutor.execute(runnable);
    }

    public static void runOnMainThread(Runnable runnable) {
        getInstance().mMainExecutor.execute(runnable);
    }

    public static void runOnMainThread(NameRunnable runnable, Long delay) {
        getInstance().mMainExecutor.postDelay(runnable, delay);
    }

    public static void runOnMainThread(Runnable runnable, Long delay) {
        getInstance().mMainExecutor.postDelay(runnable, delay);
    }

    public static void removeMainRunnable(Runnable runnable) {
        getInstance().mMainExecutor.removeRunnable(runnable);
    }

    public static void removeMainRunnable(NameRunnable runnable) {
        getInstance().mMainExecutor.removeRunnable(runnable);
    }

    public static Looper backgroundThreadLooper() {
        return getInstance().mBackgroundThread.getLooper();
    }

    public static void runOnBackground(NameRunnable runnable, long delay) {
        getInstance().mBackgroundExecutor.postDelay(runnable, delay);
    }

    public static void runOnBackground(NameRunnable runnable) {
        getInstance().mBackgroundExecutor.execute(runnable);
    }

    public static Handler getBackgroundHandler() {
        return getInstance().mBackgroundExecutor.mHandler;
    }

    public static Executor background() {
        return getInstance().mBackgroundExecutor;
    }

    public static Scheduler BACKGROUND() {
        return getInstance().mBackgroundScheduler;
    }

    public static Scheduler COMPUTATION() {
        return getInstance().mComputationScheduler;
    }

    public static Executor computation() {
        return getInstance().mComputationExecutor;
    }

    public static Executor worker() {
        return getInstance().mDiskIO;
    }

    public static Scheduler WORKER() {
        return getInstance().mDiskIOScheduler;
    }

    public static void runOnWorkThread(Runnable runnable){
        getInstance().mDiskIO.execute(runnable);
    }

    public static void runOnWorkThread(NameRunnable runnable){
        getInstance().mDiskIO.execute(runnable);
    }

    private static class SchedulerExecutor implements Executor {
        private final Scheduler mScheduler;

        private SchedulerExecutor(Scheduler scheduler) {
            this.mScheduler = scheduler;
        }

        @Override
        public void execute(Runnable command) {
            this.mScheduler.scheduleDirect(command);
        }
    }


    private static class HandleExecutor implements Executor {
        final Handler mHandler;

        private HandleExecutor(Handler handler) {
            this.mHandler = handler;
        }

        @Override
        public void execute(Runnable command) {
            this.mHandler.post(command);
        }

        void postDelay(Runnable runnable, long delay) {
            this.mHandler.postDelayed(runnable, delay);
        }

        void removeRunnable(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

}
