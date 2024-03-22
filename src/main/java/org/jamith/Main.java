package org.jamith;

import org.jamith.simulation.DeviceRegistration;
import org.jamith.simulation.DeviceRoute;
import org.jamith.simulation.TrafficLightOperator;

public class Main {
    public static void main(String[] args) {
        try {
            DeviceRegistration deviceRegistration = new DeviceRegistration();
//            deviceRegistration.registerNewDevice(100);
            deviceRegistration.getRegisteredDevices(100l);

            Thread deviceThread = new Thread(new DeviceRoute());
            deviceThread.start();


            TrafficLightOperator trafficLightOperator = new TrafficLightOperator();
//            trafficLightOperator.addTrafficLight();
            trafficLightOperator.getTrafficLight();
            trafficLightOperator.setTrafficLightStatus();
            System.out.println("traffic light simulate started");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}