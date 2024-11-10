package ru.goncharenko.examples.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.goncharenko.examples.EchoRequest;
import ru.goncharenko.examples.EchoResponse;
import ru.goncharenko.examples.EchoServiceGrpc.EchoServiceBlockingStub;

public class EchoClient {
    private final static Logger logger = LoggerFactory.getLogger(EchoClient.class);
    private final EchoServiceBlockingStub stub;

    public EchoClient(EchoServiceBlockingStub stub) {
        this.stub = stub;
    }

    public EchoResponse echo(String text) {
        logger.info("Sending request: {}", text);
        return stub.echo(
            EchoRequest.newBuilder()
                .setText(text)
                .build()
        );
    }
}
