package recipes.controller;
// RecipeController.java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.Recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger();


    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> addRecipe(@RequestBody Recipe recipe) {
        int recipeId = id.incrementAndGet();
        recipes.put(recipeId, recipe);
        return ResponseEntity.ok(Map.of("id", recipeId));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipes.get(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}