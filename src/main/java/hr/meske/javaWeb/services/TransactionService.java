package hr.meske.javaWeb.services;

import hr.meske.javaWeb.model.Transaction;
import hr.meske.javaWeb.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }


    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }

    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }


    public List<Transaction> searchByUserIdAndPurchaseDate(long userId, LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        Timestamp startTimestamp = Timestamp.valueOf(startOfDay);

        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        Timestamp endTimestamp = Timestamp.valueOf(endOfDay);

        return transactionRepository.findByUserIdAndPurchaseDateBetween(userId, startTimestamp, endTimestamp);
    }

    public List<Transaction> getAllTransactionsByDate(LocalDate date) {

            LocalDateTime startOfDay = date.atStartOfDay();
            Timestamp startTimestamp = Timestamp.valueOf(startOfDay);

            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            Timestamp endTimestamp = Timestamp.valueOf(endOfDay);

            return transactionRepository.findByPurchaseDateBetween(startTimestamp, endTimestamp);
    }
}
