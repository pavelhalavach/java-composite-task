package org.example.texthandlingtask.parser.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SentenceParserTest {

    @Test
    void parseSplitsIntoLexemesAndRestoresExactly() {
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.setNextParser(new LexemeParser());

        CustomTextComponent sentence = sentenceParser.parse("Hello world");

        assertEquals(ComponentType.SENTENCE, sentence.getType());
        assertEquals(2, sentence.getComponents().size());
        assertTrue(sentence.getComponents().stream().allMatch(c -> c.getType() == ComponentType.LEXEME));
        assertEquals("Hello world", sentence.restore());
    }

    @Test
    void withoutANextParserNoLexemesAreCreated() {
        SentenceParser sentenceParser = new SentenceParser();

        CustomTextComponent sentence = sentenceParser.parse("Hello world");

        assertTrue(sentence.getComponents().isEmpty());
        assertEquals("", sentence.restore());
    }
}
