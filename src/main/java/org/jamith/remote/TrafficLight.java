package org.jamith.remote;

import jakarta.ejb.Remote;
import org.jamith.DTO.request.AddTrafficLightRequestDTO;
import org.jamith.DTO.request.UpdateTrafficLightStatusRequestDTO;
import org.jamith.DTO.response.AddTrafficLightResponseDTO;
import org.jamith.DTO.response.GetTrafficLightResponseDTO;
import org.jamith.DTO.response.UpdateTrafficLightStatusResponseDTO;

@Remote
public interface TrafficLight {
    AddTrafficLightResponseDTO addTrafficLight(AddTrafficLightRequestDTO addTrafficLightRequestDTO);
    GetTrafficLightResponseDTO getTrafficLightList();
    UpdateTrafficLightStatusResponseDTO updateTrafficLightStatus(UpdateTrafficLightStatusRequestDTO updateTrafficLightStatusRequestDTO);
}
