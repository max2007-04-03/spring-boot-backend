package ua.opnu.dotheflop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.opnu.dotheflop.dto.*;
import ua.opnu.dotheflop.model.*;
import ua.opnu.dotheflop.repository.*;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;
    private final MeasurementRepository measurementRepository;
    private final EventRepository eventRepository;

    public RegisterDeviceResponse register(RegisterDeviceRequest request) {
        Device device = deviceRepository.findByDeviceId(request.getHardwareId())
                .orElse(new Device());

        device.setDeviceId(request.getHardwareId());
        device.setHardwareId(request.getHardwareId());
        device.setDisplayName(request.getDisplayName());
        device.setFirmwareVersion(request.getFirmwareVersion());

        String token = UUID.randomUUID().toString();
        device.setDeviceTokenHash(token);
        device.setLastSeenAt(Instant.now());

        deviceRepository.save(device);

        return new RegisterDeviceResponse(device.getDeviceId(), token, 10, 1);
    }

    public void acceptStatus(String deviceId, String token, DeviceStatusRequest request) {
        Device device = validateAndGetDevice(deviceId, token);

        DeviceStatus status = new DeviceStatus();
        status.setDeviceId(device.getDeviceId());
        status.setTimestampMs(request.getTimestampMs());
        status.setWifiRssi(request.getWifiRssi());
        status.setBatteryPercent(request.getBatteryPercent());
        status.setCharging(request.isCharging());
        status.setMode(request.getMode());
        status.setFirmwareVersion(request.getFirmwareVersion());
        status.setQueuedEvents(request.getQueuedEvents());
        status.setFreeHeap(request.getFreeHeap());

        deviceStatusRepository.save(status);
    }

    public void acceptMeasurement(String deviceId, String token, MeasurementRequest request) {
        Device device = validateAndGetDevice(deviceId, token);

        Measurement m = new Measurement();
        m.setDeviceId(device.getDeviceId());
        m.setTimestampMs(request.getTimestampMs());
        m.setHrBpm(request.getHrBpm());
        m.setSpo2Percent(request.getSpo2Percent());
        m.setTemperatureC(request.getTemperatureC());
        m.setMotionState(request.getMotionState());

        if (request.getQuality() != null) {
            m.setHrValid(request.getQuality().isHrValid());
            m.setSpo2Valid(request.getQuality().isSpo2Valid());
            m.setTempValid(request.getQuality().isTempValid());
        }

        measurementRepository.save(m);
    }

    public EventResponse acceptEvent(String deviceId, String token, EventRequest request) {
        Device device = validateAndGetDevice(deviceId, token);

        Event e = new Event();
        e.setDeviceId(device.getDeviceId());
        e.setTimestampMs(request.getTimestampMs());
        e.setEventType(request.getEventType());
        e.setSeverity(request.getSeverity());
        e.setConfidence(request.getConfidence());
        e.setMessage(request.getMessage());
        e.setPostImpactStillness(request.isPostImpactStillness());
        e.setCancelledByUser(request.isCancelledByUser());

        if (request.getFallDetails() != null) {
            e.setImpactG(request.getFallDetails().getImpactG());
            e.setGyroDps(request.getFallDetails().getGyroDps());
            e.setAngleChangeDeg(request.getFallDetails().getAngleChangeDeg());
            e.setFreeFallDetected(request.getFallDetails().isFreeFallDetected());
        }

        if (request.getLatestVitals() != null) {
            e.setHrBpm(request.getLatestVitals().getHrBpm());
            e.setSpo2Percent(request.getLatestVitals().getSpo2Percent());
            e.setTemperatureC(request.getLatestVitals().getTemperatureC());
        }

        Event savedEvent = eventRepository.save(e);

        return new EventResponse(true, savedEvent.getId(), true);
    }

    public DeviceSummaryResponse getSummary(String deviceId) {
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));

        DeviceSummaryResponse response = new DeviceSummaryResponse();
        response.setDeviceId(device.getDeviceId());
        response.setDisplayName(device.getDisplayName());
        response.setLastSeen(device.getLastSeenAt());

        boolean isOnline = device.getLastSeenAt() != null &&
                Duration.between(device.getLastSeenAt(), Instant.now()).getSeconds() < 30;
        response.setOnline(isOnline);

        java.util.List<Measurement> measurements = measurementRepository.findTop20ByDeviceIdOrderByCreatedAtDesc(deviceId);
        if (!measurements.isEmpty()) {
            Measurement lastM = measurements.get(0);
            DeviceSummaryResponse.LatestMeasurementDtl mDtl = new DeviceSummaryResponse.LatestMeasurementDtl();
            mDtl.setHrBpm(lastM.getHrBpm());
            mDtl.setSpo2Percent(lastM.getSpo2Percent());
            mDtl.setTemperatureC(lastM.getTemperatureC());
            mDtl.setCreatedAt(lastM.getCreatedAt());
            response.setLatestMeasurement(mDtl);
        }

        eventRepository.findTopByDeviceIdOrderByCreatedAtDesc(deviceId).ifPresent(lastE -> {
            DeviceSummaryResponse.LatestEventDtl eDtl = new DeviceSummaryResponse.LatestEventDtl();
            eDtl.setEventType(lastE.getEventType());
            eDtl.setSeverity(lastE.getSeverity());
            eDtl.setConfidence(lastE.getConfidence());
            eDtl.setCreatedAt(lastE.getCreatedAt());
            response.setLatestEvent(eDtl);
        });

        return response;
    }

    private Device validateAndGetDevice(String deviceId, String token) {
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Device not found"));

        if (!device.getDeviceTokenHash().equals(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        device.setLastSeenAt(Instant.now());
        deviceRepository.save(device);
        return device;
    }


}