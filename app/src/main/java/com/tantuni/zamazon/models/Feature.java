package com.tantuni.zamazon.models;

import java.util.List;
import java.util.Set;

public class Feature {
    private String id;
    private String key;
    private List<String> options;
    private Set<SubCategory> subCategories;

    // Constructors
    public Feature() {

    }

    public Feature(String id, String key, List<String> options, Set<SubCategory> subCategories) {
        this.id = id;
        this.key = key;
        this.options = options;
        this.subCategories = subCategories;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
