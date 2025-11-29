package org.creditto.creditto_service.global.config;

import java.util.UUID;

public class TxContext {
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void start() {
        context.set(UUID.randomUUID().toString());
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
