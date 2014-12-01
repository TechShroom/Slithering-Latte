package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

class StreamPythonCodeContainer implements PythonCodeContainer {
    private final Reader source;
    private String data;
    private Collection<String> dataAsColl;

    StreamPythonCodeContainer(Reader reader) {
        source = reader;
    }

    @Override
    public void load() {
        try {
            data = CharStreams.toString(source);
            source.close();
        } catch (IOException e) {
            System.err.println("Couldn't load " + source);
            if (Options.DEBUG) {
                e.printStackTrace();
            }
            data = "raise Error('Not compiled')";
        }
        dataAsColl =
                ImmutableList.copyOf(data.replace("\r\n", "\n")
                        .replace('\r', '\n').split("\n"));
    }

    @Override
    public boolean save() {
        // this doesn't save.
        return false;
    }

    @Override
    public Collection<String> getLines() {
        return dataAsColl;
    }

    @Override
    public String getAllCode() {
        return data;
    }
}
