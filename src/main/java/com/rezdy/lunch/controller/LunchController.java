package com.rezdy.lunch.controller;

import com.rezdy.lunch.service.ILunchService;
import com.rezdy.lunch.service.LunchService;
import com.rezdy.lunch.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class LunchController {
    @Autowired
    private ILunchService lunchService;

    @GetMapping("/lunch")
    public List<Recipe> getRecipes(@RequestParam(value = "date") String date){
        return lunchService.getNonExpiredRecipesOnDate(LocalDate.parse(date));
    }

    @GetMapping("/getRecipeByTitle")
    public Recipe getRecipeByTitle(@RequestParam(value = "title") String title){
        return lunchService.getRecipeByTitle(title);
    }

    @GetMapping("/receipesExcludeIngredients")
    public List<Recipe> getRecipesExcludeIngredients(@RequestParam(value = "ingredients") String ingredients) throws Exception{
        return lunchService.getRecipesExcludeIngredients(ingredients);
    }
}
