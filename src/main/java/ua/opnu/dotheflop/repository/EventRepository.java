package ua.opnu.dotheflop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.dotheflop.model.Event;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findTopByDeviceIdOrderByCreatedAtDesc(String deviceId);
}