package org.jamith.remote;


import jakarta.ejb.Remote;
import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;

@Remote
public interface IotDevice {
    IotLocationResponseDTO collectIotDeviceLocation(IotLocationRequestDTO iotLocationRequestDTO);
}
