package hr.meske.javaWeb.services;

import hr.meske.javaWeb.exceptions.ItemNotFoundException;
import hr.meske.javaWeb.model.Item;
import hr.meske.javaWeb.model.TransactionItem;
import hr.meske.javaWeb.repo.ItemRepository;
import hr.meske.javaWeb.repo.TransactionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final TransactionItemRepository transactionItemRepository;

   // private final ItemCategoryRepository itemCategoryRepository;



    @Autowired
    public ItemService(ItemRepository itemRepository, TransactionItemRepository transactionItemRepository) {
        this.itemRepository = itemRepository;
        this.transactionItemRepository = transactionItemRepository;
       // this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found."));
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    public double getItemPrice(String name) {
        return itemRepository.findItemPriceByName(name);
    }





    @Transactional
    public void deleteItemById(Long itemId) {

        List<TransactionItem> transactionItems = transactionItemRepository.findByItemId(itemId);

        transactionItemRepository.deleteAll(transactionItems);

        //List<ItemCategory> itemCategories = itemCategoryRepository.findByItemId(itemId);
        //itemCategoryRepository.deleteAll(itemCategories);

        itemRepository.deleteById(itemId);
    }

    public Long getItemIdByName(String name) {

        return itemRepository.findItemIdByName(name);
    }
}
