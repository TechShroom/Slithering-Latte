package com.techshroom.slitheringlatte.logging.log4j;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import com.techshroom.slitheringlatte.logging.DefaultLevel;
import com.techshroom.slitheringlatte.logging.Level;
import com.techshroom.slitheringlatte.logging.Log;

@AutoService(Log.class)
public final class Log4jLog extends Log {

    private static final Map<Level, org.apache.logging.log4j.Level> LEVEL_MAP;

    static {
        ImmutableMap.Builder<Level, org.apache.logging.log4j.Level> b =
                ImmutableMap.builder();
        DefaultLevel[] values = DefaultLevel.values();
        org.apache.logging.log4j.Level[] log4Values =
                org.apache.logging.log4j.Level.values();
        for (int i = 0; i < values.length; i++) {
            b.put(values[i], log4Values[i]);
        }
        LEVEL_MAP = b.build();
    }

    private final Logger log = LogManager.getLogger("Slithering-Latte");

    public Log4jLog() {
    }

    private org.apache.logging.log4j.Level mapLevel(Level level) {
        return LEVEL_MAP.get(level);
    }

    @Override
    public void log(Level level, Object message) {
        this.log.log(mapLevel(level), message);
    }

    @Override
    public void logException(Exception exception) {
        this.log.error(exception);
    }

    @Override
    public Class<?> getBackingLoggerClass() {
        return Logger.class;
    }

}
