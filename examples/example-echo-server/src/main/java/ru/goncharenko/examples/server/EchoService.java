package ru.goncharenko.examples.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.goncharenko.examples.EchoRequest;
import ru.goncharenko.examples.EchoResponse;
import ru.goncharenko.examples.EchoServiceGrpc.EchoServiceImplBase;

public class EchoService extends EchoServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(EchoService.class);

    @Override
    public void echo(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
        logger.info("Received request: {}", request.getText());
        responseObserver.onNext(
            EchoResponse.newBuilder()
                .setText(request.getText())
                .build()
        );
        responseObserver.onCompleted();
    }
}
