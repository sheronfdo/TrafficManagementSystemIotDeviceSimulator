package org.jamith.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jamith.Enum.TrafficLightColorStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TrafficLightDomain implements Serializable {
    private Long id;
    private Double latitude;
    private Double longitude;
    private TrafficLightColorStatus trafficLightColorStatus;
}
