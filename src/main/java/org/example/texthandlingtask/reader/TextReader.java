package org.example.texthandlingtask.reader;

import org.example.texthandlingtask.exception.TextHandlingException;

public interface TextReader {
    String readText(String filePath) throws TextHandlingException;
}
