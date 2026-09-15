package ua.opnu.dotheflop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterDeviceResponse {
    private String deviceId;
    private String deviceToken;
    private int uploadIntervalSec;
    private int configVersion;
}