package ua.opnu.dotheflop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class RegisterDeviceRequest {

    @NotBlank
    private String hardwareId;

    @NotBlank
    private String displayName;

    @NotBlank
    private String firmwareVersion;

    @NotEmpty
    private List<String> capabilities;
}