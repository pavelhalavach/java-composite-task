package org.example.texthandlingtask.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.composite.impl.TextComposite;
import org.example.texthandlingtask.parser.AbstractTextParser;

public class ParagraphParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String SENTENCE_REGEX = "(?<=[.!?])(?=\\s)";
    @Override
    public CustomTextComponent parse(String text) {
        logger.log(Level.DEBUG,"Parsing paragraph into sentences...");
        TextComposite textComposite = new TextComposite(ComponentType.PARAGRAPH);
        String[] sentences = text.split(SENTENCE_REGEX, -1);

        for (String sentence : sentences) {
            if (nextParser != null && !sentence.isBlank()) {
                textComposite.add(nextParser.parse(sentence));
            }
        }
        return textComposite;
    }
}
