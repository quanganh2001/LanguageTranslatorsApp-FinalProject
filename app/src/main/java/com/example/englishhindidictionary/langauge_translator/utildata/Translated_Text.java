package com.example.englishhindidictionary.langauge_translator.utildata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Translated_Text {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> text = null;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public List<String> getText() {
        return this.text;
    }

    public void setText(List<String> list) {
        this.text = list;
    }
}
