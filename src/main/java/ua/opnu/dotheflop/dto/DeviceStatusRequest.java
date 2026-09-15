package ua.opnu.dotheflop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceStatusRequest {

    @Min(1)
    private long timestampMs;

    private int wifiRssi;

    @Min(0)
    @Max(100)
    private int batteryPercent;

    private boolean charging;

    @NotBlank
    private String mode;

    @NotBlank
    private String firmwareVersion;

    @Min(0)
    private int queuedEvents;

    @Min(0)
    private int freeHeap;
}