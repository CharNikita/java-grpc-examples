package ru.goncharenko.examples.client;

import io.grpc.ManagedChannelBuilder;
import ru.goncharenko.examples.EchoRequest;
import ru.goncharenko.examples.EchoServiceGrpc;
import ru.goncharenko.examples.EchoServiceGrpc.EchoServiceBlockingStub;

public class App {
    public static void main(String[] args) {
        final var managedChannel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();
        EchoServiceBlockingStub stub = EchoServiceGrpc.newBlockingStub(managedChannel);
        for (int i = 0; i < 10; i++) {
            final var echoResponse = stub.echo(
                EchoRequest.newBuilder()
                    .setText("Hello world with index - " + i)
                    .build()
            );
            System.out.println(echoResponse.getText());
        }
    }
}
