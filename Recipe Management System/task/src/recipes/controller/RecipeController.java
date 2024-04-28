package recipes.controller;
// RecipeController.java
import org.springframework.web.bind.annotation.*;
import recipes.Recipe;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private Recipe currentRecipe = new Recipe();

    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        this.currentRecipe = recipe;
    }

    @GetMapping
    public Recipe getRecipe() {
        return this.currentRecipe;
    }
}