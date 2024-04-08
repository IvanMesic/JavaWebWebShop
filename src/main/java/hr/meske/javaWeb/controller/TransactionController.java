package hr.meske.javaWeb.controller;


import hr.meske.javaWeb.model.CartItem;
import hr.meske.javaWeb.model.Item;
import hr.meske.javaWeb.model.Transaction;
import hr.meske.javaWeb.model.TransactionItem;
import hr.meske.javaWeb.services.ItemService;
import hr.meske.javaWeb.services.TransactionItemService;
import hr.meske.javaWeb.services.TransactionService;
import hr.meske.javaWeb.services.UserDetailService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@EnableWebSecurity
@RequestMapping("/transactionController")
@Controller
public class TransactionController {


    private final TransactionService transactionService;
    private final ItemService itemService;
    private final UserDetailService userServices;
    private final TransactionItemService transactionItemService;

    public TransactionController(TransactionService transactionService, ItemService itemService, UserDetailService userServices, TransactionItemService transactionItemService) {
        this.transactionService = transactionService;
        this.itemService = itemService;
        this.userServices = userServices;
        this.transactionItemService = transactionItemService;
    }

    @GetMapping("/getTransactions")
    public String getAllItems(Model model) {

        long id = userServices.GetUsersIdByName();

        if (UserDetailService.isAdmin()) {
            model.addAttribute("items", transactionService.getAllTransactions());
        } else {
            model.addAttribute("items", transactionService.getTransactionsByUserId(id));
        }

        return "transactions";
    }

    @GetMapping("/search")
    public String searchTransactions(@RequestParam(required = false) String user,
                                     @RequestParam(required = false) LocalDate date,
                                     Model model) {
        long id;

        //if date and name are  null and user not admin, get all transactions for that user
        //if date and name are null and user is admin, get ALL transactions
        //if name is null and date is not null and user is admin, get all transactions for that date
        //if name is not null and date is null and user is admin, get all transactions for that user
        //if name is
        //if any field null and not Admin, get username print for him
        //
        List<Transaction> transactions = null;
        if ((user == null  || user == "" || date == null) && !UserDetailService.isAdmin()) {

            id = userServices.GetUsersIdByName();
            transactions = transactionService.searchByUserIdAndPurchaseDate(id, date);
        }
        else if ((user == null || user == "") && UserDetailService.isAdmin() && (date != null)) {
            transactions = transactionService.getAllTransactionsByDate(date);
        }
        else if ((user != null || user != "") && UserDetailService.isAdmin() && (date != null))
        {
            id = userServices.GetUserIdByProvidedName(user) ;
            transactions = transactionService.searchByUserIdAndPurchaseDate(id, date);
        }
        else if (date == null && UserDetailService.isAdmin())
        {
            id = userServices.GetUserIdByProvidedName(user) ;
            transactions = transactionService.getTransactionsByUserId(id);
        }

        model.addAttribute("items", transactions);
        return "transactions";
    }


    @PostMapping("/add")
    public String addTransaction(@RequestBody List<CartItem> items) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Transaction transaction = new Transaction();


        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        for (CartItem cartItem : items) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setTransaction(savedTransaction); // Directly set the savedTransaction object.
            Item item = new Item();
            item.setId(cartItem.getItemId()); // Assume you have an Item object that needs to be associated with the transactionItem.
            transactionItem.setItem(item); // Set the item.
            transactionItem.setQuantity(cartItem.getQuantity());

            transactionItemService.saveTransactionItem(transactionItem);
        }

        return "redirect:/itemController/items";
    }


}
