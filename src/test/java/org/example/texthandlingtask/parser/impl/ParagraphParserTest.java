package org.example.texthandlingtask.parser.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParagraphParserTest {
    private ParagraphParser paragraphParser;

    @BeforeEach
    void newFullyWiredParser() {
        paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.setNextParser(new LexemeParser());
        paragraphParser.setNextParser(sentenceParser);
    }

    @Test
    void splitsOnSentenceEndingPunctuationFollowedByWhitespace() {
        CustomTextComponent paragraph = paragraphParser.parse("Hello world. Bye now.");

        assertEquals(ComponentType.PARAGRAPH, paragraph.getType());
        assertEquals(2, paragraph.getComponents().size());
        assertTrue(paragraph.getComponents().stream().allMatch(c -> c.getType() == ComponentType.SENTENCE));
    }

    @Test
    void restoreRoundTripsTheOriginalParagraphExactly() {
        String original = "Hello world. Bye now.";

        CustomTextComponent paragraph = paragraphParser.parse(original);

        assertEquals(original, paragraph.restore());
    }
}
