package org.jamith.simulation;

import org.jamith.DTO.response.DeviceResponseDTO;

import java.util.HashMap;
import java.util.Random;

public class DeviceRoute {

    private HashMap<Long, DeviceResponseDTO> currentDeviceList;

    public void routeSimulate() {
//        long deviceRandomNumber = new Random().nextInt(DeviceList.deviceResponseDTOS.size());
//        currentDeviceList.put(deviceRandomNumber, DeviceList.deviceResponseDTOS.get((int) deviceRandomNumber));

        int directionRandomNumber = new Random().nextInt(2);
        int startLocationRandomNumber = new Random().nextInt(RouteAttribute.attributes.length);

        double startLocation[] = RouteAttribute.attributes[startLocationRandomNumber];
        if(directionRandomNumber == 1){
            for (int i = startLocationRandomNumber; i < RouteAttribute.attributes.length - startLocationRandomNumber; i++) {
                System.out.println(RouteAttribute.attributes[i][0]+" "+RouteAttribute.attributes[i][1]);
            }
        }else if(directionRandomNumber == 2){
            for (int i = startLocationRandomNumber; i < RouteAttribute.attributes.length - startLocationRandomNumber; i--) {
                System.out.println(RouteAttribute.attributes[i][0]+" "+RouteAttribute.attributes[i][1]);
            }
        }
    }


}
