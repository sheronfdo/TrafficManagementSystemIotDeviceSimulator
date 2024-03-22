package org.jamith.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TrafficLightResponseDTO implements Serializable {
    private Long id;
    private Double latitude;
    private Double longitude;

}
