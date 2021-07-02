import dto.generated.GeneratedWords;
import exceptions.TokenIsRequiredException;
import translates.Translation;
import wordsprocessing.WordsProcessing;
import wordssaving.Token;
import wordssaving.WordsJSON;

import java.util.List;

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
            System.out.println("wordssaving.Token is required, please, specify your token when starting");
            e.printStackTrace();
            System.exit(-1);
        }

        String path = "src\\main\\resources";

        WordsProcessing wordsProcessing = new WordsProcessing();
        List<GeneratedWords> wordList = new Translation().getTranslates(wordsProcessing);

        WordsJSON wordsJson = new WordsJSON(wordList, path, token);
        wordsJson.saveToFile();
    }
}
