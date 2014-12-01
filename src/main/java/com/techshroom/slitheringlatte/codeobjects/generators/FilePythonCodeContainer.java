package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.codeobjects.SavableCodeContainer;

final class FilePythonCodeContainer
        extends StreamPythonCodeContainer implements SavableCodeContainer {
    private final File file;
    private boolean closed;

    FilePythonCodeContainer(String file) throws FileNotFoundException {
        super(new FileReader(file));
        this.file = new File(file);
    }

    @Override
    public boolean save() {
        if (closed) {
            throw new IllegalStateException("closed");
        }
        try (Writer writer = Files.newBufferedWriter(file.toPath())) {
            writer.write(getAllCode());
            return true;
        } catch (IOException e) {
            if (Options.DEBUG) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            closed = true;
        }
    }
}
