package recipes.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import recipes.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCase(String name, Sort sort);
    List<Recipe> findByCategoryIgnoreCase(String category, Sort sort);
}