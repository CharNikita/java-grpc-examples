package ru.goncharenko.examples.server;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        final var grpcServer = new GrpcServer(
            List.of(
                new EchoService()
            )
        );

        grpcServer.start();
        grpcServer.blockAwaitTermination();
    }
}
