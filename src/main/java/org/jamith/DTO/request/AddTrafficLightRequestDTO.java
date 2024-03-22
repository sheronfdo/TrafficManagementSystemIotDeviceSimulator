package org.jamith.DTO.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddTrafficLightRequestDTO implements Serializable {
    Double latitude;
    Double longitude;
}
