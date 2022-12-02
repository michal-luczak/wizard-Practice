package me.taison.wizardpractice.service;

import java.util.concurrent.*;

public class Service {
    private static final ExecutorService service = Executors.newScheduledThreadPool(8);

    public static Future<?> submit(Runnable runnable) {
        return service.submit(runnable);
    }

    public static <V> Future<V> submit(Callable<V> callable) {
        return service.submit(callable);
    }

    public static <T> Future<T> submit(Runnable runnable, T result) {
        return service.submit(runnable, result);
    }

    public static void shutdown() {
        service.shutdown();
    }
}
