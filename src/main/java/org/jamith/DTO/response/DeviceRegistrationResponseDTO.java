package org.jamith.DTO.response;

import lombok.Data;
import org.jamith.Enum.RequestStatus;

import java.io.Serializable;

@Data
public class DeviceRegistrationResponseDTO implements Serializable {
    Long deviceId;
    String description;
    RequestStatus status;
}
