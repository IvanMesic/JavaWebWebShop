package hr.meske.javaWeb.controller;

import hr.meske.javaWeb.model.Category;
import hr.meske.javaWeb.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@EnableWebSecurity
@RequestMapping("/categoryController")
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute("message", "Category saved successfully!");
        return "redirect:/categoryController/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "editCategory";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        category.setId(id);
        categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
        return "redirect:/categoryController/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {

        String name = categoryService.getCategoryById(id).get().getName();

        categoryService.deleteCategoryById(id);

        redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
        model.addAttribute("name", name + " deleted successfully!");

        return "categoryDeleted";
    }
}

