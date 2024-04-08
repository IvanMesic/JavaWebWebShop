package hr.meske.javaWeb.events;

import hr.meske.javaWeb.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddToCartListener {

    private final CartService cartService;

    public AddToCartListener(CartService cartService) {
        this.cartService = cartService;
    }

    @EventListener
    public void onApplicationEvent(AddToCartEvent event) {
        cartService.addToCart(event.getCartItem());
    }
}
