package org.example.texthandlingtask.service.impl;

import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.parser.AbstractTextParser;
import org.example.texthandlingtask.parser.impl.LexemeParser;
import org.example.texthandlingtask.parser.impl.ParagraphParser;
import org.example.texthandlingtask.parser.impl.SentenceParser;
import org.example.texthandlingtask.parser.impl.TextParser;
import org.example.texthandlingtask.service.MostCommonWordResult;
import org.example.texthandlingtask.service.TextHandlingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextHandlingServiceImplTest {

    private TextHandlingService service;
    private AbstractTextParser textParser;

    @BeforeEach
    void setUp() {
        textParser = new TextParser();
        AbstractTextParser paragraphParser = new ParagraphParser();
        AbstractTextParser sentenceParser = new SentenceParser();
        AbstractTextParser lexemeParser = new LexemeParser();

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);

        service = new TextHandlingServiceImpl();
    }

    // --- Task 1: sentences that share the same word ---------------------

    @Test
    void findsTheWordPresentInTheMostSentences() {
        String text = "The cat sat. The dog ran. A bird flew.";
        CustomTextComponent parsed = textParser.parse(text);

        MostCommonWordResult result = service.findMostSentencesWithCommonWord(parsed);

        assertEquals("the", result.word());
        assertEquals(2, result.sentenceCount());
        assertEquals(2, result.sentences().size());
    }

    @Test
    void wordMatchingIsCaseInsensitiveAndCountsEachSentenceOnce() {
        String text = "Cats and cats play. CATS sleep a lot. Dogs bark.";
        CustomTextComponent parsed = textParser.parse(text);

        MostCommonWordResult result = service.findMostSentencesWithCommonWord(parsed);

        assertEquals("cats", result.word());
        assertEquals(2, result.sentenceCount());
    }

    @Test
    void textWithoutAnyWordsProducesAnEmptyResult() {
        CustomTextComponent parsed = textParser.parse("");

        MostCommonWordResult result = service.findMostSentencesWithCommonWord(parsed);

        assertEquals(0, result.sentenceCount());
        assertTrue(result.sentences().isEmpty());
    }

    @Test
    void resultRecordHasValueBasedEquality() {
        MostCommonWordResult a = new MostCommonWordResult("cat", 2, List.of("Cats sat.", " A cat ran."));
        MostCommonWordResult b = new MostCommonWordResult("cat", 2, List.of("Cats sat.", " A cat ran."));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void resultRecordDefensivelyCopiesTheSentenceList() {
        List<String> mutable = new ArrayList<>(List.of("Sentence."));

        MostCommonWordResult result = new MostCommonWordResult("word", 1, mutable);
        mutable.add("Another.");

        assertEquals(1, result.sentences().size());
    }

    // --- Task 2: sort sentences by letter count --------------------------

    @Test
    void sortsSentencesByAscendingLetterCount() {
        String text = "Banana. Apple. Kiwi.";
        CustomTextComponent parsed = textParser.parse(text);

        List<String> sorted = service.sortSentencesByLetterCount(parsed, 'a');

        // "Kiwi." has 0 'a', "Apple." has 1 'a', "Banana." has 3 'a'.
        assertEquals(List.of("Kiwi.", "Apple.", "Banana."), sorted);
    }

    @Test
    void letterMatchingIsCaseInsensitive() {
        String text = "AAA. bbb.";
        CustomTextComponent parsed = textParser.parse(text);

        List<String> sorted = service.sortSentencesByLetterCount(parsed, 'a');

        assertEquals(List.of("bbb.", "AAA."), sorted);
    }

    @Test
    void tiesKeepTheirOriginalRelativeOrder() {
        String text = "One. Two. Six.";
        CustomTextComponent parsed = textParser.parse(text);

        // None of these sentences contain the letter 'z', so all counts are 0
        // and the stable sort must preserve the original order.
        List<String> sorted = service.sortSentencesByLetterCount(parsed, 'z');

        assertEquals(List.of("One.", "Two.", "Six."), sorted);
    }

    // --- Task 3: swap first and last lexeme, keep sentence length --------

    @Test
    void swapsEqualLengthLexemesCleanly() {
        CustomTextComponent parsed = textParser.parse("Hello world");

        String result = service.swapFirstAndLastLexemeInEachSentence(parsed);

        assertEquals("world Hello", result);
        assertEquals("Hello world".length(), result.length());
    }

    @Test
    void swapPreservesLengthWhenLexemesHaveDifferentSizesWithoutLosingAnyCharacters() {
        String sentence = "Hi there champions.";
        CustomTextComponent parsed = textParser.parse(sentence);

        String result = service.swapFirstAndLastLexemeInEachSentence(parsed);

        // Full words are swapped as-is - no padding, no truncation - and the
        // total length still matches, because the swap only trades places
        // between the two fragments.
        assertEquals("champions. there Hi", result);
        assertEquals(sentence.length(), result.length());
    }

    @Test
    void sentenceWithASingleLexemeIsLeftUnchanged() {
        CustomTextComponent parsed = textParser.parse("Hello");

        String result = service.swapFirstAndLastLexemeInEachSentence(parsed);

        assertEquals("Hello", result);
    }

    @Test
    void everySentenceInTheTextIsTransformedIndependently() {
        String text = "Hello world. Bye now.";
        CustomTextComponent parsed = textParser.parse(text);

        String result = service.swapFirstAndLastLexemeInEachSentence(parsed);

        String expectedSentence1 = "world." + " " + "Hello";
        String expectedSentence2 = " " + "now." + " " + "Bye";
        assertEquals(expectedSentence1 + expectedSentence2, result);
        assertEquals(text.length(), result.length());
    }
}
