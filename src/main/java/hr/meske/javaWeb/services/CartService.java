package hr.meske.javaWeb.services;

import hr.meske.javaWeb.model.*;
import hr.meske.javaWeb.repo.ItemRepository;
import hr.meske.javaWeb.repo.TransactionItemRepository;
import hr.meske.javaWeb.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class CartService {


    private final Cart cart;


    @Autowired
    public CartService(Cart cart, UserDetailService userDetailService, ItemRepository itemRepository, TransactionRepository transactionRepository, TransactionItemRepository transactionItemRepository) {
        this.cart = cart;
        this.userDetailService = userDetailService;
        this.itemRepository = itemRepository;
        this.transactionRepository = transactionRepository;
        this.transactionItemRepository = transactionItemRepository;
    }

    private final UserDetailService userDetailService;
    private final ItemRepository itemRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;

    public void addToCart(CartItem cartItem) {

        boolean exists = false;

        for (CartItem ci : cart.getCart()) {
            if (Objects.equals(ci.getItemId(), cartItem.getItemId())) {
                exists = true;
                break;
            }
        }

        if (exists) {
            cart.getCart().forEach(ci -> {
                if (Objects.equals(ci.getItemId(), cartItem.getItemId())) {
                    ci.setQuantity(ci.getQuantity() + cartItem.getQuantity());
                }
            });
        } else {
            cart.getCart().add(cartItem);
        }

    }
    public List<CartItem> listCartItems() {
        return cart.getCart();
    }



    public double getTotalPriceOfItemsInCart() {

        double price = 0;


        for (CartItem cartItem : cart.getCart()) {
            price += cartItem.getPrice() * cartItem.getQuantity();
        }

        return price;
    }

    public String getProductNamesInCsv() {

        String productNames = "";
        for (CartItem cartItem : cart.getCart()) {
            productNames += cartItem.getName() + ", ";
        }

        return productNames;
    }


    public void removeFromCart(long itemId, int quantity) {
        Iterator<CartItem> iterator = cart.getCart().iterator();
        while (iterator.hasNext()) {
            CartItem ci = iterator.next();
            if (ci.getItemId() == itemId) {
                if (ci.getQuantity() > quantity) {
                    ci.setQuantity(ci.getQuantity() - quantity);
                } else {
                    iterator.remove();
                }
            }
        }
    }



    public void clearCart() {
        cart.getCart().clear();
    }


    public void commitTransaction(String type) {

        User user = new User();
        long id = userDetailService.GetUsersIdByName();
        String username = userDetailService.getUserName();
        user.setId(id);
        user.setName(username);


        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setTtype(type);
        transaction.setPurchaseDate(new java.util.Date());
        transaction.setItems(new ArrayList<>());
        transactionRepository.save(transaction);
        for (CartItem cartItem : cart.getCart()) {

            Item item = new Item();

            itemRepository.findById(cartItem.getItemId()).ifPresent(i -> {
                item.setId(i.getId());
                item.setName(i.getName());
                item.setCategory(i.getCategory());
            });

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setTransaction(transaction);


            transactionItem.setItem(item);
            transactionItem.setQuantity(cartItem.getQuantity());
            transaction.getItems().add(transactionItem);

            transactionItemRepository.save(transactionItem);



        }
        clearCart();

    }

}