package ua.opnu.dotheflop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.dotheflop.model.DeviceStatus;

@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {
}