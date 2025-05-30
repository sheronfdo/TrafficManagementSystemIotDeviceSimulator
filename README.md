# Traffic Management System IoT Device Simulator

## Overview

The **Traffic Management System IoT Device Simulator** is a Java SE application designed to simulate IoT devices and traffic light statuses for the [Traffic Management System](https://github.com/sheronfdo/TrafficManagementSystem). It generates mock data for vehicle locations, speeds, and traffic light states, enabling testing and development of the main traffic management system without requiring physical IoT hardware. The simulator communicates with the main system’s EJB application server using Remote Method Invocation (RMI) and RabbitMQ for asynchronous location updates.

This repository is a companion to the main [TrafficManagementSystem](https://github.com/sheronfdo/TrafficManagementSystem) repository, which contains the core EJB application server, web application, and database components.

## Purpose

The IoT Device Simulator serves the following purposes:

- **Simulate IoT Devices**: Generates mock data for IoT devices, including vehicle locations and speeds.
- **Simulate Traffic Lights**: Provides mock traffic light statuses and locations.
- **Enable Testing**: Allows the Traffic Management System to test traffic flow analysis and route optimization without physical IoT devices.
- **Asynchronous Communication**: Sends location data to the main system via RabbitMQ for real-time processing.

## Functionality

The simulator provides the following features:

- **IoT Device Management**:
    - Register new IoT devices.
    - Retrieve registered IoT devices.
    - Collect and send IoT device location and speed data.
- **Traffic Light Management**:
    - Register new traffic light locations.
    - Retrieve registered traffic light locations.
    - Collect and send traffic light statuses.
- **Communication**:
    - Uses RMI to interact with the EJB application server for device and traffic light management.
    - Uses RabbitMQ (`IotLocationData` queue) to send asynchronous location updates to the `IotLocationListener` in the main system.

## Technologies Used

- **Java SE**: Core programming language for the simulator.
- **RMI**: For communication with the EJB application server.
- **RabbitMQ Client**: For sending location data to the `IotLocationData` queue.
- **Maven**: Build tool for dependency management.

## Setup Instructions

### Prerequisites

- Java 8 or higher
- Maven
- RabbitMQ Server (running on `localhost`)
- Access to the [TrafficManagementSystem](https://github.com/sheronfdo/TrafficManagementSystem) EJB application server (deployed and running)

### Installation

1. **Clone the Repository**:
    
    ```bash
    git clone https://github.com/sheronfdo/TrafficManagementSystemIotDeviceSimulator.git
    cd TrafficManagementSystemIotDeviceSimulator
    ```
    
2. **Configure RabbitMQ**:
    
    - Ensure RabbitMQ is installed and running on `localhost`.
    - Verify the `IotLocationData` queue is accessible (configured in the main system).
3. **Configure RMI**:
    
    - Ensure the EJB application server from the [TrafficManagementSystem](https://github.com/sheronfdo/TrafficManagementSystem) repository is deployed and running.
    - Confirm the JNDI name for the `IotDeviceBean` (e.g., `java:global/TrafficManagementSystem/IotDeviceBean`) is accessible.
4. **Build the Project**:
    
    ```bash
    mvn clean install
    ```
    
5. **Run the Simulator**:
    
    - Execute the main class (e.g., `IotDeviceSimulator`) to start generating mock data:
        
        ```bash
        java -jar target/TrafficManagementSystemIotDeviceSimulator.jar
        ```
        
    - Alternatively, run from your IDE by executing the main class.

## Usage

1. **Start the Simulator**:
    
    - Run the application to begin simulating IoT devices and traffic lights.
    - The simulator will connect to the EJB server via RMI to register devices and traffic lights.
2. **Generate Mock Data**:
    
    - The simulator generates mock vehicle locations and speeds, sending them to the `IotLocationData` RabbitMQ queue.
    - Traffic light statuses (e.g., red, green, yellow) and locations are sent via RMI to the EJB server.
3. **Integration with Main System**:
    
    - The main Traffic Management System processes the simulated data to:
        - Calculate average vehicle speeds.
        - Analyze traffic flow.
        - Generate route attributes for display on the web interface.
    - Ensure the main system’s EJB server and RabbitMQ listener (`IotLocationListener`) are running to receive data.

## Code Example

Below is a sample of how the simulator sends IoT location data to RabbitMQ (based on the main system’s `IotLocationListener` code):

```java
private void updateLocation(IotLocationRequestDTO iotLocationRequestDTO) {
    String QUEUE_NAME = "IotLocationData";
    try {
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        byte[] object = serializeObject(iotLocationRequestDTO);
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, object);
        channel.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private byte[] serializeObject(Object obj) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(bos)) {
        oos.writeObject(obj);
        return bos.toByteArray();
    }
}
```

## Dependencies

- `com.rabbitmq:amqp-client`: For RabbitMQ communication.
- Java SE libraries for RMI and serialization.
- Maven dependencies (check `pom.xml` for details).

## Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request with your changes.

## License

This project is licensed under the MIT License.

## Contact

For questions or feedback, contact [sheronfdo](https://github.com/sheronfdo).
