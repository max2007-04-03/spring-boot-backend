package ua.opnu.dotheflop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.dotheflop.dto.*;
import ua.opnu.dotheflop.service.DeviceService;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDeviceResponse> register(@Valid @RequestBody RegisterDeviceRequest request) {
        return ResponseEntity.ok(deviceService.register(request));
    }

    @PostMapping("/{deviceId}/status")
    public ResponseEntity<Map<String, Object>> status(
            @PathVariable String deviceId,
            @RequestHeader("X-Device-Token") String token,
            @Valid @RequestBody DeviceStatusRequest request
    ) {
        deviceService.acceptStatus(deviceId, token, request);
        return ResponseEntity.ok(Map.of("accepted", true));
    }

    @PostMapping("/{deviceId}/measurements")
    public ResponseEntity<Map<String, Object>> measurement(
            @PathVariable String deviceId,
            @RequestHeader("X-Device-Token") String token,
            @Valid @RequestBody MeasurementRequest request
    ) {
        deviceService.acceptMeasurement(deviceId, token, request);
        return ResponseEntity.ok(Map.of("accepted", true));
    }

    @PostMapping("/{deviceId}/events")
    public ResponseEntity<EventResponse> event(
            @PathVariable String deviceId,
            @RequestHeader("X-Device-Token") String token,
            @Valid @RequestBody EventRequest request
    ) {
        return ResponseEntity.ok(deviceService.acceptEvent(deviceId, token, request));
    }

    @GetMapping("/{deviceId}/summary")
    public ResponseEntity<DeviceSummaryResponse> getSummary(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getSummary(deviceId));
    }
}