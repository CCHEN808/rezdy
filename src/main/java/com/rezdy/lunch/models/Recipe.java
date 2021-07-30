package com.rezdy.lunch.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe",referencedColumnName = "title"),
            inverseJoinColumns = @JoinColumn(name = "ingredient",referencedColumnName = "title"))
    private Set<Ingredient> ingredients;

    @JsonIgnore
    @Transient
    private LocalDate earliestBestBefore;

    public String getTitle() {
        return title;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public LocalDate getEarliestBestBefore() {
        return earliestBestBefore;
    }

    public void setEarliestBestBefore(LocalDate earliestBestBefore) {
        this.earliestBestBefore = earliestBestBefore;
    }
}
