package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.array.GenerateArray;
import com.techshroom.slitheringlatte.codeobjects.LoadableCodeContainer;
import com.techshroom.slitheringlatte.codeobjects.PythonCodeContainer;

class StreamPythonCodeContainer implements PythonCodeContainer,
        LoadableCodeContainer {
    private final Reader source;
    private String data;
    private Collection<String> dataAsColl;

    StreamPythonCodeContainer(Reader reader) {
        this.source = reader;
    }

    @Override
    public boolean load() {
        try {
            this.data = CharStreams.toString(this.source);
        } catch (IOException e) {
            System.err.println("Couldn't load " + this.source);
            if (Options.DEBUG) {
                e.printStackTrace();
            }
            this.data = "raise Error('Not compiled')";
            this.dataAsColl = ImmutableList.of(this.data);
            return false;
        }
        this.dataAsColl =
                ImmutableList.copyOf(GenerateArray.ofLinesInString(this.data));
        return true;
    }

    @Override
    public Collection<String> getLines() {
        return this.dataAsColl;
    }

    @Override
    public String getAllCode() {
        return this.data;
    }

    @Override
    public void close() throws IOException {
        this.source.close();
    }
}
