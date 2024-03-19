package org.jamith.DTO.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceRegistrationRequestDTO implements Serializable {
    private String vehicleName;
    private String vehicleType;
}
