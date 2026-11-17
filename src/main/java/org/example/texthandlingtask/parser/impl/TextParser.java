package org.example.texthandlingtask.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.composite.impl.TextComposite;
import org.example.texthandlingtask.parser.AbstractTextParser;

public class TextParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAGRAPH_REGEX = "(?<!\\r)(?=\\R[ \\t]+\\S)";

    @Override
    public CustomTextComponent parse(String text) {
        logger.log(Level.DEBUG,"Parsing text into paragraphs...");
        TextComposite textComposite = new TextComposite(ComponentType.TEXT);
        String[] paragraphs = text.split(PARAGRAPH_REGEX, -1);

        for (String paragraph : paragraphs) {
            if (nextParser != null) {
                textComposite.add(nextParser.parse(paragraph));
            }
        }
        return textComposite;
    }
}
