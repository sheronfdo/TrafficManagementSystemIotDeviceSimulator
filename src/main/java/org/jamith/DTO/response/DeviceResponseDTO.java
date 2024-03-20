package org.jamith.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeviceResponseDTO implements Serializable {
    private Long id;
    private String vehicleName;
    private String vehicleType;
}
