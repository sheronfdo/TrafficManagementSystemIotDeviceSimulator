package org.jamith.DTO.response;


import lombok.Data;
import org.jamith.Enum.RequestStatus;

import java.io.Serializable;

@Data
public class IotLocationResponseDTO implements Serializable {
    String description;
    RequestStatus status;

}
