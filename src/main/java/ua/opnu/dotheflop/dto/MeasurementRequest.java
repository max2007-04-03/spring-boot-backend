package ua.opnu.dotheflop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeasurementRequest {

    @Min(1)
    private long timestampMs;

    @Min(0)
    private int hrBpm;

    @Min(0)
    private int spo2Percent;

    private double temperatureC;

    @NotBlank
    private String motionState;

    @NotNull
    private QualityDtl quality;

    @Data
    public static class QualityDtl {
        private boolean hrValid;
        private boolean spo2Valid;
        private boolean tempValid;
    }
}