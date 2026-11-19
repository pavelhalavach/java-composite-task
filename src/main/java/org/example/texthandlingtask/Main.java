package org.example.texthandlingtask;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.texthandlingtask.composite.CustomTextComponent;
import org.example.texthandlingtask.exception.TextHandlingException;
import org.example.texthandlingtask.parser.AbstractTextParser;
import org.example.texthandlingtask.parser.impl.LexemeParser;
import org.example.texthandlingtask.parser.impl.SentenceParser;
import org.example.texthandlingtask.parser.impl.ParagraphParser;
import org.example.texthandlingtask.parser.impl.TextParser;
import org.example.texthandlingtask.reader.TextReader;
import org.example.texthandlingtask.reader.impl.TextReaderImpl;
import org.example.texthandlingtask.service.MostCommonWordResult;
import org.example.texthandlingtask.service.TextHandlingService;
import org.example.texthandlingtask.service.impl.TextHandlingServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        AbstractTextParser textParser = new TextParser();
        AbstractTextParser paragraphParser = new ParagraphParser();
        AbstractTextParser sentenceParser = new SentenceParser();
        AbstractTextParser lexemeParser = new LexemeParser();
        TextHandlingService service = new TextHandlingServiceImpl();

        textParser.setNextParser(paragraphParser);
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);

        TextReader textReader = new TextReaderImpl();
        try {
            String text = textReader.readText("src/main/resources/text.txt");
            logger.log(Level.INFO,"Text read successfully: \n{}", text);
            CustomTextComponent textComposite = textParser.parse(text);

            String restoredText = textComposite.restore();
            logger.log(Level.INFO,"Text restored successfully: \n{}", restoredText);
            Files.writeString(new File("src/main/resources/restored_text.txt").toPath(), restoredText);

            logger.log(Level.INFO, "===========================================");
            MostCommonWordResult resultTask1 = service.findMostSentencesWithCommonWord(textComposite);
            logger.log(Level.INFO,"Text searched for common words successfully: \n{}", resultTask1);

            logger.log(Level.INFO, "===========================================");
            List<String> resultTask2 = service.sortSentencesByLetterCount(textComposite, 'y');
            logger.log(Level.INFO,"Text sorted successfully:");
            for (int i = 0; i < resultTask2.size(); i++) {
                logger.log(Level.INFO, "#" + (i + 1) + "[" + resultTask2.get(i) + "]");
            }

            logger.log(Level.INFO, "===========================================");
            String resultTask3 = service.swapFirstAndLastLexemeInEachSentence(textComposite);
            logger.log(Level.INFO,"Text swapped successfully: \n{}", resultTask3);


        } catch (TextHandlingException e) {
            logger.log(Level.ERROR,"Error reading text: {}", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}