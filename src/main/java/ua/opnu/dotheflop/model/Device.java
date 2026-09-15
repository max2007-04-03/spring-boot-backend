package ua.opnu.dotheflop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "devices")
@Data
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", unique = true, nullable = false)
    private String deviceId;

    @Column(name = "hardware_id", unique = true, nullable = false)
    private String hardwareId;

    @Column(name = "device_token_hash", nullable = false)
    private String deviceTokenHash;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt = Instant.now();

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}