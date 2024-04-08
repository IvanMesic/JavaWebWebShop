package hr.meske.javaWeb.controller;
import hr.meske.javaWeb.events.AddToCartEvent;
import hr.meske.javaWeb.model.CartItem;
import hr.meske.javaWeb.model.Category;
import hr.meske.javaWeb.model.Item;
import hr.meske.javaWeb.services.CategoryService;
import hr.meske.javaWeb.services.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@EnableWebSecurity
@RequestMapping("/itemController")
@Controller
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, ApplicationEventPublisher eventPublisher) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/items/details/{id}")
    public String getItemDetails(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        if (item == null) {
            return "item-not-found";
        }
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/items")
    public String getAllItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "items";
    }

    @GetMapping("/addItem")
    public String showAddItemForm(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());
        return "addItem";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("categoryId") int category,
                          @RequestParam("price") double price,
                          @RequestParam("url") String url,
                          Model model) {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setUrl(url);
        item.setCategory(categoryService.getCategoryById((long) category).orElse(new Category()));

        itemService.saveItem(item);

        model.addAttribute("message", "Item added successfully");

        return "addItem";
    }


    @PostMapping("/addItemToCart")
    public String addItemToCart(@RequestParam("name") String name,
                                @RequestParam("quantity") Integer quantity,
                                @RequestParam("itemId") long itemId,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        CartItem cartItem = new CartItem();
        cartItem.setItemId(itemId);

        cartItem.setPrice(itemService.getItemById(itemId).getPrice());
        cartItem.setUsername(username);
        cartItem.setName(name);
        cartItem.setQuantity(quantity);

        eventPublisher.publishEvent(new AddToCartEvent(this, cartItem));
        redirectAttributes.addAttribute("message", "Item added to cart successfully");


        return "redirect:/cart/showCart";
    }

    @GetMapping("/edit/{id}")
    public String showEditItemForm(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        return "editItem";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/itemController/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return "redirect:/itemController/items";
    }

}