package org.jamith;

import org.jamith.DTO.request.DeviceRegistrationRequestDTO;
import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.response.DeviceRegistrationResponseDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;
import org.jamith.remote.IotDevice;

import javax.naming.*;

public class Main {
    public static void main(String[] args) {
        try {
            Context context =  context = new InitialContext();
//
//            NamingEnumeration<NameClassPair> list = context.list("");
//            while (list.hasMore()) {
//                System.out.println(list.next().getName());
//            }


            IotDevice iotDevice = (IotDevice) context.lookup("java:global/TrafficManagementSystem/IotDeviceBean");

            DeviceRegistrationRequestDTO deviceRegistrationRequestDTO = new DeviceRegistrationRequestDTO();
            deviceRegistrationRequestDTO.setVehicleName("alto");
            deviceRegistrationRequestDTO.setVehicleType("car");
            DeviceRegistrationResponseDTO deviceRegistrationResponseDTO = iotDevice.registrationIotDevice(deviceRegistrationRequestDTO);
            System.out.println(deviceRegistrationResponseDTO.getDeviceId());
            System.out.println(deviceRegistrationResponseDTO.getDescription());

            IotLocationRequestDTO iotLocationRequestDTO = new IotLocationRequestDTO();
            iotLocationRequestDTO.setDeviceId(deviceRegistrationResponseDTO.getDeviceId());
            iotLocationRequestDTO.setLatitude(0.0);
            iotLocationRequestDTO.setLongitude(0.0);
            iotLocationRequestDTO.setVehicleSpeed(50.0);
            IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
            System.out.println(iotLocationResponseDTO.getDescription());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
}