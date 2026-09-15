package ua.opnu.dotheflop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventRequest {

    @NotBlank
    private String eventType;

    @Min(1)
    private long timestampMs;

    @NotBlank
    private String severity;

    private int confidence;

    private String message;

    @Valid
    @NotNull
    private FallDetailsDtl fallDetails;

    private boolean postImpactStillness;

    private boolean cancelledByUser;

    private LatestVitalsDtl latestVitals;

    @Data
    public static class FallDetailsDtl {
        private double impactG;
        private double gyroDps;
        private double angleChangeDeg;
        private boolean freeFallDetected;
    }

    @Data
    public static class LatestVitalsDtl {
        @Min(0)
        private int hrBpm;

        @Min(0)
        private int spo2Percent;

        private double temperatureC;
    }
}