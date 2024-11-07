package ru.goncharenko.examples.server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder.forPort(8080)
            .addService(new EchoService())
            .build()
            .start()
            .awaitTermination();
    }
}
