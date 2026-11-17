package org.example.texthandlingtask.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.composite.impl.SymbolLeaf;
import org.example.texthandlingtask.composite.impl.TextComposite;
import org.example.texthandlingtask.parser.AbstractTextParser;

public class LexemeParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CustomTextComponent parse(String text) {
        logger.log(Level.DEBUG, "Parsing lexeme into symbols...");
        TextComposite lexemeComposite = new TextComposite(ComponentType.LEXEME);
        char[] symbols = text.toCharArray();
        for (char symbol : symbols) {
            lexemeComposite.add(new SymbolLeaf(symbol));
        }

        return lexemeComposite;
    }
}
