package com.techshroom.slitheringlatte.logging;

interface MessageLogger {

    void log(Level level, Object message);

    void logException(Exception exception);

    default void fatal(Object message) {
        log(DefaultLevel.FATAL, message);
    }

    default void error(Object message) {
        log(DefaultLevel.ERROR, message);
    }

    default void warn(Object message) {
        log(DefaultLevel.WARN, message);
    }

    default void info(Object message) {
        log(DefaultLevel.INFO, message);
    }

    default void debug(Object message) {
        log(DefaultLevel.DEBUG, message);
    }

    default void trace(Object message) {
        log(DefaultLevel.TRACE, message);
    }
}
