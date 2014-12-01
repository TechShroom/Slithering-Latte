package com.techshroom.slitheringlatte.codeobjects.generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

import com.techshroom.slitheringlatte.Options;

final class FilePythonCodeContainer
        extends StreamPythonCodeContainer {
    private final File file;

    FilePythonCodeContainer(String file) throws FileNotFoundException {
        super(new FileReader(file));
        this.file = new File(file);
    }

    @Override
    public boolean save() {
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
}
