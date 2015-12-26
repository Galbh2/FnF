package com.parse.starter;

import com.parse.ParseObject;

/**
 * Created by Gal on 12/2/2015.
 */
public class Offer extends ParseObject {

    private String title, description;
    private int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
