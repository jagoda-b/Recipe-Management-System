package recipes.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipes.Recipe;
import recipes.service.RecipeService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> createRecipe(@Valid @RequestBody Recipe recipe) {
       Recipe savedRecipe = recipeService.save(recipe);

        return ResponseEntity.ok(Map.of("id", savedRecipe.getId()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = getById(id);

        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = getById(id);

        if (recipe.isPresent()) {
            recipeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipe = getById(id);

        if (optionalRecipe.isPresent()) {
            recipe.setId(id);
            recipeService.save(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Optional<Recipe> getById(Long id) {
        return recipeService.findById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Recipe>> searchRecipes(@RequestParam(required = false) String category,
                                                          @RequestParam(required = false) String name) {
        if (category != null && name == null) {
            return new ResponseEntity<>(recipeService.findByCategory(category), HttpStatus.OK);

        } else if (name != null && category == null) {
            return new ResponseEntity<>(recipeService.findByName(name), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}