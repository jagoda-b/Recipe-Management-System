package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}