package ru.goncharenko.examples.server;

import io.grpc.stub.StreamObserver;
import ru.goncharenko.examples.EchoRequest;
import ru.goncharenko.examples.EchoResponse;
import ru.goncharenko.examples.EchoServiceGrpc.EchoServiceImplBase;

public class EchoService extends EchoServiceImplBase {
    @Override
    public void echo(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
        responseObserver.onNext(
            EchoResponse.newBuilder()
                .setText(request.getText())
                .build()
        );
        responseObserver.onCompleted();
    }
}
