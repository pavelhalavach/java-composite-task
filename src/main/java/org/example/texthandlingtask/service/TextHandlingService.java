package org.example.texthandlingtask.service;

import org.example.texthandlingtask.composite.CustomTextComponent;

import java.util.List;

public interface TextHandlingService {

    MostCommonWordResult findMostSentencesWithCommonWord(CustomTextComponent text);

    List<String> sortSentencesByLetterCount(CustomTextComponent text, char letter);

    String swapFirstAndLastLexemeInEachSentence(CustomTextComponent text);

    long countLetters(CustomTextComponent text);

    long countSymbols(CustomTextComponent text);
}
