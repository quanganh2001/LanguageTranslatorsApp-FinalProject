package com.example.englishhindidictionary.langauge_translator.utildata;

public class GetSetClass {
    int direction;
    String engMeaning;
    String engWord;
    int favourite;
    String hinMeaning;
    String hinWord;
    int history;


    int f207id;

    public int getId() {
        return this.f207id;
    }

    public void setId(int i) {
        this.f207id = i;
    }

    public String getEngWord() {
        return this.engWord;
    }

    public void setEngWord(String str) {
        this.engWord = str;
    }

    public String getHinWord() {
        return this.hinWord;
    }

    public void setHinWord(String str) {
        this.hinWord = str;
    }

    public String getEngMeaning() {
        return this.engMeaning;
    }

    public void setEngMeaning(String str) {
        this.engMeaning = str;
    }

    public String getHinMeaning() {
        return this.hinMeaning;
    }

    public void setHinMeaning(String str) {
        this.hinMeaning = str;
    }

    public int getFavourite() {
        return this.favourite;
    }

    public void setFavourite(int i) {
        this.favourite = i;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int i) {
        this.direction = i;
    }

    public int getHistory() {
        return this.history;
    }

    public void setHistory(int i) {
        this.history = i;
    }
}
