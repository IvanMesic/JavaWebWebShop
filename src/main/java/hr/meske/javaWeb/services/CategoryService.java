package hr.meske.javaWeb.services;

import hr.meske.javaWeb.model.Category;
import hr.meske.javaWeb.repo.CategoryRepository;
import hr.meske.javaWeb.repo.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Getter
@Setter
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public CategoryService(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id)
    {

        itemRepository.getItemsByCategoryId(id).forEach(item -> {
             item.setCategory(categoryRepository.findById((long) 1).orElse(new Category("Default", "Default category for items without category" )));
            itemRepository.save(item);
        });

        categoryRepository.deleteById(id);
    }
}
