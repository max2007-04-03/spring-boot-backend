package ua.opnu.dotheflop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "measurements")
@Data
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "timestamp_ms")
    private Long timestampMs;

    @Column(name = "hr_bpm")
    private Integer hrBpm;

    @Column(name = "spo2_percent")
    private Integer spo2Percent;

    @Column(name = "temperature_c")
    private Double temperatureC;

    @Column(name = "motion_state")
    private String motionState;

    @Column(name = "hr_valid")
    private Boolean hrValid;

    @Column(name = "spo2_valid")
    private Boolean spo2Valid;

    @Column(name = "temp_valid")
    private Boolean tempValid;
}