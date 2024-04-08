package hr.meske.javaWeb.repo;

import hr.meske.javaWeb.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
 void deleteTransactionItemsByItemId(Long itemId);

    List<TransactionItem> findByItemId(Long ItemId);
}
