    package hr.meske.javaWeb.controller;

    import hr.meske.javaWeb.events.AddToCartEvent;
    import hr.meske.javaWeb.model.CartItem;
    import hr.meske.javaWeb.services.CartService;
    import hr.meske.javaWeb.services.UserDetailService;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.context.ApplicationEventPublisher;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;

    @Controller
    @EnableWebSecurity
    @RequestMapping("/cart")
    @SessionAttributes("cart")
    public class CartController {

        private final CartService cartService;
        private final UserDetailService userDetailService;
        private final ApplicationEventPublisher eventPublisher;
        public CartController(CartService cartService, UserDetailService userDetailService, ApplicationEventPublisher eventPublisher) {
            this.cartService = cartService;
            this.userDetailService = userDetailService;
            this.eventPublisher = eventPublisher;
        }

        @GetMapping("/showCart")
        public String showCart(Model model) {
            List<CartItem> cartItems = cartService.listCartItems();
            model.addAttribute("cartItems", cartItems);
            return "cart";
        }




        @PostMapping("/remove")
        public String removeFromCart(@RequestParam("itemId") int itemId,
                                     @RequestParam("quantity") int quantity) {


            cartService.removeFromCart((long)itemId, quantity);
            return "redirect:/cart/showCart";
        }

        @PostMapping("/clear")
        public String clearCart() {
            cartService.clearCart();
            return "redirect:/cart/showCart";
        }

        @GetMapping("/checkout")
        public String checkout(Model model) {

            List<CartItem> cartItems = cartService.listCartItems();
            List<String> productNames = new ArrayList<>();
            int total = 0;
            for (CartItem ci : cartItems) {
                productNames.add(ci.getName());
                total += ci.getPrice() * ci.getQuantity();
            }
            cartService.commitTransaction("cash");




            model.addAttribute("productNames", productNames);
            model.addAttribute("total", total);


            cartService.clearCart();

            return "payed";
        }




    }
