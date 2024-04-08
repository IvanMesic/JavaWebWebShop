package hr.meske.javaWeb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    private long itemId;
    private String name;
    private String username;
    private int quantity;
    private double price;

}
