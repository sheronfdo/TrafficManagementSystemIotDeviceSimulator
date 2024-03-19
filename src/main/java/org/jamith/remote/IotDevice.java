package org.jamith.remote;


import jakarta.ejb.Remote;
import org.jamith.DTO.request.DeviceRegistrationRequestDTO;
import org.jamith.DTO.request.GetRegisteredDeviceDTO;
import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.response.DeviceRegistrationResponseDTO;
import org.jamith.DTO.response.DeviceResponseListDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;

@Remote
public interface IotDevice {
    IotLocationResponseDTO collectIotDeviceLocation(IotLocationRequestDTO iotLocationRequestDTO);
    DeviceRegistrationResponseDTO registrationIotDevice(DeviceRegistrationRequestDTO deviceRegistrationRequestDTO);
    DeviceResponseListDTO getRegisteredDeviceList(GetRegisteredDeviceDTO getRegisteredDeviceListDTO);
}
