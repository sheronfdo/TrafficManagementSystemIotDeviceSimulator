package org.jamith.simulation;

import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.response.DeviceResponseDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;
import org.jamith.Enum.TrafficLightColorStatus;
import org.jamith.domain.TrafficLightDomain;
import org.jamith.remote.IotDevice;

import java.util.HashMap;
import java.util.Random;

public class DeviceRoute implements Runnable {

    private HashMap<Long, Thread> currentDeviceList = new HashMap<>();

    @Override
    public void run() {
        routeSimulate();
        System.out.println("device simulate started");
    }

    public void routeSimulate()  {

        for (DeviceResponseDTO deviceResponseDTO1 : DeviceList.deviceResponseDTOS) {
            currentDeviceList.put(deviceResponseDTO1.getId(), new Thread(new DeviceRouteRunner(deviceResponseDTO1, BeanConnection.iotDevice)));
        }

        System.out.println("DEvice count = "+currentDeviceList.size());

        for (Thread thread : currentDeviceList.values()) {
            thread.start();
        }

        for (Thread thread : currentDeviceList.values()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}

class DeviceRouteRunner implements Runnable {
    DeviceResponseDTO deviceResponseDTO;
    IotDevice iotDevice;

    public DeviceRouteRunner(DeviceResponseDTO deviceResponseDTO, IotDevice iotDevice) {
        this.deviceResponseDTO = deviceResponseDTO;
        this.iotDevice = iotDevice;
    }

    @Override
    public void run() {
        int directionRandomNumber = new Random().nextInt(1);
        int startLocationRandomNumber = new Random().nextInt(RouteAttribute.attributes.length);
        System.out.println(deviceResponseDTO.toString());
        if (directionRandomNumber == 0) {
            for (int i = startLocationRandomNumber; (i > startLocationRandomNumber - 1) && (i < RouteAttribute.attributes.length); i++) {
                System.out.println(deviceResponseDTO.toString());
                System.out.println(RouteAttribute.attributes[i][0] + " " + RouteAttribute.attributes[i][1]);
                IotLocationRequestDTO iotLocationRequestDTO = new IotLocationRequestDTO();
                iotLocationRequestDTO.setDeviceId(deviceResponseDTO.getId());
                for(TrafficLightDomain trafficLightDomain :TrafficLightList.trafficLightResponseSimulatorValues.values()){
                    System.out.println("Traffic light value Lat = "+trafficLightDomain.getLatitude()+" Lon "+trafficLightDomain.getLongitude());
                    System.out.println("My loc value Lat = "+RouteAttribute.attributes[i][0]+" Lon "+RouteAttribute.attributes[i][1]);
                    if(trafficLightDomain.getLatitude().equals(RouteAttribute.attributes[i][0])
                            && trafficLightDomain.getLongitude().equals(RouteAttribute.attributes[i][1])
                            && trafficLightDomain.getTrafficLightColorStatus().equals(TrafficLightColorStatus.RED)){
                        System.out.println("on a Traffic Light Red waiting for green");
                        i-=1;
                    }
                }
                iotLocationRequestDTO.setLatitude(RouteAttribute.attributes[i][0]);
                iotLocationRequestDTO.setLongitude(RouteAttribute.attributes[i][1]);
                iotLocationRequestDTO.setVehicleSpeed(20.0);
                IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
                System.out.println(iotLocationResponseDTO.getDescription());
            }
        } else if (directionRandomNumber == 1) {
            for (int i = startLocationRandomNumber; (i < startLocationRandomNumber + 1) && (i > 0); i--) {
                System.out.println(deviceResponseDTO.toString());
                System.out.println(RouteAttribute.attributes[i][0] + " " + RouteAttribute.attributes[i][1]);
                IotLocationRequestDTO iotLocationRequestDTO = new IotLocationRequestDTO();
                iotLocationRequestDTO.setDeviceId(deviceResponseDTO.getId());
                for(TrafficLightDomain trafficLightDomain :TrafficLightList.trafficLightResponseSimulatorValues.values()){
                    System.out.println("Traffic light value Lat = "+trafficLightDomain.getLatitude()+" Lon "+trafficLightDomain.getLongitude());
                    System.out.println("My loc value Lat = "+RouteAttribute.attributes[i][0]+" Lon "+RouteAttribute.attributes[i][1]);
                    if(trafficLightDomain.getLatitude().equals(RouteAttribute.attributes[i][0])
                            && trafficLightDomain.getLongitude().equals(RouteAttribute.attributes[i][1])
                            && trafficLightDomain.getTrafficLightColorStatus().equals(TrafficLightColorStatus.RED)){
                        System.out.println("on a Traffic Light Red waiting for green");
                        i-=1;
                    }
                }
                iotLocationRequestDTO.setLatitude(RouteAttribute.attributes[i][0]);
                iotLocationRequestDTO.setLongitude(RouteAttribute.attributes[i][1]);
                iotLocationRequestDTO.setVehicleSpeed(5.0);
                IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
                System.out.println(iotLocationResponseDTO.getDescription());
            }
        }
    }
}
