package com.example.englishhindidictionary.langauge_translator.utildata;

public class DictionaryUtils {
    private String antnym;
    private String mean;
    private String noun;
    private String synon;

    public String getNoun() {
        return this.noun;
    }

    public void setNoun(String str) {
        this.noun = str;
    }

    public String getMean() {
        return this.mean;
    }

    public void setMean(String str) {
        this.mean = str;
    }

    public String getSynonym() {
        return this.synon;
    }

    public void setSynonym(String str) {
        this.synon = str;
    }

    public String getAntonym() {
        return this.antnym;
    }

    public void setAntonym(String str) {
        this.antnym = str;
    }
}
