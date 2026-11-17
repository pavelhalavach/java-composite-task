package org.example.texthandlingtask.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.composite.impl.TextComposite;
import org.example.texthandlingtask.parser.AbstractTextParser;

public class SentenceParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final String LEXEME_REGEX = "(?<=\\S)(?=\\s)";

    @Override
    public CustomTextComponent parse(String text) {
        logger.log(Level.DEBUG, "Parsing sentences into lexemes...");
        TextComposite sentenceComposite = new TextComposite(ComponentType.SENTENCE);
        String[] lexemes = text.split(LEXEME_REGEX, -1);

        for (String lexeme : lexemes) {
            if (nextParser != null && !lexeme.isBlank()) {
                sentenceComposite.add(nextParser.parse(lexeme));
            }
        }
        return sentenceComposite;
    }
}
