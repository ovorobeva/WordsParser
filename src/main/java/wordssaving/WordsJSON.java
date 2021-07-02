package wordssaving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.generated.GeneratedWords;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WordsJSON {
    String path;
    private List<GeneratedWords> words;
    Token token;

    public WordsJSON(List<GeneratedWords> words, String path, Token token) {
        this.words = words;
        this.path = path;
        this.token = token;

    }

    public void saveToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONArray wordsArray = new JSONArray();
        File targetFile = new File(path + "\\words_source_v0.json");

        try (FileWriter writer = new FileWriter(targetFile)) {
            for (GeneratedWords generatedWord : words) {
                JSONObject word = new JSONObject(gson.toJson(generatedWord));
                wordsArray.add(word);
            }

            writer.write(wordsArray.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        saveFileToRepository();
    }

    private void saveFileToRepository() {
        try {
            Repository repository = new FileRepository("D:\\Projects\\WordsParser\\.git");

        Git git = new Git(repository) ;
            Status status = git.status().call();
            System.out.println("Uncommitted changes before commit are: " + status.getUncommittedChanges());
            System.out.println("Changes before commit are: " + status.getChanged());

            git.commit().setAll(true).setMessage("Words json is updated")
                    .setAuthor("Olga Vorobeva", "passant.dlm@gmail.com").call();

            System.out.println("Uncommitted changes after commit are: " + status.getUncommittedChanges());
            System.out.println("Changes after commit are: " + status.getChanged());


            PushCommand pushCommand = git.push();
            pushCommand.setRemote("git@github.com:ovorobeva/WordsParser.git");
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("ovorobeva", token.getToken()));

            pushCommand.add("master");
            pushCommand.setRemote("origin");
            pushCommand.call();

        } catch (IOException | GitAPIException e) {
        e.printStackTrace();
    }

    }


    public void getFileFromRepository() {

    }
}