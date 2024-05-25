package recipes.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import recipes.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCase(category, Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name, Sort.by(Sort.Direction.DESC, "date"));
    }

}
