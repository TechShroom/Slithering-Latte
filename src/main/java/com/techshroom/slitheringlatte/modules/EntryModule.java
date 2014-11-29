package com.techshroom.slitheringlatte.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.techshroom.slitheringlatte.EntryPoint;

/**
 * Entry point binding.
 * 
 * @author Kenzie Togami
 */
public class EntryModule
        extends AbstractModule {
    private EntryPoint lazyEntryPoint;

    @Override
    protected void configure() {
    }

    @Provides
    private EntryPoint getEntryPoint(@Named("input") String input,
            @Named("output") String output, @Named("debug") boolean debug) {
        if (lazyEntryPoint == null) {
            lazyEntryPoint = new EntryPoint(input, output, debug);
        }
        return lazyEntryPoint;
    }
}
