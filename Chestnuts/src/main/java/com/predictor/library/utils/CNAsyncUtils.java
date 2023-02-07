package com.predictor.library.utils;


import android.os.Handler;
import android.os.Looper;

import com.predictor.library.jni.ChestnutData;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 封装异步操作，模仿kotlin anko-common,
 * https://github.com/Kotlin/anko/blob/master/anko/library/static/commons/src/main/java/Async.kt
 * <p>
 * doAsync用于线程交互，切到子线程做一些操作后再切到主线程，
 * forceAsync用于将一些只能放在子线程的操作放在子线程，同时阻塞主线程等子线程返回值，
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CNAsyncUtils {
    public static ExecutorService backgroundExecutor = Executors.newScheduledThreadPool(
            2 * Runtime.getRuntime().availableProcessors());

    private CNAsyncUtils() {
    }

    public static <T> void postDelayed(final T t, final Function<T> block, long delay) {
        if (ChestnutData.getPermission()) {
            ContextHelper.handler.postDelayed(() -> {
                try {
                    block.apply(t);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }, delay);
        }
    }

    public static <T> void runOnUiThread(final T t, final Function<T> block) {
        if (ChestnutData.getPermission()) {
            if (Looper.getMainLooper().equals(Looper.myLooper())) {
                try {
                    block.apply(t);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            } else {
                ContextHelper.handler.post(() -> {
                    try {
                        block.apply(t);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
            }
        }
    }

    public static <T> Future<?> doAsync(
            T t,
            Function<AsyncContext<T>> task
    ) {
        return doAsync(t, backgroundExecutor, task);
    }

    public static <T> Future<?> doAsync(
            T t,
            ExecutorService executorService,
            Function<AsyncContext<T>> task
    ) {
        return doAsync(t, executorService, task);
    }

    public static <T> Future<?> doAsync(
            T t,
            Function<Throwable> exceptionHandler,
            Function<AsyncContext<T>> task
    ) {
        return doAsync(t, exceptionHandler, backgroundExecutor, task);
    }

    public static <T> Future<?> doAsync(
            T t,
            final Function<Throwable> exceptionHandler,
            ExecutorService executorService,
            final Function<AsyncContext<T>> task
    ) {
        final AsyncContext<T> context = new AsyncContext<>(new WeakReference<>(t));
        return executorService.submit(() -> {
            try {
                task.apply(context);
            } catch (Throwable t1) {
                if (exceptionHandler != null) {
                    try {
                        exceptionHandler.apply(t1);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        });
    }

    /**
     * 强行把操作放在子线程，主线程等待该子线程返回，
     */
    public static <R> R forceAsync(
            final Callable<R> block
    ) {
        return forceAsync(backgroundExecutor, block);
    }

    /**
     * 强行把操作放在子线程，主线程等待该子线程返回，
     */
    public static <R> R forceAsync(
            ExecutorService executorService,
            final Callable<R> block
    ) {
        Future<R> future = executorService.submit(() -> {
            try {
                return block.call();
            } catch (Throwable t) {
                throw t;
            }
        });
        try {
            return future.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public interface Function0 {
        void apply() throws Exception;
    }

    public interface Function<T> {
        void apply(T t) throws Exception;
    }

    public interface FunctionResult<T, R> {
        R apply(T t) throws Exception;
    }

    private static class ContextHelper {
        private static final Handler handler = new Handler(Looper.getMainLooper());

        private ContextHelper() {
        }
    }

    public static class AsyncContext<T> {
        public WeakReference<T> weakRef;

        public AsyncContext(WeakReference<T> weakRef) {
            this.weakRef = weakRef;
        }

        public T getRef() {
            return weakRef.get();
        }

        public boolean postDelayed(final Function<T> block, long delay) {
            final T ref = weakRef.get();
            if (ref == null) {
                return false;
            }
            ContextHelper.handler.postDelayed(() -> {
                try {
                    block.apply(ref);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }, delay);
            return true;
        }

        /**
         * @return 方法块没执行就返回false, 也就是弱引用被回收的情况，
         */
        public boolean uiThread(final Function<T> block) {
            final T ref = weakRef.get();
            if (ref == null) {
                return false;
            }
            if (Looper.getMainLooper().equals(Looper.myLooper())) {
                try {
                    block.apply(ref);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            } else {
                ContextHelper.handler.post(() -> {
                    try {
                        block.apply(ref);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
            }
            return true;
        }
    }
}
