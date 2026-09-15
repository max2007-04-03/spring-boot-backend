package ua.opnu.dotheflop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "timestamp_ms")
    private Long timestampMs;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "severity", nullable = false)
    private String severity;

    @Column(name = "confidence")
    private Integer confidence;

    @Column(name = "message")
    private String message;

    @Column(name = "impact_g")
    private Double impactG;

    @Column(name = "gyro_dps")
    private Double gyroDps;

    @Column(name = "angle_change_deg")
    private Double angleChangeDeg;

    @Column(name = "free_fall_detected")
    private Boolean freeFallDetected;

    @Column(name = "post_impact_stillness")
    private Boolean postImpactStillness;

    @Column(name = "cancelled_by_user")
    private Boolean cancelledByUser;

    @Column(name = "hr_bpm")
    private Integer hrBpm;

    @Column(name = "spo2_percent")
    private Integer spo2Percent;

    @Column(name = "temperature_c")
    private Double temperatureC;
}