package org.jamith.DTO.request;

import lombok.Data;
import org.jamith.Enum.TrafficLightColorStatus;

import java.io.Serializable;

@Data
public class UpdateTrafficLightStatusRequestDTO implements Serializable {
    Long id;
    TrafficLightColorStatus trafficLightStatus;
}
