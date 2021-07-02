package translateclient;

import dto.generated.GeneratedWords;
import dto.translation.Translate;
import exceptions.TooManyRequestsException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslateClient {
    private static final Object OBJECT = new Object();
    private static final String TAG = "Custom logs";

    public static Logger logger = Logger.getLogger(TAG);
    private static TranslateClient translateClient;
    private final String BASE_URL = "https://cloud.yandex.ru/api/translate/";

    private final TranslateApi translateApi;

    private TranslateClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        translateApi = retrofit.create(TranslateApi.class);
    }

    public static TranslateClient getTranslateClient() {
        if (translateClient != null)
            return translateClient;

        synchronized (OBJECT) {
            if (translateClient == null)
                translateClient = new TranslateClient();
            return translateClient;
        }
    }

    public String getTranslate(GeneratedWords word) throws InterruptedException {

        List<String> words = new LinkedList<>();

        Map<String, String> apiVariables = new HashMap<>();
        apiVariables.put("sourceLanguageCode", "en");
        apiVariables.put("targetLanguageCode", "ru");


        Call<Translate> translateRequest = translateApi.sendRequest(apiVariables.get("sourceLanguageCode"),
                apiVariables.get("targetLanguageCode"), word.getEn());


        try {
            Response<Translate> response = translateRequest.execute();
            TranslateClient.logger.log(Level.INFO, "execute: URL is: " + translateRequest.request().url());
            if (response.isSuccessful()) {
                word.setRu(response.body().getTranslations().get(0).getText());
                TranslateClient.logger.log(Level.INFO, "execute: Translate for the word " + word.getEn() + " is: " + word.getRu());
            } else if (response.code() == 429) {
                throw new TooManyRequestsException();
            } else
                words.add("There is an error during request by link " + response.raw().request().url() + " . Error code is: " + response.code());
        } catch (IOException e) {
            words.add("Something went wrong. Error is: " + e.getMessage());
            e.printStackTrace();
        } catch (TooManyRequestsException e) {
            Thread.sleep(10000);
            e.printStackTrace();
            return getTranslate(word);
        }

        return word.getRu();
    }

}