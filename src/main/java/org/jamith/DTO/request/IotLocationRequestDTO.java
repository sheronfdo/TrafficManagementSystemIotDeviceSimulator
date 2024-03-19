package org.jamith.DTO.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class IotLocationRequestDTO implements Serializable {
    private Long deviceId;
    private Double vehicleSpeed;
//    private boolean trafficLightStatus;
    private Double latitude;
    private Double longitude;

}
