package hr.meske.javaWeb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> cart = new ArrayList<>();

}
