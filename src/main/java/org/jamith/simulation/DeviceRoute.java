package org.jamith.simulation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.jamith.DTO.request.IotLocationRequestDTO;
import org.jamith.DTO.request.UpdateTrafficLightStatusRequestDTO;
import org.jamith.DTO.response.DeviceResponseDTO;
import org.jamith.DTO.response.IotLocationResponseDTO;
import org.jamith.Enum.TrafficLightColorStatus;
import org.jamith.domain.TrafficLightDomain;
import org.jamith.remote.IotDevice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class DeviceRoute implements Runnable {

    private HashMap<Long, Thread> currentDeviceList = new HashMap<>();

    @Override
    public void run() {
        routeSimulate();
        System.out.println("device simulate started");
    }

    public void routeSimulate() {

        for (DeviceResponseDTO deviceResponseDTO1 : DeviceList.deviceResponseDTOS) {
            currentDeviceList.put(deviceResponseDTO1.getId(), new Thread(new DeviceRouteRunner(deviceResponseDTO1, BeanConnection.iotDevice)));
        }

        System.out.println("DEvice count = " + currentDeviceList.size());

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
    ConnectionFactory connectionFactory;
    Connection connection;


    public DeviceRouteRunner(DeviceResponseDTO deviceResponseDTO, IotDevice iotDevice) {
        this.deviceResponseDTO = deviceResponseDTO;
        this.iotDevice = iotDevice;
        try {
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
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
                for (TrafficLightDomain trafficLightDomain : TrafficLightList.trafficLightResponseSimulatorValues.values()) {
                    System.out.println("Traffic light value Lat = " + trafficLightDomain.getLatitude() + " Lon " + trafficLightDomain.getLongitude());
                    System.out.println("My loc value Lat = " + RouteAttribute.attributes[i][0] + " Lon " + RouteAttribute.attributes[i][1]);
                    if (trafficLightDomain.getLatitude().equals(RouteAttribute.attributes[i][0])
                            && trafficLightDomain.getLongitude().equals(RouteAttribute.attributes[i][1])
                            && trafficLightDomain.getTrafficLightColorStatus().equals(TrafficLightColorStatus.RED)) {
                        System.out.println("on a Traffic Light Red waiting for green");
                        i -= 1;
                    }
                }
                iotLocationRequestDTO.setLatitude(RouteAttribute.attributes[i][0]);
                iotLocationRequestDTO.setLongitude(RouteAttribute.attributes[i][1]);
                iotLocationRequestDTO.setVehicleSpeed(30.0);
                updateLocation(iotLocationRequestDTO);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
//                System.out.println(iotLocationResponseDTO.getDescription());
            }
        } else if (directionRandomNumber == 1) {
            for (int i = startLocationRandomNumber; (i < startLocationRandomNumber + 1) && (i > 0); i--) {
                System.out.println(deviceResponseDTO.toString());
                System.out.println(RouteAttribute.attributes[i][0] + " " + RouteAttribute.attributes[i][1]);
                IotLocationRequestDTO iotLocationRequestDTO = new IotLocationRequestDTO();
                iotLocationRequestDTO.setDeviceId(deviceResponseDTO.getId());
                for (TrafficLightDomain trafficLightDomain : TrafficLightList.trafficLightResponseSimulatorValues.values()) {
                    System.out.println("Traffic light value Lat = " + trafficLightDomain.getLatitude() + " Lon " + trafficLightDomain.getLongitude());
                    System.out.println("My loc value Lat = " + RouteAttribute.attributes[i][0] + " Lon " + RouteAttribute.attributes[i][1]);
                    if (trafficLightDomain.getLatitude().equals(RouteAttribute.attributes[i][0])
                            && trafficLightDomain.getLongitude().equals(RouteAttribute.attributes[i][1])
                            && trafficLightDomain.getTrafficLightColorStatus().equals(TrafficLightColorStatus.RED)) {
                        System.out.println("on a Traffic Light Red waiting for green");
                        i -= 1;
                    }
                }
                iotLocationRequestDTO.setLatitude(RouteAttribute.attributes[i][0]);
                iotLocationRequestDTO.setLongitude(RouteAttribute.attributes[i][1]);
                iotLocationRequestDTO.setVehicleSpeed(25.0);
                updateLocation(iotLocationRequestDTO);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                IotLocationResponseDTO iotLocationResponseDTO = iotDevice.collectIotDeviceLocation(iotLocationRequestDTO);
//                System.out.println(iotLocationResponseDTO.getDescription());
            }
        }
    }

    private void updateLocation(IotLocationRequestDTO iotLocationRequestDTO) {
        String QUEUE_NAME = "IotLocationData";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            byte[] object = serializeObject(iotLocationRequestDTO);
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, object);
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] serializeObject(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }
}
