package com.rezdy.lunch.repository;

import com.rezdy.lunch.models.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,String>, JpaSpecificationExecutor<Recipe> {
    Recipe findByTitleIgnoreCase(String title);
}
