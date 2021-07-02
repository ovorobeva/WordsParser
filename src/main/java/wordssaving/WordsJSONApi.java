package wordssaving;

import dto.generated.GeneratedWords;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface WordsJSONApi {

    @GET("words_source_v0.json")
    Call<List<GeneratedWords>> sendRequest();
}
