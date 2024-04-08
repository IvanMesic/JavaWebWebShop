package hr.meske.javaWeb.repo;

import hr.meske.javaWeb.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
