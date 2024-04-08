package hr.meske.javaWeb.services;


//create a static service class that will be used to log user events

import hr.meske.javaWeb.model.EventType;
import hr.meske.javaWeb.model.UserLog;
import hr.meske.javaWeb.repo.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserLogService {

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    UserLogService(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

    public void logUserEvent(String name, String ipAddress, EventType eventType) {
        UserLog userLog = new UserLog();
        userLog.setName(name);
        userLog.setIpAddress(ipAddress);
        userLog.setLoginDate(Instant.now());
        userLog.setEventType(eventType);
        userLogRepository.save(userLog);
    }

    public List<UserLog> getAllLogs() {
        return userLogRepository.findAll();
    }
}

