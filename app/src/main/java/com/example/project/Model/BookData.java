package com.example.project.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class BookData {
    private String title;
    private JsonObject imageLinks;
    private JsonArray authors;


    private String image;
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

    public String getImageLink(String type) {
        return image!=null? image:imageLinks.get(type).toString();
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public JsonObject getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(JsonObject imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String transformAuthors(){
        StringBuilder authorsBuilder = new StringBuilder();
        if (authors!=null)
            for (JsonElement author :authors)
                if(author!=null)
                    authorsBuilder.append(author.toString()+"\n");
        return authorsBuilder.toString();
    }

}


