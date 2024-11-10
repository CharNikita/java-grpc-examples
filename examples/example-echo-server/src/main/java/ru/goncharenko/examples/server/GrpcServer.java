package ru.goncharenko.examples.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    private final Server server;

    public GrpcServer(final List<BindableService> services) {
        addShutdownHook();
        this.server = ServerBuilder.forPort(8080)
            .addServices(
                services.stream()
                    .map(BindableService::bindService)
                    .toList()
            )
            .build();
    }

    public void start() throws IOException {
        logger.info("Starting gRPC server");
        server.start();
    }

    public void stop() throws InterruptedException {
        logger.info("Stopping gRPC server");
        if (server != null) {
            logger.info("Awaiting gRPC server shutdown");
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockAwaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
            logger.info("gRPC server terminated");
        }
    }

    private void addShutdownHook() {
        logger.info("Adding shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
            }
            System.out.println("Server shut down");
        }));
    }
}
