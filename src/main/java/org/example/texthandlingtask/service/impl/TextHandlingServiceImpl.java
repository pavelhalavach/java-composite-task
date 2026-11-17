package org.example.texthandlingtask.service.impl;

import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.service.MostCommonWordResult;
import org.example.texthandlingtask.service.TextHandlingService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextHandlingServiceImpl implements TextHandlingService {

    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+");
    private static final Pattern LEXEME_PATTERN = Pattern.compile("\\S+");

    @Override
    public MostCommonWordResult findMostSentencesWithCommonWord(CustomTextComponent text) {
        Map<String, List<String>> wordToSentences = new LinkedHashMap<>();
        sentencesOf(text).map(String::strip).forEach(sentence -> {
            for (String word : distinctWords(sentence)) {
                    wordToSentences.computeIfAbsent(word, key -> new ArrayList<>()).add(sentence);
            }
        });

        return wordToSentences.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .map(entry -> new MostCommonWordResult(entry.getKey(), entry.getValue().size(), entry.getValue()))
                .orElse(new MostCommonWordResult(null, 0, List.of()));
    }

    @Override
    public List<String> sortSentencesByLetterCount(CustomTextComponent text, char letter) {
        char target = Character.toLowerCase(letter);
        return sentencesOf(text)
                .map(String::strip)
                .sorted(Comparator.comparingLong(sentence -> countOccurrences(sentence, target)))
                .toList();
    }

    @Override
    public String swapFirstAndLastLexemeInEachSentence(CustomTextComponent text) {
        return sentencesOf(text)
                .map(this::swapFirstAndLastLexeme)
                .collect(Collectors.joining());
        }

        @Override
    public long countLetters(CustomTextComponent text) {
        return text.restore().codePoints().filter(Character::isLetter).count();
        }

        @Override
    public long countSymbols(CustomTextComponent text) {
        return text.restore().codePoints()
                .filter(codePoint -> !Character.isLetter(codePoint) && !Character.isWhitespace(codePoint))
                .count();
    }

    private Stream<String> sentencesOf(CustomTextComponent component) {
        return switch (component.getType()) {
            case SENTENCE -> Stream.of(component.restore());
            case TEXT, PARAGRAPH -> component.getComponents().stream().flatMap(this::sentencesOf);
            case LEXEME, SYMBOL -> throw new UnsupportedOperationException(
                    "Expected TEXT or PARAGRAPH, but got " + component.getType());
        };
    }

    private Set<String> distinctWords(String sentence) {
        return WORD_PATTERN.matcher(sentence).results()
                .map(match -> match.group().toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private long countOccurrences(String sentence, char targetLowerCase) {
        return sentence.chars().filter(c -> Character.toLowerCase(c) == targetLowerCase).count();
    }

    private String swapFirstAndLastLexeme(String sentence) {
        List<MatchResult> lexemes = LEXEME_PATTERN.matcher(sentence).results().toList();
        if (lexemes.size() < 2) {
            return sentence;
        }

        MatchResult first = lexemes.getFirst();
        MatchResult last = lexemes.getLast();

        return sentence.substring(0, first.start())
                + last.group()
                + sentence.substring(first.end(), last.start())
                + first.group()
                + sentence.substring(last.end());
    }
}