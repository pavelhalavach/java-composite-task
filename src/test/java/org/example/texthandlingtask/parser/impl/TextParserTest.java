package org.example.texthandlingtask.parser.impl;

import org.example.texthandlingtask.composite.ComponentType;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.parser.AbstractTextParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextParserTest {
    private TextParser textParser;

    @BeforeEach
    void setUp() {
        textParser = new TextParser();
        AbstractTextParser paragraphParser = new ParagraphParser();
        AbstractTextParser sentenceParser = new SentenceParser();
        AbstractTextParser lexemeParser = new LexemeParser();

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
    }

    @Test
    void singleParagraphTextProducesOneParagraphNode() {
        CustomTextComponent text = textParser.parse("Hello world. Bye now.");

        assertEquals(ComponentType.TEXT, text.getType());
        assertEquals(1, text.getComponents().size());
        assertEquals(ComponentType.PARAGRAPH, text.getComponents().get(0).getType());
    }

    @Test
    void splitsOnIndentedLineBreaksIntoParagraphs() {
        String twoParagraphs = "First paragraph.\n    Second paragraph.";

        CustomTextComponent text = textParser.parse(twoParagraphs);

        assertEquals(2, text.getComponents().size());
    }

    @Test
    void restoreRoundTripsAMultiParagraphTextExactly() {
        String original = "It has survived not only five centuries, but also the leap into electronic\r\n"
                + "typesetting, remaining essentially unchanged.\r\n"
                + "    It is a long established fact that a reader will be distracted by the readable\r\n"
                + "content of a page when looking at its layout.\r\n"
                + "    Bye.";
        CustomTextComponent text = textParser.parse(original);

        assertEquals(original, text.restore());
    }
}
