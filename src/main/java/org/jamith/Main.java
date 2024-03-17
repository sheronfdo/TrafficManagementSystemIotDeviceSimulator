package org.jamith;

import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;
import org.jamith.remote.IotDevice;

import javax.naming.*;

public class Main {
    public static void main(String[] args) {
        try {
            Context context =  context = new InitialContext();
//
//            NamingEnumeration<NameClassPair> list = context.list("IotDeviceBean");
//            while (list.hasMore()) {
//                System.out.println(list.next().getName());
//            }


            IotDevice iotDevice = (IotDevice) context.lookup("java:global/TrafficManagementSystem/IotDeviceBean");
            IotLocationRequestDTO iotLocationRequestDTO = new IotLocationRequestDTO();
            iotLocationRequestDTO.setDeviceId("0");
            iotLocationRequestDTO.setLatitude(0);
            iotLocationRequestDTO.setLongitude(0);
            iotLocationRequestDTO.setVehicleSpeed(50);
            iotLocationRequestDTO.setTrafficLightStatus(true);
            IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
            System.out.println(iotLocationResponseDTO.getDescription());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
}