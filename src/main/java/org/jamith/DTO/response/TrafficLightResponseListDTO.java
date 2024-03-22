package org.jamith.DTO.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrafficLightResponseListDTO implements Serializable {
    List<TrafficLightResponseDTO> trafficLightResponseDTOS;
}
