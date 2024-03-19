package org.jamith.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class DeviceResponseListDTO {
    List<DeviceResponseDTO> deviceResponseDTOS;
}
