package org.jamith.DTO.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetRegisteredDeviceDTO implements Serializable {
    Long limit;
}
