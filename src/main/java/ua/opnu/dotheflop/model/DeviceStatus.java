package ua.opnu.dotheflop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "device_statuses")
@Data
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "timestamp_ms")
    private Long timestampMs;

    @Column(name = "wifi_rssi")
    private Integer wifiRssi;

    @Column(name = "battery_percent")
    private Integer batteryPercent;

    @Column(name = "charging")
    private Boolean charging;

    @Column(name = "mode")
    private String mode;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "queued_events")
    private Integer queuedEvents;

    @Column(name = "free_heap")
    private Integer freeHeap;
}