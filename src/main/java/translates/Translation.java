package translates;

import dto.generated.GeneratedWords;
import wordsprocessing.WordsClient;
import wordsprocessing.WordsProcessing;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class Translation {
    public List<GeneratedWords> getTranslates(WordsProcessing wordsProcessing) {
        List<GeneratedWords> wordList;
        TranslateClient translateClient = TranslateClient.getTranslateClient();
        try {
            wordList = new LinkedList<>(wordsProcessing.getWords());
        } catch (InterruptedException e) {
            wordList = new LinkedList<>();
            e.printStackTrace();
        }

        WordsClient.logger.log(Level.INFO, "Parsing finished. Words are: " + wordList);

        try {
            for (GeneratedWords word : wordList) {
                translateClient.getTranslate(word);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wordList;
    }
}
