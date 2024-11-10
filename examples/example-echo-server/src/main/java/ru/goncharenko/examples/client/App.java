package ru.goncharenko.examples.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.goncharenko.examples.EchoServiceGrpc;
import ru.goncharenko.examples.EchoServiceGrpc.EchoServiceBlockingStub;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        EchoServiceBlockingStub stub = EchoServiceGrpc.newBlockingStub(
            ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build()
        );

        final var echoClient = new EchoClient(stub);
        for (int i = 0; i < 10; i++) {
            final var echoResponse = echoClient.echo("Hello world " + i);
            logger.info("Received response: {}", echoResponse.getText());
        }
    }
}
