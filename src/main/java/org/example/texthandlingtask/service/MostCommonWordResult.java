package org.example.texthandlingtask.service;

import java.util.List;

public record MostCommonWordResult(String word, int sentenceCount, List<String> sentences) {

    public MostCommonWordResult {
        sentences = List.copyOf(sentences);
    }
}