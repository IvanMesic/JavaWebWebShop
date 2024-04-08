package hr.meske.javaWeb.services;

import hr.meske.javaWeb.model.TransactionItem;
import hr.meske.javaWeb.repo.TransactionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionItemService {

    private final TransactionItemRepository transactionItemRepository;

    @Autowired
    public TransactionItemService(TransactionItemRepository transactionItemRepository) {
        this.transactionItemRepository = transactionItemRepository;
    }

    public List<TransactionItem> getAllTransactionItems() {
        return transactionItemRepository.findAll();
    }

    public Optional<TransactionItem> getTransactionItemById(Long id) {
        return transactionItemRepository.findById(id);
    }

    public void saveTransactionItem(TransactionItem transactionItem) {
        transactionItemRepository.save(transactionItem);
    }

    public void deleteTransactionItemById(Long id) {
        transactionItemRepository.deleteById(id);
    }
}
