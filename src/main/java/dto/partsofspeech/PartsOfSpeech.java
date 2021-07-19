package dto.partsofspeech;

import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartsOfSpeech {

    private String id;
    @Nullable
    private String partOfSpeech;
    private String attributionText;
    private String sourceDictionary;
    private String text;
    private String sequence;
    private Integer score;
    private List<Label> labels = null;
    private List<Citation> citations = null;
    private String word;
    private List<Object> relatedWords = null;
    private List<ExampleUse> exampleUses = null;
    private List<Object> textProns = null;
    private List<Object> notes = null;
    private String attributionUrl;
    private String wordnikUrl;
    private Map<String, Object> additionalProperties = new HashMap<>();

    @Override
    public String toString() {
        return partOfSpeech + ", ";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getSourceDictionary() {
        return sourceDictionary;
    }

    public void setSourceDictionary(String sourceDictionary) {
        this.sourceDictionary = sourceDictionary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Citation> getCitations() {
        return citations;
    }

    public void setCitations(List<Citation> citations) {
        this.citations = citations;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Object> getRelatedWords() {
        return relatedWords;
    }

    public void setRelatedWords(List<Object> relatedWords) {
        this.relatedWords = relatedWords;
    }

    public List<ExampleUse> getExampleUses() {
        return exampleUses;
    }

    public void setExampleUses(List<ExampleUse> exampleUses) {
        this.exampleUses = exampleUses;
    }

    public List<Object> getTextProns() {
        return textProns;
    }

    public void setTextProns(List<Object> textProns) {
        this.textProns = textProns;
    }

    public List<Object> getNotes() {
        return notes;
    }

    public void setNotes(List<Object> notes) {
        this.notes = notes;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }

    public String getWordnikUrl() {
        return wordnikUrl;
    }

    public void setWordnikUrl(String wordnikUrl) {
        this.wordnikUrl = wordnikUrl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}