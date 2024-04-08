package hr.meske.javaWeb.repo;

import hr.meske.javaWeb.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface  TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndPurchaseDate(long userId, Timestamp purchaseDate);

    List<Transaction> findByUserIdAndPurchaseDateBetween(long userId, Timestamp startTimestamp, Timestamp endTimestamp);

    List<Transaction> findByPurchaseDateBetween(Timestamp startTimestamp, Timestamp endTimestamp);
}
