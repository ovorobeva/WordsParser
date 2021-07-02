import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.generated.GeneratedWords;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class JSONCreator {
    String path;
    private List<GeneratedWords> words;
    Token token;

    public JSONCreator(List<GeneratedWords> words, String path, Token token) {
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
            pushToRemoteSteam();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void pushToRemoteSteam() {
        try {
            Repository repository = new FileRepository("D:\\Projects\\WordsParser\\.git");

        Git git = new Git(repository) ;
            Status status = git.status().call();
            git.commit().setAll(true).setMessage("Words json is updated")
                    .setAuthor("Olga Vorobeva", "passant.dlm@gmail.com").call();

            System.out.println("Uncommitted changes are: " + status.getUncommittedChanges());
            System.out.println("Changes are: " + status.getChanged());

            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish("git@github.com:ovorobeva/WordsParser.git"));
            remoteAddCommand.call();


            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("ovorobeva", token.getToken()));

            pushCommand.add("master");
            pushCommand.setRemote("origin");
            pushCommand.call();
        } catch (IOException | GitAPIException | URISyntaxException e) {
        e.printStackTrace();
    }

    }


    public void getFile() {

    }
}
