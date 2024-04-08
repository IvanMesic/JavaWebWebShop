package hr.meske.javaWeb.events;

import hr.meske.javaWeb.model.CartItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class AddToCartEvent extends ApplicationEvent {
    private CartItem cartItem;

    public AddToCartEvent(Object source, CartItem cartItem) {
        super(source);
        this.cartItem = cartItem;
    }

}
