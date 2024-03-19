package org.jamith.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceResponseDTO {
    private Long id;
    private String vehicleName;
    private String vehicleType;
}
