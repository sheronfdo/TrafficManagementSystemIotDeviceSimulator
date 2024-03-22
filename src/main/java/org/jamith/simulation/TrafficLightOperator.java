package org.jamith.simulation;

import org.jamith.DTO.request.AddTrafficLightRequestDTO;
import org.jamith.DTO.request.UpdateTrafficLightStatusRequestDTO;
import org.jamith.DTO.response.AddTrafficLightResponseDTO;
import org.jamith.DTO.response.GetTrafficLightResponseDTO;
import org.jamith.DTO.response.TrafficLightResponseDTO;
import org.jamith.DTO.response.UpdateTrafficLightStatusResponseDTO;
import org.jamith.Enum.RequestStatus;
import org.jamith.Enum.TrafficLightColorStatus;
import org.jamith.domain.TrafficLightDomain;
import org.jamith.remote.TrafficLight;

import java.util.HashMap;
import java.util.Map;

public class TrafficLightOperator{



    TrafficLight trafficLight = BeanConnection.trafficLight;
    Map<Long, Thread> trafficLightList = new HashMap<>();

    public void addTrafficLight() {
        AddTrafficLightRequestDTO addTrafficLightRequestDTO = new AddTrafficLightRequestDTO();
        addTrafficLightRequestDTO.setLatitude(6.933420);
        addTrafficLightRequestDTO.setLongitude(79.864310);

        AddTrafficLightResponseDTO addTrafficLightResponseDTO = trafficLight.addTrafficLight(addTrafficLightRequestDTO);
        System.out.println(addTrafficLightResponseDTO);
    }

    public void getTrafficLight() {
        GetTrafficLightResponseDTO getTrafficLightResponseDTO = trafficLight.getTrafficLightList();
        TrafficLightList.trafficLightResponseDTOS = getTrafficLightResponseDTO.getTrafficLightResponseListDTO().getTrafficLightResponseDTOS();
        System.out.println(getTrafficLightResponseDTO);
    }

    public void setTrafficLightStatus() {
        for (TrafficLightResponseDTO trafficLightResponseDTO : TrafficLightList.trafficLightResponseDTOS) {
            trafficLightList.put(trafficLightResponseDTO.getId(), new Thread(new TrafficLightRunner(trafficLightResponseDTO, trafficLight)));
        }

        for (Thread thread : trafficLightList.values()) {
            thread.start();
        }

        for (Thread thread : trafficLightList.values()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}

class TrafficLightRunner implements Runnable {
    TrafficLightResponseDTO trafficLightResponseDTO;
    TrafficLight trafficLight;
    TrafficLightColorStatus trafficLightColorStatus = TrafficLightColorStatus.GREEN;

    public TrafficLightRunner(TrafficLightResponseDTO trafficLightResponseDTO, TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
        this.trafficLightResponseDTO = trafficLightResponseDTO;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Traffic Light List Updating");
            if (trafficLightColorStatus.equals(TrafficLightColorStatus.GREEN)) {
                UpdateTrafficLightStatusRequestDTO updateTrafficLightStatusRequestDTO = new UpdateTrafficLightStatusRequestDTO();
                updateTrafficLightStatusRequestDTO.setTrafficLightStatus(TrafficLightColorStatus.RED);
                updateTrafficLightStatusRequestDTO.setId(trafficLightResponseDTO.getId());
                UpdateTrafficLightStatusResponseDTO updateTrafficLightStatusResponseDTO = trafficLight.updateTrafficLightStatus(updateTrafficLightStatusRequestDTO);
                if (updateTrafficLightStatusResponseDTO.getStatus().equals(RequestStatus.success)) {
                    trafficLightColorStatus = TrafficLightColorStatus.RED;
//                    System.out.println(trafficLightResponseDTO);
                    TrafficLightList.trafficLightResponseSimulatorValues.put(trafficLightResponseDTO.getId(),
                            new TrafficLightDomain(trafficLightResponseDTO.getId(), trafficLightResponseDTO.getLatitude(), trafficLightResponseDTO.getLongitude(), trafficLightColorStatus));
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            } else if (trafficLightColorStatus.equals(TrafficLightColorStatus.RED)) {
                UpdateTrafficLightStatusRequestDTO updateTrafficLightStatusRequestDTO = new UpdateTrafficLightStatusRequestDTO();
                updateTrafficLightStatusRequestDTO.setTrafficLightStatus(TrafficLightColorStatus.GREEN);
                updateTrafficLightStatusRequestDTO.setId(trafficLightResponseDTO.getId());
                UpdateTrafficLightStatusResponseDTO updateTrafficLightStatusResponseDTO = trafficLight.updateTrafficLightStatus(updateTrafficLightStatusRequestDTO);
                if (updateTrafficLightStatusResponseDTO.getStatus().equals(RequestStatus.success)) {
                    trafficLightColorStatus = TrafficLightColorStatus.GREEN;
//                    System.out.println(trafficLightResponseDTO);
                    TrafficLightList.trafficLightResponseSimulatorValues.put(trafficLightResponseDTO.getId(),
                            new TrafficLightDomain(trafficLightResponseDTO.getId(), trafficLightResponseDTO.getLatitude(), trafficLightResponseDTO.getLongitude(), trafficLightColorStatus));
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}
