package org.jamith.DTO.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class IotLocationRequestDTO implements Serializable {
    private String deviceId;
    private int vehicleSpeed;
    private boolean trafficLightStatus;
    private double latitude;
    private double longitude;

}
