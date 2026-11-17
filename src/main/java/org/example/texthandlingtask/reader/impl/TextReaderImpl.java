package org.example.texthandlingtask.reader.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.exception.TextHandlingException;
import org.example.texthandlingtask.reader.TextReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class TextReaderImpl implements TextReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String readText(String fileName) throws TextHandlingException {
        if (fileName == null) {
            throw new TextHandlingException("Provided file name is null.");
        }

        try {
            logger.log(Level.DEBUG, "Reading file '{}'.", fileName);
            Path filePath = Path.of(fileName);
            return Files.readString(filePath);
        } catch (InvalidPathException e) {
            throw new TextHandlingException("Provided file name '" + fileName + "' is invalid.", e);
        } catch (IOException e) {
            throw new TextHandlingException("Failed to read file '" + fileName + "'.", e);
        }
    }
}
