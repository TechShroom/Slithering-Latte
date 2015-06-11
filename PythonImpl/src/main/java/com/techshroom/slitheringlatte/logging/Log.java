package com.techshroom.slitheringlatte.logging;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public abstract class Log implements MessageLogger {

    private static final Map<Class<?>, Log> logImplementations =
            new HashMap<>();

    // this field uses a fully-qualified class for readability
    private static final Class<?> defaultImplementationClass =
            org.apache.logging.log4j.Logger.class;

    public static final Log getDefaultLogImplementation() {
        return logImplementations.get(defaultImplementationClass);
    }

    public static final Log getLogImplementation(Class<?> backingLoggerClass) {
        checkNotNull(backingLoggerClass);
        Log impl = logImplementations.get(backingLoggerClass);
        if (impl == null) {
            checkState(!logImplementations.containsKey(backingLoggerClass),
                    "null value in map");
            Optional<Log> gen = findLogImplementation(backingLoggerClass);
            checkArgument(gen.isPresent(), "%s has no Log implementation",
                    backingLoggerClass);
            logImplementations.put(backingLoggerClass, impl = gen.get());
        }
        return impl;
    }

    private static final Optional<Log> findLogImplementation(
            Class<?> backingLoggerClass) {
        return StreamSupport
                .stream(ServiceLoader.load(Log.class).spliterator(), false)
                .filter(Predicate.isEqual(backingLoggerClass)).findFirst();
    }

    public abstract Class<?> getBackingLoggerClass();

}
