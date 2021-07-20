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
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import wordsprocessing.WordsClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class WordsJSON {
    private List<GeneratedWords> words;
    private final String BASE_URL = "https://raw.githubusercontent.com/ovorobeva/WordsParser/master/src/main/resources/";
    List<GeneratedWords> loadedWords;
    private Token token;
    private WordsJSONApi wordsJSONApi;


    public WordsJSON(Token token) {
        this.token = token;
        this.loadedWords = loadFileFromRepository();
    }

    public int getLastId() {
        return loadedWords.size();
    }

    public void saveToFile(List<GeneratedWords> words) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONArray wordsArray = new JSONArray();

        for (GeneratedWords generatedWord : loadedWords) {
            JSONObject word = null;
            try {
                word = new JSONObject(gson.toJson(generatedWord));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            wordsArray.add(word);
        }
//todo: to check duplicates and uncensored words
        for (GeneratedWords generatedWord : words) {
            JSONObject word = null;
            try {
                word = new JSONObject(gson.toJson(generatedWord));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            wordsArray.add(word);
        }

        File targetFile = new File("D:\\Projects\\WordsParser\\src\\main\\resources\\words_source_v0.json");

        try (FileWriter writer = new FileWriter(targetFile)) {

            writer.write(wordsArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveFileToRepository();
    }

    private void saveFileToRepository() {
        try {
            Repository repository = new FileRepository("D:\\Projects\\WordsParser\\.git");
            System.setProperty("file.encoding", "UTF-8");
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


    private List<GeneratedWords> loadFileFromRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        wordsJSONApi = retrofit.create(WordsJSONApi.class);

        Call<List<GeneratedWords>> jsonFromRepositoryRequest = wordsJSONApi.sendRequest();

        List<GeneratedWords> responseBody = new LinkedList<>();
        try {
            Response<List<GeneratedWords>> response = jsonFromRepositoryRequest.execute();
            if (response.isSuccessful()) {
                responseBody = response.body();
            } else

                WordsClient.logger.log(Level.SEVERE, "There is an error during request by link " + response.raw().request().url() + " . Error code is: " + response.code());
        } catch (IOException e) {
            WordsClient.logger.log(Level.SEVERE, "Something went wrong during request by link " + jsonFromRepositoryRequest.request().url() + " . Error is: " + e.getMessage());
            e.printStackTrace();
        }
        return responseBody;
    }
}
