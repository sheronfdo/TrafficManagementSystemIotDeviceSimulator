package org.jamith.simulation;

import org.jamith.remote.IotDevice;

import javax.naming.Context;
import javax.naming.InitialContext;

public class IotConnection {
    static Context context;
    static IotDevice iotDevice;

    static {
        try {
            context = new InitialContext();
            iotDevice = (IotDevice) context.lookup("java:global/TrafficManagementSystem/IotDeviceBean");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
