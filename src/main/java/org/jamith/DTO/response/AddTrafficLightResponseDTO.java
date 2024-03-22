package org.jamith.DTO.response;

import lombok.Data;
import org.jamith.Enum.RequestStatus;

import java.io.Serializable;

@Data
public class AddTrafficLightResponseDTO implements Serializable {
    Long trafficLightId;
    String description;
    RequestStatus status;
}
