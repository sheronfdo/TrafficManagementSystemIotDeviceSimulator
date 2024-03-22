package org.jamith;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.jamith.DTO.request.UpdateTrafficLightStatusRequestDTO;
import org.jamith.Enum.TrafficLightColorStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

public class RabbitMqTest {

    static final String EXCHANGE_NAME = "RabbitTestExchange";
    static final String QUEUE_NAME = "RabbitTestQueue";
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true,false,false, null);
        String msge  = "my message";
        UpdateTrafficLightStatusRequestDTO updateTrafficLightStatusRequestDTO = new UpdateTrafficLightStatusRequestDTO();
        updateTrafficLightStatusRequestDTO.setTrafficLightStatus(TrafficLightColorStatus.RED);
        updateTrafficLightStatusRequestDTO.setId(444l);

        byte[] object = serializeObject(updateTrafficLightStatusRequestDTO);

        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, object);
        channel.close();
        connection.close();
    }

    private static byte[] serializeObject(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }
}
