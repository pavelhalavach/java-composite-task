package org.example.texthandlingtask.parser.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LexemeParserTest {

    private LexemeParser lexemeParser;

    @BeforeEach
    void setUp() {
        lexemeParser = new LexemeParser();
    }

    @Test
    void parseProducesOneSymbolLeafPerCharacter() {
        CustomTextComponent lexeme = lexemeParser.parse("cat");

        assertEquals(ComponentType.LEXEME, lexeme.getType());
        assertEquals(3, lexeme.getComponents().size());
        assertTrue(lexeme.getComponents().stream().allMatch(c -> c.getType() == ComponentType.SYMBOL));
    }

    @Test
    void restoreRoundTripsTheOriginalLexeme() {
        CustomTextComponent lexeme = lexemeParser.parse(" world");

        assertEquals(" world", lexeme.restore());
    }

    @Test
    void emptyInputProducesAnEmptyComponent() {
        CustomTextComponent lexeme = lexemeParser.parse("");

        assertTrue(lexeme.getComponents().isEmpty());
        assertEquals("", lexeme.restore());
    }
}
