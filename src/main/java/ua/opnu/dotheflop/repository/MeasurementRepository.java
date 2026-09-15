package ua.opnu.dotheflop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.dotheflop.model.Measurement;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findTop20ByDeviceIdOrderByCreatedAtDesc(String deviceId);
}