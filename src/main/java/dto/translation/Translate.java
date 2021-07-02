package dto.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translate {

    private List<Translation> translations = null;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}