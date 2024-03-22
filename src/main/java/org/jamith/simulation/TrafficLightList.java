package org.jamith.simulation;

import org.jamith.DTO.response.TrafficLightResponseDTO;
import org.jamith.domain.TrafficLightDomain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLightList {
    static List<TrafficLightResponseDTO> trafficLightResponseDTOS;
    static Map<Long, TrafficLightDomain> trafficLightResponseSimulatorValues= new HashMap<>();
}
