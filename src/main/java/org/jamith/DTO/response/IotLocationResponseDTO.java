package org.jamith.DTO.response;


import org.jamith.Enum.RequestStatus;

import java.io.Serializable;

public class IotLocationResponseDTO implements Serializable {
    String description;
    RequestStatus status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
