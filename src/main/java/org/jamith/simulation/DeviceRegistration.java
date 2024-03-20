package org.jamith.simulation;

import org.jamith.DTO.request.DeviceRegistrationRequestDTO;
import org.jamith.DTO.request.GetRegisteredDeviceDTO;
import org.jamith.DTO.response.DeviceRegistrationResponseDTO;
import org.jamith.DTO.response.DeviceResponseListDTO;
import org.jamith.remote.IotDevice;
import org.jamith.util.VehicleType;

import java.util.Random;

public class DeviceRegistration {
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    VehicleType[] vehicleTypes = VehicleType.values();

    public void registerNewDevice(long count) {
        IotDevice iotDevice = IotConnection.iotDevice;
        for (int i = 0; i < count; i++) {
            VehicleType vehicleType = vehicleTypes[new Random().nextInt(vehicleTypes.length)];
            String vehicleName = generateRandomString(12);
            DeviceRegistrationRequestDTO deviceRegistrationRequestDTO = new DeviceRegistrationRequestDTO();
            deviceRegistrationRequestDTO.setVehicleName(vehicleName);
            deviceRegistrationRequestDTO.setVehicleType(vehicleType.name());
            DeviceRegistrationResponseDTO deviceRegistrationResponseDTO = iotDevice.registrationIotDevice(deviceRegistrationRequestDTO);
            System.out.println(deviceRegistrationResponseDTO.getDeviceId());
            System.out.println(deviceRegistrationResponseDTO.getDescription());
        }
    }

    public void getRegisteredDevices(Long count){
        GetRegisteredDeviceDTO getRegisteredDeviceDTO = new GetRegisteredDeviceDTO();
        getRegisteredDeviceDTO.setLimit(count);
        IotDevice iotDevice = IotConnection.iotDevice;
        DeviceResponseListDTO deviceResponseListDTO = iotDevice.getRegisteredDeviceList(getRegisteredDeviceDTO);
        DeviceList.deviceResponseDTOS = deviceResponseListDTO.getDeviceResponseDTOS();
        System.out.println(DeviceList.deviceResponseDTOS);
    }


    String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
