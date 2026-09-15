package ua.opnu.dotheflop.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class DeviceSummaryResponse {
    private String deviceId;
    private String displayName;
    private boolean online;
    private Instant lastSeen;
    private int batteryPercent;
    private String mode;
    private LatestMeasurementDtl latestMeasurement;
    private LatestEventDtl latestEvent;

    @Data
    public static class LatestMeasurementDtl {
        private int hrBpm;
        private int spo2Percent;
        private double temperatureC;
        private Instant createdAt;
    }

    @Data
    public static class LatestEventDtl {
        private String eventType;
        private String severity;
        private int confidence;
        private Instant createdAt;
    }
}