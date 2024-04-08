package hr.meske.javaWeb.repo;

import hr.meske.javaWeb.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    double findItemPriceByName(String name);

    Long findItemIdByName(String name);

    Iterable<Item> getItemsByCategoryId(Long id);
}
