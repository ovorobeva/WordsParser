import dto.generated.GeneratedWords;
import exceptions.TokenIsRequiredException;
import gettingwordsclient.WordsClient;
import translateclient.TranslateClient;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class ParserStarter {
    public static void main(String[] args) {
        String tokenFromArg;
        if (args.length == 0) {
            tokenFromArg = "Not found";
        } else tokenFromArg = args[0];

        Token token = null;
        try {
            token = new Token(tokenFromArg);
        } catch (TokenIsRequiredException e) {
            System.out.println("Token is required, please, specify your token when starting");
            e.printStackTrace();
            System.exit(-1);
        }

        String path = "src\\main\\resources";

        TranslateClient translateClient = TranslateClient.getTranslateClient();

        List<GeneratedWords> wordList;

        WordsProcessing wordsProcessing = new WordsProcessing();
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
        JSONCreator jsonCreator = new JSONCreator(wordList, path, token);
        jsonCreator.saveToFile();
    }
}
