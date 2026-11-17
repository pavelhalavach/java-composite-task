package org.example.texthandlingtask.reader.impl;

import org.example.texthandlingtask.exception.TextHandlingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextReaderImplTest {

    private TextReaderImpl reader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        reader = new TextReaderImpl();
    }

    @Test
    void readTextReturnsTheFullFileContent() throws IOException, TextHandlingException {
        Path file = tempDir.resolve("sample.txt");
        Files.writeString(file, "Hello, file!", StandardCharsets.UTF_8);

        String content = reader.readText(file.toString());

        assertEquals("Hello, file!", content);
    }

    @Test
    void nullFileNameThrowsTextHandlingException() {
        TextHandlingException exception = assertThrows(TextHandlingException.class, () -> reader.readText(null));

        assertEquals("Provided file name is null.", exception.getMessage());
    }

    @Test
    void missingFileThrowsTextHandlingExceptionWrappingIOException() {
        Path missing = tempDir.resolve("does-not-exist.txt");

        TextHandlingException exception = assertThrows(TextHandlingException.class,
                () -> reader.readText(missing.toString()));

        assertTrue(exception.getMessage().startsWith("Failed to read file"));
        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test
    void invalidPathThrowsTextHandlingException() {
        // A NUL character is illegal in a path on every mainstream JVM/OS
        // and reliably triggers InvalidPathException.
        String invalidPath = "invalid\0path.txt";

        TextHandlingException exception = assertThrows(TextHandlingException.class,
                () -> reader.readText(invalidPath));

        assertTrue(exception.getMessage().contains("is invalid"));
    }
}
