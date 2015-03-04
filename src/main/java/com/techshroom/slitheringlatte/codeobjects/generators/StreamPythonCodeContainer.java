package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.techshroom.slitheringlatte.codeobjects.LoadableCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

class StreamPythonCodeContainer implements PythonCodeContainer,
        LoadableCodeContainer {
    private final Reader source;
    private String data;
    private Collection<String> dataAsColl;

    StreamPythonCodeContainer(Reader reader) {
        source = reader;
    }

    @Override
    public boolean load() {
        try {
            data = CharStreams.toString(source);
        } catch (IOException e) {
            System.err.println("Couldn't load " + source);
            data = "raise Error('Not compiled')";
            dataAsColl = ImmutableList.of(data);
            return false;
        }
        // disabled as irrelevant
        // dataAsColl =
        // ImmutableList.copyOf(GenerateArray.ofLinesInString(data));
        return true;
    }

    @Override
    public Collection<String> getLines() {
        return dataAsColl;
    }

    @Override
    public String getAllCode() {
        return data;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}
