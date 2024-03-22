package org.jamith.simulation;

import org.jamith.remote.IotDevice;
import org.jamith.remote.TrafficLight;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BeanConnection {
    static Context context;
    static IotDevice iotDevice;
    static TrafficLight trafficLight;

    static {
        try {
            context = new InitialContext();
            iotDevice = (IotDevice) context.lookup("java:global/TrafficManagementSystem/IotDeviceBean");
            trafficLight = (TrafficLight) context.lookup("java:global/TrafficManagementSystem/TrafficLightBean");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
