package ua.opnu.dotheflop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventResponse {
    private boolean accepted;
    private Long eventId;
    private boolean notificationQueued;
}