package com.rezdy.lunch.service;

import com.rezdy.lunch.models.Ingredient;
import com.rezdy.lunch.models.Recipe;
import com.rezdy.lunch.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;

@Service
public class LunchService implements ILunchService{
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private EntityManager entityManager;

    private List<Recipe> recipesSorted;

    @Override
    public List<Recipe> getNonExpiredRecipesOnDate(LocalDate date) {
        List<Recipe> recipes = loadRecipes(date);
        if(recipes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe Not Found");
        }
        sortRecipes(recipes);
        return recipesSorted;
    }

    @Override
    public List<Recipe> getRecipesExcludeIngredients(String ingredients){
        ArrayList <String> ingredientListToExclude = new ArrayList(Arrays.asList(ingredients.split(",")));
        List<Recipe> recipes = getRecipes();
        List<Recipe> resultRecipes = new ArrayList();
        for (Recipe r : recipes){
            //if recipes doesn't have exclude ingredient add to result list
            if(!recipesHasIngredient(r,ingredientListToExclude)){
                resultRecipes.add(r);
            }
        }
        //if recipe not found 404
        if(resultRecipes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe Not Found");
        }
        return resultRecipes;
    }

    @Override
    public Recipe getRecipeByTitle(String title){
        Recipe recipe = findRecipeByTitleIgnoreCase(title);
        if(recipe == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe Not Found");
        return recipe;
    }

    // sort recipes by minBestBefore date of it's ingredients
    // so recipes has earliest bestBefore ingredients will be on the bottom
    private void sortRecipes(List<Recipe> recipes) {
        for(Recipe r:recipes){
            LocalDate minBestBefore =null;
            boolean fristMinBestfore = true;
            for(Ingredient i:r.getIngredients()){
                if(fristMinBestfore) {
                    minBestBefore = i.getBestBefore();
                }
                fristMinBestfore = false;
                if(i.getBestBefore().compareTo(minBestBefore) < 0) {
                    minBestBefore = i.getBestBefore();
                }
            }
            r.setEarliestBestBefore(minBestBefore);
        }
        Collections.sort(recipes, (x, y) -> y.getEarliestBestBefore().compareTo(x.getEarliestBestBefore()));
        recipesSorted = recipes; //TODO sort recipes considering best-before
    }

    //return all recipes whitin useByDate
    private List<Recipe> loadRecipes(LocalDate date) {
        List<Recipe> recipes = getRecipes();
        List<Recipe> result = new ArrayList<>();
        for(Recipe r: recipes){
            boolean hasExpiriedIngredient = false;
            for(Ingredient ingredient: r.getIngredients()){
                if(date.compareTo(ingredient.getUseBy()) > 0){
                    hasExpiriedIngredient = true;
                    break;
                }
            }
            if(!hasExpiriedIngredient)
            result.add(r);
        }
        return result;
    }

    private boolean recipesHasIngredient(Recipe recipe, List<String> ingredient){
        for(Ingredient i: recipe.getIngredients()){
            String ingredientTitle = i.getTitle().toLowerCase();
            if(ingredient.stream().anyMatch(ingredientTitle::equalsIgnoreCase)){
                return true;
            }
        }
        return false;
    }

    private List<Recipe> getRecipes(){
        List<Recipe> allRecipes = new ArrayList<>();
        recipeRepository.findAll().forEach(allRecipes::add);
        return allRecipes;
    }

    private Recipe findRecipeByTitleIgnoreCase(String title){
        return recipeRepository.findByTitleIgnoreCase(title);
    }
}
