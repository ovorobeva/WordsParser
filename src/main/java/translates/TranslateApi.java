package translates;

import dto.translation.Translate;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TranslateApi {

    @FormUrlEncoded
    @POST("translate")
    Call<Translate> sendRequest(@Field("sourceLanguageCode") String sourceLanguageCode,
                                @Field("targetLanguageCode") String targetLanguageCode,
                                @Field("texts") String word);

}
