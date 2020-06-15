package com.example.project.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class BookData {
    private String title;
    private JsonObject imageLinks;
    private JsonArray authors;

    public JsonArray getAuthors() {
        return authors;
    }

    public void setAuthors(JsonArray authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




    public JsonObject getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(JsonObject imageLinks) {
        this.imageLinks = imageLinks;
    }


}

