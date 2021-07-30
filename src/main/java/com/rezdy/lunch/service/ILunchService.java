package com.rezdy.lunch.service;

import com.rezdy.lunch.models.Recipe;

import java.time.LocalDate;
import java.util.List;

public interface ILunchService {
    List<Recipe> getNonExpiredRecipesOnDate(LocalDate date);
    Recipe getRecipeByTitle(String title);
    List<Recipe> getRecipesExcludeIngredients(String ingredients);
}
